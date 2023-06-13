package com.atguigu.ssyx.vo.user;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LeaderBillQueryVo {
	
	@Schema(name= "团长ID")
	private String leaderId;

	@Schema(name= "账单类型")
	private String billType;

	@Schema(name= "业务编号")
	private String businessNo;

	@Schema(name= "交易时间")
	private Date billTime;

	@Schema(name= "账单金额")
	private String billAmount;

	@Schema(name= "账单编号")
	private String billNo;

	@Schema(name= "账单描述")
	private String billDesc;

	@Schema(name= "交易前资金余额")
	private String balanceBefore;

	@Schema(name= "交易后资金余额")
	private String balanceAfter;

	@Schema(name= "账单状态")
	private Integer billStatus;

	@Schema(name= "创建时间")
	private Date createTime;

}

