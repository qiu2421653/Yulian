package com.naga.yulian.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class FsGetUserTimeLineOutDTOTimeListVo {

	// 帖子id
	private String topicId;

	// 内容
	private String content;

	// 日期
	private String date;

	// 标签
	private String tag;

	// 时间
	private String time;

	// 图片url列表
	private List<FsGetUserTopicListReturnImgUrlVo> urlList;

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
//		if (StringUtils.isNotBlank(date)) {
//			this.date = date.substring(0, 11);
//		} else {
//			this.date = date;
//		}
		this.date = date;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		if (StringUtils.isNotBlank(time)) {
			this.time = time.substring(11, 13) + ":" + time.substring(14, 16) + "分" + time.substring(17, 19) + "秒";
		} else {
			this.time = time;
		}
	}

	public List<FsGetUserTopicListReturnImgUrlVo> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<FsGetUserTopicListReturnImgUrlVo> urlList) {
		this.urlList = urlList;
	}
}