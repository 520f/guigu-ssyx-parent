package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "SkuImages")
@TableName("sku_image")
public class SkuImage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "sku_id")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name = "图片名称")
	@TableField("img_name")
	private String imgName;

	@Schema(name = "图片地址")
	@TableField("img_url")
	private String imgUrl;

	@Schema(name = "排序")
	@TableField("sort")
	private Integer sort;

}