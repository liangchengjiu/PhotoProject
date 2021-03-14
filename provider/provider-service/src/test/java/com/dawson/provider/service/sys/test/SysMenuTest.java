package com.dawson.provider.service.sys.test;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dawson.provider.service.sys.entity.SysMenu;
import com.dawson.provider.service.sys.mapper.SysMenuMapper;
import com.dawson.provider.service.sys.service.SysMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-14 17:40
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuTest {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysMenuService sysMenuService;

    @Test
    public void selectSysMenuList() {
        LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class);
        List<SysMenu> list = sysMenuMapper.selectList(queryWrapper);

        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void selectSysMenuListByService() {
        List<SysMenu> list = sysMenuService.list();

        System.out.println(JSON.toJSONString(list));
    }
}
