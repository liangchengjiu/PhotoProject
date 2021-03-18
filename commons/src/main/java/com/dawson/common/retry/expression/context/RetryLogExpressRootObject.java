package com.dawson.common.retry.expression.context;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * spel  执行的上下文
 **/
public class RetryLogExpressRootObject {

    /**
     * 当时执行的方法
     */
    private final Method method;

    /**
     * 当时执行参数
     */
    private final Object[] args;
    /**
     * 当时执行这个方法的对象
     */
    private final Object target;
    /**
     * 当时执行这个方法的class
     */
    private final Class<?> targetClass;
    /**
     * 当时执行这个方法的结果，
     */
    private final Object result;


    public RetryLogExpressRootObject(Method method, Object[] args, Object target, Object result) {

        Assert.notNull(method, "Method is required");
        Assert.notNull(target.getClass(), "targetClass is required");
        this.method = method;
        this.target = target;
        this.targetClass = target.getClass();
        this.args = args;
        this.result = result;
    }


    public Method getMethod() {
        return this.method;
    }

    public String getMethodName() {
        return this.method.getName();
    }

    public Object[] getArgs() {
        return this.args;
    }

    public Object getTarget() {
        return this.target;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public Object getResult() {
        return result;
    }
}
