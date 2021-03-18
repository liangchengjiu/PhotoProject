package com.dawson.common.retry.config;

import com.dawson.common.rabbitmq.service.MQService;
import com.dawson.common.retry.sender.RetryLogMessageSender;
import com.dawson.common.retry.sender.impl.MqServiceRetryLogMessageSender;
import com.dawson.common.retry.sender.impl.RabbitTemplateRetryLogMessageSender;
import com.dawson.common.retry.support.RetryLogAnnotationBeanPostProcessor;
import com.dawson.common.retry.support.RetryLogAopSupport;
import com.dawson.common.utils.ConstantUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(value = RetryLogProperties.class)
@Configuration
@ConditionalOnProperty(prefix = "photo.retry", name = "enable", havingValue = "true", matchIfMissing = true)
@Log4j2
public class RetryLogConfig {

    @Autowired(required = false)
    private RetryLogProperties retryLogProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RetryLogAnnotationBeanPostProcessor bizLogAnnotationBeanPostProcessor() {
        return new RetryLogAnnotationBeanPostProcessor();
    }

    @Bean
    public RetryLogMessageSender retryLogMessageSender() {
        RetryLogProperties.Mq mq;
        if (retryLogProperties.getMq() == null) {
            mq = defaultMq();
        } else {
            mq = retryLogProperties.getMq();
        }
        log.info("重试方法发生错误的时候发送的mq exchange 和 routingkey信息是" + mq);
        if ("auto".equals(mq.getSender())) {
            return autoDeduceSender(mq);
        }
        if ("mqService".equals(mq.getSender())) {
            return createSenderByMqService(mq);
        }
        if (ConstantUtils.DEFAULT_USER.equals(mq.getSender())) {
            return createSenderByRabbitTemplate(mq);
        }
        return autoDeduceSender(mq);
    }


    @Bean
    public RetryLogAopSupport retryLogAopSupport(@Autowired RetryLogMessageSender retryLogMessageSender) {
        RetryLogAopSupport retryLogAopSupport = new RetryLogAopSupport();
        retryLogAopSupport.setSystemType(retryLogProperties.getCommonSystem());
        retryLogAopSupport.setCommonSystem(retryLogProperties.getCommonSystem());
        retryLogAopSupport.setDefaultOperator(retryLogProperties.getDefaultUser());
        retryLogAopSupport.setMessageSender(retryLogMessageSender);
        retryLogAopSupport.setObjectMapper(objectMapper);
        return retryLogAopSupport;
    }


    private RetryLogMessageSender autoDeduceSender(RetryLogProperties.Mq mq) {
        RetryLogMessageSender sender = null;
        try {
            log.info("尝试采用mqservice 进行配置RetryLogMessageSender");
//            sender = createSenderByMqService(mq);
        } catch (Exception e) {
            log.info("采用mqservice 进行配置RetryLogMessageSender失败，采用rabbitTemplate 进行配置");
            sender = createSenderByRabbitTemplate(mq);
        }
        return sender;
    }

    private RetryLogMessageSender createSenderByMqService(RetryLogProperties.Mq mq) {
        MQService mqService = applicationContext.getBean(MQService.class);
        MqServiceRetryLogMessageSender mqServiceRetryLogMessageSender = new MqServiceRetryLogMessageSender();
        mqServiceRetryLogMessageSender.setMqProperties(mq);
        mqServiceRetryLogMessageSender.setMqService(mqService);
        mqServiceRetryLogMessageSender.setObjectMapper(objectMapper);
        return mqServiceRetryLogMessageSender;
    }

    private RetryLogMessageSender createSenderByRabbitTemplate(RetryLogProperties.Mq mq) {
        RabbitTemplate mqService = applicationContext.getBean(RabbitTemplate.class);
        RabbitTemplateRetryLogMessageSender rabbitTemplateRetryLogMessageSender = new RabbitTemplateRetryLogMessageSender();
        rabbitTemplateRetryLogMessageSender.setMqProperties(mq);
        rabbitTemplateRetryLogMessageSender.setRabbitTemplate(mqService);
        rabbitTemplateRetryLogMessageSender.setObjectMapper(objectMapper);
        return rabbitTemplateRetryLogMessageSender;
    }

    private RetryLogProperties.Mq defaultMq() {
        RetryLogProperties.Mq mq = new RetryLogProperties.Mq();
        mq.setExchange(ConstantUtils.BIZ_ERROR_LOG_EXCHANGE);
        mq.setRoutingKey(ConstantUtils.BIZ_LOG_ROUTING_KEY);
        mq.setSender(ConstantUtils.DEFAULT_USER);
        return mq;
    }
}
