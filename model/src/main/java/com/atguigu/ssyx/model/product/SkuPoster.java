package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * SpuPoster
 * </p>
 *

 */
@Data
@Schema(description = "SkuPoster")
@TableName("sku_poster")
public class SkuPoster extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "商品id")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name = "文件名称")
	@TableField("img_name")
	private String imgName;

	@Schema(name = "文件路径")
	@TableField("img_url")
	private String imgUrl;

}

