package com.dawson.common.retry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 重试相关的配置
 */
@ConfigurationProperties(prefix = "photo.retry")
public class RetryLogProperties {

    /**
     * 是否开启重试注解
     */
    private Boolean enable;

    /**
     * 总系统别
     */
    private String commonSystem;

    /**
     * 每个子的系统别
     */
    private String systemType;

    /**
     * 默认用户名，如果用户表达式配置为空，或者推断用户错误的情况下使用的
     */
    private String defaultUser;

    /**
     * 与RetryLog  mq 相关配置
     */
    private Mq mq;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


    public String getCommonSystem() {
        return commonSystem;
    }

    public void setCommonSystem(String commonSystem) {
        this.commonSystem = commonSystem;
    }

    public String getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
    }


    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    @Override
    public String toString() {
        return "RetryLogProperties{" +
                "enable=" + enable +
                ", commonSystem='" + commonSystem + '\'' +
                ", systemType='" + systemType + '\'' +
                ", defaultUser='" + defaultUser + '\'' +
                ", mq=" + mq +
                '}';
    }

    public Mq getMq() {
        return mq;
    }

    public void setMq(Mq mq) {
        this.mq = mq;
    }

    /**
     * 与RetryLog  mq 相关配置
     */
    public static class Mq {

        /**
         * mq 发送时候用的exchange
         */
        private String exchange;
        /**
         * mq 发送时候用的routingKey
         */
        private String routingKey;
        /**
         * mq 发送使用何种方式
         */
        private String sender;

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        @Override
        public String toString() {
            return "Mq{" +
                    "exchange='" + exchange + '\'' +
                    ", routingKey='" + routingKey + '\'' +
                    ", sender='" + sender + '\'' +
                    '}';
        }
    }
}
