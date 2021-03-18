package com.dawson.common.retry.meta;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 扫描的重试的方法meta  holder
 */
public class RetryLogMetaInfoHolder {
    private RetryLogMetaInfoHolder() {
    }

    /**
     * 标记是否是重新执行流程
     */
    private static ThreadLocal<Boolean> redoFlag = ThreadLocal.withInitial(() -> Boolean.FALSE);


    public static boolean isRedo() {
        return redoFlag.get();
    }

    /**
     * 执行的方法与spring beanName 的映射
     */
    private static ConcurrentHashMap<Class, String> CLASS_TYPE_TO_BEAN_NAME = new ConcurrentHashMap<>(10);

    /**
     * 执行的方法与Meta的映射
     */
    private static ConcurrentHashMap<Method, RetryLogMeta> METHOD_TO_META = new ConcurrentHashMap<>(10);


    public static void setRedoFlagTrue() {
        redoFlag.set(true);
    }

    public static void setRedoFlagFalse() {
        redoFlag.set(false);
    }

    //getter & setter


    public static ConcurrentHashMap<Class, String> getClassToBeanNameMap() {
        return CLASS_TYPE_TO_BEAN_NAME;
    }

    public static String getBeanNameByClass(Class<?> clazz) {
        return CLASS_TYPE_TO_BEAN_NAME.get(clazz);
    }

    public static ConcurrentHashMap<Method, RetryLogMeta> getMethodToMeta() {
        return METHOD_TO_META;
    }


    public static RetryLogMeta getBizLogByMethod(Method method) {
        return METHOD_TO_META.get(method);
    }
}
