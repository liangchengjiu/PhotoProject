package com.dawson.oauth2.server.service;

import com.dawson.oauth2.server.domain.TbUser;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-13 01:03
 **/
public interface TbUserService {

    /**
     * 根据id获取用户信息
     * @param username
     * @return
     */
    TbUser getUser(String username);
}
