package com.naga.yulian.vo;

/**
 * 返回视频Vo
 * 
 * @author wangyan
 *
 */
public class FsGetUserTopicMovieReturnVideoVo {
	
    // 视频标题
    private String title;
	
	// 视频显示图片
	private String videoThumb;
	
	// 视频url
	private String videoUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideoThumb() {
		return videoThumb;
	}

	public void setVideoThumb(String videoThumb) {
		this.videoThumb = videoThumb;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}