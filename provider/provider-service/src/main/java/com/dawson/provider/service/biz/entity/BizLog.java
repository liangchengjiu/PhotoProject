package com.dawson.provider.service.biz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ricardo
 * @since 2021-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("biz_log")
public class BizLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    /**
     * 日志号
     */
    @TableField("log_code")
    private String logCode;

    /**
     * 日志来源
     */
    @TableField("log_system")
    private String logSystem;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 业务类型
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 业务号
     */
    @TableField("biz_code")
    private String bizCode;

    /**
     * 业务类别
     */
    @TableField("biz_category")
    private String bizCategory;

    /**
     * 参数内容
     */
    @TableField("param_content")
    private String paramContent;

    /**
     * 返回内容
     */
    @TableField("result_content")
    private String resultContent;

    /**
     * 异常内容
     */
    @TableField("exception_content")
    private String exceptionContent;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private String startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private String endTime;

    /**
     * 消耗时间
     */
    @TableField("cost_time")
    private Long costTime;

    /**
     * 操作人
     */
    @TableField("operator")
    private String operator;

    /**
     * 操作时间
     */
    @TableField("operation_time")
    private Date operationTime;

    /**
     * 备注标记
     */
    @TableField("remark")
    private String remark;

    /**
     * 预警开始时间——开始
     */
    @TableField("search_start_time_start")
    private Date searchStartTimeStart;

    /**
     * 预警开始时间——结束
     */
    @TableField("search_start_time_end")
    private Date searchStartTimeEnd;

    /**
     * 预警结束时间——开始
     */
    @TableField("search_end_time_start")
    private Date searchEndTimeStart;

    /**
     * 预警结束时间——结束
     */
    @TableField("search_end_time_end")
    private Date searchEndTimeEnd;


}
