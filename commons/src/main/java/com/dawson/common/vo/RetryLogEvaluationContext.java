package com.dawson.common.vo;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

public class RetryLogEvaluationContext extends MethodBasedEvaluationContext {

    private Boolean noReturnValue=false;

    public RetryLogEvaluationContext(Object rootObject, Method method, Object[] args, ParameterNameDiscoverer paramDiscoverer, Boolean noReturnValue) {
        super(rootObject, method, args, paramDiscoverer);
        this.noReturnValue=noReturnValue;
    }

    public RetryLogEvaluationContext(Object rootObject, Method method, Object[] args, ParameterNameDiscoverer paramDiscoverer) {
        super(rootObject, method, args, paramDiscoverer);
    }

    public Boolean getNoReturnValue() {
        return noReturnValue;
    }
}
