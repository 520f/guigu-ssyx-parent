package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Comment")
@TableName("comment")
public class Comment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "sku_id")
	@TableField("sku_id")
	private Long skuId;

	@Schema(name = "商品名字")
	@TableField("sku_name")
	private String skuName;

	@Schema(name = "会员昵称")
	@TableField("nick_name")
	private String nickName;

	@Schema(name = "用户头像")
	@TableField("icon")
	private String icon;

	@Schema(name = "星级")
	@TableField("star")
	private Boolean star;

	@Schema(name = "会员ip")
	@TableField("ip")
	private String ip;

	@Schema(name = "显示状态[0-不显示，1-显示]")
	@TableField("status")
	private Boolean status;

	@Schema(name = "点赞数")
	@TableField("follow_count")
	private Integer followCount;

	@Schema(name = "回复数")
	@TableField("reply_count")
	private Integer replyCount;

	@Schema(name = "评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]")
	@TableField("resources")
	private String resources;

	@Schema(name = "内容")
	@TableField("content")
	private String content;

	@Schema(name = "评论类型[0 - 对商品的直接评论，1 - 对评论的回复]")
	@TableField("type")
	private Integer type;

}