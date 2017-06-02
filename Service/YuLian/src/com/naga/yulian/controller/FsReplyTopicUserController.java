package com.naga.yulian.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
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
import com.naga.yulian.dao.ReplyMapper;
import com.naga.yulian.dao.ScoreMapper;
import com.naga.yulian.entity.Reply;
import com.naga.yulian.vo.FsReplyTopicUserVo;
import com.naga.yulian.vo.FsSetTopicForkVo;

/**
 * 回复帖子/人
 */
@RestController
public class FsReplyTopicUserController {
	private static final Logger logger = LoggerFactory.getLogger(FsReplyTopicUserController.class);

	@Autowired
	private ReplyMapper replyMapper;

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
	@RequestMapping(value = "FsReplyTopicUser", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsReplyTopicUser(@RequestBody FsReplyTopicUserVo vo) {
		// 实例化
		Reply reply = new Reply();
		// 设值
		reply.setUuid(UUID.randomUUID().toString());
		reply.setTopId(vo.getInfoId());
		reply.setContent(vo.getMessage());
		if (StringUtils.isNotBlank(vo.getReplyID())) {
			reply.setToUserId(vo.getReplyID());
		}
		reply.setCreaterId(vo.getUserID());
		reply.setCreateDate(new Date());
		reply.setStatus((short) 10);
		if (StringUtils.isNotBlank(vo.getCommentId())) {
			reply.setPartentId(vo.getCommentId());
		}
		// 插入
		if (replyMapper.insertSelective(reply) == 0) {
			logger.error("评论失败");
			return new JsonResponse().failure(new ApiException("XXX", "评论失败"));
		}

		// 查询
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuId", vo.getUserID());

		int dateStte = scoreMapper.selectUpdateState(map);
		int state = 0;
		if (dateStte == 1) {
			// 日期一致
			state = scoreMapper.updateCommentScore(map);
		} else {
			// 日期不一致
			state = scoreMapper.updateCommentDateScore(map);
		}
		if (state == 1)
			goldMapper.updateCommentGold(map);

		// 成功则实例化
		FsSetTopicForkVo resultVo = new FsSetTopicForkVo();
		resultVo.setIsSuccess(1);
		// 返回
		return new JsonResponse().success(resultVo);
	}

}
