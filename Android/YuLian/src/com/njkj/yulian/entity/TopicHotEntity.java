package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:热门帖子List
 * 
 * @date 2016-6-13 上午9:41:48
 * 
 * @version 1.0 ==============================
 */
public class TopicHotEntity extends BaseEntity {

	public ArrayList<AdvertEntity> adverts = new ArrayList<AdvertEntity>();

	public ArrayList<TopicDetailEntity> topicList;

}
