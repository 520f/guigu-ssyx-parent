package com.atguigu.ssyx.vo.product;

import java.util.List;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.product.SkuPoster;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SkuInfoVo extends SkuInfo {

	@Schema(name = "海报列表")
	private List<SkuPoster> skuPosterList;

	@Schema(name = "属性值")
	private List<SkuAttrValue> skuAttrValueList;

	@Schema(name = "图片")
	private List<SkuImage> skuImagesList;

}

