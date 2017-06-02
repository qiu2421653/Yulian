package com.njkj.yulian.entity;

import java.util.List;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:其他人爱情
 * 
 * @date 2016-6-2 上午9:49:25
 * 
 * @version 1.0 ==============================
 */
public class OtherLoveEntity extends BaseEntity {
	private static final long serialVersionUID = -2514674764175144462L;
	/**
	 * 我的爱情经历
	 */
	public List<LoveEntity> outDTO;
	/**
	 * 粉丝
	 */
	public String funsNum;
	/**
	 * 关注
	 */
	public String forkNum;
	/**
	 * 是否关注(1:true;2:false)
	 */
	public String isFollow;
	/**
	 * 对方的个人信息
	 */
	public UserEntity userInfo;

}
