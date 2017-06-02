package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description: 打赏
 * 
 * @date 2016-3-28 下午1:42:18
 * 
 * @version 1.0 ==============================
 */
public class ReWardEntity extends BaseEntity {
	private static final long serialVersionUID = 2464845512579356961L;
	/**
	 * 昵称
	 */
	public String nickName;
	/**
	 * 头像
	 */
	public String hPic;
	/**
	 * 打赏金额
	 */
	public String reward;
	/**
	 * 打赏日期
	 */
	public String rewardDate;
	/**
	 * 打赏时间
	 */
	public String rewardTime;
	/**
	 * 时间间隔
	 */
	public String timeLag;
	/**
	 * 主题Id
	 */
	public String topicId;
	/**
	 * 主题图片
	 */
	public String topicThumb;
	/**
	 * 用户Id
	 */
	public String userID;

	public ArrayList<ReWardEntity> fsGetRewardListVo;
}
