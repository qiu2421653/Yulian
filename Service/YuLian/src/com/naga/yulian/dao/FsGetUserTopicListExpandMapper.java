package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetUserTopicListReturnTopicListVo;

public interface FsGetUserTopicListExpandMapper {

	/**
	 * 根据经历id获取帖子列表
	 * 
	 * @param topicId
	 * @return
	 */
	List<FsGetUserTopicListReturnTopicListVo> getTopicListById(Map<String, String> map);

	/**
	 * 根据帖子id获取帖子信息
	 * 
	 * @param topicId
	 * @return
	 */
	FsGetUserTopicListReturnTopicListVo getTopicById(Map<String, String> map);

	/**
	 * 根据帖子Id修改访问次数
	 * 
	 * @param map
	 * @return
	 */
	int updateTopicAcceccById(Map<String, String> map);

}