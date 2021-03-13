package com.dawson.oauth2.server.service.impl;

import com.dawson.oauth2.server.domain.TbUser;
import com.dawson.oauth2.server.mapper.TbUserMapper;
import com.dawson.oauth2.server.service.TbUserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-13 01:03
 **/
@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    private TbUserMapper tbUserMapper;

    /**
     * 根据id获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public TbUser getUser(String username) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("username", username);
        return tbUserMapper.selectOneByExample(example);
    }
}
