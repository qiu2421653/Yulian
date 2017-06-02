package com.naga.yulian.vo;

import java.util.List;

public class FsGetForksVoOutDto {

	// 点赞人
	private String nickName;

	// 用户
	private String userID;

	// 头像
	private String hPic;

	// 是否关注
	private String isFork;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
	}

	public String getIsFork() {
		return isFork;
	}

	public void setIsFork(String isFork) {
		this.isFork = isFork;
	}

}