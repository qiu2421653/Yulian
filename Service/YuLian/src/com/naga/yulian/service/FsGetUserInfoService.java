package com.naga.yulian.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.LevelnameExpandMapper;
import com.naga.yulian.dao.UserExpandMapper;
import com.naga.yulian.entity.Levelname;
import com.naga.yulian.vo.FsGetUserInfoVo;

@Service("FsGetUserInfoService")
public class FsGetUserInfoService {
	private static final Logger logger = LoggerFactory.getLogger(FsGetUserInfoService.class);

	@Autowired
	private UserExpandMapper userExpandMapper;
	@Autowired
	private LevelnameExpandMapper levelnameExpandMapper;

	/**
	 * 获取用户个人信息
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return FsGetCommentListVo
	 */
	public FsGetUserInfoVo selectUserInfoByUserId(String uuId) {

		// Reply record = new Reply();
		// record.setCreaterId(userID);
		return userExpandMapper.selectUserInfoByUserId(uuId);
	}

	/**
	 * 获取当前积分所在的等级
	 * 
	 * @return
	 */
	public Levelname getCurrentLevel(String currentPoint) {

		return levelnameExpandMapper.getCurrentLevel(currentPoint);
	}

}