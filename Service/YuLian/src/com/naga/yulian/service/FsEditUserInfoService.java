package com.naga.yulian.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.GoldMapper;
import com.naga.yulian.dao.MediaExpandMapper;
import com.naga.yulian.dao.ScoreMapper;
import com.naga.yulian.dao.UserExpandMapper;
import com.naga.yulian.entity.User;

@Service("FsEditUserInfoService")
public class FsEditUserInfoService {
	private static final Logger logger = LoggerFactory.getLogger(FsEditUserInfoService.class);

	@Autowired
	private UserExpandMapper userExpandEntityMapper;

	@Autowired
	private ScoreMapper scoreMapper;

	@Autowired
	private GoldMapper goldMapper;

	@Autowired
	private MediaExpandMapper mediaExpandMapper;

	/**
	 * 编辑用户个人信息
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return FsGetCommentListVo
	 */
	public int editUserInfoByUserId(User user) {

		// record.setCreaterId(userID);
		return userExpandEntityMapper.editUserInfoByUserId(user);
	}
	
	/**
	 * 编辑用户个人信息
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return FsGetCommentListVo
	 */
	public int editUserInfoByUuId(User user) {
		
		// record.setCreaterId(userID);
		return userExpandEntityMapper.editUserInfoByUuId(user);
	}

	/**
	 * 更新积分(全部资料)
	 * 
	 * @param score
	 * @return
	 */
	public int updateScore(String uuId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuId", uuId);
		return scoreMapper.updateAllScore(map);
	}

	/**
	 * 更新金币(全部资料)
	 * 
	 * @param score
	 * @return
	 */
	public int updateAllGold(String uuId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuId", uuId);
		return goldMapper.updateAllGold(map);
	}

	/**
	 * 删除media表中与该用户有关的图片信息
	 * 
	 * @param 当前页
	 * @param 每页显示
	 * @param 用户ID
	 * @return FsGetCommentListVo
	 */
	public int deleteInfoByUserId(String uuId) {

		// record.setCreaterId(userID);
		return mediaExpandMapper.deleteInfoByUserId(uuId);
	}

}