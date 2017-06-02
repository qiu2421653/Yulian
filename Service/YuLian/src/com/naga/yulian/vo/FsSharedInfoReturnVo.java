package com.naga.yulian.vo;

/**
 * 获取分享内容传入参数Vo
 * 
 * @author wangyan
 *
 */
public class FsSharedInfoReturnVo {
	
    // 图片url
    private String imageUrl;
    
    // 站点url
    private String siteUrl;
    
    // 内容
    private String text;

    // 标题
    private String title;

    // 标题url
    private String titleUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleUrl() {
		return titleUrl;
	}

	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}
    
}