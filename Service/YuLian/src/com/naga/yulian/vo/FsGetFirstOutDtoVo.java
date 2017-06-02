package com.naga.yulian.vo;

import java.util.List;

public class FsGetFirstOutDtoVo {
	private String tag;
	
	private String tagId;
	
	private List<FirstUrlListVo> url;
	
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	   /**
     * @return the tagId
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * @param tagId the tagId to set
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    
    public List<FirstUrlListVo> getUrl() {
        return url;
    }

    public void setUrlList(List<FirstUrlListVo> url) {
        this.url = url;
    }
}
