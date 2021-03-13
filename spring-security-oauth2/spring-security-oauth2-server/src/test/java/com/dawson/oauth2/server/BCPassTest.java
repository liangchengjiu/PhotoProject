package com.dawson.oauth2.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-12 23:19
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class BCPassTest {

    @Test
    public void getBCPass() {

        System.out.println(new BCryptPasswordEncoder().encode("123qwe"));

    }
}
