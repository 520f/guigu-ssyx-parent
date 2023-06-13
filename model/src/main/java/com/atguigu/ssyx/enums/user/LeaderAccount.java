package com.atguigu.ssyx.enums.user;

import java.math.BigDecimal;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "LeaderAccount")
@TableName("leader_account")
public class LeaderAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name= "团长ID")
	@TableField("leader_id")
	private Long leaderId;

	@Schema(name= "总收益, 可能有部分余额因为订单未结束而不能提现")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@Schema(name= "可提现余额")
	@TableField("available_amount")
	private BigDecimal availableAmount;

	@Schema(name= "冻结余额")
	@TableField("frozen_amount")
	private BigDecimal frozenAmount;

}