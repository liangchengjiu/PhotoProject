package com.dawson.provider.service;

import cn.hutool.core.lang.UUID;
import com.dawson.common.retry.annotation.RetryLog;
import com.dawson.provider.api.EchoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: PhotoProject
 * @description: 服务提供方实现
 * @author: liangchengjiu
 * @create: 2021-03-14 02:50
 **/
@Slf4j
@Service(version = "1.0.0")
public class EchoServiceImpl implements EchoService {

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @Value("${dubbo.protocol.port}")
    private String port;

    @Override
    public String echo(String string) {
        return "Ricardo at your Dubbo service " + string + ", i am from port: " + port;
    }

    @RetryLog(appName = "photo",bizCategory = "测试服务",bizType = "provider",bizCode = "#string")
    @Override
    public String sendMq(String string) {
        log.info("=====EchoService -> sendMq====={}", string);
        int i = 1/0;
        return sendDirectMessage();
    }

    private String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

}
