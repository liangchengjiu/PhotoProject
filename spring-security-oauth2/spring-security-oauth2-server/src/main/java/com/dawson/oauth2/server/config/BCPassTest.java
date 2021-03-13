package com.dawson.oauth2.server.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-12 23:24
 **/
public class BCPassTest {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("QWER1234"));

    }
}
