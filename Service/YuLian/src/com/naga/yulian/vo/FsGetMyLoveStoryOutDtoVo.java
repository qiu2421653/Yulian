package com.naga.yulian.vo;

import org.apache.commons.lang.StringUtils;

public class FsGetMyLoveStoryOutDtoVo {

	private String createTime;

	private String isLock;

	private String loveDesc;

	private String thumb;

	private String topicId;

	private String userId;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		if (!StringUtils.isBlank(createTime)) {
			this.createTime = createTime.substring(0, 11);
		} else {
			this.createTime = createTime;
		}
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getLoveDesc() {
		return loveDesc;
	}

	public void setLoveDesc(String loveDesc) {
		this.loveDesc = loveDesc;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
