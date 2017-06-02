package com.naga.yulian.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetUserTopicMovieService;
import com.naga.yulian.vo.FsGetUserTopicListVo;
import com.naga.yulian.vo.FsGetUserTopicMovieReturnVideoVo;
import com.naga.yulian.vo.FsGetUserTopicMovieReturnVo;

import net.sf.json.JSONObject;

/**
 * 根据帖子id分页取点赞人信息
 */
@RestController
public class FsGetUserTopicMovieController {
    private static final Logger logger = LoggerFactory.getLogger(FsGetUserTopicMovieController.class);
   
    @Autowired
    private FsGetUserTopicMovieService fsGetUserTopicMovieService;

    
    /**
     * 根据用户id获取时间轴内容
     * 
     * @param userId
     * @param httpSession
     * @return
     */
	@RequestMapping(value = "FsGetUserTopicMovie", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetUserTopicMovie(
			@RequestBody FsGetUserTopicListVo vo) {
		// 实例化
		FsGetUserTopicMovieReturnVo returnVo = new FsGetUserTopicMovieReturnVo();
		// 分页
		PageHelper.startPage(vo.getCurrentPage()+1, vo.getPageCount());
		// 根据帖子id获取点赞列表
		List<FsGetUserTopicMovieReturnVideoVo> list = fsGetUserTopicMovieService.getVideoListByTopicId(vo.getTopicId());
		// 设值
		returnVo.setVideoList(list);
		//返回
		return new JsonResponse().success(returnVo);
	}
    
}
