package com.naga.yulian.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

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
import com.naga.yulian.service.FsGetTopicListService;
import com.naga.yulian.vo.FsGetTopicListAdvertsVo;
import com.naga.yulian.vo.FsGetTopicListFirstTagsVo;
import com.naga.yulian.vo.FsGetTopicListMailTagVo;
import com.naga.yulian.vo.FsGetTopicListParameter;
import com.naga.yulian.vo.FsGetTopicListRecommTagsVo;
import com.naga.yulian.vo.FsGetTopicListTopicListVo;
import com.naga.yulian.vo.FsGetTopicListVo;

import net.sf.json.JSONObject;

/**
 * 获得主题列表
 * 
 * @author miaowei
 *
 */
@RestController
public class FsGetTopicListController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetTopicListController.class);

	@Autowired
	private FsGetTopicListService fsGetTopicListService;

	/**
	 * 获得主题列表
	 * 
	 */
	@RequestMapping(value = "FsGetTopicList", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetTopicList(@RequestBody FsGetTopicListParameter vo, HttpSession httpSession) {
		FsGetTopicListVo a = new FsGetTopicListVo();

		// List<FsGetTopicListAdvertsVo> fsGetTopicListAdvertsList =
		// fsGetTopicListService
		// .getFsGetTopicListAdvertsVoList();
		// TODO
		// List<FsGetTopicListFirstTagsVo> fsGetTopicListFirstTagsList =
		// fsGetTopicListService
		// .getFsGetTopicListFirstTagsVoList();
		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());

		List<FsGetTopicListTopicListVo> fsGetTopicListTopicListList = fsGetTopicListService
				.getFsGetTopicListTopicListVoList(vo.getUserID());
		// a.setAdverts(fsGetTopicListAdvertsList);
		// a.setFirstTags(fsGetTopicListFirstTagsList);
		// a.setMailTag(fsGetTopicListMailTagMap);
		// a.setRecommTags(fsGetTopicListRecommTagsList);
		a.setToken("IamToken");
		a.setTopicList(fsGetTopicListTopicListList);
		System.out.print(new JSONObject().fromObject(a)); // TODO
		return new JsonResponse().success(a);
	}

	/**
	 * 获得推荐主题列表
	 * 
	 */
	@RequestMapping(value = "FsGetRecommendTopicList", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetRecommendTopicList(@RequestBody FsGetTopicListParameter vo,
			HttpSession httpSession) {
		FsGetTopicListVo a = new FsGetTopicListVo();

		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());

		List<FsGetTopicListTopicListVo> fsGetTopicListTopicListList = fsGetTopicListService
				.getFsRecommendTopicVoList(vo.getUserID());
		a.setToken("IamToken");
		a.setTopicList(fsGetTopicListTopicListList);
		System.out.print(new JSONObject().fromObject(a)); // TODO
		return new JsonResponse().success(a);
	}
}
