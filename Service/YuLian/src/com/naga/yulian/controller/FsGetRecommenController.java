package com.naga.yulian.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.dao.MediaExpandMapper;
import com.naga.yulian.service.FsGetRecommenTopicService;
import com.naga.yulian.vo.FsGetCommentTopicVo;
import com.naga.yulian.vo.FsGetUserTopicListReturnImgUrlVo;
import com.naga.yulian.vo.TopicIdVo;

/**
 * 获取推荐
 */
@RestController
public class FsGetRecommenController {

	private static final Logger logger = LoggerFactory.getLogger(FsGetRecommenController.class);

	@Autowired
	private FsGetRecommenTopicService fsGetRecommenTopicService;

	@Autowired
	private MediaExpandMapper mediaExpandMapper;

	/**
	 * 根据用户id获取时间轴内容
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsGetRecommen", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetRecommen(@RequestBody TopicIdVo vo) {
		// 实例化
		FsGetCommentTopicVo fsGetCommentTopicVo = new FsGetCommentTopicVo();
		List<FsGetUserTopicListReturnImgUrlVo> urllist = new ArrayList<>();

		// 根据经历id获取帖子列表
		fsGetCommentTopicVo = fsGetRecommenTopicService.getTopicById(vo.getTopicId(), vo.getUserId());
//		if (null != fsGetCommentTopicVo) {
//			// 初始化
//			urllist = new ArrayList<>();
//			// 根据帖子id获取图片列表
//			urllist = mediaExpandMapper.getUrlListById(fsGetCommentTopicVo.getInfoId());
//			fsGetCommentTopicVo.setUrlList(urllist);
//		}
		FsGetUserTopicListReturnImgUrlVo urlVo=new FsGetUserTopicListReturnImgUrlVo();
		urlVo.setUrl(fsGetCommentTopicVo.getUrl());
		urllist.add(urlVo);
		
		fsGetCommentTopicVo.setUrlList(urllist);
		// 返回
		return new JsonResponse().success(fsGetCommentTopicVo);
	}

}
