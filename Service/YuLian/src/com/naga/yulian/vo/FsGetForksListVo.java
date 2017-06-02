package com.naga.yulian.vo;

import java.util.List;

public class FsGetForksListVo {

	private List<FsGetForksVo> fsGetForksVo;

	private String token;

	public List<FsGetForksVo> getFsGetForksVo() {
		return fsGetForksVo;
	}

	public void setFsGetForksVo(List<FsGetForksVo> fsGetForksVo) {
		this.fsGetForksVo = fsGetForksVo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}