package com.naga.yulian.vo;

import java.util.List;

/**
 * 返回评论列表Vo
 * 
 * @author wangyan
 *
 */
public class FsGetTopicCommentsReturnVo {
    
    // 评论列表
    private List<FsGetTopicCommentsReturnCommentsVo> comments;

	public List<FsGetTopicCommentsReturnCommentsVo> getComments() {
		return comments;
	}

	public void setComments(List<FsGetTopicCommentsReturnCommentsVo> comments) {
		this.comments = comments;
	}
    
}