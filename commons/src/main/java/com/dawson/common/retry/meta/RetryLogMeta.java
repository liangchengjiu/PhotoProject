package com.dawson.common.retry.meta;

import com.dawson.common.retry.annotation.RetryLog;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class RetryLogMeta {

    private Method method;

    private RetryLog retryLog;

    public RetryLogMeta(Method method, RetryLog retryLog) {
        this.method = method;
        this.retryLog = retryLog;
    }
}
