package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:评论信息
 * 
 * @date 2016-3-28 下午1:42:18
 * 
 * @version 1.0 ==============================
 */
public class CommentEntity extends BaseEntity {
	private static final long serialVersionUID = 2464845512579356961L;

	/**
	 * 用户ID
	 * */
	public String userId;
	public String userID;
	/**
	 * 帖子Id
	 * */
	public String topicId;
	/**
	 * 是否关注(0:false; 1:true)
	 * */
	public String isFollowed;
	/**
	 * 头像地址
	 */
	public String hPic;
	/**
	 * 昵称
	 */
	public String nickName;
	/**
	 * 来自用户的昵称
	 */
	public String fromName;
	/**
	 * 送达用户的昵称
	 */
	public String toName;
	/**
	 * 来自用户的Id
	 */
	public String fromUserID;
	/**
	 * 回复内容
	 */
	public String comment;
	/**
	 * 回复Id
	 */
	public String commentId;
	/**
	 * 发布时间
	 */
	public String createTime;
	/**
	 * 时间差
	 */
	public String timeLag;
	/**
	 * 被评论主题图片
	 */
	public String topicThumb;

	public ArrayList<CommentEntity> replyList;

	public ArrayList<CommentEntity> fsGetRewardListVo;
	public ArrayList<CommentEntity> fsGetCommentVo;
}
