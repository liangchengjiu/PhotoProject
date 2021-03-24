package com.dawson.provider.service.es.service;


import com.alibaba.fastjson.JSON;
import com.dawson.provider.service.es.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @program: PhotoProject
 * @description: term terms
 * @author: liangchengjiu
 * @create: 2021-03-24 21:19
 **/
@Slf4j
@Service
public class TermQueryService {

    // 索引、类型
    private String index = "mydlq-user";
    private String type = "doc";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 精确查询（查询条件不会进行分词，但是查询内容可能会分词，导致查询不到）
     */
    public void termQuery() {
        try {
            // 创建查询请求对象，将查询对象配置到其中
            SearchRequest searchRequest = new SearchRequest(index);
            searchRequest.types(type);

            // 构建查询条件（注意：termQuery 支持多种格式查询，如 boolean、int、double、string 等，这里使用的是 string 的查询）
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("address.keyword", "北京市通州区"));

            searchRequest.source(searchSourceBuilder);

            // 执行查询，然后处理响应结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            // 根据状态和数据条数验证是否返回了数据
            if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().totalHits > 0) {
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    // 将 JSON 转换成对象
                    UserInfo userInfo = JSON.parseObject(hit.getSourceAsString(), UserInfo.class);
                    // 输出查询信息
                    log.info(userInfo.toString());
                }
            } else {
                log.info("查询结果为空");
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 多个内容在一个字段中进行查询
     */
    public void termsQuery() {
        try {
            // 构建查询条件（注意：termsQuery 支持多种格式查询，如 boolean、int、double、string 等，这里使用的是 string 的查询）
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termsQuery("address.keyword", "北京市丰台区", "北京市昌平区", "北京市大兴区"));
            // 创建查询请求对象，将查询对象配置到其中
            SearchRequest searchRequest = new SearchRequest("mydlq-user");
            searchRequest.source(searchSourceBuilder);
            // 执行查询，然后处理响应结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // 根据状态和数据条数验证是否返回了数据
            if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().totalHits > 0) {
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    // 将 JSON 转换成对象
                    UserInfo userInfo = JSON.parseObject(hit.getSourceAsString(), UserInfo.class);
                    // 输出查询信息
                    log.info(userInfo.toString());
                }
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

}
