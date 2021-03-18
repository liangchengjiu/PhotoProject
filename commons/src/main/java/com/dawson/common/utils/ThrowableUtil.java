package com.dawson.common.utils;

import cn.hutool.core.exceptions.ExceptionUtil;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

/**
 * Created by ttjkst on 2018/8/23.
 */
public class ThrowableUtil {
    private ThrowableUtil() {
    }

    /**
     * 获取是否dubbo参数校验的异常，如果是将dubbo参数的校验的异常反解析出来。
     * */
    public static String acquireDubboRcpErrorMsg(Exception ex){
        ConstraintViolationException causedBy = (ConstraintViolationException) ExceptionUtil.getCausedBy(ex, ConstraintViolationException.class);
        if(causedBy!=null){
            return causedBy.getConstraintViolations().stream().map(ConstraintViolation::getMessage).reduce((x1, x2)->x1+x2).orElse(ex.getMessage());
        }else {
            return ex.getMessage();
        }
    }

    public static String getStackTrace( Throwable throwable)
    {
        return getStackTrace(throwable,null);
    }


    public static String getStackTrace( Throwable throwable, Consumer<Throwable> errorHandler)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try
        {
            throwable.printStackTrace(pw);
        } catch (Exception e){
            if(errorHandler!=null) {
                errorHandler.accept(e);
            }
        }finally {
            pw.close();
        }
        return sw.toString();
    }
}
