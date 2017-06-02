package com.naga.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.naga.common.json.JsonResponse;
import com.naga.common.service.UploadService;
import com.naga.common.util.CommonUserData;
import com.naga.common.util.TokenManager;

/**
 * 文件上传
 * 
 * @author HHB
 */
@Controller
@RequestMapping(value = "upload")
public class UploadController {
	@Autowired
	private UploadService uploadservice;
	@Autowired
	private TokenManager tokenManager;

	/**
	 * 上传
	 * 
	 * @param session
	 * @param response
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "files", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse files(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile[] file,
			@RequestParam(name = "type", defaultValue = "others", required = false) String type) {
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = CommonUserData.getStfNm();
		return new JsonResponse().success(uploadservice.saveFile(request, type, username, file));
	}
	
	/**
	 * 获取文件服务器地址
	 * @return
	 */
	@RequestMapping(value="getFileServerUrl", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public JsonResponse getFileServerUrl(){
		return new JsonResponse().success(uploadservice.getFileServerUrl());
	}
	
	// 图片浏览的方法 readImg/这里是本地地址
	@RequestMapping(value = "readImg/**", method = RequestMethod.GET)
	public @ResponseBody String readImg(HttpServletResponse response, HttpServletRequest request) {
		uploadservice.urlRead(response, request);
		return null;
	}
	// 下载并删除logs错误文件
	@RequestMapping(value = "delLogs/**", method = RequestMethod.GET)
	public @ResponseBody String delLogs(HttpServletResponse response, HttpServletRequest request) {
		uploadservice.delLogs(response, request);
		return null;
	}
	
}
