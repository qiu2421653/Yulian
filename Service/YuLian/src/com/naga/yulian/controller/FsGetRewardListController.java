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
import com.naga.yulian.service.FsGetRewardListService;
import com.naga.yulian.vo.FsGetRewardListParamVo;
import com.naga.yulian.vo.FsGetRewardListResultVo;
import com.naga.yulian.vo.FsGetRewardListVo;

import net.sf.json.JSONObject;

/**
 * 获取打赏页列表集合
 */
@RestController
public class FsGetRewardListController {

	@Autowired
	private FsGetRewardListService fsGetRewardListService;

	/**
	 * 获取打赏页列表集合
	 * 
	 * @param userID
	 * @return
	 */
	@RequestMapping(value = "FsGetRewardList", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetRewardList(@RequestBody FsGetRewardListParamVo vo) {
		// 实例化
		FsGetRewardListResultVo fsGetRewardListResultVo = new FsGetRewardListResultVo();

		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());

		// 获取Vo
		List<FsGetRewardListVo> fsGetRewardListVo = fsGetRewardListService.fsGetRewardListById(vo.getUuId());

		// 设定FsGetGoldDetailVo
		fsGetRewardListResultVo.setFsGetRewardListVo(fsGetRewardListVo);
		// 设定tokenID
		// TODO
		fsGetRewardListResultVo.setToken("token");

		System.out.println(new JSONObject().fromObject(fsGetRewardListResultVo));

		return new JsonResponse().success(fsGetRewardListResultVo);
	}

}
