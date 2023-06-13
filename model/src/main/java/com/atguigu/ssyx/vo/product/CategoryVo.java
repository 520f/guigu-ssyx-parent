package com.atguigu.ssyx.vo.product;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分类")
public class CategoryVo  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name = "分类id")
	private Long id;

	@Schema(name = "分类名称")
	private String name;
}