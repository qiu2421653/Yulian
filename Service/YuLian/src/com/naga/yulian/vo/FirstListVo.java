package com.naga.yulian.vo;

import java.util.List;
import java.util.Map;

public class FirstListVo {

	private int currentPage;

	private int pageCount;

	private String tag;

	private String tagId;

	private String tagCode;

	private List<Map<String, Object>> urlList;

	private List<FirstListVo> outDTO;

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public List<Map<String, Object>> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<Map<String, Object>> urlList) {
		this.urlList = urlList;
	}

	public List<FirstListVo> getOutDTO() {
		return outDTO;
	}

	public void setOutDTO(List<FirstListVo> outDTO) {
		this.outDTO = outDTO;
	}

}
