package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:主题详情信息
 * 
 * @date 2016-3-25 上午10:51:59
 * 
 * @version 1.0 ==============================
 */
public class TopicDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -8218705026676486213L;

	/**
	 * 信息ID
	 * */
	public String infoId;
	public String infoID;
	/**
	 * 信息ID
	 * */
	public String topicId;
	/**
	 * 用户ID
	 * */
	public String userId;
	/**
	 * 用户昵称
	 */
	public String nickName;

	/***
	 * 点赞数
	 */
	public int isFork;
	/***
	 * 评论数
	 */
	public int isComment;

	/**
	 * 主题
	 */
	public String topic;
	/**
	 * 内容
	 */
	public String content;
	/**
	 * 标签
	 */
	public String tag;
	/**
	 * 创建时间
	 */
	public String createDate;
	/**
	 * 标签Id
	 */
	public String tagId;

	/**
	 * 创建时间
	 */
	public String createTime;

	public String date;

	public String time;

	public String hPic;
	
	public String url;
	/**
	 * 是否点过赞
	 */
	public boolean isFavort;

	public int isSuccess;

	/**
	 * 图片集合
	 * */
	public ArrayList<PictureEntity> urlList = new ArrayList<PictureEntity>();
	/**
	 * 评论人员集合
	 */
	public ArrayList<CommentEntity> comments = new ArrayList<CommentEntity>();
	/**
	 * TODO 点赞人员集合
	 */
	public ArrayList<CommentEntity> forks = new ArrayList<CommentEntity>();
	/**
	 * 当前用户
	 */
	// public UserEntity userEntity;

	public ArrayList<TopicDetailEntity> outDTO;

	public ArrayList<TopicDetailEntity> topicList;

}
