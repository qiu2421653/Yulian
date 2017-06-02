package com.naga.yulian.dao;

import java.util.List;
import java.util.Map;

import com.naga.yulian.vo.FsGetTopicListAdvertsVo;
import com.naga.yulian.vo.FsGetTopicListFirstTagsVo;
import com.naga.yulian.vo.FsGetTopicListMailTagVo;
import com.naga.yulian.vo.FsGetTopicListRecommTagsVo;
import com.naga.yulian.vo.FsGetTopicListTopicListVo;

public interface FsGetTopicListMapper {

	/**
	 * adverts
	 * 
	 */
	List<FsGetTopicListAdvertsVo> getFsGetTopicListAdvertsVoList(Map<String, String> map);

	/**
	 * firstTags
	 * 
	 */
	List<FsGetTopicListFirstTagsVo> getFsGetTopicListFirstTagsVoList(Map<String, String> map);

	/**
	 * recommTags
	 * 
	 */
	List<FsGetTopicListRecommTagsVo> getFsGetTopicListRecommTagsVoList(Map<String, String> map);

	/**
	 * topicList
	 * 
	 */
	List<FsGetTopicListTopicListVo> getFsGetTopicListTopicListVoList(Map<String, String> map);

	/**
	 * recommendToplicList
	 * 
	 */
	List<FsGetTopicListTopicListVo> getFsRecommendTopicVoList(Map<String, String> map);

	/**
	 * mailTag
	 * 
	 */
	FsGetTopicListMailTagVo getFsGetTopicListMailTagVoList(Map<String, String> map);

}