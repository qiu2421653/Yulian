package com.naga.yulian.vo;

import java.util.List;

public class FsGetUsedTagsVo {

	private String userID;

	private String tag;

	private String tagID;
	
	private String code;

	private String token;

	private List<FsGetUsedTagsVo> tagDTO;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	public String getTagID() {
		return tagID;
	}

	public void setTagID(String tagID) {
		this.tagID = tagID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<FsGetUsedTagsVo> getTagDTO() {
		return tagDTO;
	}

	public void setTagDTO(List<FsGetUsedTagsVo> tagDTO) {
		this.tagDTO = tagDTO;
	}



}
