package com.atguigu.ssyx.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * OrderDetailActivity
 * </p>
 *
 * @author qy
 */
@Data
public class OrderConfirmVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(name = "预生产订单号")
	private String orderNo;

	@Schema(name = "用户对应的团长地址")
	private LeaderAddressVo leaderAddressVo;
	
	@Schema(name = "购物项列表")
	private List<CartInfoVo> carInfoVoList;

	@Schema(name = "订单优惠券列表")
    private List<CouponInfo> couponInfoList;

	@Schema(name = "促销优惠金额")
	private BigDecimal activityReduceAmount;

	@Schema(name = "优惠券优惠金额")
	private BigDecimal couponReduceAmount;

	@Schema(name = "购物车原始总金额")
	private BigDecimal originalTotalAmount;

	@Schema(name = "最终总金额")
	private BigDecimal totalAmount;

}

