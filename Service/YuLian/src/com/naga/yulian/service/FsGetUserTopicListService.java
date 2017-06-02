package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetUserTopicListExpandMapper;
import com.naga.yulian.vo.FsGetUserTopicListReturnTopicListVo;

/**
 * 获取指定用户主题详情列表service
 * 
 * @author wangyan
 *
 */
@Service("FsGetUserTopicListService")
public class FsGetUserTopicListService {

	@Autowired
	private FsGetUserTopicListExpandMapper fsGetUserTopicListExpandMapper;

	public List<FsGetUserTopicListReturnTopicListVo> getTopicListById(String topicId, String userId) {
		Map<String, String> map = new HashMap<>();
		map.put("topicId", topicId);
		map.put("userId", userId);
		return fsGetUserTopicListExpandMapper.getTopicListById(map);
	}

	public FsGetUserTopicListReturnTopicListVo getTopicById(String topicId, String userId) {
		Map<String, String> map = new HashMap<>();
		map.put("topicId", topicId);
		map.put("userId", userId);
		updateTopicAcceccById(map);
		return fsGetUserTopicListExpandMapper.getTopicById(map);
	}

	public int updateTopicAcceccById(Map<String, String> map) {
		return fsGetUserTopicListExpandMapper.updateTopicAcceccById(map);
	}

}
