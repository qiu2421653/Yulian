package com.naga.yulian.vo;

import java.util.List;

public class FsGetCommentListVo {

	private List<FsGetCommentTopicVo> fsGetCommentVo;

	private String token;

	public List<FsGetCommentTopicVo> getFsGetCommentVo() {
		return fsGetCommentVo;
	}

	public void setFsGetCommentVo(List<FsGetCommentTopicVo> fsGetCommentVo) {
		this.fsGetCommentVo = fsGetCommentVo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}