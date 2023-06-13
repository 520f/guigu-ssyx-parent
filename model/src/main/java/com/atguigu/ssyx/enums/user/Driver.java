package com.atguigu.ssyx.enums.user;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Driver")
@TableName("driver")
public class Driver extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "名称")
	@TableField("name")
	private String name;

	@Schema(name = "手机")
	@TableField("phone")
	private String phone;

	@Schema(name = "仓库id")
	@TableField("ware_id")
	private Long wareId;

}