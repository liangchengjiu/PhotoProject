package com.dawson.provider.service.es.service;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-23 21:44
 **/

import com.alibaba.fastjson.JSON;
import com.dawson.provider.service.es.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;


@Slf4j
@Service
public class IndexService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     */
    public void createIndex() {
        try {
            // 创建 Mapping
            XContentBuilder mapping = XContentFactory.jsonBuilder()
                    .startObject()
                        .field("dynamic", true)
                        .startObject("properties")
                            .startObject("name")
                                .field("type","text")
                                    .startObject("fields")
                                        .startObject("keyword")
                                            .field("type","keyword")
                                        .endObject()
                                    .endObject()
                            .endObject()
                            .startObject("address")
                                .field("type","text")
                                .startObject("fields")
                                    .startObject("keyword")
                                        .field("type","keyword")
                                    .endObject()
                                .endObject()
                            .endObject()
                            .startObject("remark")
                                .field("type","text")
                                .startObject("fields")
                                    .startObject("keyword")
                                        .field("type","keyword")
                                    .endObject()
                                .endObject()
                            .endObject()
                            .startObject("age")
                                .field("type","integer")
                            .endObject()
                            .startObject("salary")
                                .field("type","float")
                            .endObject()
                            .startObject("birthDate")
                                .field("type","date")
                                .field("format", "yyyy-MM-dd")
                            .endObject()
                            .startObject("createTime")
                                .field("type","date")
                            .endObject()
                        .endObject()
                    .endObject();
            // 创建索引配置信息，配置
            Settings settings = Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 0)
                    .build();
            // 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
            CreateIndexRequest request = new CreateIndexRequest("mydlq-user", settings);
            request.mapping("doc", mapping);
            // RestHighLevelClient 执行创建索引
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            // 判断是否创建成功
            boolean isCreated = createIndexResponse.isAcknowledged();
            log.info("是否创建成功：{}", isCreated);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 删除索引
     */
    public void deleteIndex() {
        try {
            // 新建删除索引请求对象
            DeleteIndexRequest request = new DeleteIndexRequest("mydlq-user");
            // 执行删除索引
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            // 判断是否删除成功
            boolean siDeleted = acknowledgedResponse.isAcknowledged();
            log.info("是否删除成功：{}", siDeleted);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 增加文档信息
     */
    public void addDocument() {
        try {
            // 创建索引请求对象
            IndexRequest indexRequest = new IndexRequest("mydlq-user", "doc", "1");
            // 创建员工信息
            UserInfo userInfo = new UserInfo();
            userInfo.setName("张三");
            userInfo.setAge(29);
            userInfo.setSalary(100.00f);
            userInfo.setAddress("北京市");
            userInfo.setRemark("来自北京市的张先生");
            userInfo.setCreateTime(new Date());
            userInfo.setBirthDate("1990-01-10");
            // 将对象转换为 byte 数组
            byte[] json = JSON.toJSONBytes(userInfo);
            // 设置文档内容
            indexRequest.source(json, XContentType.JSON);
            // 执行增加文档
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("创建状态：{}", response.status());
        } catch (Exception e) {
            log.error("", e);
        }
    }

   /**
     * 批量增加文档信息
     */
    public void addDocumentBulk() {
        try {

            // 创建员工信息 json
            String str1 = "{\"name\":\"刘一\",\"address\":\"北京市丰台区\",\"remark\":\"低层员工\",\"age\":30,\"salary\":3000,\"birthDate\":\"1989-11-11\",\"createTime\":\"2019-03-15T08:18:00.000Z\"}";
            String str2 = "{\"name\":\"陈二\",\"address\":\"北京市昌平区\",\"remark\":\"中层员工\",\"age\":27,\"salary\":7900,\"birthDate\":\"1992-01-25\",\"createTime\":\"2019-11-08T11:15:00.000Z\"}";
            String str3 = "{\"name\":\"张三\",\"address\":\"北京市房山区\",\"remark\":\"中层员工\",\"age\":28,\"salary\":8800,\"birthDate\":\"1991-10-05\",\"createTime\":\"2019-07-22T13:22:00.000Z\"}";
            String str4 = "{\"name\":\"李四\",\"address\":\"北京市大兴区\",\"remark\":\"高层员工\",\"age\":26,\"salary\":9000,\"birthDate\":\"1993-08-18\",\"createTime\":\"2019-10-17T15:00:00.000Z\"}";
            String str5 = "{\"name\":\"王五\",\"address\":\"北京市密云区\",\"remark\":\"低层员工\",\"age\":31,\"salary\":4800,\"birthDate\":\"1988-07-20\",\"createTime\":\"2019-05-29T09:00:00.000Z\"}";
            String str6 = "{\"name\":\"赵六\",\"address\":\"北京市通州区\",\"remark\":\"中层员工\",\"age\":32,\"salary\":6500,\"birthDate\":\"1987-06-02\",\"createTime\":\"2019-12-10T18:00:00.000Z\"}";
            String str7 = "{\"name\":\"孙七\",\"address\":\"北京市朝阳区\",\"remark\":\"中层员工\",\"age\":33,\"salary\":7000,\"birthDate\":\"1986-04-15\",\"createTime\":\"2019-06-06T13:00:00.000Z\"}";
            String str8 = "{\"name\":\"周八\",\"address\":\"北京市西城区\",\"remark\":\"低层员工\",\"age\":32,\"salary\":5000,\"birthDate\":\"1987-09-26\",\"createTime\":\"2019-01-26T14:00:00.000Z\"}";
            String str9 = "{\"name\":\"吴九\",\"address\":\"北京市海淀区\",\"remark\":\"高层员工\",\"age\":30,\"salary\":11000,\"birthDate\":\"1989-11-25\",\"createTime\":\"2019-09-07T13:34:00.000Z\"}";
            String str10 = "{\"name\":\"郑十\",\"address\":\"北京市东城区\",\"remark\":\"低层员工\",\"age\":29,\"salary\":5000,\"birthDate\":\"1990-12-25\",\"createTime\":\"2019-03-06T12:08:00.000Z\"}";
            String str11 = "{\"name\":\"萧十一\",\"address\":\"北京市平谷区\",\"remark\":\"低层员工\",\"age\":29,\"salary\":3300,\"birthDate\":\"1990-11-11\",\"createTime\":\"2019-03-10T08:17:00.000Z\"}";
            String str12 = "{\"name\":\"曹十二\",\"address\":\"北京市怀柔区\",\"remark\":\"中层员工\",\"age\":27,\"salary\":6800,\"birthDate\":\"1992-01-25\",\"createTime\":\"2019-12-03T11:09:00.000Z\"}";
            String str13 = "{\"name\":\"吴十三\",\"address\":\"北京市延庆区\",\"remark\":\"中层员工\",\"age\":25,\"salary\":7000,\"birthDate\":\"1994-10-05\",\"createTime\":\"2019-07-27T14:22:00.000Z\"}";
            String str14 = "{\"name\":\"冯十四\",\"address\":\"北京市密云区\",\"remark\":\"低层员工\",\"age\":25,\"salary\":3000,\"birthDate\":\"1994-08-18\",\"createTime\":\"2019-04-22T15:00:00.000Z\"}";
            String str15 = "{\"name\":\"蒋十五\",\"address\":\"北京市通州区\",\"remark\":\"低层员工\",\"age\":31,\"salary\":2800,\"birthDate\":\"1988-07-20\",\"createTime\":\"2019-06-13T10:00:00.000Z\"}";
            String str16 = "{\"name\":\"苗十六\",\"address\":\"北京市门头沟区\",\"remark\":\"高层员工\",\"age\":32,\"salary\":11500,\"birthDate\":\"1987-06-02\",\"createTime\":\"2019-11-11T18:00:00.000Z\"}";
            String str17 = "{\"name\":\"鲁十七\",\"address\":\"北京市石景山区\",\"remark\":\"高员工\",\"age\":33,\"salary\":9500,\"birthDate\":\"1986-04-15\",\"createTime\":\"2019-06-06T14:00:00.000Z\"}";
            String str18 = "{\"name\":\"沈十八\",\"address\":\"北京市朝阳区\",\"remark\":\"中层员工\",\"age\":31,\"salary\":8300,\"birthDate\":\"1988-09-26\",\"createTime\":\"2019-09-25T14:00:00.000Z\"}";
            String str19 = "{\"name\":\"吕十九\",\"address\":\"北京市西城区\",\"remark\":\"低层员工\",\"age\":31,\"salary\":4500,\"birthDate\":\"1988-11-25\",\"createTime\":\"2019-09-22T13:34:00.000Z\"}";
            String str20 = "{\"name\":\"丁二十\",\"address\":\"北京市东城区\",\"remark\":\"低层员工\",\"age\":33,\"salary\":2100,\"birthDate\":\"1986-12-25\",\"createTime\":\"2019-03-07T12:08:00.000Z\"}";

            // 索引、类型
            String index = "mydlq-user";
            String type = "doc";

            // 创建索引请求对象（批量）
            BulkRequest request = new BulkRequest();
            // 设置文档内容
            request.add(new IndexRequest(index, type, "1").source(str1, XContentType.JSON));
            request.add(new IndexRequest(index, type, "2").source(str2, XContentType.JSON));
            request.add(new IndexRequest(index, type, "3").source(str3, XContentType.JSON));
            request.add(new IndexRequest(index, type, "4").source(str4, XContentType.JSON));
            request.add(new IndexRequest(index, type, "5").source(str5, XContentType.JSON));
            request.add(new IndexRequest(index, type, "6").source(str6, XContentType.JSON));
            request.add(new IndexRequest(index, type, "7").source(str7, XContentType.JSON));
            request.add(new IndexRequest(index, type, "8").source(str8, XContentType.JSON));
            request.add(new IndexRequest(index, type, "9").source(str9, XContentType.JSON));
            request.add(new IndexRequest(index, type, "10").source(str10, XContentType.JSON));
            request.add(new IndexRequest(index, type, "11").source(str11, XContentType.JSON));
            request.add(new IndexRequest(index, type, "12").source(str12, XContentType.JSON));
            request.add(new IndexRequest(index, type, "13").source(str13, XContentType.JSON));
            request.add(new IndexRequest(index, type, "14").source(str14, XContentType.JSON));
            request.add(new IndexRequest(index, type, "15").source(str15, XContentType.JSON));
            request.add(new IndexRequest(index, type, "16").source(str16, XContentType.JSON));
            request.add(new IndexRequest(index, type, "17").source(str17, XContentType.JSON));
            request.add(new IndexRequest(index, type, "18").source(str18, XContentType.JSON));
            request.add(new IndexRequest(index, type, "19").source(str19, XContentType.JSON));
            request.add(new IndexRequest(index, type, "20").source(str20, XContentType.JSON));

            // 执行增加文档
            BulkResponse responses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            log.info("创建状态：{}", responses.status());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 获取文档信息
     */
    public void getDocument() {
        try {
            // 获取请求对象
            GetRequest getRequest = new GetRequest("mydlq-user", "doc", "1");
            // 获取文档信息
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            // 将 JSON 转换成对象
            if (getResponse.isExists()) {
                UserInfo userInfo = JSON.parseObject(getResponse.getSourceAsBytes(), UserInfo.class);
                log.info("员工信息：{}", userInfo);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 更新文档信息
     */
    public void updateDocument() {
        try {
            // 创建索引请求对象
            UpdateRequest updateRequest = new UpdateRequest("mydlq-user", "doc", "1");
            // 设置员工更新信息
            UserInfo userInfo = new UserInfo();
            userInfo.setSalary(200.00f);
            userInfo.setAddress("北京市海淀区");
            // 将对象转换为 byte 数组
            byte[] json = JSON.toJSONBytes(userInfo);
            // 设置更新文档内容
            updateRequest.doc(json, XContentType.JSON);
            // 执行更新文档
            UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("创建状态：{}", response.status());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 删除文档信息
     */
    public void deleteDocument() {
        try {
            // 创建删除请求对象
            DeleteRequest deleteRequest = new DeleteRequest("mydlq-user", "doc", "1");
            // 执行删除文档
            DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("删除状态：{}", response.status());
        } catch (IOException e) {
            log.error("", e);
        }
    }

}
