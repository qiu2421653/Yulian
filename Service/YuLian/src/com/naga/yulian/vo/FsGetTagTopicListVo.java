package com.naga.yulian.vo;

/**
 * 获取指定 标签列表传入参数Vo
 * 
 * @author Qiu
 *
 */
public class FsGetTagTopicListVo {

	// 每页显示数量
	private int pageCount;

	// 页数
	private int currentPage;

	private String tagCode;

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

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

}