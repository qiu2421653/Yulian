package com.njkj.yulian.entity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:极光推送
 * 
 * @date 2016-6-17 上午10:27:52
 * 
 * @version 1.0 ==============================
 */
public class JPushEntity extends BaseEntity {

	private static final long serialVersionUID = 4442872453661840951L;
	/**
	 * topicId
	 */
	public String topicId;
	/**
	 * 推送的信息
	 */
	public String msg;
	/**
	 * 提示头
	 */
	public String title;
}
