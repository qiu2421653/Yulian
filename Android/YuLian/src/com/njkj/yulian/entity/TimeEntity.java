package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:时间轴
 * 
 * @date 2016-4-11 上午10:24:57
 * 
 * @version 1.0 ==============================
 */
public class TimeEntity extends BaseEntity {

	public String hPic;

	public String topicId;

	public String time;

	public String tag;

	public String topicThumb;

	public String date = null;

	// 内容
	public String content;

	// 图片地址集合
	public ArrayList<PictureEntity> urlList;

	public ArrayList<TimeEntity> timeList;

	public TimeEntity outDTO;

}
