package com.atguigu.ssyx.model.order;

import com.atguigu.ssyx.enums.ProcessStatus;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderLog")
@TableName("order_log")
public class OrderLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "订单id")
	@TableField("order_id")
	private Long orderId;

	@Schema(name = "操作人：用户；系统；后台管理员")
	@TableField("operate_user")
	private String operateUser;

	@Schema(name = "操作状态")
	@TableField("process_status")
	private ProcessStatus processStatus;

	@Schema(name = "备注")
	@TableField("note")
	private String note;

}