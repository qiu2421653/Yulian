package com.naga.yulian.vo;

import java.util.List;

public class FsGetMyLoveStoryVo {

	private List<FsGetMyLoveStoryOutDtoVo> outDTO;

	private String token;

	private String funsNum;

	private String forkNum;
	
	private FsGetUserInfoVo userInfo;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<FsGetMyLoveStoryOutDtoVo> getOutDTO() {
		return outDTO;
	}

	public void setOutDTO(List<FsGetMyLoveStoryOutDtoVo> outDTO) {
		this.outDTO = outDTO;
	}

	public String getFunsNum() {
		return funsNum;
	}

	public void setFunsNum(String funsNum) {
		this.funsNum = funsNum;
	}

	public String getForkNum() {
		return forkNum;
	}

	public void setForkNum(String forkNum) {
		this.forkNum = forkNum;
	}

	public FsGetUserInfoVo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(FsGetUserInfoVo userInfo) {
		this.userInfo = userInfo;
	}
}
