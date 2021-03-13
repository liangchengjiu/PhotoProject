package com.dawson.oauth2.server.service;

import com.dawson.oauth2.server.domain.TbPermission;

import java.util.List;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-13 01:03
 **/
public interface TbPermissionService {

    List<TbPermission> getPermissionByUserId(Long id);
}
