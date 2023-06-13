package com.atguigu.ssyx.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "MqRepeatRecord")
@TableName("mq_repeat_record")
public class MqRepeatRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "业务编号")
	@TableField("business_no")
	private String businessNo;

}