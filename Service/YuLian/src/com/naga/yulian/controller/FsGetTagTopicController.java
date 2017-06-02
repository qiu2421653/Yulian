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
import com.naga.yulian.service.FsGetTagTopicService;
import com.naga.yulian.vo.FsGetHotTopicListOutVo;
import com.naga.yulian.vo.FsGetNewTopicListReturnVo;
import com.naga.yulian.vo.FsGetTagTopicListVo;

/**
 * 获取标签对应详情列表
 */
@RestController
public class FsGetTagTopicController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetTagTopicController.class);

	@Autowired
	private FsGetTagTopicService fsGetTagTopicService;

	@Autowired
	private FsGetHotTopicService fsGetHotTopicService;

	/**
	 * 新上榜帖
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "FsGetTagsTopic", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetTagsTopic(@RequestBody FsGetTagTopicListVo vo) {
		// 实例化
		FsGetNewTopicListReturnVo returnVo = new FsGetNewTopicListReturnVo();
		// 帖子List
		List<FsGetHotTopicListOutVo> list = new ArrayList<>();
		// 新上榜帖帖子Id的List
		List<String> tagTopicIdList = new ArrayList();
		// 新上榜帖帖子
		FsGetHotTopicListOutVo outEntity;
		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());
		// 获取新上榜帖帖子Id
		tagTopicIdList = fsGetTagTopicService.getTopTopicId(vo.getTagCode());

		for (String id : tagTopicIdList) {
			// 根据经历id获取帖子列表
			outEntity = fsGetHotTopicService.getTopicListById(id);
			list.add(outEntity);
		}
		// 设值
		returnVo.setTopicList(list);
		// 返回
		return new JsonResponse().success(returnVo);
	}

}
