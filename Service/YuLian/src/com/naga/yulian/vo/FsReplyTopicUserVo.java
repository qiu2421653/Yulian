package com.naga.yulian.vo;

/**
 * 回复帖子/人 传入参数Vo
 * 
 * @author wangyan
 *
 */
public class FsReplyTopicUserVo {
    
    // 帖子id
    private String infoId;
    
    // 内容
    private String message;
    
    // 用户id
    private String userID;
    
    // 被回复人id
    private String replyID;
    
    // 被回复id
    private String commentId;

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getReplyID() {
		return replyID;
	}

	public void setReplyID(String replyID) {
		this.replyID = replyID;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

}