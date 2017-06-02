package com.naga.yulian.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetTopicListMapper;
import com.naga.yulian.dao.MediaExpandMapper;
import com.naga.yulian.vo.FsGetTopicListAdvertsVo;
import com.naga.yulian.vo.FsGetTopicListFirstTagsVo;
import com.naga.yulian.vo.FsGetTopicListMailTagVo;
import com.naga.yulian.vo.FsGetTopicListRecommTagsVo;
import com.naga.yulian.vo.FsGetTopicListTopicListVo;
import com.naga.yulian.vo.FsGetUserTopicMovieReturnVideoVo;

/**
 * 获取主题视频列表
 * 
 * @author wangyan
 *
 */
@Service("FsGetUserTopicMovieService")
public class FsGetUserTopicMovieService {

    @Autowired
    private MediaExpandMapper mediaExpandMapper;

	public List<FsGetUserTopicMovieReturnVideoVo> getVideoListByTopicId(String topicId) {
		return mediaExpandMapper.getVideoListByTopicId(topicId);
	}

    

}
