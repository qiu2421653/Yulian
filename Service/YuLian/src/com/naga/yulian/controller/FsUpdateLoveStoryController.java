package com.naga.yulian.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;
import com.naga.common.util.SystemConfigUtil;
import com.naga.common.util.UploadUtil;
import com.naga.common.vo.UploadFile;
import com.naga.yulian.service.FsCreateLoveService;
import com.naga.yulian.service.FsUpdateLoveStoryService;
import com.naga.yulian.vo.FsCreateLoveParameter;
import com.naga.yulian.vo.FsCreateLoveVo;
import com.naga.yulian.vo.FsUpdateLoveStoryParameter;
import com.naga.yulian.vo.FsUpdateLoveStoryReturn;

/**
 * 根据用户id帖子id点赞/取消赞
 */
@RestController
public class FsUpdateLoveStoryController {
	private static final Logger logger = LoggerFactory.getLogger(FsUpdateLoveStoryController.class);

	@Autowired
	private FsUpdateLoveStoryService fsUpdateLoveStoryService;

	@Autowired
	private SystemConfigUtil systemConfigUtil;

	/**
	 * 我的爱情删除
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsGetStoryDelete", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetStoryDelete(
			@RequestBody FsUpdateLoveStoryParameter fsUpdateLoveStoryParameter) {

		// 返回值
		int result = 0;
		fsUpdateLoveStoryService.deleteTopic(fsUpdateLoveStoryParameter.getThemeId());
		fsUpdateLoveStoryService.deletePraise(fsUpdateLoveStoryParameter.getThemeId());
		fsUpdateLoveStoryService.deleteMedia(fsUpdateLoveStoryParameter.getThemeId());
		fsUpdateLoveStoryService.deleteReply(fsUpdateLoveStoryParameter.getThemeId());
		result = fsUpdateLoveStoryService.deleteLoveStory(fsUpdateLoveStoryParameter.getThemeId());
		// 成功则实例化
		FsUpdateLoveStoryReturn vo = new FsUpdateLoveStoryReturn();
		vo.setIsSuccess(Integer.toString(result));
		vo.setToken("IamToken");
		// 返回
		return new JsonResponse().success(vo);
	}

	/**
	 * 我的爱情当前
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsGetStoryCurrent", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetStoryCurrent(
			@RequestBody FsUpdateLoveStoryParameter fsUpdateLoveStoryParameter) {

		// 返回值
		int result = 0;
		result = fsUpdateLoveStoryService.FsUpdateLoveStory(fsUpdateLoveStoryParameter.getThemeId(), (short) 10);
		// 成功则实例化
		FsUpdateLoveStoryReturn vo = new FsUpdateLoveStoryReturn();
		vo.setIsSuccess(Integer.toString(result));
		vo.setToken("IamToken");
		if (result == 0) {
			return new JsonResponse().failure(new ApiException("XXX", "数据异常,请联系管理员"));
		} else {
			// 返回
			return new JsonResponse().success(vo);
		}
	}

	/**
	 * 修改爱情状态
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsGetStoryType", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetStoryType(
			@RequestBody FsUpdateLoveStoryParameter fsUpdateLoveStoryParameter) {

		// 返回值
		int result = 0;
		result = fsUpdateLoveStoryService.FsUpdateLoveStory(fsUpdateLoveStoryParameter.getThemeId(),
				fsUpdateLoveStoryParameter.getType());
		// 成功则实例化
		FsUpdateLoveStoryReturn vo = new FsUpdateLoveStoryReturn();
		vo.setIsSuccess(Integer.toString(result));
		vo.setToken("IamToken");
		// 返回
		return new JsonResponse().success(vo);
	}

}
