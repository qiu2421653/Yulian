package com.naga.yulian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetForkLoveStoryService;
import com.naga.yulian.service.FsGetOtherLoveStoryService;
import com.naga.yulian.service.FsGetUserInfoService;
import com.naga.yulian.vo.FsGetOtherLoveStoryOutDtoVo;
import com.naga.yulian.vo.FsGetOtherLoveStoryVo;
import com.naga.yulian.vo.FsGetUserInfoVo;
import com.naga.yulian.vo.FsSearchUserParameter;

/**
 * 获取目标爱情经历
 * 
 * @author Qiu
 *
 */
@RestController
public class FsGetOtherLoveStoryController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetOtherLoveStoryController.class);

	@Autowired
	private FsGetOtherLoveStoryService fsGetOtherLoveStoryService;

	@Autowired
	private FsGetForkLoveStoryService fsGetForkLoveStoryService;

	@Autowired
	private FsGetUserInfoService fsGetUserInfoService;

	/**
	 * 获得主题列表
	 * 
	 */
	@RequestMapping(value = "FsGetOtherLoveStory", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetMyLoveStory(@RequestBody FsSearchUserParameter vo, HttpSession httpSession) {

		FsGetOtherLoveStoryVo a = new FsGetOtherLoveStoryVo();
		
		// 对方爱情集合
		List<FsGetOtherLoveStoryOutDtoVo> fsGetTopicList = fsGetOtherLoveStoryService
				.getFsGetLoveListVoList(vo.getUserID());
	
		// 对方个人信息
		FsGetUserInfoVo fsGetUserInfoVo = fsGetUserInfoService.selectUserInfoByUserId(vo.getUserID());
		
		// 获取关注,粉丝,点赞
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap = fsGetForkLoveStoryService.getForkFunAndIsFollow(vo.getUserID(), vo.getCreaterID());
		a.setOutDTO(fsGetTopicList);
		a.setUserInfo(fsGetUserInfoVo);
		a.setToken("token");
		a.setForkNum(returnMap.get("forkNum").toString());
		a.setFunsNum(returnMap.get("funsNum").toString());
		a.setIsFollow(returnMap.get("isFollow").toString());
		return new JsonResponse().success(a);
	}
}
