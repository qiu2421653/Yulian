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

import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetUsedTagsService;
import com.naga.yulian.vo.FsGetUsedTagsVo;

import net.sf.json.JSONObject;

/**
 * 获得可使用的标签
 */
@RestController
public class FsGetUsedTagsController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetUsedTagsController.class);

	@Autowired
	private FsGetUsedTagsService fsGetUsedTagsService;

	/**
	 * 根据用户id获取时间轴内容
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsGetUsedTags", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetUsedTags(@RequestBody FsGetUsedTagsVo vo) {
		// 实例化
		FsGetUsedTagsVo returnVo = new FsGetUsedTagsVo();
		List<FsGetUsedTagsVo> usedTags = fsGetUsedTagsService.getUsedTags(vo.getUserID());
		returnVo.setTagDTO(usedTags);
		// 返回
		return new JsonResponse().success(returnVo);
	}

}
