package com.dawson.common.retry.service;


import com.dawson.common.returnBean.Result;
import com.dawson.common.vo.BizLogVo;

/**
 * @program: PhotoProject
 * @description: 错误日志重试通用服务（各个微服务独自实现，根据微服务进行重试）
 * @author: liangchengjiu
 * @create: 2021-03-17 22:06
 **/
public interface RetryLogHandler {

    Class<?> supportClass();

    Result doRetry(BizLogVo entity);
}
