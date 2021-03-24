package com.dawson.provider.service.es.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-23 22:02
 **/
@Data
public class UserInfo {

    @JsonIgnore
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     *
     */
    private float salary;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 生日
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDate;


}
