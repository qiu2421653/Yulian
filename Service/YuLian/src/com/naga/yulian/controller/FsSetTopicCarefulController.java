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
import com.naga.yulian.service.FsSetTopicCarefulService;
import com.naga.yulian.vo.FsSetTopicCarefulParameter;
import com.naga.yulian.vo.FsSetTopicCarefulVo;

/**
 * 根据用户id帖子id点关注/取消关注
 */
@RestController
public class FsSetTopicCarefulController {
	private static final Logger logger = LoggerFactory.getLogger(FsSetTopicCarefulController.class);

	@Autowired
	private FsSetTopicCarefulService fsSetTopicCarefulService;

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
	@RequestMapping(value = "FsSetTopicCareful", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsSetTopicCareful(@RequestBody FsSetTopicCarefulParameter vo) {
		// 返回值
		int result = 0;

		// 判断是否关注
		if ("1".equals(vo.getIsCareful())) {
			Map<String, String> map = new HashMap<>();
			map.put("uuId", vo.getExpID());
			// 更新积分
			int state = scoreMapper.updateFollow(map);
			// 更新金币
			if (state == 1)
				goldMapper.updateFollowGold(map);

			// 看有无记录
			int count = fsSetTopicCarefulService.selectById(vo.getUserID(), vo.getExpID());
			if (count == 1) {
				// 修改
				result = fsSetTopicCarefulService.updateByCreaterIdAndUserId(vo.getUserID(), vo.getExpID());
			} else {
				// 插入
				result = fsSetTopicCarefulService.FsSetTopicCareful(vo.getUserID(), vo.getExpID());
			}
			// 判断是否成功
			if (result == 0) {
				logger.error("关注失败");
				return new JsonResponse().failure(new ApiException("XXX", "关注失败"));
			}
		} else {
			// 取消关注
			result = fsSetTopicCarefulService.FsupdateCarefulCancel(vo.getUserID(), vo.getExpID());

			// 判断是否成功
			if (result == 0) {
				logger.error("取消失败");
				return new JsonResponse().failure(new ApiException("XXX", "取消失败"));
			}
		}

		// 成功则实例化
		FsSetTopicCarefulVo vos = new FsSetTopicCarefulVo();
		vos.setIsSuccess(result);
		// 返回
		return new JsonResponse().success(vos);
	}

}
