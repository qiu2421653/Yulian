package com.naga.yulian.controller;

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
import com.naga.yulian.entity.Accusation;
import com.naga.yulian.service.FsReportTopicService;
import com.naga.yulian.vo.FsSetTopicForkVo;

import net.sf.json.JSONObject;

/**
 * 举报帖子
 */
@RestController
public class FsReportTopicController {
	private static final Logger logger = LoggerFactory.getLogger(FsEditUserInfoController.class);

	@Autowired
	private FsReportTopicService fsReportTopicService;

	/**
	 * 举报帖子
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsReportTopic", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsreportTopic(@RequestBody Accusation accusation) {
		// 返回值
		int result = 0;

		result = fsReportTopicService.insertAccusation(accusation);

		// 判断是否成功
		if (result == 0) {
			logger.error("插入失败");
			return new JsonResponse().failure(new ApiException("XXX", "插入失败"));
		}
		// 成功则实例化
		FsSetTopicForkVo vo = new FsSetTopicForkVo();
		vo.setIsSuccess(result);
		System.out.print(new JSONObject().fromObject(vo)); // TODO
		return new JsonResponse().success(vo);
	}

}
