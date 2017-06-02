package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetTagTopicMapper;

/**
 * 获取标签Topic
 * 
 * @author Qiu
 *
 */
@Service("FsGetTagTopicService")
public class FsGetTagTopicService {

	@Autowired
	private FsGetTagTopicMapper fsGetTagTopicMapper;

	public List<String> getTopTopicId(String tagCode) {
		Map<String, String> map = new HashMap<>();
		map.put("tagCode", tagCode);
		return fsGetTagTopicMapper.getTagTopicId(map);
	}
}
