package com.njkj.yulian.entity;

import android.graphics.Bitmap;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:我的告白
 * 
 * @date 2016-4-20 下午3:52:50
 * 
 * @version 1.0 ==============================
 */
public class ConfessionEntity {
	/**
	 * 用户ID
	 * */
	public String userId;

	/**
	 * 视频的url
	 * */
	public String videoUrl;

	/**
	 * 视频描述头
	 * */
	public String title;

	/**
	 * 显示(true)|隐藏(false)
	 * */
	public boolean isShow;

	/**
	 * 封面的bitmap
	 * */
	public Bitmap thumbBitMap;

	public long lastModified;

}
