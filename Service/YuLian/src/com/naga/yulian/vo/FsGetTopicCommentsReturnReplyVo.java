package com.naga.yulian.vo;

/**
 * 返回回复Vo
 * 
 * @author wangyan
 *
 */
public class FsGetTopicCommentsReturnReplyVo {

	// 回复内容
    private String comment;
    
    // 回复人姓名
    private String fromName;
    
    // 回复人id
    private String fromUserID;
    
    // 被回复人姓名
    private String toName;
    
    // 被回复人id
    private String toUserID;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromUserID() {
		return fromUserID;
	}

	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToUserID() {
		return toUserID;
	}

	public void setToUserID(String toUserID) {
		this.toUserID = toUserID;
	}
    
}