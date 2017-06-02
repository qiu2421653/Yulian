package com.naga.yulian.controller;

import java.util.ArrayList;
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

import com.github.pagehelper.PageHelper;
import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetFirstListService;
import com.naga.yulian.vo.FirstListVo;
import com.naga.yulian.vo.FsGetForkLoveStoryOutDtoVo;
import com.naga.yulian.vo.FsGetForkLoveStoryVo;
import com.naga.yulian.vo.FsResultListVo;
import com.naga.yulian.vo.FsSearchUserParameter;

/**
 * 根据用户id判断用户是否有可用经历
 */
@RestController
public class FsGetFirstListController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetFirstListController.class);

	@Autowired
	private FsGetFirstListService FsGetFirstListService;

	/**
	 * 根据用户id获取时间轴内容
	 * 
	 * @param FirstListVo
	 * @return
	 */
	@RequestMapping(value = "FsGetFirstList2", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetFirstList2(@RequestBody FirstListVo Vo) {

		// 分页
		PageHelper.startPage(Vo.getCurrentPage() + 1, Vo.getPageCount());
		FsResultListVo a = new FsResultListVo();

		// 根据userId取当前经历
		// tag,tagId,url
		List<Map<String, Object>> OutDto = FsGetFirstListService.SelectFirstList(Vo);
		// 最终结果集
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

		// 每个map中的url集合
		List<Object> urlList = null;
		// 每个map中url 对象
		Map<String, Object> urlLists = null;
		for (Map<String, Object> map : OutDto) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			urlList = new ArrayList<Object>();
			String[] urlArray = map.get("url").toString().split("\\*");
			int len = urlArray.length;
			if (len > 4) {
				len = 4;
			}
			for (int i = 0; i < len; i++) {
				String url = urlArray[i];
				if (url.indexOf(",http") != -1) {
					url = url.replace(",http", "http");
				}
				urlLists = new HashMap<String, Object>();
				urlLists.put("url", url);
				urlList.add(urlLists);
			}
			itemMap.put("tagId", map.get("tagId").toString());
			itemMap.put("tag", map.get("tag").toString());
			itemMap.put("urlList", urlList);
			lists.add(itemMap);
		}
		Map<String, Object> mapss = new HashMap<String, Object>();
		mapss.put("outDTO", lists);
		// mapss.put("token", "true");
		JsonResponse resultJson = new JsonResponse();
		resultJson.setResult(mapss);
		resultJson.setSuccess("true");
		// 返回
		return resultJson;
	}

	/**
	 * 获得主题列表
	 * 
	 */
	@RequestMapping(value = "FsGetFirstList", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsGetFirstList(@RequestBody FirstListVo Vo, HttpSession httpSession) {

		FirstListVo returnVo = new FirstListVo();
		// 分页
		PageHelper.startPage(Vo.getCurrentPage() + 1, Vo.getPageCount());
		
		List<FirstListVo> fsGetAllTags = FsGetFirstListService.getFsGetAllTags();
		returnVo.setOutDTO(fsGetAllTags);

		for (FirstListVo firstListVo : returnVo.getOutDTO()) {
			List<Map<String, Object>> fsGetUrlsByTag = FsGetFirstListService
					.getFsGetUrlsByTag(firstListVo.getTagCode());
			firstListVo.setUrlList(fsGetUrlsByTag);
		}
		return new JsonResponse().success(returnVo);
	}

}
