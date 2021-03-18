package com.dawson.common.retry.support;

import com.dawson.common.retry.annotation.RetryLog;
import com.dawson.common.retry.meta.RetryLogMeta;
import com.dawson.common.retry.meta.RetryLogMetaInfoHolder;
import lombok.extern.log4j.Log4j2;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 用户获取重试方法所在类与spring bean的映射,
 * 主要是扫描实例化bean的过程的一些元数据信息。。
 */
@Log4j2
public class RetryLogAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        AtomicBoolean findIt = new AtomicBoolean(false);
        ReflectionUtils.doWithMethods(targetClass, method -> {
            RetryLog bizLog = AnnotationUtils.findAnnotation(method, RetryLog.class);
            if (bizLog != null) {
                RetryLogMeta bizLogMeta = new RetryLogMeta(method, bizLog);
                if (findIt.compareAndSet(false, true)) {
                    log.info(String.format("已经查找到有@RetryLog的服务bean,class:%s,beanName:%s", targetClass, beanName));
                    ConcurrentHashMap classToBeanNameMap = RetryLogMetaInfoHolder.getClassToBeanNameMap();
                    classToBeanNameMap.put(targetClass, beanName);
                }
                RetryLogMetaInfoHolder.getMethodToMeta().put(bizLogMeta.getMethod(), bizLogMeta);
            }

        });
        return bean;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
