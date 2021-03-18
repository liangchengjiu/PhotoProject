package com.dawson.common.retry;

import com.dawson.common.enums.BizLogStatus;
import com.dawson.common.exception.MicroserviceException;
import com.dawson.common.retry.annotation.RetryLog;
import com.dawson.common.retry.expression.context.RetryLogErrorEntity;
import com.dawson.common.retry.expression.utils.RetryLogExpressionUtils;
import com.dawson.common.retry.meta.RetryLogMeta;
import com.dawson.common.retry.meta.RetryLogMetaInfoHolder;
import com.dawson.common.retry.service.BizLogApi;
import com.dawson.common.retry.service.RetryLogHandler;
import com.dawson.common.returnBean.Result;
import com.dawson.common.vo.BizLogVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/***
 * 默认情况下使用的重试方法，该方法会直接调用原来的方法重试,
 * 该实现会在每一个中心作为一份dubbo的提供者，用于对外的重试的接口服务。
 * 该实现承接内部的其他的自定义服务，如果有自定义的服务，则会注入在该实现中。
 * */
@Log4j2
public class RetryLogDefaultLogHandler implements RetryLogHandler, InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;


    @Autowired
    protected ObjectMapper objectMapper;

    private Map<? extends Class<?>, RetryLogHandler> mapToHandlers;


    @Autowired(required = false)
    private BizLogApi bizLogApi;


    @Override
    public Class<?> supportClass() {
        return null;
    }

    @Override
    public Result doRetry(BizLogVo entity) {
        try {
            //标识当前的执行的流程为重试流程
            RetryLogMetaInfoHolder.setRedoFlagTrue();
            String paramContent = entity.getParamContent();
            Class aClass = executeClass(entity.getParamContent());
            RetryLogHandler retryLogHandler = mapToHandlers.get(aClass);
            //自定义的方法
            if (retryLogHandler != null) {
                return retryLogHandler.doRetry(entity);
            }
            //否则走统一的重试方法

            RetryLogErrorEntity bizContext = objectMapper.readValue(paramContent, RetryLogErrorEntity.class);
            if (bizContext.getOnlyLog()) {
                return Result.error(Result.ERROR_CODE, "只打印log,不允许重试");
            }
            //当时执行的class，非proxy bean
            Class executeType = bizContext.getExecuteType();
            //获取spring bean name
            String beanName = RetryLogMetaInfoHolder.getBeanNameByClass(executeType);
            //spring 在诸如transaction 时候会使用代理的方式生成bean.此时获取的bean代理类。
            //当时执行的method，非proxy method
            Method realMethod = executeType.getMethod(bizContext.getMethodName(), bizContext.getArgTyps());
            RetryLogMeta metaInfo = RetryLogMetaInfoHolder.getBizLogByMethod(realMethod);
            //获取执行的对象
            Object bean = applicationContext.getBean(beanName);
            Method proxyMethod = bean.getClass().getMethod(bizContext.getMethodName(), bizContext.getArgTyps());
            Class<?>[] parameterTypes = proxyMethod.getParameterTypes();
            Object[] args = bizContext.getArgs();
            //根据正确的类型的重新序列化参数。。
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                Class<?> type = parameterTypes[i];
                args[i] = objectMapper.readValue(objectMapper.writeValueAsString(arg), type);
            }

            proxyMethod.setAccessible(true);
            Object result = proxyMethod.invoke(bean, args);
            RetryLog shgtTraceLog = metaInfo.getRetryLog();
            String jsonResult = objectMapper.writeValueAsString(result);
            log.info("重试结果是" + jsonResult);
            //如果还有表达判断成功失败结果，则根据spel重新计算成功失败标记
            if (!StringUtils.isBlank(shgtTraceLog.successFlag())) {
                EvaluationContext evaluationContext = RetryLogExpressionUtils.getEvaluator().createEvaluationContext(proxyMethod, args, bean, result);
                AnnotatedElementKey elementKey = new AnnotatedElementKey(proxyMethod, bean.getClass());
                if (RetryLogExpressionUtils.determineSuccess(result, elementKey, shgtTraceLog, evaluationContext)) {
                    return Result.error(Result.ERROR_CODE, String.format("重试失败，结果是%s", jsonResult));
                }
            }
            processBizLogStatus(entity);
            return Result.success(String.format("重试成功，结果是%s", jsonResult));
        } catch (Exception e) {
            log.error("重试报错", e);
            return Result.error(Result.ERROR_CODE, e.getMessage());
        } finally {
            RetryLogMetaInfoHolder.setRedoFlagFalse();
        }
    }

    protected void processBizLogStatus(BizLogVo entity) throws MicroserviceException {
        entity.setStatus(BizLogStatus.REDO_SUCCESS.getValue());
        BizLogVo bizLogVo2 = new BizLogVo();
        BeanUtils.copyProperties(entity, bizLogVo2);
        int i = bizLogApi.updateBizLog(bizLogVo2);
        if (i != 1) {
            throw new MicroserviceException("重试更新的状态失败");
        }
    }


    private Class executeClass(String params) throws MicroserviceException, IOException, ClassNotFoundException {
        String executeType = objectMapper.readTree(params).get("executeType").asText("");
        if (StringUtils.isBlank(executeType)) {
            throw new MicroserviceException("自定定义的参数的中必须携带excuteType作为辨别自定义重试功能的标记识别");
        }
        return Class.forName(executeType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, RetryLogHandler> beansOfType = this.applicationContext.getBeansOfType(RetryLogHandler.class);
        if (beansOfType == null) {
            this.mapToHandlers = Collections.emptyMap();
        } else {
            this.mapToHandlers = beansOfType
                    .values()
                    .stream()
                    .filter(x -> x.supportClass() != null)
                    .collect(Collectors.toMap(k -> k.supportClass(), Function.identity(), (v1, v2) -> {
                        throw new UnsupportedOperationException("不支持同一个重试的类" + "v1:" + v1.getClass() + "v2:" + v2.getClass());
                    }));
        }
        log.info("重试自定义方法加载以下自定义重试方法:\n" + String.join("\n",
                mapToHandlers.entrySet()
                        .stream().map(x -> x.getKey() + "->" + x.getValue().getClass())
                        .collect(Collectors.toList())));
    }
}
