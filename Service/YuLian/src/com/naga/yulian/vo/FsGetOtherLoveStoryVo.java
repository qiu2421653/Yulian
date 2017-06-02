package com.naga.yulian.vo;

import java.util.List;

public class FsGetOtherLoveStoryVo {

	private List<FsGetOtherLoveStoryOutDtoVo> outDTO;

	private String token;

	private String funsNum;

	private String forkNum;

	private FsGetUserInfoVo userInfo;
	
	private String isFollow;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}

	public List<FsGetOtherLoveStoryOutDtoVo> getOutDTO() {
		return outDTO;
	}

	public void setOutDTO(List<FsGetOtherLoveStoryOutDtoVo> outDTO) {
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
