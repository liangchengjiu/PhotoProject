package com.dawson.common.retry.sender.impl;

import com.dawson.common.retry.config.RetryLogProperties;
import com.dawson.common.retry.sender.RetryLogMessageSender;
import com.dawson.common.vo.BizLogVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 使用原生的RabbitTemplate 发送mq 消息
 */
@Log4j2
public class RabbitTemplateRetryLogMessageSender implements RetryLogMessageSender {

    private RetryLogProperties.Mq mqProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void send(BizLogVo vo) {
        try {
            String jsonStr = objectMapper.writeValueAsString(vo);
            log.info("使用rabbitTemplate发送到跟踪库，exchange:" + mqProperties.getExchange() + ",routingKey:" + mqProperties.getRoutingKey() + "data:" + jsonStr);
            String id = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(id);
            rabbitTemplate.convertAndSend(mqProperties.getExchange(), mqProperties.getRoutingKey(), jsonStr, correlationId);
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

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
