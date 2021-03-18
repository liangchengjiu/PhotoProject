package com.dawson.common.retry.service;

import com.dawson.common.vo.BizLogVo;

/**
 * @program: PhotoProject
 * @description: 日志服务
 * @author: liangchengjiu
 * @create: 2021-03-17 22:22
 **/
public interface BizLogApi {
//    BizLogResponse getBizLogQuery(BizLogQueryCommand var1);

    int updateBizLog(BizLogVo var1);

    int insertBizLog(BizLogVo var1);
}
