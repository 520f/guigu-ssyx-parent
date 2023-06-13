package com.atguigu.ssyx.enums.user;

import java.math.BigDecimal;
import java.util.Date;

import com.atguigu.ssyx.enums.BillType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "LeaderBill")
@TableName("leader_bill")
public class LeaderBill extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "团长ID")
	@TableField("leader_id")
	private Long leaderId;

	@Schema(name = "账单类型")
	@TableField("bill_type")
	private BillType billType;

	@Schema(name = "业务编号")
	@TableField("business_no")
	private String businessNo;

	@Schema(name = "交易时间")
	@TableField("bill_time")
	private Date billTime;

	@Schema(name = "账单金额")
	@TableField("bill_amount")
	private BigDecimal billAmount;

	@Schema(name = "账单编号")
	@TableField("bill_no")
	private String billNo;

	@Schema(name = "账单描述")
	@TableField("bill_desc")
	private String billDesc;

	@Schema(name = "交易前资金余额")
	@TableField("balance_before")
	private BigDecimal balanceBefore;

	@Schema(name = "交易后资金余额")
	@TableField("balance_after")
	private BigDecimal balanceAfter;

	@Schema(name = "账单状态")
	@TableField("bill_status")
	private Integer billStatus;

}