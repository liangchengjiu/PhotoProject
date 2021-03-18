package com.dawson.common.retry.expression.context;

import com.dawson.common.vo.RetryLogEvaluationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RetryLogExpressionEvaluator extends CachedExpressionEvaluator {

    /**
     *
     */
    public static final String RESULT_VARIABLE = "result";

    // shared param discoverer since it caches data internally
    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();
    /**
     * 判断成功spring-el cache的expression 缓存
     */
    private final Map<ExpressionKey, Expression> successCache = new ConcurrentHashMap<ExpressionKey, Expression>(64);
    /**
     * 提取相关单据号spring-el cache的expression 缓存
     */
    private final Map<ExpressionKey, Expression> codeCache = new ConcurrentHashMap<ExpressionKey, Expression>(64);
    /**
     * 提取用户信息spring-el cache的expression 缓存
     */
    private final Map<ExpressionKey, Expression> userExpressionCache = new ConcurrentHashMap<ExpressionKey, Expression>(64);
    /**
     * 提取真实的执行方法 缓存
     */
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<AnnotatedElementKey, Method>(64);


    public EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Object result) {
        RetryLogExpressRootObject rootObject = new RetryLogExpressRootObject(method, args, target, result);
        Method targetMethod = getTargetMethod(target.getClass(), method);
        RetryLogEvaluationContext evaluationContext = new RetryLogEvaluationContext(rootObject, targetMethod, args, this.paramNameDiscoverer);
        evaluationContext.setVariable(RESULT_VARIABLE, result);
        return evaluationContext;
    }

    /**
     * 通过spring -el 判断是否成功
     */
    public boolean success(String successConditionExpress, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return getExpression(this.successCache, methodKey, successConditionExpress).getValue(evalContext, boolean.class);
    }

    /**
     * 通过spring -el 提取相关单据号
     */
    public String code(String codeExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        if (StringUtils.isBlank(codeExpression)) {
            return "";
        }
        return getExpression(this.codeCache, methodKey, codeExpression).getValue(evalContext, String.class);
    }

    /**
     * 通过spring -el 提取用户信息
     */
    public String user(String codeExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        if (StringUtils.isBlank(codeExpression)) {
            return "";
        }
        return getExpression(this.userExpressionCache, methodKey, codeExpression).getValue(evalContext, String.class);
    }

    /**
     * Clear all caches.
     */
    void clear() {
        this.successCache.clear();
        this.codeCache.clear();
        this.targetMethodCache.clear();
        this.userExpressionCache.clear();
    }

    /**
     * 获取代理对象 中被代理对象的真正的方法。。
     */
    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            if (targetMethod == null) {
                targetMethod = method;
            }
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }
}
