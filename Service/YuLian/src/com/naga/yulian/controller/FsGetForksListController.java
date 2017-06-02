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
import com.naga.yulian.service.FsGetForksListService;
import com.naga.yulian.vo.FsGetForksListVo;
import com.naga.yulian.vo.FsGetForksParamVo;
import com.naga.yulian.vo.FsGetForksVo;

import net.sf.json.JSONObject;

/**
 * 获取点赞页列表集合
 */
@RestController
public class FsGetForksListController {

	@Autowired
	private FsGetForksListService fsGetForksListService;

	/**
	 * 获取点赞页列表集合
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsGetForksList", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetForksList(@RequestBody FsGetForksParamVo vo) {
		FsGetForksListVo  fsGetForksListVo = new FsGetForksListVo();
		
		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());
		
		//获取VoList
		List<FsGetForksVo> fsGetForksVo = fsGetForksListService.selectPraiseByUserId(vo.getUserId());
		//设定vo
		fsGetForksListVo.setFsGetForksVo(fsGetForksVo);
		//设定tokenID
		//TODO
		fsGetForksListVo.setToken("token");
		System.out.println(new JSONObject().fromObject(fsGetForksListVo));
		
		return new JsonResponse().success(fsGetForksListVo);
	}

	

}
