package com.naga.yulian.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.entity.Tag;
import com.naga.yulian.service.FsCreateTagsService;
import com.naga.yulian.vo.FsCreateTagInVo;
import com.naga.yulian.vo.FsGetUsedTagsVo;

/**
 * 新建标签
 */
@RestController
public class FsCreateTagsController {
	private static final Logger logger = LoggerFactory.getLogger(FsCreateTagsController.class);

	@Autowired
	private FsCreateTagsService fsCreateTagsService;

	/**
	 * 根据用户id获取时间轴内容
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsCreateTags", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsCreateTags(@RequestBody FsCreateTagInVo vo) {
		// 实例化
		FsGetUsedTagsVo returnVo = new FsGetUsedTagsVo();

		String uuid = UUID.randomUUID().toString();
		String code = UUID.randomUUID().toString();

		Tag tag = new Tag();
		tag.setUuid(uuid);
		;
		tag.setComClass("C_TAG");
		tag.setCode(code);
		tag.setCreaterId(vo.getUserId());
		tag.setName(vo.getTag());

		int result = fsCreateTagsService.createTags(tag);
		if (result > 0) {
			returnVo.setCode(code);
			returnVo.setTagID(uuid);
			returnVo.setTag(vo.getTag());
			// 返回
			return new JsonResponse().success(returnVo);
		} else {
			return new JsonResponse().failure();
		}
	}

}
