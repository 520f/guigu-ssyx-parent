package com.atguigu.ssyx.model.activity;

import java.math.BigDecimal;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "SeckillSku")
@TableName("seckill_sku")
public class SeckillSku extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "秒杀活动id")
	@TableField("seckill_id")
	private Long seckillId;

	@Schema(name = "活动场次id")
	@TableField("seckill_time_id")
	private Long seckillTimeId;

	@Schema(name = "商品id")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name = "spu名称")
	@TableField("sku_name")
	private String skuName;

	@Schema(name = "展示图片")
	@TableField("img_url")
	private String imgUrl;

	@Schema(name = "秒杀价格")
	@TableField("seckill_price")
	private BigDecimal seckillPrice;

	@Schema(name = "秒杀总量")
	@TableField("seckill_stock")
	private Integer seckillStock;

	@Schema(name = "每人限购数量")
	@TableField("seckill_limit")
	private Integer seckillLimit;

	@Schema(name = "秒杀销量")
	@TableField("seckill_sale")
	private Integer seckillSale;

	@Schema(name = "排序")
	@TableField("seckill_sort")
	private Integer seckillSort;

	@Schema(name = "sku信息")
	@TableField(exist = false)
	private SkuInfo skuInfo;

}