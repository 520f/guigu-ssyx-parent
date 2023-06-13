package com.atguigu.ssyx.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryQueryVo {

	@Schema(name = "分类名称")
	private String name;

}

