package com.naga.yulian.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.FsGetGoldExpandMapper;
import com.naga.yulian.dao.UserExpandMapper;
import com.naga.yulian.vo.FsGetGoldVo;
import com.naga.yulian.vo.FsGetUserInfoVo;

@Service("FsGetGoldService")
public class FsGetGoldService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetGoldService.class);

	@Autowired
	private UserExpandMapper userExpandMapper;

	@Autowired
	private FsGetGoldExpandMapper fsGetGoldExpandMapper;

	/**
	 * 获取禹币积分
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return FsGetCommentListVo
	 */
	public FsGetUserInfoVo getGoldInfoByUserId(String uuId) {

		// Reply record = new Reply();
		// record.setCreaterId(userID);
		return userExpandMapper.selectUserInfoByUserId(uuId);
	}

	/**
	 * 获取评论总数
	 * 
	 * @param userID
	 * @return
	 */
	public BigDecimal getTotalReply(String uuId) {
		return fsGetGoldExpandMapper.getTotalReply(uuId);

	}

	/**
	 * 获取点赞总数
	 * 
	 * @param userID
	 * @return
	 */
	public BigDecimal getTotalPraise(String uuId) {
		return fsGetGoldExpandMapper.getTotalPraise(uuId);

	}

	/**
	 * 获取积分
	 * 
	 * @param userID
	 * @return
	 */
	public FsGetGoldVo getGoldById(String uuId) {
		return fsGetGoldExpandMapper.getGoldById(uuId);

	}

}