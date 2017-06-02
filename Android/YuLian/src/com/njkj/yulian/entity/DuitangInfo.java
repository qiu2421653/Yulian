package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:瀑布流标签
 * 
 * @date 2016-4-8 下午1:48:41
 * 
 * @version 1.0 ==============================
 */
public class DuitangInfo extends BaseEntity {

	private static final long serialVersionUID = 3618020798674193685L;
	public int comment;
	public String createTime;
	public int fork;
	public int height;
	public int width;

	public String id = "";
	public String nickName = "";
	public String url = "";

	public String topicId;

	public ArrayList<DuitangInfo> tagList;

}
