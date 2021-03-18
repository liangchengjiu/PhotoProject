package com.dawson.common.returnBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -7598829391091941077L;

    public static Integer SUCCESS_CODE = 1;

    public static Integer ERROR_CODE = 0;

    public static Integer UNEXPECTED_CODE = -1;


    /**
     * message 可以为空
     */
    private String message;

    /**
     * 返回的code 请勿使用 20000
     */
    @NotNull
    private Integer code;

    /**
     * 返回的data , 可以为空
     */
    private T data;


    /**
     * @param data 返回的实体
     * @return 成功
     */
    public static <T> Result<T> success(T data) {
        return customize(SUCCESS_CODE, data, null);
    }

    /**
     * @param message 返回的message
     * @return 成功
     */
    public static <T> Result<T> success(String message) {
        return customize(SUCCESS_CODE, null, message);
    }

    /**
     * @return 成功
     */
    public static <T> Result<T> success() {
        return customize(SUCCESS_CODE, null, null);
    }

    /**
     * @param message 错误消息
     * @return 错误
     */
    public static <T> Result<T> error(String message) {
        return error(ERROR_CODE, message);
    }

    /**
     * @param code    {@link org.springframework.http.HttpStatus} 或者0和1
     * @param message 错误消息
     * @return 错误
     */
    public static <T> Result<T> error(Integer code, String message) {
        return customize(code, null, message);
    }

    /**
     * @param code    {@link org.springframework.http.HttpStatus} 或者0和1
     * @param data    返回泛型对象
     * @param message 返回消息
     *                自定义
     */
    public static <T> Result<T> customize(Integer code, T data, String message) {
        Result<T> result = new Result<>();
        result.setMessage(message);
        result.setCode(code);
        result.setData(data);
        return result;
    }

    /**
     * @param code {@link org.springframework.http.HttpStatus} 或者0和1
     * @param data 返回泛型对象
     *             自定义
     */
    public static <T> Result<T> customize(Integer code, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        return result;
    }

    /**
     * @param code {@link org.springframework.http.HttpStatus} 或者0和1
     *             自定义
     */
    public static <T> Result<T> customize(Integer code) {
        Result<T> result = new Result<>();
        result.setCode(code);
        return result;
    }

    /**
     * 判断当前 {@link Result} 是否成功
     * @return true 表示成功
     */
    public boolean izSuccess() {
        return Objects.equals(this.code, SUCCESS_CODE);
    }
}
