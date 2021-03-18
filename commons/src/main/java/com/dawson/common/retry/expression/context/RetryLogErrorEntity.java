package com.dawson.common.retry.expression.context;

import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 存入paramContent的实体
 */
@Data
public class RetryLogErrorEntity implements Serializable {

    /**
     * 执行方法的参数
     */
    private Object[] args;
    /**
     * 执行方法的类型
     */
    private Class[] argTyps;
    /**
     * 执行class type
     */
    private Class executeType;
    /**
     * 执行方法的名称
     */
    private String methodName;
    /**
     * 系统别
     */
    private String systemType;
    /**
     * 是否只打印日志
     */
    private Boolean onlyLog;
    /**
     * 其他的参数
     */
    private String othersParams;

    /**
     * 在报错的时候构建的错误信息实体
     */
    public RetryLogErrorEntity(Object[] args, Class classType, Method methodName, String systemType, Boolean onlyLog) {
        this.executeType = classType;
        this.methodName = methodName.getName();
        if (args != null && args.length != 0) {
            this.args = args;
        } else {
            this.args = new Object[0];
        }
        this.systemType = systemType;
        this.onlyLog = onlyLog;
        this.argTyps = methodName.getParameterTypes();
    }

    public RetryLogErrorEntity() {
    }

    /*
     * 自定义的时候使用的context
     * */
    public RetryLogErrorEntity(Class executeType, String systemType, String othersParams) {
        this.executeType = executeType;
        this.systemType = systemType;
        this.othersParams = othersParams;
    }
}
