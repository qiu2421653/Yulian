package com.naga.yulian.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.entity.User;
import com.naga.yulian.service.FsEditUserInfoService;
import com.naga.yulian.service.UserDataService;
import com.naga.yulian.vo.LoadInVo;
import com.naga.yulian.vo.LoadOutVo;

/**
 * 登录
 * 
 * @author liangzhihao
 *
 */
@RestController
public class FsLoadController {
	private static final Logger logger = LoggerFactory.getLogger(FsLoadController.class);

	@Autowired
	private UserDataService UserDataService;

	@Autowired
	private FsEditUserInfoService fsEditUserInfoService;

	/**
	 * 登录
	 * 
	 * @param vo
	 *            Json数据
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsLoad", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsLoad(@RequestBody LoadInVo vo, HttpSession httpSession) {
		// 用户信息取得
		LoadOutVo outVo = UserDataService.selectUserInfo(vo);
		if (outVo == null) {
			logger.error("登录失败");
			return new JsonResponse().failure(new ApiException("XXX", "用户名或者密码错误，请重新输入"));
		}
		User user = new User();
		user.setUserid(outVo.getUserId());
		user.setLatitude(vo.getLatitude());
		user.setLongitude(vo.getLongitude());
		fsEditUserInfoService.editUserInfoByUserId(user);
		// 登录用户ID保存
		httpSession.setAttribute("LoginMobile", outVo.getUserId());
		httpSession.setAttribute("LoginUUID", outVo.getUuid());
		// 返回登录用户信息
		return new JsonResponse().success(outVo);
	}
}
