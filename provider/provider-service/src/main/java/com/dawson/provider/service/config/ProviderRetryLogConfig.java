package com.dawson.provider.service.config;

import com.dawson.common.retry.support.RetryLogAopSupport;
import com.dawson.common.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-18 00:45
 **/
@Slf4j
@Configuration
public class ProviderRetryLogConfig {

    @Bean(autowire = Autowire.BY_TYPE)
    public RetryLogAopSupport retryLogAopSupport(){
        log.info("================================retryLogAopSupport");
        RetryLogAopSupport aopShgtTraceLogSupport = new RetryLogAopSupport();
        aopShgtTraceLogSupport.setSystemType(ConstantUtils.PROVIDER_SERVICE);
        return  aopShgtTraceLogSupport;
    }
}
