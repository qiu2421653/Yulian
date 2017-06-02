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
import com.naga.yulian.service.FsGetUserTopicListService;
import com.naga.yulian.vo.FsGetUserTopicListReturnImgUrlVo;
import com.naga.yulian.vo.FsGetUserTopicListReturnTopicListVo;
import com.naga.yulian.vo.TopicIdVo;

/**
 * 获取内容
 */
@RestController
public class FsGetTopicController {

	private static final Logger logger = LoggerFactory.getLogger(FsGetTopicController.class);

	@Autowired
	private FsGetUserTopicListService fsGetUserTopicListService;

	@Autowired
	private MediaExpandMapper mediaExpandMapper;

	/**
	 * 获取帖在内容
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsGetTopic", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetTopic(@RequestBody TopicIdVo vo) {
		// 实例化
		FsGetUserTopicListReturnTopicListVo fsGetUserTopicListReturnTopicListVo = new FsGetUserTopicListReturnTopicListVo();
		List<FsGetUserTopicListReturnImgUrlVo> urllist = new ArrayList<>();

		// 根据经历id获取帖子列表
		fsGetUserTopicListReturnTopicListVo = fsGetUserTopicListService.getTopicById(vo.getTopicId(), vo.getUserId());
		if (null != fsGetUserTopicListReturnTopicListVo) {
			// 初始化
			urllist = new ArrayList<>();
			// 根据帖子id获取图片列表
			urllist = mediaExpandMapper.getUrlListById(fsGetUserTopicListReturnTopicListVo.getInfoId());
			fsGetUserTopicListReturnTopicListVo.setUrlList(urllist);
		}

		// 返回
		return new JsonResponse().success(fsGetUserTopicListReturnTopicListVo);
	}

}
