package com.dawson.oauth2.resource.utils;


import lombok.Data;

import java.io.Serializable;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-12 22:59
 **/
@Data
public class ResultBean<T> implements Serializable {

    public static Integer SUCCESS = 200;

    public static Integer error = 400;

    private Integer code;

    private String massage;

    private T obj;

    public static ResultBean success(Integer code, String massage){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMassage(massage);
        return resultBean;
    }

    public static ResultBean success(Integer code, String massage, Object obj){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMassage(massage);
        resultBean.setObj(obj);
        return resultBean;
    }

}
