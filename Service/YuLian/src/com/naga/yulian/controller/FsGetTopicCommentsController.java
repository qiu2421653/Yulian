package com.naga.yulian.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naga.common.json.JsonResponse;
import com.naga.yulian.service.FsGetTopicCommentsService;
import com.naga.yulian.vo.FsGetTopicCommentsResultListVo;
import com.naga.yulian.vo.FsGetTopicCommentsReturnCommentsVo;
import com.naga.yulian.vo.FsGetTopicCommentsReturnReplyVo;
import com.naga.yulian.vo.FsGetTopicCommentsReturnVo;
import com.naga.yulian.vo.FsGetTopicCommentsVo;

/**
 * 根据帖子id分页取评论信息
 */
@RestController
public class FsGetTopicCommentsController {
	private static final Logger logger = LoggerFactory.getLogger(FsGetTopicCommentsController.class);

	@Autowired
	private FsGetTopicCommentsService fsGetTopicCommentsService;

	/**
	 * 根据用户id获取时间轴内容
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "FsGetTopicComments", method = RequestMethod.POST)
	public @ResponseBody JsonResponse FsGetTopicComments(@RequestBody FsGetTopicCommentsVo vo) {
		// 实例化
		FsGetTopicCommentsReturnVo returnVo = new FsGetTopicCommentsReturnVo();
		List<FsGetTopicCommentsResultListVo> list = new ArrayList<>();
		// 根据帖子id获取点赞列表
		list = fsGetTopicCommentsService.fsGetTopicComments(vo.getInfoId(), vo.getCommentCount(), vo.getCommentPage());
		// 声明变量
		String compareUuid = "";
		List<FsGetTopicCommentsReturnCommentsVo> returnList = new ArrayList<>();
		List<FsGetTopicCommentsReturnReplyVo> returnRepList = new ArrayList<>();
		FsGetTopicCommentsReturnCommentsVo returnCommentsVo = new FsGetTopicCommentsReturnCommentsVo();
		FsGetTopicCommentsReturnReplyVo returnReplyVo = new FsGetTopicCommentsReturnReplyVo();
		// 遍历结果
		for (int i = 0; i < list.size(); i++) {
			// 判断取出值是否与比较值相同
			if (!StringUtils.equals(compareUuid, list.get(i).getUuid())) {
				// 不同
				// 第一次进入不添加
				if (i > 0) {
					// 清空
					returnRepList = new ArrayList<>();
					// 添加到list
					returnList.add(returnCommentsVo);
				}
				// 初始化
				returnCommentsVo = new FsGetTopicCommentsReturnCommentsVo();
				// 设值
				compareUuid = list.get(i).getUuid();
				returnCommentsVo.setCommentId(list.get(i).getUuid());
				returnCommentsVo.setComment(list.get(i).getComment());
				returnCommentsVo.setNickName(list.get(i).getNickName());
				returnCommentsVo.sethPic(list.get(i).gethPic());
				returnCommentsVo.setTimeLag(list.get(i).getTimeLag());
				returnCommentsVo.setUserId(list.get(i).getUserId());
			}
			// 如果有回复人id则表示有回复 填入列表中
			if (StringUtils.isNotBlank(list.get(i).getFromUserId())) {
				// 初始化
				returnReplyVo = new FsGetTopicCommentsReturnReplyVo();
				// 设值
				returnReplyVo.setComment(list.get(i).getReplyComment());
				returnReplyVo.setFromName(list.get(i).getFromName());
				returnReplyVo.setFromUserID(list.get(i).getFromUserId());
				returnReplyVo.setToName(list.get(i).getToName());
				returnReplyVo.setToUserID(list.get(i).getToUserId());
				// 加入到list中
				returnRepList.add(returnReplyVo);
				returnCommentsVo.setReplyList(returnRepList);
			}
			// 最后一次循环添加
			if (i == list.size() - 1) {
				// 添加到list
				returnList.add(returnCommentsVo);
			}
		}
		for (FsGetTopicCommentsReturnCommentsVo rr : returnList) {
			if(rr.getReplyList()!=null){
				Collections.reverse(rr.getReplyList());
			}
		}
		
		// 设值
		returnVo.setComments(returnList);
		// 返回
		return new JsonResponse().success(returnVo);
	}

}
