package com.naga.yulian.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetSearchHistoryService;
import com.naga.yulian.vo.FsGetSearchUserOutDTOVo;
import com.naga.yulian.vo.FsGetSearchUserVo;
import com.naga.yulian.vo.FsSearchUserParameter;

/**
 * 搜索标签
 * 
 * @author miaowei
 *
 */
@RestController
public class FsGetSearchHistoryController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetSearchHistoryController.class);

	@Autowired
	private FsGetSearchHistoryService fsGetSearchHistoryService;

	/**
	 * 获得主题列表
	 * 
	 */
	@RequestMapping(value = "FsGetSearchHistory", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsSearchUser(@RequestBody FsSearchUserParameter vo) {
		FsGetSearchUserVo a = new FsGetSearchUserVo();
		List<FsGetSearchUserOutDTOVo> fsGetTopicList = fsGetSearchHistoryService
				.FsGetSearchHistoryMapper(vo.getUserID(), "");
		a.setOutDTO(fsGetTopicList);
		a.setToken("IamToken");
		return new JsonResponse().success(a);
	}
}
