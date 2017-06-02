package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetHotTopicListOutVo;

public interface FsGetHotTopicMapper {

	/**
	 * 根据热门帖Id
	 * 
	 * @param topicId
	 * @return
	 */
	List<String> getHotTopicId();

	/**
	 * 根据帖子id获取帖子详情
	 * 
	 * @param topicId
	 * @return
	 */
	FsGetHotTopicListOutVo getHotTopicById(Map<String, String> map);

}