package com.naga.yulian.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 根据用户id帖子id点赞/取消赞
 */
@RestController
public class FsCreateLoveController {
	private static final Logger logger = LoggerFactory.getLogger(FsCreateLoveController.class);

	@Autowired
	private FsCreateLoveService fsCreateLoveService;

	@Autowired
	private FsUpdateLoveStoryService fsUpdateLoveStoryService;

	@Autowired
	private SystemConfigUtil systemConfigUtil;

	/**
	 * 根据用户id获取时间轴内容
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsCreateLove", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsSetTopicFork(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile[] files, @RequestParam(name = "createDate") String createDate,
			@RequestParam(name = "loveDesc") String loveDesc, @RequestParam(name = "userID") String userID) {

		// 上传文件
		String urls = "";
		String height = "";
		String width = "";
		List<UploadFile> list = UploadUtil.uploadFiles(files, request, systemConfigUtil.getBasepath(),
				systemConfigUtil.getTopicpath());
		if (list != null && list.size() > 0) {
			for (UploadFile file : list) {
				if (file != null) {
					urls = systemConfigUtil.getWebpath() + file.getFilePath();
					height = Integer.toString(file.getHeight());
					width = Integer.toString(file.getWidth());
				}
			}
		}

		// 返回值
		int result = 0;

		FsCreateLoveParameter fsCreateLoveParameter = new FsCreateLoveParameter();
		fsCreateLoveParameter.setCreateDate(createDate);
		fsCreateLoveParameter.setLoveDesc(loveDesc);
		fsCreateLoveParameter.setThumb(urls);
		fsCreateLoveParameter.setUserID(userID);
		fsCreateLoveParameter.setWidth(width);
		fsCreateLoveParameter.setHeight(height);
		result = fsCreateLoveService.insert(fsCreateLoveParameter);
		// 判断是否成功
		if (result == 0) {
			logger.error("新建影集失败");
			return new JsonResponse().failure(new ApiException("XXX", "新建影集失败"));
		}
		fsUpdateLoveStoryService.updateLoveStoryHide(userID);
		// 成功则实例化
		FsCreateLoveVo vo = new FsCreateLoveVo();
		vo.setIsSuccess(Integer.toString(result));
		// 返回
		return new JsonResponse().success(vo);
	}

}
