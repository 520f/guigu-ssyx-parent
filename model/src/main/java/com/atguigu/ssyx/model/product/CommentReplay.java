package com.atguigu.ssyx.model.product;

import com.atguigu.ssyx.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "CommentReplay")
@TableName("comment_replay")
public class CommentReplay extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(name = "commentId")
	@TableField("comment_id")
	private Long commentId;

	@Schema(name = "nickName")
	@TableField("nick_name")
	private String nickName;

	@Schema(name = "icon")
	@TableField("icon")
	private String icon;

	@Schema(name = "content")
	@TableField("content")
	private String content;

}