package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetHotTopicMapper;
import com.naga.yulian.dao.FsGetNewTopicMapper;
import com.naga.yulian.vo.FsGetHotTopicListOutVo;

/**
 * 获取新上榜
 * 
 * @author Qiu
 *
 */
@Service("FsGetNewTopicService")
public class FsGetNewTopicService {

	@Autowired
	private FsGetNewTopicMapper fsGetNewTopicMapper;

	public List<String> getNewTopicId() {
		return fsGetNewTopicMapper.getNewTopicId();
	}
}
