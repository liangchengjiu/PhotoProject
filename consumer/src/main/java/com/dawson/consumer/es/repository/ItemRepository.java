package com.dawson.consumer.es.repository;


import com.dawson.consumer.es.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-24 22:48
 **/
@Component
// <ItemEntity, Long>: ItemEntity的@Id所注解的数据项的数据类型, 必须与Long一致,
//   即ItemEntity的@Id如果是String类型, 这里的Long就变为String
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {
    /**
     * @Description:根据价格区间查询
     * @Param price1
     * @Param price2
     * @Author: https://blog.csdn.net/chen_2890
     */
    List<Item> findByPriceBetween(double price1, double price2);
}
