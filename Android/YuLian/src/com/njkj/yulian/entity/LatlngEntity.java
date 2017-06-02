package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description: 坐标点信息
 * 
 * @date 2016-6-7 下午1:33:46
 * 
 * @version 1.0 ==============================
 */
public class LatlngEntity extends BaseEntity {

	private static final long serialVersionUID = 991441012501952206L;

	/**
	 * 经度
	 */
	public double latitude;
	/**
	 * 纬度
	 */
	public double longitude;

	/**
	 * 距离
	 */
	public String distance;

	/**
	 * 用户Id
	 */
	public String userId;

	/**
	 * 头像地址
	 */
	public String hPic;

	public ArrayList<LatlngEntity> outDTO;

}
