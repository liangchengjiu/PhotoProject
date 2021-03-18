package com.dawson.common.utils;

/**
 * @program: PhotoProject
 * @description: 通用常量
 * @author: liangchengjiu
 * @create: 2021-03-17 23:25
 **/
public interface ConstantUtils {

    // 错误日志 MQ
    String BIZ_ERROR_LOG_EXCHANGE = "biz.log.exchange";
    String BIZ_ERROR_LOG_QUEUES = "biz_log_queues";

    // MQ-RoutingKey
    String BIZ_LOG_ROUTING_KEY = "bizLogRoutingKey";

    // 默认用户
    String DEFAULT_USER = "system";

    // 文本格式
    String DEFAULT_CHARSET = "UTF-8";

    String PROVIDER_SERVICE = "provider_service";
}
