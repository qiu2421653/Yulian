package com.naga.yulian.dao;

import java.util.Map;

import com.naga.yulian.vo.FsGetCommentTopicVo;

public interface FsGetRecommenTopicMapper {

	/**
	 * 根据帖子id获取帖子信息
	 * 
	 * @param topicId
	 * @return
	 */
	FsGetCommentTopicVo getTopicById(Map<String, String> map);

}