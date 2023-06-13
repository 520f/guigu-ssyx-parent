package com.atguigu.ssyx.model.sys;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "RegionWare")
@TableName("region_ware")
public class RegionWare extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "开通区域")
	@TableField("region_id")
	private Long regionId;

	@Schema(name = "区域名称")
	@TableField("region_name")
	private String regionName;

	@Schema(name = "仓库")
	@TableField("ware_id")
	private Long wareId;

	@Schema(name = "仓库名称")
	@TableField("ware_name")
	private String wareName;

	@Schema(name = "状态（0：未开通 1：已开通）")
	@TableField("status")
	private Integer status;

}