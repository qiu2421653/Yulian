package com.naga.yulian.vo;

import org.springframework.web.multipart.MultipartFile;

public class FsSetTopicVo {
	
	private String userId;
	
	private String content;
	
	private String tag;

	private MultipartFile[] files;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	
}
