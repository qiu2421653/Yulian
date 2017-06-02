package com.naga.yulian.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.ReplyExpandMapper;
import com.naga.yulian.entity.Reply;
import com.naga.yulian.vo.FsGetCommentTopicVo;

@Service("fsGetCommentListService")
public class FsGetCommentListService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetCommentListService.class);

	@Autowired
	private ReplyExpandMapper replyExpandEntityMapper;

	/**
	 * 获取评论页列表集合
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return FsGetCommentListVo
	 */
	public List<FsGetCommentTopicVo> selectReplyByUserId(String uuId) {

		Reply record = new Reply();
		record.setCreaterId(uuId);
		return replyExpandEntityMapper.selectReplyByUserId(record);
	}

}