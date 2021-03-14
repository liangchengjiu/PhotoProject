package com.dawson.provider.service.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 定时任务日志
 * </p>
 *
 * @author Ricardo
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_quartz_log")
public class SysQuartzLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    @TableField("bean_name")
    private String beanName;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("cron_expression")
    private String cronExpression;

    @TableField("exception_detail")
    private String exceptionDetail;

    @TableField("is_success")
    private Boolean isSuccess;

    @TableField("job_name")
    private String jobName;

    @TableField("method_name")
    private String methodName;

    @TableField("params")
    private String params;

    @TableField("time")
    private Long time;


}
