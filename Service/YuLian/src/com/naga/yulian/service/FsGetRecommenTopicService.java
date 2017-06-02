package com.naga.yulian.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetRecommenTopicMapper;
import com.naga.yulian.vo.FsGetCommentTopicVo;

/**
 * 获取指定用户主题详情列表service
 * 
 * @author wangyan
 *
 */
@Service("FsGetRecommenTopicService")
public class FsGetRecommenTopicService {

	@Autowired
	private FsGetRecommenTopicMapper fsGetRecommenTopicMapper;

	public FsGetCommentTopicVo getTopicById(String topicId,String userId) {
		Map<String, String> map = new HashMap<>();
		map.put("topicId", topicId);
		map.put("userId", userId);
		return fsGetRecommenTopicMapper.getTopicById(map);
	}

}
