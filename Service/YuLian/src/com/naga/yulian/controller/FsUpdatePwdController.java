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
import com.naga.yulian.service.UserDataService;
import com.naga.yulian.vo.FsGetUserInfoVo;
import com.naga.yulian.vo.RegistVo;

/**
 * 获取验证码
 * 
 * @author liangzhihao
 *
 */
@RestController
public class FsUpdatePwdController {
	private static final Logger logger = LoggerFactory.getLogger(FsUpdatePwdController.class);

	@Autowired
	private UserDataService UserDataService;

	/**
	 * 修改密码
	 * 
	 * @param vo
	 *            Json数据
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsUpdatePwd", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsUpdatePwd(@RequestBody RegistVo vo, HttpSession httpSession) {
		String savedValidCode = UserDataService.getValidCodeByMobile(vo.getMobile());
		// 验证码校验
		if (savedValidCode == null || !savedValidCode.equals(vo.getValidCode())) {
			return new JsonResponse().failure(new ApiException("XXX", "验证码错误"));
		}
		// 登录注册信息
		int updateCnt = UserDataService.updatePwd(vo);
		if (updateCnt == 0) {
			logger.error("更新用户表失败");
			return new JsonResponse().failure(new ApiException("XXX", "修改密码失败"));
		}
		UserDataService.deleteValidCode(vo.getMobile());
		FsGetUserInfoVo selectUserInfoByUserId = UserDataService.selectUserByUserId(vo);
		return new JsonResponse().success(selectUserInfoByUserId);
	}
}
