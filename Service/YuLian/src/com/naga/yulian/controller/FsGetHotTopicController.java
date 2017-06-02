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
import com.naga.yulian.service.FsGetHotTopicService;
import com.naga.yulian.service.FsGetTopicListService;
import com.naga.yulian.vo.FsGetHotTopicListOutVo;
import com.naga.yulian.vo.FsGetHotTopicListReturnVo;
import com.naga.yulian.vo.FsGetTopicListAdvertsVo;
import com.naga.yulian.vo.FsGetUserTopicListVo;

/**
 * 获取指定用户主题详情列表
 */
@RestController
public class FsGetHotTopicController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetHotTopicController.class);

	@Autowired
	private FsGetHotTopicService fsGetHotTopicService;
	
	@Autowired
	private FsGetTopicListService fsGetTopicListService;

	/**
	 * 热门帖
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "FsGetHotTopic", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetHotTopic(@RequestBody FsGetUserTopicListVo vo) {
		// 实例化
		FsGetHotTopicListReturnVo returnVo = new FsGetHotTopicListReturnVo();
		// 帖子List
		List<FsGetHotTopicListOutVo> list = new ArrayList<>();
		// 热门帖子Id的List
		List<String> hotTopicIdList = new ArrayList();
		// 热门帖子
		FsGetHotTopicListOutVo outEntity;
		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());
		
		// 获取热门帖子Id
		hotTopicIdList = fsGetHotTopicService.getHotTopicId();
		
		//广告
		List<FsGetTopicListAdvertsVo> fsGetTopicListAdvertsList = fsGetTopicListService
				.getFsGetTopicListAdvertsVoList();

		for (String id : hotTopicIdList) {
			// 根据经历id获取帖子列表
			outEntity = fsGetHotTopicService.getTopicListById(id);
			list.add(outEntity);
		}

		// 设值
		returnVo.setAdverts(fsGetTopicListAdvertsList);
		returnVo.setTopicList(list);
		// 返回
		return new JsonResponse().success(returnVo);
	}

}
