package com.dawson.oauth2.server.config;

import com.dawson.oauth2.server.domain.TbPermission;
import com.dawson.oauth2.server.domain.TbUser;
import com.dawson.oauth2.server.service.TbPermissionService;
import com.dawson.oauth2.server.service.TbUserService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-13 01:05
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TbUserService tbUserService;

    @Autowired
    private TbPermissionService tbPermissionService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        // 查询用户信息
        TbUser tbUser = tbUserService.getUser(s);

        if (tbUser != null) {
            // 获取用户授权
            List<TbPermission> tbPermissionList = tbPermissionService.getPermissionByUserId(tbUser.getId());
            // 声明用户授权
            tbPermissionList.forEach(tbPermission -> {
                if (tbPermission != null && tbPermission.getEnname() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbPermission.getEnname());
                    grantedAuthorities.add(grantedAuthority);
                }
            });
// 由框架完成认证工作
            return new User(tbUser.getUsername(), tbUser.getPassword(), grantedAuthorities);
        }
        return null;
    }
}
