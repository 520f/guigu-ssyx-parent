package com.atguigu.ssyx.model.activity;

import java.math.BigDecimal;

import com.atguigu.ssyx.enums.ActivityType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * ActivityRule
 * </p>
 *
 * @author qy
 */
@Data
@Schema(description = "ActivityRule")
@TableName("activity_rule")
public class ActivityRule extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "类型")
	@TableField("activity_id")
	private Long activityId;

	@Schema(name = "活动类型（满减、折扣）")
	@TableField("activity_type")
	private ActivityType activityType;

	@Schema(name = "满减金额")
	@TableField("condition_amount")
	private BigDecimal conditionAmount;

	@Schema(name = "满减件数")
	@TableField("condition_num")
	private Long conditionNum;

	@Schema(name = "优惠金额")
	@TableField("benefit_amount")
	private BigDecimal benefitAmount;

	@Schema(name = "优惠折扣")
	@TableField("benefit_discount")
	private BigDecimal benefitDiscount;

	@Schema(name = "活动skuId")
	@TableField(exist = false)
	private Long skuId;

	@Schema(name = "优惠后减少金额")
	@TableField(exist = false)
	private BigDecimal reduceAmount;

	@Schema(name = "选中类型：1：去凑单 2：逛一逛")
	@TableField(exist = false)
	private Integer selectType;

	@Schema(name = "规则优惠描述")
	@TableField(exist = false)
	private String ruleDesc;

}

