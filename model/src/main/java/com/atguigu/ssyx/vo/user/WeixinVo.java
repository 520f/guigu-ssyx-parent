package com.atguigu.ssyx.vo.user;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "weixinVo")
public class WeixinVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String iv;
	private String encryptedData;
	private String sessionKey;
	private String openId;

}