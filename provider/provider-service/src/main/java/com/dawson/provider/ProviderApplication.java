package com.dawson.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: PhotoProject
 * @description: 服务提供方启动类
 * @author: liangchengjiu
 * @create: 2021-03-14 02:49
 **/
@SpringBootApplication
@MapperScan("com.dawson.provider.service")
@ComponentScan(basePackages = {"com.dawson"})
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
