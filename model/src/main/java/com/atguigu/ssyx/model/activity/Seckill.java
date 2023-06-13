package com.atguigu.ssyx.model.activity;

import java.util.Date;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Seckill")
@TableName("seckill")
public class Seckill extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "活动标题")
	@TableField("title")
	private String title;

	@Schema(name = "开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("start_time")
	private Date startTime;

	@Schema(name = "结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("end_time")
	private Date endTime;

	@Schema(name = "上下线状态")
	@TableField("status")
	private Integer status;

}