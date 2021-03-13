package com.dawson.oauth2.server.mapper;

import com.dawson.oauth2.server.domain.TbPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

@Mapper
public interface TbPermissionMapper extends MyMapper<TbPermission> {

    List<TbPermission> getPermissionByUserId(@Param("id") Long id);
}