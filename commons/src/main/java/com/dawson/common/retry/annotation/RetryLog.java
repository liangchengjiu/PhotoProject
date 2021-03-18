package com.dawson.common.retry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryLog {
    /**
     * 业务类别，配置为某个业务的大分类，
     * 举个例子
     * 比如说结算的模块有销售和采购，销售底下还有预结算，结算，开票等，则该属性应该配置为销售之类
     *
     * @return
     */
    String bizCategory() default "";

    /**
     * 业务类型，配置为某个业务的大分类，
     * 举个例子
     * 比如说结算的模块有销售和采购，销售底下还有预结算，结算，开票等，则该属性应该配置为预结算之类，
     * bizCategory 和 bizType 请根据实际情况来配置。建议不要配置过长。
     *
     * @return
     */
    String bizType() default "";

    /**
     * 业务号,支持spring el 表达式，支持级联（由spring el 保证，el 语法规则，请参考sping el即可）
     * el example:
     * 假如入参数，为 xxxMethod（String code,.....）
     * bizCode = "#code",
     *
     * @return
     */
    String bizCode() default "";

    /**
     * 日志号前缀
     *
     * @return
     */
    String codePrefix() default "";

    /**
     * 判断成功标志 支持sping el 表达式，
     * el example:
     * 假设方法为  Result xxxMethod();
     * 其中 Result 中 有一个code 属性且类型为Integer，code 值为数值1的代表成功
     * 则successFlag = "#result.code==1"
     * el example2:
     * 假设方法为  Result2 xxxMethod();
     * 其中 Result 中 有一个code 属性且类型为String,code 值为字符串1的代表成功
     * 则successFlag = "#result.code=='1'"
     *
     * @return
     */
    String successFlag() default "";

    /**
     * 项目名称，
     * 建议配置为中心的名称。。
     *
     * @return
     */
    String appName() default "";

    /**
     * 用户名称获取方式，请配置el,如果不配置，则取 userName,
     */
    String userExpression() default "";

    /**
     * 用户名称获取的方式，非el表达方式，如果没有配置，则取配置文件中的ouyeel.retry.default-user属性
     */
    String userName() default "";

    /**
     * 是否只是打印日志，如果配置该值，则在重试的时候会抛出异常
     * 针对自定的义的重试，由自定义重试的实现决定。。
     */
    boolean onlyLog() default false;
}
