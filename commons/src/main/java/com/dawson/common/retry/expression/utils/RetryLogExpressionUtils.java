package com.dawson.common.retry.expression.utils;

import com.dawson.common.retry.annotation.RetryLog;
import com.dawson.common.retry.expression.context.RetryLogExpressionEvaluator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

@Log4j2
public class RetryLogExpressionUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * spel evaluator
     */
    private final static RetryLogExpressionEvaluator evaluator = new RetryLogExpressionEvaluator();

    /**
     * 判断是否成功
     */
    public static boolean determineSuccess(Object result, AnnotatedElementKey elementKey, RetryLog bizLog, EvaluationContext context) {
        if (result instanceof Exception) {
            return false;
        }
        if (StringUtils.isBlank(bizLog.successFlag())) {
            return true;
        }
        // 通过spel 判断是否执行成功
        return evaluator.success(bizLog.successFlag(), elementKey, context);
    }

    /**
     * 获取结果信息
     */
    public static String generateResult(Object result) throws JsonProcessingException {
        if (result instanceof Exception) {
            return "";
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * 生成相关单号
     */
    public static String generateBizCode(RetryLog bizLog, EvaluationContext context, AnnotatedElementKey elementKey) {
        try {
            // 通过spel 生成相关单据号
            return bizLog.codePrefix() + evaluator.code(bizLog.bizCode(), elementKey, context);
        } catch (Exception e) {
            log.error("生成相关单号表达式执行出错，直接用时间序列生成相关单据号", e);
            return bizLog.codePrefix() + System.currentTimeMillis() + "";
        }
    }

    public static RetryLogExpressionEvaluator getEvaluator() {
        return evaluator;
    }
}
