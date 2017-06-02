package com.naga.yulian.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.common.util.DateUtil;
import com.naga.yulian.entity.Advise;
import com.naga.yulian.service.AdviseService;
import com.naga.yulian.vo.FeedBackVo;

/**
 * 意见反馈
 * 
 * @author liangzhihao
 *
 */
@RestController
public class FsFeedBackController {

	@Autowired
	private AdviseService AdviseService;

	/**
	 * 意见反馈
	 * 
	 * @param vo
	 *            Json数据
	 * @return JsonResponse
	 */
	@RequestMapping(value = "FsFeedBack", method = RequestMethod.POST)
	public @ResponseBody JsonResponse fsFeedBack(@RequestBody FeedBackVo vo,
			HttpSession httpSession) {
		//String createrId = (String)httpSession.getAttribute("LoginUUID");
		// UUID
		String uuid = UUID.randomUUID().toString();
		Advise entity = new Advise();
		entity.setUuid(uuid);
		entity.setCreaterid(vo.getUserID());
		entity.setRemarks(vo.getFeedback());
		entity.setCreatedate(DateUtil.getNowSqlDate());
		AdviseService.insert(entity);
		return new JsonResponse().success();
	}
}
