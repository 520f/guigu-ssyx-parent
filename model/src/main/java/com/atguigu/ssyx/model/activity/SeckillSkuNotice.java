package com.atguigu.ssyx.model.activity;

import java.util.Date;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "SeckillSkuNotice")
@TableName("seckill_sku_notice")
public class SeckillSkuNotice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "user_id")
	@TableField("user_id")
	private Long userId;

	@Schema(name = "sku_id")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name = "活动场次id")
	@TableField("session_id")
	private Long sessionId;

	@Schema(name = "订阅时间")
	@TableField("subcribe_time")
	private Date subcribeTime;

	@Schema(name = "发送时间")
	@TableField("send_time")
	private Date sendTime;

	@Schema(name = "通知方式[0-短信，1-邮件]")
	@TableField("notice_type")
	private Boolean noticeType;

}