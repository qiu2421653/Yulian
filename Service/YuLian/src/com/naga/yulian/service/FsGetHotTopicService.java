package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetHotTopicMapper;
import com.naga.yulian.vo.FsGetHotTopicListOutVo;

/**
 * 获取热门帖
 * 
 * @author Qiu
 *
 */
@Service("FsGetHotTopicService")
public class FsGetHotTopicService {

	@Autowired
	private FsGetHotTopicMapper fsGetHotTopicMapper;

	public FsGetHotTopicListOutVo getTopicListById(String topicId) {
		Map<String, String> map = new HashMap<>();
		map.put("topicId", topicId);
		return fsGetHotTopicMapper.getHotTopicById(map);
	}
	
	public List<String> getHotTopicId() {
		return fsGetHotTopicMapper.getHotTopicId();
	}
}
