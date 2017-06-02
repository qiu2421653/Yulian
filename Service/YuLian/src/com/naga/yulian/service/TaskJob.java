package com.naga.yulian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naga.yulian.dao.GoldMapper;
import com.naga.yulian.dao.ScoreMapper;

/**
 * 
 * 定时任务- 更新禹币积分
 * 
 * @author Qiu
 *
 */
@Service
public class TaskJob {

	@Autowired
	private ScoreMapper scoreMapper;

	@Autowired
	private GoldMapper goldMapper;

	/**
	 * 定时任务
	 */
	public void job() {
		// 更新积分
		int updateScore = updateAllUserAddScore();
		// 更新禹币
		int updateGlod = updateAllUserAddGold();
	}

	/**
	 * 更新积分(前十名)
	 * 
	 * @return
	 */
	public int updateAllUserAddScore() {
		return scoreMapper.updateAllUserAddScore();
	}

	/**
	 * 更新积分(前十名)
	 * 
	 * @return
	 */
	public int updateAllUserAddGold() {
		return goldMapper.updateAllUserAddGold();
	}
}