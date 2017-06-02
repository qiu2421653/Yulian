package com.naga.yulian.vo;

/**
 * 获取评论列表结果集传入参数Vo
 * 
 * @author wangyan
 *
 */
public class FsGetTopicCommentsVo {
    
    // 每页显示数量
    private int commentCount;
    
    // 页数
    private int commentPage;
    
    // 帖子id
    private String infoId;

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getCommentPage() {
		return commentPage;
	}

	public void setCommentPage(int commentPage) {
		this.commentPage = commentPage;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	
    
}