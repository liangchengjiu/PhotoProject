package com.dawson.oauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: PhotoProject
 * @description:  认证服务器，用于获取 Token
 * @author: liangchengjiu
 * @create: 2021-03-12 15:51
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.dawson.oauth2.server.mapper")
public class OAuth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServerApplication.class, args);
    }

}