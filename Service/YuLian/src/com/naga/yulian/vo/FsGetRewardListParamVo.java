package com.naga.yulian.vo;

/**
 * 获取打赏页列表集合传入参数Vo
 * 
 * @author songwei
 *
 */
public class FsGetRewardListParamVo {

	// 每页显示数量
	private int pageCount;

	// 页数
	private int currentPage;

	// 用户id
	private String uuId;

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

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

}