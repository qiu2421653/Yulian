package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:主题(首页)
 * 
 * @date 2016-3-24 下午2:36:21
 * 
 * @version 1.0 ==============================
 */
public class TopicEntity extends BaseEntity {

	private static final long serialVersionUID = -7004792103035530343L;
	/**
	 * 用户ID
	 * */
	public String userID;
	/**
	 * 主题ID
	 * */
	public String topicId;
	/**
	 * 主题ID
	 * */
	public String infoId;
	/**
	 * 头像地址
	 */
	public String hPic;
	/**
	 * 昵称
	 */
	public String nickName;
	/**
	 * 创建时间
	 */
	public String createTime;
	/**
	 * 创建描述
	 */
	public String createDescription;
	/**
	 * 情感状态(1:暗恋中;2:追求中;3:热恋中;4:分手中)
	 */
	public String eEmotionalState;
	/**
	 * 是否关注(1:true;0:false)
	 */
	public String isFork;
	/**
	 * 推送描述
	 */
	public String pushDesc;
	/**
	 * 主题背景地址
	 */
	public String themePic;

	public MallEntity mailTag;

	public ArrayList<TopicEntity> topicList = new ArrayList<TopicEntity>();

	public ArrayList<TagEntity> recommTags = new ArrayList<TagEntity>();

	public ArrayList<TagEntity> firstTags = new ArrayList<TagEntity>();

	public ArrayList<AdvertEntity> adverts = new ArrayList<AdvertEntity>();

}
