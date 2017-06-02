package com.naga.yulian.vo;

import java.util.List;

public class FsGetGoldDetailListVo {

	private List<FsGetGoldDetailVo> FsGetGoldDetailVo;

	private String token;

	public List<FsGetGoldDetailVo> getFsGetGoldDetailVo() {
		return FsGetGoldDetailVo;
	}

	public void setFsGetGoldDetailVo(List<FsGetGoldDetailVo> fsGetGoldDetailVo) {
		FsGetGoldDetailVo = fsGetGoldDetailVo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}