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
import com.naga.yulian.service.FsSetTopicForkService;
import com.naga.yulian.service.UserDataService;
import com.naga.yulian.vo.FsSetTopicForkParameter;
import com.naga.yulian.vo.FsSetTopicForkVo;
import com.naga.yulian.vo.FsWeChatLoadVo;
import com.naga.yulian.vo.LoadInVo;
import com.naga.yulian.vo.LoadOutVo;

/**
 * 根据用户id帖子id点赞/取消赞
 */
@RestController
public class FsWeChatLoadController {
    private static final Logger logger = LoggerFactory.getLogger(FsWeChatLoadController.class);

	@Autowired
	private UserDataService UserDataService;

    
    /**
	 * 微信登录
	 * 
	 * @param vo
	 *            Json数据
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsWeChatLoad", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsWeChatLoad(@RequestBody FsWeChatLoadVo vo,
			HttpSession httpSession) {
		//用户信息取得
		LoadOutVo outVo = UserDataService.selectUserInfoByWeChat(vo);
		if(outVo ==null){
			outVo = UserDataService.insertUserInfoByWeChat(vo);
		}
		//登录用户ID保存
		httpSession.setAttribute("LoginMobile", outVo.getUserId());
		httpSession.setAttribute("LoginUUID", outVo.getUuid());
		//返回登录用户信息
		return new JsonResponse().success(outVo);
	}
    
}
