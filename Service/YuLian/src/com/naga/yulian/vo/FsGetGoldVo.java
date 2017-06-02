package com.naga.yulian.vo;

public class FsGetGoldVo {

	// 禹币
	private String currency;

	// 用户Id
	private String uuId;

	// 积分
	private String score;

	// 评论总数
	private String totalReply;

	// 点赞总数
	private String totalPraise;

	// 是否完善信息
	private int isAll;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTotalReply() {
		return totalReply;
	}

	public void setTotalReply(String totalReply) {
		this.totalReply = totalReply;
	}

	public String getTotalPraise() {
		return totalPraise;
	}

	public void setTotalPraise(String totalPraise) {
		this.totalPraise = totalPraise;
	}

	public int getIsAll() {
		return isAll;
	}

	public void setIsAll(int isAll) {
		this.isAll = isAll;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

}