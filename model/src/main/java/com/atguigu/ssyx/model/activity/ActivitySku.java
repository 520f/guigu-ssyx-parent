package com.atguigu.ssyx.model.activity;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * ActivitySku
 * </p>
 *
 * @author qy
 */
@Data
@Schema(description = "ActivitySku")
@TableName("activity_sku")
public class ActivitySku extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "活动id ")
	@TableField("activity_id")
	private Long activityId;

	@Schema(name = "sku_id")
	@TableField("sku_id")
	private Long skuId;

//	@TableField(exist = false)
//	private SkuInfo skuInfo;

}

