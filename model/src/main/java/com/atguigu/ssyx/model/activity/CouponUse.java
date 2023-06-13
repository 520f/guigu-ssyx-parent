package com.atguigu.ssyx.model.activity;

import java.util.Date;

import com.atguigu.ssyx.enums.CouponStatus;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * CouponUse
 * </p>
 *
 * @author qy
 */
@Data
@Schema(description = "优惠券领取记录表")
@TableName("coupon_use")
public class CouponUse extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "购物券ID")
	@TableField("coupon_id")
	private Long couponId;

	@Schema(name = "用户ID")
	@TableField("user_id")
	private Long userId;

	@Schema(name = "订单ID")
	@TableField("order_id")
	private Long orderId;

	@Schema(name = "购物券状态")
	@TableField("coupon_status")
	private CouponStatus couponStatus;

	@Schema(name = "领券时间")
	@TableField("get_time")
	private Date getTime;

	@Schema(name = "使用时间")
	@TableField("using_time")
	private Date usingTime;

	@Schema(name = "支付时间")
	@TableField("used_time")
	private Date usedTime;

	@Schema(name = "过期时间")
	@TableField("expire_time")
	private Date expireTime;

}

