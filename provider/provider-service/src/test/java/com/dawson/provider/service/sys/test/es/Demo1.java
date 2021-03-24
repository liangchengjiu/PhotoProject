package com.dawson.provider.service.sys.test.es;

import com.alibaba.fastjson.JSONObject;
import com.dawson.provider.service.es.service.IndexService;
import com.dawson.provider.service.es.service.MatchQueryService;
import com.dawson.provider.service.es.service.TermQueryService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-23 21:35
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1 {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private IndexService indexService;

    @Autowired
    private TermQueryService termQueryService;

    @Autowired
    private MatchQueryService matchMultiQuery;

    @Test
    public void config() {

        log.info(JSONObject.toJSONString(restHighLevelClient));

    }

    @Test
    public void createIndex() {
        indexService.createIndex();
    }

    @Test
    public void addDocument() {
        indexService.addDocument();
    }

    @Test
    public void addDocumentBulk() {
        indexService.addDocumentBulk();
    }

    @Test
    public void termQuery(){
        termQueryService.termQuery();
    }

    @Test
    public void termsQuery(){
        termQueryService.termsQuery();
    }

    @Test
    public void matchMultiQuery(){
        matchMultiQuery.matchMultiQuery();
    }

}
