package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetTopicListMapper;
import com.naga.yulian.vo.FsGetTopicListAdvertsVo;
import com.naga.yulian.vo.FsGetTopicListFirstTagsVo;
import com.naga.yulian.vo.FsGetTopicListMailTagVo;
import com.naga.yulian.vo.FsGetTopicListRecommTagsVo;
import com.naga.yulian.vo.FsGetTopicListTopicListVo;

/**
 * 获得主题列表
 * 
 * @author miaowei
 *
 */
@Service("FsGetTopicListService")
public class FsGetTopicListService {

	@Autowired
	private FsGetTopicListMapper fsGetTopicListMapper;

	/**
	 * adverts
	 * 
	 */
	public List<FsGetTopicListAdvertsVo> getFsGetTopicListAdvertsVoList() {
		Map<String, String> a = new HashMap<String, String>();
		return fsGetTopicListMapper.getFsGetTopicListAdvertsVoList(a);
	}

	/**
	 * firstTags
	 * 
	 */
	public List<FsGetTopicListFirstTagsVo> getFsGetTopicListFirstTagsVoList() {
		Map<String, String> a = new HashMap<String, String>();
		return fsGetTopicListMapper.getFsGetTopicListFirstTagsVoList(a);
	}

	/**
	 * recommTags
	 * 
	 */
	public List<FsGetTopicListRecommTagsVo> getFsGetTopicListRecommTagsVoList() {
		Map<String, String> a = new HashMap<String, String>();
		return fsGetTopicListMapper.getFsGetTopicListRecommTagsVoList(a);
	}

	/**
	 * topicList
	 * 
	 */
	public List<FsGetTopicListTopicListVo> getFsGetTopicListTopicListVoList(String userid) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("userid", userid);
		return fsGetTopicListMapper.getFsGetTopicListTopicListVoList(a);
	}

	/**
	 * recommendTopicList
	 * 
	 */
	public List<FsGetTopicListTopicListVo> getFsRecommendTopicVoList(String userid) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("userid", userid);
		return fsGetTopicListMapper.getFsRecommendTopicVoList(a);
	}

	/**
	 * mailTag
	 * 
	 */
	public FsGetTopicListMailTagVo getFsGetTopicListMailTagVoList() {
		Map<String, String> a = new HashMap<String, String>();
		return fsGetTopicListMapper.getFsGetTopicListMailTagVoList(a);
	}

}
