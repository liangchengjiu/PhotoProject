package com.dawson.common.retry;

import com.dawson.common.enums.BizLogStatus;
import com.dawson.common.exception.MicroserviceException;
import com.dawson.common.retry.expression.context.RetryLogErrorEntity;
import com.dawson.common.retry.service.BizLogApi;
import com.dawson.common.retry.service.RetryLogHandler;
import com.dawson.common.returnBean.Result;
import com.dawson.common.vo.BizLogVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 供自定重试的一些方法
 */
public abstract class RetryLogHandlerAdapter implements RetryLogHandler {

    protected Logger log = LogManager.getLogger(getClass());

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired(required = false)
    protected BizLogApi bizLogApi;

    @Override
    @SneakyThrows
    public Result doRetry(BizLogVo entity) {
        RetryLogErrorEntity bizContext = null;
        try {
            bizContext = objectMapper.readValue(entity.getParamContent(), RetryLogErrorEntity.class);
        } catch (Exception e) {

        }
        return doRetry(bizContext, entity);
    }

    /***
     * 只需要继承这个的服务就可以自定重试服务了。。。
     * */
    protected Result doRetry(RetryLogErrorEntity errorEntity, BizLogVo entity) {
        throw new UnsupportedOperationException("不支持的行为");
    }

    /**
     * 静默的更新为成功
     */
    protected void updateToSuccessSilent(BizLogVo entity) {
        try {
            entity.setStatus(BizLogStatus.REDO_SUCCESS.getValue());
            BizLogVo bizLogVo2 = new BizLogVo();
            BeanUtils.copyProperties(entity, bizLogVo2);
            int i = bizLogApi.updateBizLog(bizLogVo2);
            if (i != 1) {
                throw new MicroserviceException("重试更新的状态失败,返回更新的个数不为1");
            }
        } catch (Exception e) {
            log.error("更新重试日志为成功失败", e);
        }
    }
}
