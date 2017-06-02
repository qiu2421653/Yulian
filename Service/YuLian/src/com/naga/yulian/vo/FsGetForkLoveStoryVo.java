package com.naga.yulian.vo;

import java.util.List;

public class FsGetForkLoveStoryVo {

	private List<FsGetForkLoveStoryOutDtoVo> outDTO;

	private String token;

	private String funsNum;

	private String forkNum;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<FsGetForkLoveStoryOutDtoVo> getOutDTO() {
		return outDTO;
	}

	public void setOutDTO(List<FsGetForkLoveStoryOutDtoVo> outDTO) {
		this.outDTO = outDTO;
	}

}
