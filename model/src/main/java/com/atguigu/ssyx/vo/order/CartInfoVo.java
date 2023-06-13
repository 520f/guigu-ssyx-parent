package com.atguigu.ssyx.vo.order;

import java.io.Serializable;
import java.util.List;

import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.order.CartInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * OrderDetailActivity
 * </p>
 *
 * @author qy
 */
@Data
public class CartInfoVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 购物项凑单，同一活动对应的最优活动规则
	 */
	@Schema(name = "cartInfoList")
	private List<CartInfo> cartInfoList;

	@Schema(name = "活动规则")
	private ActivityRule activityRule;


}

