package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "AttrGroup")
@TableName("attr_group")
public class AttrGroup extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "组名")
	@TableField("name")
	private String name;

	@Schema(name = "排序")
	@TableField("sort")
	private Integer sort;

	@Schema(name = "备注")
	@TableField("remark")
	private String remark;

}