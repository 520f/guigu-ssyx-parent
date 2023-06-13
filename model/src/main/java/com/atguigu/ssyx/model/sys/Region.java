package com.atguigu.ssyx.model.sys;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Region")
@TableName("region")
public class Region extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "上级id")
	@TableField("parent_id")
	private Long parentId;

	@Schema(name = "名称")
	@TableField("name")
	private String name;

	@Schema(name = "是否包含子节点")
	@TableField(exist = false)
	private boolean hasChildren;

}