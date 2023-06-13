package com.atguigu.ssyx.model.base;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;

@Data
public class BaseMongoEntity implements Serializable {

    @Schema(name = "id")
    @Id
    private String id;

    @Schema(name = "创建时间")
    @CreatedDate
    private Date createTime;

    @Schema(name = "更新时间")
    @LastModifiedDate
    private Date updateTime;

    @Schema(name = "其他参数")
    @Transient //被该注解标注的，将不会被录入到数据库中。只作为普通的javaBean属性
    private Map<String,Object> param = new HashMap<>();
}
