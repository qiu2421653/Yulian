package com.naga.yulian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetEnumInfoService;
import com.naga.yulian.vo.FsGetEnumInfoListVo;
import com.naga.yulian.vo.FsGetEnumInfoVo;

import net.sf.json.JSONObject;

/**
 * 获取枚举信息
 */
@RestController
public class FsGetEnumInfoController {

	@Autowired
	private FsGetEnumInfoService fsGetEnumInfoService;

	/**
	 * 根据关键字获取枚举信息
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsEnumInfo", method = RequestMethod.GET)
	public @ResponseBody JsonResponse fsGetEnumInfoList(@RequestParam("keyword") String keyword) {
		
		FsGetEnumInfoListVo FsGetEnumInfoListVo = new FsGetEnumInfoListVo();
		// 获取Vo
		List<FsGetEnumInfoVo> fsGetEnumInfoVo = fsGetEnumInfoService.getEnumInfoByUserKeyWord(keyword);
		
		FsGetEnumInfoListVo.setFsGetEnumInfoVo(fsGetEnumInfoVo);

		System.out.println(new JSONObject().fromObject(FsGetEnumInfoListVo));

		return new JsonResponse().success(FsGetEnumInfoListVo);
	}

}
