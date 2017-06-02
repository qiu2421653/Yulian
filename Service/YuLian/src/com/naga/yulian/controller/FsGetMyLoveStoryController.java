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
import com.naga.yulian.service.FsGetMyLoveStoryService;
import com.naga.yulian.service.FsGetUserInfoService;
import com.naga.yulian.vo.FsGetMyLoveStoryOutDtoVo;
import com.naga.yulian.vo.FsGetMyLoveStoryVo;
import com.naga.yulian.vo.FsGetUserInfoVo;
import com.naga.yulian.vo.FsSearchUserParameter;

/**
 * 获取对应标签集合
 * 
 * @author miaowei
 *
 */
@RestController
public class FsGetMyLoveStoryController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetMyLoveStoryController.class);

	@Autowired
	private FsGetMyLoveStoryService fsGetMyLoveStoryService;

	@Autowired
	private FsGetForkLoveStoryService fsGetForkLoveStoryService;

	@Autowired
	private FsGetUserInfoService fsGetUserInfoService;

	/**
	 * 获得主题列表
	 * 
	 */
	@RequestMapping(value = "FsGetMyLoveStory", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetMyLoveStory(@RequestBody FsSearchUserParameter vo, HttpSession httpSession) {
		FsGetMyLoveStoryVo a = new FsGetMyLoveStoryVo();
		// 爱情集合
		List<FsGetMyLoveStoryOutDtoVo> fsGetTopicList = fsGetMyLoveStoryService.getFsGetTagListVoList(vo.getUserID());
		// 对方个人信息
		FsGetUserInfoVo fsGetUserInfoVo = fsGetUserInfoService.selectUserInfoByUserId(vo.getUserID());

		// 获取关注,粉丝,点赞
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap = fsGetForkLoveStoryService.getForkAndFun(vo.getUserID());
		a.setOutDTO(fsGetTopicList);
		a.setUserInfo(fsGetUserInfoVo);
		a.setToken("IamToken");
		a.setForkNum(returnMap.get("forkNum").toString());
		a.setFunsNum(returnMap.get("funsNum").toString());
		return new JsonResponse().success(a);
	}
}
