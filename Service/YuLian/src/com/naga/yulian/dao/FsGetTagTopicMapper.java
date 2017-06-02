package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetHotTopicListOutVo;

public interface FsGetTagTopicMapper {

	/**
	 * 获取tag标签集合Id
	 * 
	 * @param topicId
	 * @return
	 */
	List<String> getTagTopicId(Map<String, String> map);

}