package com.naga.yulian.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
import com.naga.yulian.entity.Score;
import com.naga.yulian.service.UserDataService;
import com.naga.yulian.vo.RegistVo;
import com.naga.yulian.vo.UserIdVo;

/**
 * 获取验证码
 * 
 * @author liangzhihao
 *
 */
@RestController
public class FsRegistController {
	private static final Logger logger = LoggerFactory
			.getLogger(FsRegistController.class);

	@Autowired
	private UserDataService UserDataService;

	/**
	 * 注册用户
	 * 
	 * @param vo
	 *            Json数据
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsRegist", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsRegist(@RequestBody RegistVo vo,
			HttpSession httpSession) {
		String savedValidCode = UserDataService.getValidCodeByMobile(vo.getMobile());
		//验证码校验
		if(savedValidCode == null || !savedValidCode.equals(vo.getValidCode())){
			return new JsonResponse().failure(new ApiException("XXX",
					"验证码错误"));
		}
		
		//手机号码是否被注册过校验
		int result = UserDataService.checkMobile(vo.getMobile());
		if (result > 0) {
			return new JsonResponse().failure(new ApiException("XXX",
					"该手机号码已经被注册过！"));
		}
		// UUID
		String uuid = UUID.randomUUID().toString();
		//登录注册信息
		int insertCnt = UserDataService.regist(uuid,vo);
		if(insertCnt == 0){
			logger.error("登录注册信息失败");
			return new JsonResponse().failure(new ApiException("XXX",
					"注册失败"));
		}
		// UUID
		String scoreUuid = UUID.randomUUID().toString();
		Score score=new Score();
		score.setUuid(scoreUuid);
		score.setFkId(uuid);
		score.setIsAll(0);
		UserDataService.score(score);
		UserDataService.deleteValidCode(vo.getMobile());
		UserIdVo user = new UserIdVo();
		user.setUserID(uuid);
		return new JsonResponse().success(user);
	}
}
