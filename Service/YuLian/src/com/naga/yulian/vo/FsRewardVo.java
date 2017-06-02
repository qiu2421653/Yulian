package com.naga.yulian.vo;

import java.math.BigDecimal;

/**
 * 对帖子打赏传入参数Vo
 * 
 * @author wangyan
 *
 */
public class FsRewardVo {
	
	// 帖子id
	private String infoId;
	
	// 用户id
	private String userId;

    // 每页显示数量
    private BigDecimal reward;

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}


	
}