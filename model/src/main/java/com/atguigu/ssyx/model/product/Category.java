package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Category")
@TableName("category")
public class Category extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name= "分类名称")
	@TableField("name")
	private String name;

	@Schema(name= "图标")
	@TableField("img_url")
	private String imgUrl;

	@Schema(name= "父分类id")
	@TableField("parent_id")
	private Long parentId;

	@Schema(name= "是否显示[0-不显示，1显示]")
	@TableField("status")
	private Integer status;

	@Schema(name= "排序")
	@TableField("sort")
	private Integer sort;

}