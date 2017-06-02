package com.njkj.yulian.entity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.naga.love.entity
 * 
 * @Description:版本更新
 * 
 * @date 2016-3-8 上午9:29:17
 * 
 * @version 1.0 ==============================
 */
public class UpgradeEntity extends BaseEntity {

	private static final long serialVersionUID = -3852195373619126275L;
	/**
	 * 提示描述
	 * */
	public String remarks;
	/**
	 * apk地址
	 * */
	public String path;
	/**
	 * 强制更新标志(0:false;1:true)
	 * */
	public int forced;
	/***
	 * 系统号
	 */
	public int system;

	/**
	 * 系统号
	 */
	public String versionno;

}
