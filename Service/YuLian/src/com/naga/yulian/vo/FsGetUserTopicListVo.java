package com.naga.yulian.vo;

/**
 * 获取指定用户主题详情列表传入参数Vo
 * 
 * @author wangyan
 *
 */
public class FsGetUserTopicListVo {
	
    // 每页显示数量
    private int pageCount;
    
    // 页数
    private int currentPage;
    
    // 用户id
    private String userId;
    
    // 帖子id
    private String topicId;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

}