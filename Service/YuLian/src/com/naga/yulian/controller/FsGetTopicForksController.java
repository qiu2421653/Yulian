package com.naga.yulian.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.naga.yulian.service.FsSetTopicForkService;
import com.naga.yulian.vo.FsGetTopicForks;
import com.naga.yulian.vo.FsGetTopicForksResultListVo;
import com.naga.yulian.vo.FsGetTopicForksVo;

import net.sf.json.JSONObject;

/**
 * 根据帖子id分页取点赞人信息
 */
@RestController
public class FsGetTopicForksController {
    private static final Logger logger = LoggerFactory.getLogger(FsGetTopicForksController.class);
   
    @Autowired
    private FsSetTopicForkService fsSetTopicForkService;

    
    /**
     * 根据用户id获取时间轴内容
     * 
     * @param userId
     * @param httpSession
     * @return
     */
	@RequestMapping(value = "FsGetTopicForks", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetTopicForks(
			@RequestBody FsGetTopicForksVo vo) {
		// 实例化
		FsGetTopicForks forks = new FsGetTopicForks();
		List<FsGetTopicForksResultListVo> list = new ArrayList<>();
		// 分页
		PageHelper.startPage(vo.getForkPage()+1, vo.getForkCount());
		// 根据帖子id获取点赞列表
		list = fsSetTopicForkService.fsGetTopicForks(vo.getInfoId(), vo.getForkCount(), vo.getForkPage(),vo.getUserId());
		// 设值
		forks.setForks(list);
		//返回
		return new JsonResponse().success(forks);
	}
    
}
