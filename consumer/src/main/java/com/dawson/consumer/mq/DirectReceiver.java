package com.dawson.consumer.mq;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-17 09:47
 **/
@Slf4j
@Component
public class DirectReceiver {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${server.port}")
    private String port;

    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
    public void process(Map message) {
        try {
            log.info("=============TestDirectQueue -> process -> {}============= begin {}", port, JSON.toJSONString(message));
            Thread.sleep(5000);
            log.info("=============TestDirectQueue -> process -> {}============= over {}", port, JSON.toJSONString(message));

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("DirectReceiver消费者收到消息  : " + message.toString());
        }

    }

}
