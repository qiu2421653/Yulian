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

import com.github.pagehelper.PageHelper;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.dao.MediaExpandMapper;
import com.naga.yulian.service.FsGetUserTopicListService;
import com.naga.yulian.vo.FsGetUserTopicListReturnImgUrlVo;
import com.naga.yulian.vo.FsGetUserTopicListReturnTopicListVo;
import com.naga.yulian.vo.FsGetUserTopicListReturnVo;
import com.naga.yulian.vo.FsGetUserTopicListVo;

import net.sf.json.JSONObject;

/**
 * 获取指定用户主题详情列表
 */
@RestController
public class FsGetUserTopicListController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetUserTopicListController.class);

	@Autowired
	private FsGetUserTopicListService fsGetUserTopicListService;

	@Autowired
	private MediaExpandMapper mediaExpandMapper;

	/**
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsGetUserTopicList", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetUserTopicList(@RequestBody FsGetUserTopicListVo vo) {
		// 实例化
		FsGetUserTopicListReturnVo returnVo = new FsGetUserTopicListReturnVo();
		List<FsGetUserTopicListReturnTopicListVo> list = new ArrayList<>();
		List<FsGetUserTopicListReturnImgUrlVo> urllist = new ArrayList<>();
		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());
		// 根据经历id获取帖子列表
		list = fsGetUserTopicListService.getTopicListById(vo.getTopicId(), vo.getUserId());
//		// 遍历
//		for (int i = 0; i < list.size(); i++) {
//			// 初始化
//			urllist = new ArrayList<>();
//			// 根据帖子id获取图片列表
//			urllist = mediaExpandMapper.getUrlListById(list.get(i).getInfoId());
//			list.get(i).setUrlList(urllist);
//		}
		// 设值
		returnVo.setTopicList(list);
		// 返回
		return new JsonResponse().success(returnVo);
	}

}
