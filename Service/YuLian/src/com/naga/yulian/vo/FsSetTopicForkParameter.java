package com.naga.yulian.vo;

public class FsSetTopicForkParameter {

	// 用户id
	private String userId;

	// 帖子id
	private String infoId;

	// 是否点赞
	private int isFork;

	//状态区分
	private int status;
	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public int getIsFork() {
		return isFork;
	}

	public void setIsFork(int isFork) {
		this.isFork = isFork;
	}

}
