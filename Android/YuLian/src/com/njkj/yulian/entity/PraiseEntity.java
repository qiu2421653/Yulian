package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:赞
 * 
 * @date 2016-3-28 下午6:39:41
 * 
 * @version 1.0 ==============================
 */
public class PraiseEntity extends BaseEntity {
	private static final long serialVersionUID = 2464845512579356961L;

	/**
	 * 用户ID
	 * */
	public String userId;
	public String userID;

	/**
	 * 主题Id
	 * */
	public String topicId;
	/**
	 * 头像地址
	 */
	public String hPic;
	/**
	 * 昵称
	 */
	public String nickName;
	/**
	 * 点赞时间
	 */
	public String forkTime;
	/**
	 * 点赞日期
	 */
	public String forkDate;

	/**
	 * 被点赞主题图片
	 */
	public String topicThumb;

	public ArrayList<PraiseEntity> fsGetForksVo;

}
