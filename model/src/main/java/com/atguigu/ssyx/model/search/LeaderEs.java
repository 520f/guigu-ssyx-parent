package com.atguigu.ssyx.model.search;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Data
@Document(indexName = "leaderes" )
public class LeaderEs {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword, index = false)
    private String takeName;

    //https://blog.csdn.net/zaishijizhidian/article/details/81015988
    @GeoPointField
    private GeoPoint location; //x:经度 y:纬度

    @Field(type = FieldType.Keyword, index = false)
    private String storePath;

    @Field(type = FieldType.Keyword, index = false)
    private String detailAddress;

    @Field(type = FieldType.Double, index = false)
    private Double distance;
}
