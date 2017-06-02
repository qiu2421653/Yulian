package com.naga.yulian.dao;

import java.util.List;

import com.naga.yulian.vo.FsGetUserTopicListReturnImgUrlVo;
import com.naga.yulian.vo.FsGetUserTopicMovieReturnVideoVo;

public interface MediaExpandMapper {

	/**
	 * 根据帖子id获取视频列表
	 * 
	 * @param topicId
	 * @return
	 */
	List<FsGetUserTopicMovieReturnVideoVo> getVideoListByTopicId(String topicId);

	/**
	 * 根据帖子id获取图片列表
	 * 
	 * @param topicId
	 * @return
	 */
	List<FsGetUserTopicListReturnImgUrlVo> getUrlListById(String topicId);

	/** 删除media表中与该用户有关的图片信息 **/
	int deleteInfoByUserId(String media);

}