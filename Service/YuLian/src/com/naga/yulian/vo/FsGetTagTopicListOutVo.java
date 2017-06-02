package com.naga.yulian.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 返回帖子Vo
 * 
 * @author Qiu
 *
 */
public class FsGetTagTopicListOutVo {

	// 内容
	private String content;

	// 创建时间
	private String createTime;

	// 帖子id
	private String infoId;

	// 标签名
	private String tag;

	// 标签id
	private String tagId;

	// 头像
	private String hPic;

	// 用户id
	private String userId;

	// 昵称
	private String nickName;

	// 点赞数
	private int isFork;

	// 评论数
	private int isComment;

	// 帖子图片
	private String url;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		if (StringUtils.isNotBlank(createTime)) {
			this.createTime = createTime.substring(0, 4) + "年" + createTime.substring(5, 7) + "月"
					+ createTime.substring(8, 10) + "日";
		} else {
			this.createTime = "";
		}
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getIsFork() {
		return isFork;
	}

	public void setIsFork(int isFork) {
		this.isFork = isFork;
	}

	public int getIsComment() {
		return isComment;
	}

	public void setIsComment(int isComment) {
		this.isComment = isComment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}