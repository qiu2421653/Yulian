package com.naga.yulian.vo;

import java.util.List;

public class FsGetFirstListVo {
    private List<FsGetFirstCommentListVo> commentList;
    
	private String token ;
	
	private String isSuccess ;

	
    public List<FsGetFirstCommentListVo> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<FsGetFirstCommentListVo> commentList) {
        this.commentList = commentList;
    }
    
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }
}
