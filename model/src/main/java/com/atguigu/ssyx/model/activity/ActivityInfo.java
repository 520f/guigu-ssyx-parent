package com.atguigu.ssyx.model.activity;

import java.util.Date;

import com.atguigu.ssyx.enums.ActivityType;
import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * ActivityInfo
 * </p>
 *
 * @author qy
 */
@Data
@Schema(description = "ActivityInfo")
@TableName("activity_info")
public class ActivityInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(name = "活动名称")
	@TableField("activity_name")
	private String activityName;

	@Schema(name = "活动类型（满减、折扣）")
	@TableField("activity_type")
	private ActivityType activityType;

	@Schema(name = "活动描述")
	@TableField("activity_desc")
	private String activityDesc;

	@Schema(name = "开始时间")
	@TableField("start_time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@Schema(name = "结束时间")
	@TableField("end_time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@Schema(name = "创建时间")
	@TableField("create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@TableField(exist = false)
	private String activityTypeString;
}

