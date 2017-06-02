package com.naga.yulian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.entity.Version;
import com.naga.yulian.service.AppInfoService;
import com.naga.yulian.vo.AppVersionVo;

/**
 * 最新版本信息取得
 * 
 * @author liangzhihao
 *
 */
@RestController
public class FsGetAppVersionController {

	@Autowired
	private AppInfoService AppInfoService;

	/**
	 * 最新版本信息取得
	 * 
	 * @param vo
	 *            Json数据
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsGetAppVersion", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetAppVersion(
			@RequestBody AppVersionVo vo) {
		Version info = AppInfoService.getLatestVersionInfo(vo.getSystem());
		return new JsonResponse().success(info);
	}
}
