package com.naga.yulian.dao;

import java.math.BigDecimal;

import com.naga.yulian.vo.FsGetGoldVo;

public interface FsGetGoldExpandMapper {

	/**
	 * 获取评论总数
	 * 
	 * @param userID
	 * @return
	 */
	BigDecimal getTotalReply(String uuId);

	/**
	 * 获取点赞总数
	 * 
	 * @param user
	 * @return
	 */
	BigDecimal getTotalPraise(String uuId);

	/**
	 * 获取积分
	 * 
	 * @param user
	 * @return
	 */
	FsGetGoldVo getGoldById(String uuId);
}