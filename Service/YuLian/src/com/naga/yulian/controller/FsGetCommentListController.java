package com.naga.yulian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetCommentListService;
import com.naga.yulian.vo.FsGetCommentListVo;
import com.naga.yulian.vo.FsGetCommentParamVo;
import com.naga.yulian.vo.FsGetCommentTopicVo;

import net.sf.json.JSONObject;

/**
 * 获取评论页列表集合
 */
@RestController
public class FsGetCommentListController {

	@Autowired
	private FsGetCommentListService fsGetCommentListService;

	/**
	 * 获取评论页列表集合
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsGetCommentList", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetCommentList(@RequestBody FsGetCommentParamVo vo) {
		FsGetCommentListVo fsGetCommentListVo = new FsGetCommentListVo();
		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());
		// 获取VoList
		List<FsGetCommentTopicVo> fsGetCommentVo = fsGetCommentListService.selectReplyByUserId(vo.getUuId());
		// 设定vo
		fsGetCommentListVo.setFsGetCommentVo(fsGetCommentVo);
		// 设定tokenID
		// TODO
		fsGetCommentListVo.setToken("token");
		System.out.println(new JSONObject().fromObject(fsGetCommentListVo));

		return new JsonResponse().success(fsGetCommentListVo);
	}

}
