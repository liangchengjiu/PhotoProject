package com.dawson.provider.service;

import com.dawson.provider.api.EchoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * @program: PhotoProject
 * @description: 服务提供方实现
 * @author: liangchengjiu
 * @create: 2021-03-14 02:50
 **/
@Service(version = "1.0.0")
public class EchoServiceImpl implements EchoService {

    @Value("${dubbo.protocol.port}")
    private String port;

    @Override
    public String echo(String string) {
        return "Ricardo at your Dubbo service " + string + ", i am from port: " + port;
    }
}
