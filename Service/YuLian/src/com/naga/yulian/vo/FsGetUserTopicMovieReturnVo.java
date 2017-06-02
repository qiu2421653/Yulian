package com.naga.yulian.vo;

import java.util.List;

/**
 * 返回视频列表Vo
 * 
 * @author wangyan
 *
 */
public class FsGetUserTopicMovieReturnVo {
    
    // 视频列表
    private List<FsGetUserTopicMovieReturnVideoVo> videoList;

	public List<FsGetUserTopicMovieReturnVideoVo> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<FsGetUserTopicMovieReturnVideoVo> videoList) {
		this.videoList = videoList;
	}

	
    
}