package com.naga.yulian.vo;

import java.util.List;

/**
 * 返回新上榜帖子列表Vo
 * 
 * @author Qiu
 *
 */
public class FsGetNewTopicListReturnVo {

	// 帖子列表
	private List<FsGetHotTopicListOutVo> topicList;

	public List<FsGetHotTopicListOutVo> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<FsGetHotTopicListOutVo> topicList) {
		this.topicList = topicList;
	}

}