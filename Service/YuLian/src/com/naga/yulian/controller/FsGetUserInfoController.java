package com.naga.yulian.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.entity.Levelname;
import com.naga.yulian.service.FsGetUserInfoService;
import com.naga.yulian.vo.FsGetUserInfoVo;

import net.sf.json.JSONObject;

/**
 * 获取用户个人信息
 */
@RestController
public class FsGetUserInfoController {

	@Autowired
	private FsGetUserInfoService fsGetUserInfoService;

	/**
	 * 获取用户个人信息
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsGetUserInfo", method = RequestMethod.GET)
	public @ResponseBody JsonResponse fsGetUserInfoList(@RequestParam("uuId") String uuId) {
		// 获取Vo
		FsGetUserInfoVo fsGetUserInfoVo = fsGetUserInfoService.selectUserInfoByUserId(uuId);
		
		
		if("0".equals(fsGetUserInfoVo.getSex())||!StringUtils.isNotBlank(fsGetUserInfoVo.getSex())){
			
			fsGetUserInfoVo.setSex("未知");
		}

		// 人脸验证判断
		if (!"false".equals(fsGetUserInfoVo.getFace())) {
			fsGetUserInfoVo.setFace("true");

		}

		// 获取当前积分
		String currentPoint = fsGetUserInfoVo.getPoint();
		// 获取当前积分有关的等级信息
		Levelname levelInfo = fsGetUserInfoService.getCurrentLevel(currentPoint);

		// 设定等级起始积分
		fsGetUserInfoVo.setStart(levelInfo.getStart().toString());
		// 设定等级结束积分
		fsGetUserInfoVo.setEnd(levelInfo.getEnd().toString());
		// 设定等级
		fsGetUserInfoVo.setLevelname(levelInfo.getLevelname().toString());
		// 设定tokenID
		// TODO
		// fsGetUserInfoVo.setToken("111");

		new JSONObject().fromObject(fsGetUserInfoVo);

		return new JsonResponse().success(fsGetUserInfoVo);
	}

}
