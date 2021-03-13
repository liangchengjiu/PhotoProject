package com.dawson.oauth2.server.service.impl;

import com.dawson.oauth2.server.domain.TbPermission;
import com.dawson.oauth2.server.mapper.TbPermissionMapper;
import com.dawson.oauth2.server.service.TbPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-13 01:06
 **/
@Service
public class TbPermissionServiceImpl implements TbPermissionService {

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    public List<TbPermission> getPermissionByUserId(Long id) {
        return tbPermissionMapper.getPermissionByUserId(id);
    }
}
