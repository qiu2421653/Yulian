package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetHotTopicListOutVo;

public interface FsGetNewTopicMapper {

	/**
	 * 根据热门帖Id
	 * 
	 * @param topicId
	 * @return
	 */
	List<String> getNewTopicId();


}