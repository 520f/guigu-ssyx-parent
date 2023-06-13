package com.atguigu.ssyx.model.order;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderReturnReason")
@TableName("order_return_reason")
public class OrderReturnReason extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "退货类型")
	@TableField("name")
	private String name;

	@Schema(name = "sort")
	@TableField("sort")
	private Integer sort;

	@Schema(name = "状态：0->不启用；1->启用")
	@TableField("status")
	private Integer status;

}