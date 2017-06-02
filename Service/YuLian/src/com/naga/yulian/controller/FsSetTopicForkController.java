package com.naga.yulian.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.naga.yulian.dao.GoldMapper;
import com.naga.yulian.dao.ScoreMapper;
import com.naga.yulian.service.FsSetTopicForkService;
import com.naga.yulian.vo.FsSetTopicForkParameter;
import com.naga.yulian.vo.FsSetTopicForkVo;

/**
 * 根据用户id帖子id点赞/取消赞
 */
@RestController
public class FsSetTopicForkController {
	private static final Logger logger = LoggerFactory.getLogger(FsSetTopicForkController.class);

	@Autowired
	private FsSetTopicForkService fsSetTopicForkService;

	@Autowired
	private ScoreMapper scoreMapper;

	@Autowired
	private GoldMapper goldMapper;

	/**
	 * 根据用户id获取时间轴内容
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsSetTopicFork", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsSetTopicFork(@RequestBody FsSetTopicForkParameter vo) {
		// 返回值
		int result = 0;
		boolean flag = false;
		if (vo.getIsFork() == 1) {
			flag = true;
		}
		// 点赞、取消赞
		result = fsSetTopicForkService.fsSetTopicFork(vo.getUserId(), vo.getInfoId(), flag, vo.getStatus());
		// 判断是否成功
		if (result == 0) {
			logger.error("点赞失败");
			return new JsonResponse().failure(new ApiException("XXX", "点赞失败"));
		}

		if (flag) {
			// 查询
			Map<String, String> map = new HashMap<String, String>();
			map.put("uuId", vo.getUserId());
			int state = 0;
			int dateStte = scoreMapper.selectUpdateState(map);
			if (dateStte == 1) {
				// 日期一致
				state = scoreMapper.updateForkScore(map);
			} else {
				// 日期不一致
				state = scoreMapper.updateForkDateScore(map);
			}
			if (state == 1)
				goldMapper.updateForkGold(map);
		}

		// 成功则实例化
		FsSetTopicForkVo returnVo = new FsSetTopicForkVo();
		returnVo.setIsSuccess(result);
		// 返回
		return new JsonResponse().success(returnVo);
	}

}
