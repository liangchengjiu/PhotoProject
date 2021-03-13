package com.dawson.oauth2.resource.controller;

import com.dawson.oauth2.resource.utils.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-13 11:37
 **/
@RestController
@RequestMapping()
public class ResourceController {

    @GetMapping("/")
    public ResultBean<Object> sty() {
        ResultBean<Object> resultBean = new ResultBean();
        return ResultBean.success(ResultBean.SUCCESS, "success");

    }
    @GetMapping("/view")
    public ResultBean<Object> sty1() {
        ResultBean<Object> resultBean = new ResultBean();

        return ResultBean.success(ResultBean.SUCCESS, "success");

    }
    @GetMapping("/insert")
    public ResultBean<Object> sty2() {
        ResultBean<Object> resultBean = new ResultBean();

        return ResultBean.success(ResultBean.SUCCESS, "success");

    }

}
