package com.dawson.common.rabbitmq.service;

/**
 * @program: PhotoProject
 * @description: MQ通用服务
 * @author: liangchengjiu
 * @create: 2021-03-17 22:06
 **/
public interface MQService {
    void sendToMQ(String exchange, String routingKey, String jsonStr);
}
