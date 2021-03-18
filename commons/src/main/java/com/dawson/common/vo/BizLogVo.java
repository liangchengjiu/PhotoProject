//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.dawson.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BizLogVo implements Serializable {

    private BigDecimal logId;
    /**
     * 日志号
     */
    private String logCode;
    /**
     * 日志来源
     */
    private String logSystem;
    /**
     * 状态 00 失败未处理,01 失败已处理,10 成功
     */
    private String status;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 业务号
     */
    private String bizCode;
    /**
     * 业务类别
     */
    private String bizCategory;
    /**
     * 参数内容
     */
    private String paramContent;
    /**
     * 返回内容
     */
    private String resultContent;
    /**
     * 异常内容
     */
    private String exceptionContent;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 消耗时间
     */
    private Long costTime;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作时间
     */
    private String operationTime;
    /**
     * 备注标记
     */
    private String remark;

    /**
     * 查询字段，预警开始时间——开始
     */
    private Date searchStartTimeStart;
    /**
     * 查询字段，预警开始时间——结束
     */
    private Date searchStartTimeEnd;

    /**
     * 查询字段，预警结束时间——开始
     */
    private Date searchEndTimeStart;
    private Date searchEndTimeEnd;
}
