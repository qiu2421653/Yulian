package com.naga.yulian.vo;

import java.util.List;

/**
 * 返回打赏页列表集合VO
 * 
 * @author songwei
 *
 */
public class FsGetRewardListResultVo {

	/** token **/
	private String token;

	/** 返回打赏页列表VO **/
	private List<FsGetRewardListVo> fsGetRewardListVo;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<FsGetRewardListVo> getFsGetRewardListVo() {
		return fsGetRewardListVo;
	}

	public void setFsGetRewardListVo(List<FsGetRewardListVo> fsGetRewardListVo) {
		this.fsGetRewardListVo = fsGetRewardListVo;
	}

}