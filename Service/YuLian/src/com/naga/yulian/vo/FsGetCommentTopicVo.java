package com.naga.yulian.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 返回帖子列表Vo
 * 
 * @author wangyan
 *
 */
public class FsGetCommentTopicVo {

	// 内容
	private String content;

	// 创建时间
	private String createDate;

	// 帖子id
	private String infoId;

	// 帖子名
	private String topic;

	// 是否点赞
	private String isFork;

	private String url;

	// 图片列表
	private List<FsGetUserTopicListReturnImgUrlVo> urlList;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createTime) {
		if (StringUtils.isNotBlank(createTime)) {
			this.createDate = createTime.substring(0, 4) + "年" + createTime.substring(5, 7) + "月"
					+ createTime.substring(8, 10) + "日";
		} else {
			this.createDate = "";
		}
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getIsFork() {
		return isFork;
	}

	public void setIsFork(String isFork) {
		this.isFork = isFork;
	}

	public List<FsGetUserTopicListReturnImgUrlVo> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<FsGetUserTopicListReturnImgUrlVo> urlList) {
		this.urlList = urlList;
	}

}