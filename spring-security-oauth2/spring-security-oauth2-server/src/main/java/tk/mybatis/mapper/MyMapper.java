package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-13 00:59
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
