package com.naga.yulian.vo;

import java.util.List;

/**
 * 返回热门帖子列表Vo
 * 
 * @author Qiu
 *
 */
public class FsGetHotTopicListReturnVo {

	// 轮换广告
	private List<FsGetTopicListAdvertsVo> adverts;

	// 帖子列表
	private List<FsGetHotTopicListOutVo> topicList;

	public List<FsGetHotTopicListOutVo> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<FsGetHotTopicListOutVo> topicList) {
		this.topicList = topicList;
	}

	public List<FsGetTopicListAdvertsVo> getAdverts() {
		return adverts;
	}

	public void setAdverts(List<FsGetTopicListAdvertsVo> adverts) {
		this.adverts = adverts;
	}
	
	

}