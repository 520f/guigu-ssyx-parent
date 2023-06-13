package com.atguigu.ssyx.model.activity;

import com.atguigu.ssyx.enums.CouponRangeType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * CouponRange
 * </p>
 *
 * @author qy
 */
@Data
@Schema(description = "CouponRange")
@TableName("coupon_range")
public class CouponRange extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "优惠券id")
	@TableField("coupon_id")
	private Long couponId;

	@Schema(name = "范围类型 1、商品(spuid) 2、品类(三级分类id) 3、品牌")
	@TableField("range_type")
	private CouponRangeType rangeType;

	@Schema(name = "rangeId")
	@TableField("range_id")
	private Long rangeId;

}

