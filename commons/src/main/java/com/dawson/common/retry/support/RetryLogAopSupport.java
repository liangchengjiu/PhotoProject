package com.dawson.common.retry.support;

import com.dawson.common.enums.BizLogStatus;
import com.dawson.common.retry.annotation.RetryLog;
import com.dawson.common.retry.config.RetryLogProperties;
import com.dawson.common.retry.expression.context.RetryLogErrorEntity;
import com.dawson.common.retry.expression.utils.RetryLogExpressionUtils;
import com.dawson.common.retry.meta.RetryLogMetaInfoHolder;
import com.dawson.common.retry.sender.RetryLogMessageSender;
import com.dawson.common.utils.ConstantUtils;
import com.dawson.common.utils.ThrowableUtil;
import com.dawson.common.vo.BizLogVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * 使用Aop 技代理以RetryLog目标方法。
 * 主要功能是，探测目标方法执行的时间，结果，然后将失败的结果同sender 发送的到跟踪库中
 */
@Aspect
@Slf4j
public class RetryLogAopSupport {

    private RetryLogProperties.Mq mqProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired(required = false)
    private RetryLogMessageSender messageSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String systemType;

    private String commonSystem;

    private String defaultOperator;

    @Around("@annotation(com.dawson.common.retry.annotation.RetryLog)")
    public Object aroundRetryLogMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取开始执行的时间戳
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            processResult(joinPoint, start, result);
            return result;
        } catch (Throwable e) {
            processResult(joinPoint, start, e);
            throw e;
        }
    }

    /**
     * 处理结果
     */
    private void processResult(ProceedingJoinPoint joinPoint, long start, Object result) {
        try {
            BizLogVo bizLogEntity = this.buildLogDto(start, joinPoint, result);
            if (BizLogStatus.FAIL.getValue().equals(bizLogEntity.getStatus()) && !RetryLogMetaInfoHolder.isRedo()) {
                messageSender.send(bizLogEntity);
            }
        } catch (Exception e) {
            log.error("doBizLogAOP写入日志失败," + e.getMessage(), e);
        }
    }

    /**
     * 获取执行的方法
     */
    private Method acquireMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getMethod().getParameterTypes());
    }

    /**
     * 构建BizLogVo对象
     */
    private BizLogVo buildLogDto(Long startTime, ProceedingJoinPoint joinPoint, Object result) throws NoSuchMethodException, JsonProcessingException {
        BizLogVo logEntity = new BizLogVo();
        Object[] args = joinPoint.getArgs();
        Method method = acquireMethod(joinPoint);
        RetryLog bizLog = method.getAnnotation(RetryLog.class);

        // 构建spring-el 执行的上下文
        EvaluationContext evaluationContext = RetryLogExpressionUtils.getEvaluator().createEvaluationContext(method, args, joinPoint.getTarget(), result);
        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, joinPoint.getTarget().getClass());

        // 构建执行参数，方面后续序列化成json存入paramContent
        RetryLogErrorEntity bizContext = new RetryLogErrorEntity(args, joinPoint.getTarget().getClass(), method, systemType, bizLog.onlyLog());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Long end = System.currentTimeMillis();

        //判断是否成功
        if (RetryLogExpressionUtils.determineSuccess(result, elementKey, bizLog, evaluationContext)) {
            logEntity.setStatus(BizLogStatus.SUCCESS.getValue());
        } else {
            logEntity.setStatus(BizLogStatus.FAIL.getValue());
        }

        //相关单据号
        logEntity.setBizCode(RetryLogExpressionUtils.generateBizCode(bizLog, evaluationContext, elementKey));
        //类似主键
        logEntity.setLogCode(bizLog.codePrefix() + System.currentTimeMillis());
        //业务类别
        logEntity.setBizCategory(bizLog.bizCategory());
        //业务类型
        logEntity.setBizType(bizLog.bizType());
        //开始时间
        logEntity.setStartTime(formatter.format(startTime));
        //结束时间
        logEntity.setEndTime(formatter.format(end));
        //耗时
        logEntity.setCostTime(end - startTime);
        //参数，原本只放入参数，而外加入的执行上下文信息。。如 执行的对象类型，执行的方法等。方便后面做重试机制
        logEntity.setParamContent(objectMapper.writeValueAsString(bizContext));
        //系统类别
        logEntity.setLogSystem(commonSystem);

        //用户解析
        if (StringUtils.isNotBlank(bizLog.userExpression())) {
            String user = RetryLogExpressionUtils.getEvaluator().user(bizLog.userExpression(), elementKey, evaluationContext);
            if (StringUtils.isNotBlank(user)) {
                logEntity.setOperator(user);
            }
        }

        String usernameFromAnnotation = bizLog.userName();
        if (StringUtils.isNotBlank(usernameFromAnnotation)) {
            logEntity.setOperator(usernameFromAnnotation);
        }
        if (StringUtils.isBlank(logEntity.getOperator())) {
            logEntity.setOperator(defaultOperator);
        }
        // 用户信息解析结束

        if (result instanceof Exception) {
            logEntity.setExceptionContent(ThrowableUtil.getStackTrace((Exception) result));
        }
        logEntity.setResultContent(RetryLogExpressionUtils.generateResult(result));
        return logEntity;
    }

    public void send(BizLogVo vo) {
        try {
            String jsonStr = objectMapper.writeValueAsString(vo);
            log.info("使用rabbitTemplate发送到跟踪库，exchange:" + ConstantUtils.BIZ_ERROR_LOG_EXCHANGE
                    + ",routingKey:" + ConstantUtils.BIZ_LOG_ROUTING_KEY + "data:" + jsonStr);
            String id = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(id);
            rabbitTemplate.convertAndSend(ConstantUtils.BIZ_ERROR_LOG_EXCHANGE, ConstantUtils.BIZ_LOG_ROUTING_KEY, jsonStr, correlationId);
        } catch (Exception e) {
            log.error("发送消息到跟踪库失败", e);
        }
    }


    /**
     * getter&setter
     **/

    public RetryLogMessageSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(RetryLogMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getCommonSystem() {
        return commonSystem;
    }

    public void setCommonSystem(String commonSystem) {
        this.commonSystem = commonSystem;
    }

    public String getDefaultOperator() {
        return defaultOperator;
    }

    public void setDefaultOperator(String defaultOperator) {
        this.defaultOperator = defaultOperator;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
}
