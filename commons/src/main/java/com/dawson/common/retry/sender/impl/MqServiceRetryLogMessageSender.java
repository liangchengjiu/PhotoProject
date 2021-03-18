package com.dawson.common.retry.sender.impl;

import com.dawson.common.rabbitmq.service.MQService;
import com.dawson.common.retry.config.RetryLogProperties;
import com.dawson.common.retry.sender.RetryLogMessageSender;
import com.dawson.common.vo.BizLogVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

/**
 * 使用MqService 发送 mq 消息
 */
@Log4j2
public class MqServiceRetryLogMessageSender implements RetryLogMessageSender{

    private RetryLogProperties.Mq mqProperties;

    private MQService mqService;

    private ObjectMapper objectMapper;

    public void send(BizLogVo vo) {
        try {
            String jsonStr = objectMapper.writeValueAsString(vo);
            log.info("使用mqService发送到跟踪库，exchange:" + mqProperties.getExchange() + ",routingKey:" + mqProperties.getRoutingKey() + ",data:" + jsonStr);
            mqService.sendToMQ(mqProperties.getExchange(), mqProperties.getRoutingKey(), jsonStr);
        } catch (Exception e) {
            log.error("发送消息到跟踪库失败", e);
        }
    }

    public RetryLogProperties.Mq getMqProperties() {
        return mqProperties;
    }

    public void setMqProperties(RetryLogProperties.Mq mqProperties) {
        this.mqProperties = mqProperties;
    }

    public MQService getMqService() {
        return mqService;
    }

    public void setMqService(MQService mqService) {
        this.mqService = mqService;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
