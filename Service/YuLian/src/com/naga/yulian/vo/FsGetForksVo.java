package com.naga.yulian.vo;

public class FsGetForksVo {

	// 点赞人
	private String nickName;

	// 点赞日期
	private String forkDate;

	// 点赞时间
	private String forkTime;

	// 点赞帖子ID
	private String topicId;

	// 点赞帖子封面
	private String topicThumb;

	// 头像地址
	private String hPic;

	// 用户
	private String userID;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getForkDate() {
		return forkDate;
	}

	public void setForkDate(String forkDate) {
		this.forkDate = forkDate;
	}

	public String getForkTime() {
		return forkTime;
	}

	public void setForkTime(String forkTime) {
		this.forkTime = forkTime;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getTopicThumb() {
		return topicThumb;
	}

	public void setTopicThumb(String topicThumb) {
		this.topicThumb = topicThumb;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
	}

}