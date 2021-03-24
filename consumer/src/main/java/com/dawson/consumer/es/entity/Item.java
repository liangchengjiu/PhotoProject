package com.dawson.consumer.es.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @program: PhotoProject
 * @description:
 * @author: liangchengjiu
 * @create: 2021-03-24 22:46
 **/
// indexName索引名称 可以理解为数据库名 必须为小写,
//                          不然会报org.elasticsearch.indices.InvalidIndexNameException异常
// type类型 可以理解为表名
@Document(indexName = "item", type = "docs", shards = 1, replicas = 0)
public class Item {
    /**
     * 注: 该注解是必填项
     * @Description: @Id注解必须是springframework包下的
     * org.springframework.data.annotation.Id
     * @Author: https://blog.csdn.net/chen_2890
     */
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title; //标题

    @Field(type = FieldType.Keyword)
    private String category;// 分类

    @Field(type = FieldType.Keyword)
    private String brand; // 品牌

    @Field(type = FieldType.Double)
    private Double price; // 价格

    @Field(index = false, type = FieldType.Keyword)
    private String images; // 图片地址

    public Item(Long id, String title, String category,
                String brand, Double price, String images) {
        this.id       = id;
        this.title    = title;
        this.category = category;
        this.brand    = brand;
        this.price    = price;
        this.images   = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
