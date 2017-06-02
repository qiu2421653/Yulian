package com.naga.yulian.vo;

import java.util.List;

/**
 * 返回帖子列表Vo
 * 
 * @author wangyan
 *
 */
public class FsGetUserTopicListReturnVo {

    // 帖子列表
    private List<FsGetUserTopicListReturnTopicListVo> topicList;

	public List<FsGetUserTopicListReturnTopicListVo> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<FsGetUserTopicListReturnTopicListVo> topicList) {
		this.topicList = topicList;
	}

	
    
}