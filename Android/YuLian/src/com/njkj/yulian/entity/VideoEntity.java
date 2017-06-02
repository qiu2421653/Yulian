package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:视频信息类
 * 
 * @date 2016-4-7 上午11:39:19
 * 
 * @version 1.0 ==============================
 */
public class VideoEntity {
	/**
	 * 用户ID
	 * */
	public String userId;

	/**
	 * 视频的url
	 * */
	public String videoUrl;

	/**
	 * 视频封面的Url
	 * */
	public String videoThumb;

	/**
	 * 视频描述头
	 * */
	public String title;
	
	public ArrayList<VideoEntity> videoList;

}
