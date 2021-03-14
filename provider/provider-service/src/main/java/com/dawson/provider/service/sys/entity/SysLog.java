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
 * 系统日志
 * </p>
 *
 * @author Ricardo
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    @TableField("description")
    private String description;

    @TableField("log_type")
    private String logType;

    @TableField("method")
    private String method;

    @TableField("params")
    private String params;

    @TableField("request_ip")
    private String requestIp;

    @TableField("time")
    private Long time;

    @TableField("username")
    private String username;

    @TableField("address")
    private String address;

    @TableField("browser")
    private String browser;

    @TableField("exception_detail")
    private String exceptionDetail;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


}
