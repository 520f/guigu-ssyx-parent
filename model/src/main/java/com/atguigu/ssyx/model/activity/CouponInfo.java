package com.atguigu.ssyx.model.activity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atguigu.ssyx.enums.CouponRangeType;
import com.atguigu.ssyx.enums.CouponType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "CouponInfo")
@TableName("coupon_info")
public class CouponInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "购物券类型 1 现金券 2 满减券")
	@TableField("coupon_type")
	private CouponType couponType;

	@Schema(name = "优惠卷名字")
	@TableField("coupon_name")
	private String couponName;

	@Schema(name = "金额")
	@TableField("amount")
	private BigDecimal amount;

	@Schema(name = "使用门槛 0->没门槛")
	@TableField("condition_amount")
	private BigDecimal conditionAmount;

	@Schema(name = "可以领取的开始日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("start_time")
	private Date startTime;

	@Schema(name = "可以领取的结束日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("end_time")
	private Date endTime;

	@Schema(name = "使用范围[1->全场通用；2->指定商品;3->指定分类；]")
	@TableField("range_type")
	private CouponRangeType rangeType;

	@Schema(name = "使用范围描述")
	@TableField("range_desc")
	private String rangeDesc;

	@Schema(name = "发行数量")
	@TableField("publish_count")
	private Integer publishCount;

	@Schema(name = "每人限领张数")
	@TableField("per_limit")
	private Integer perLimit;

	@Schema(name = "已使用数量")
	@TableField("use_count")
	private Integer useCount;

	@Schema(name = "领取数量")
	@TableField("receive_count")
	private Integer receiveCount;

	@Schema(name = "过期时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("expire_time")
	private Date expireTime;

	@Schema(name = "发布状态[0-未发布，1-已发布]")
	@TableField("publish_status")
	private Boolean publishStatus;

	@TableField(exist = false)
	private String couponTypeString;
	@TableField(exist = false)
	private String rangeTypeString;

	@Schema(name = "使用状态")
	@TableField(exist = false)
	private Integer couponStatus;

	@Schema(name = "是否可选")
	@TableField(exist = false)
	private Integer isSelect = 0;

	@Schema(name = "是否最优选项")
	@TableField(exist = false)
	private Integer isOptimal = 0;

	@Schema(name = "优惠券对应的skuId列表，提交订单使用")
	@TableField(exist = false)
	private List<Long> skuIdList;
}