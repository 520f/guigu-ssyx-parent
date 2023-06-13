package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Attr")
@TableName("attr")
public class Attr extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "属性名")
	@TableField("name")
	private String name;

	@Schema(name = "属性录入方式：0->手工录入；1->从列表中选取")
	@TableField("input_type")
	private Integer inputType;

	@Schema(name = "可选值列表[用逗号分隔]")
	@TableField("select_list")
	private String selectList;

	@Schema(name = "属性分组id")
	@TableField("attr_group_id")
	private Long attrGroupId;

}