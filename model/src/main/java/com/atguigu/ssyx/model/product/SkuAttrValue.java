package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "SkuAttrValue")
@TableName("sku_attr_value")
public class SkuAttrValue extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name= "商品id")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name= "属性id")
	@TableField("attr_id")
	private Long attrId;

	@Schema(name= "属性名")
	@TableField("attr_name")
	private String attrName;

	@Schema(name= "属性值")
	@TableField("attr_value")
	private String attrValue;

	@Schema(name= "顺序")
	@TableField("sort")
	private Integer sort;

}