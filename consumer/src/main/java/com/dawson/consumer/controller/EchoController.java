package com.dawson.consumer.controller;

import com.dawson.provider.api.EchoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: PhotoProject
 * @description: 服务消费者
 * @author: liangchengjiu
 * @create: 2021-03-14 03:00
 **/

@Slf4j
@RefreshScope //配置动态刷新
@RestController
public class EchoController {

    @Reference(version = "1.0.0")
    private EchoService echoService;

    @Value("${user.name}")
    private String username;


    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        return echoService.echo(string) + ", " + username;
    }

    @GetMapping(value = "/sendMq/{string}")
    public String sendMq(@PathVariable String string) {
        log.info("============consumer=====");
        return echoService.sendMq(string) + ", " + username;
    }
}

