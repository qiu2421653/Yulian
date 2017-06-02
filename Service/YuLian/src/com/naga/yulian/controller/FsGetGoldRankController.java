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
import com.naga.yulian.entity.Levelname;
import com.naga.yulian.service.FsGetGoldRankService;
import com.naga.yulian.service.FsGetUserInfoService;
import com.naga.yulian.vo.FsGEtGoldRankListVo;
import com.naga.yulian.vo.FsGEtGoldRankParamVo;
import com.naga.yulian.vo.FsGEtGoldRankVo;

import net.sf.json.JSONObject;

/**
 * 获得禹币排行榜
 */
@RestController
public class FsGetGoldRankController {

	@Autowired
	private FsGetGoldRankService fsGetGoldRankService;

	@Autowired
	private FsGetUserInfoService fsGetUserInfoService;

	/**
	 * 获得禹币排行榜
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsGetGoldRank", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetGoldRankList(@RequestBody FsGEtGoldRankParamVo vo) {
		FsGEtGoldRankListVo fsGEtGoldRankListVo = new FsGEtGoldRankListVo();

		// 分页
		PageHelper.startPage(vo.getCurrentPage() + 1, vo.getPageCount());
		// 获取VoLt(排行榜)
		List<FsGEtGoldRankVo> fsGEtGoldRankVo = fsGetGoldRankService.fsGetGoldRankList();

		// 根据每位用户的积分算出用户的等级
		for (int i = 0; i < fsGEtGoldRankVo.size(); i++) {
			// 获取当前积分有关的等级信息
			Levelname levelInfo = fsGetUserInfoService.getCurrentLevel(fsGEtGoldRankVo.get(i).getPoint());

			fsGEtGoldRankVo.get(i).setRankDesc(levelInfo.getLevelname());

		}

		// 判断当前用户在排行榜中排第几
		FsGEtGoldRankVo userTop = fsGetGoldRankService.getUserTop(vo.getUuId());
		// 设定vo
		fsGEtGoldRankListVo.setFsGEtGoldRankVoList(fsGEtGoldRankVo);
		// 设定tokenID
		// TODO
		fsGEtGoldRankListVo.setToken("token");
		// TODO
		fsGEtGoldRankListVo.setRankDesc(userTop.getTop());
		fsGEtGoldRankListVo.setGold(userTop.getGold());
		fsGEtGoldRankListVo.sethPic(userTop.gethPic());
		return new JsonResponse().success(fsGEtGoldRankListVo);
	}

}
