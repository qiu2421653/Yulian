package com.naga.yulian.vo;

public class FsGetForksParamVo {


	// 每页显示数量
	private int pageCount;

	// 页数
	private int currentPage;

	// 用户id
	private String userId;

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

}