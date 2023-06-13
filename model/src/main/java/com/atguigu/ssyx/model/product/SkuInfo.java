package com.atguigu.ssyx.model.product;

import java.math.BigDecimal;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "SkuInfo")
@TableName("sku_info")
public class SkuInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "分类id")
	@TableField("category_id")
	private Long categoryId;

	@Schema(name = "平台属性分组id")
	@TableField("attr_group_id")
	private Long attrGroupId;

	@Schema(name = "商品类型：0->普通商品 1->秒杀商品")
	@TableField("sku_type")
	private Integer skuType;

	@Schema(name = "spu名称")
	@TableField("sku_name")
	private String skuName;

	@Schema(name = "展示图片")
	@TableField("img_url")
	private String imgUrl;

	@Schema(name = "限购个数/每天（0：不限购）")
	@TableField("per_limit")
	private Integer perLimit;

	@Schema(name = "上架状态：0->下架；1->上架")
	@TableField("publish_status")
	private Integer publishStatus;

	@Schema(name = "审核状态：0->未审核；1->审核通过")
	@TableField("check_status")
	private Integer checkStatus;

	@Schema(name = "是否新人专享：0->否；1->是")
	@TableField("is_new_person")
	private Integer isNewPerson;

	@Schema(name = "排序")
	@TableField("sort")
	private Integer sort;

	@Schema(name = "sku编码")
	@TableField("sku_code")
	private String skuCode;

	@Schema(name = "价格")
	@TableField("price")
	private BigDecimal price;

	@Schema(name = "市场价")
	@TableField("market_price")
	private BigDecimal marketPrice;

	@Schema(name = "库存")
	@TableField("stock")
	private Integer stock;

	@Schema(name = "锁定库存")
	@TableField("lock_stock")
	private Integer lockStock;

	@Schema(name = "预警库存")
	@TableField("low_stock")
	private Integer lowStock;

	@Schema(name = "销量")
	@TableField("sale")
	private Integer sale;

	@Schema(name = "仓库")
	@TableField("ware_id")
	private Long wareId;
}