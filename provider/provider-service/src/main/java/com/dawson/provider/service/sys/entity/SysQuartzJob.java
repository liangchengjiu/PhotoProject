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
 * 定时任务
 * </p>
 *
 * @author Ricardo
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_quartz_job")
public class SysQuartzJob implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

    /**
     * Spring Bean名称
     */
    @TableField("bean_name")
    private String beanName;

    /**
     * cron 表达式
     */
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * 状态：1暂停、0启用
     */
    @TableField("is_pause")
    private Boolean isPause;

    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 方法名称
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 参数
     */
    @TableField("params")
    private String params;

    /**
     * 备注
     */
    @TableField("description")
    private String description;

    /**
     * 负责人
     */
    @TableField("person_in_charge")
    private String personInCharge;

    /**
     * 报警邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 子任务ID
     */
    @TableField("sub_task")
    private String subTask;

    /**
     * 任务失败后是否暂停
     */
    @TableField("pause_after_failure")
    private Boolean pauseAfterFailure;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 创建日期
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
