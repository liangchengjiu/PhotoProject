package com.dawson.provider.service.biz.mq;

import com.alibaba.fastjson.JSON;
import com.dawson.common.exception.MicroserviceException;
import com.dawson.common.utils.ConstantUtils;
import com.dawson.provider.service.biz.entity.BizLog;
import com.dawson.provider.service.biz.mapper.BizLogMapper;
import com.dawson.provider.service.biz.service.BizLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-17 09:47
 **/
@Slf4j
@Component
public class RetryLogReceiver {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BizLogService bizLogService;

    @Autowired
    private BizLogMapper bizLogMapper;

    @RabbitListener(queues = {ConstantUtils.BIZ_ERROR_LOG_QUEUES})
    public void process(Message message) {
        try {
            BizLog bizLog = objectMapper.readValue(message.getBody(), BizLog.class);
            log.info("=======bizLogMQ=======:{}", JSON.toJSONString(bizLog));
            bizLogMapper.insert(bizLog);
        } catch (Exception e) {
            log.error("======bizLogMQ=======->error:{}", e.getMessage(), e);
        }

    }

}
