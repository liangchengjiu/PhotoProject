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
 * 岗位
 * </p>
 *
 * @author Ricardo
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_job")
public class SysJob implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

    /**
     * 岗位名称
     */
    @TableField("name")
    private String name;

    /**
     * 岗位状态
     */
    @TableField("enabled")
    private Boolean enabled;

    /**
     * 排序
     */
    @TableField("job_sort")
    private Integer jobSort;

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
