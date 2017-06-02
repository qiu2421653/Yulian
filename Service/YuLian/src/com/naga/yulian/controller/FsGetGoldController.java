package com.naga.yulian.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetGoldService;
import com.naga.yulian.vo.FsGetGoldVo;
import com.naga.yulian.vo.FsGetUserInfoVo;

import net.sf.json.JSONObject;

/**
 * 获得禹币积分
 */
@RestController
public class FsGetGoldController {

	@Autowired
	private FsGetGoldService fsGetGoldService;

	/**
	 * 获得禹币积分
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsGetGold", method = RequestMethod.GET)
	public @ResponseBody JsonResponse fsGetGoldInfo(@RequestParam("uuId") String uuId) {

		FsGetGoldVo fsGetGoldVo = new FsGetGoldVo();

		fsGetGoldVo = fsGetGoldService.getGoldById(uuId);
		// // 禹币
		// String currency;
		// // 积分
		// String score;
		// 评论总数
		// BigDecimal totalReply = new BigDecimal(0);
		// // 点赞总数
		// BigDecimal totalPraise = new BigDecimal(0);

		// 获取Vo
		// FsGetUserInfoVo fsGetUserInfoVo =
		// fsGetGoldService.getGoldInfoByUserId(uuId);

		// 获取评论总数
		// totalReply = fsGetGoldService.getTotalReply(uuId);
		//
		// //获取点赞总数
		// totalPraise = fsGetGoldService.getTotalPraise(uuId);
		// 设定tokenID
		// TODO
		// fsGetUserInfoVo.setToken("token");
		//
		// fsGetGoldVo.setCurrency(fsGetUserInfoVo.getCurrency());
		//
		// fsGetGoldVo.setScore(fsGetUserInfoVo.getPoint());
		//
		// fsGetGoldVo.setTotalReply(totalReply.toString());
		//
		// fsGetGoldVo.setTotalPraise(totalPraise.toString());
		if (fsGetGoldVo == null) {
			ApiException exception = new ApiException("XXX", "数据错误,请联系管理员");
			return new JsonResponse().failure(exception);
		}
		return new JsonResponse().success(fsGetGoldVo);
	}

}
