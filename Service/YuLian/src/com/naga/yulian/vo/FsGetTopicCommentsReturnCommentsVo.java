package com.naga.yulian.vo;

import java.util.List;

/**
 * 返回评论Vo
 * 
 * @author wangyan
 *
 */
public class FsGetTopicCommentsReturnCommentsVo {
    
    // 评论id
    private String commentId;
    
    // 评论内容
    private String comment;
    
    // 用户昵称
    private String nickName;
    
    // 用户头像
    private String hPic;
    
    // 回复列表
    private List<FsGetTopicCommentsReturnReplyVo> replyList;
    
    // 评论时间
    private String timeLag;
    
    // 用户id
    private String userId;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
	}

	public List<FsGetTopicCommentsReturnReplyVo> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<FsGetTopicCommentsReturnReplyVo> replyList) {
		this.replyList = replyList;
	}

	public String getTimeLag() {
		return timeLag;
	}

	public void setTimeLag(String timeLag) {
		this.timeLag = timeLag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}