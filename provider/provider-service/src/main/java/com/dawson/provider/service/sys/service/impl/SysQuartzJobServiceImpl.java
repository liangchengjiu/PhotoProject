package com.dawson.provider.service.sys.service.impl;

import com.dawson.provider.service.sys.entity.SysQuartzJob;
import com.dawson.provider.service.sys.mapper.SysQuartzJobMapper;
import com.dawson.provider.service.sys.service.SysQuartzJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author Ricardo
 * @since 2021-03-14
 */
@Service
public class SysQuartzJobServiceImpl extends ServiceImpl<SysQuartzJobMapper, SysQuartzJob> implements SysQuartzJobService {

}
