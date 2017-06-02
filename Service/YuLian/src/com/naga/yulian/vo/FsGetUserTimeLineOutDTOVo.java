package com.naga.yulian.vo;

import java.util.List;

public class FsGetUserTimeLineOutDTOVo {
	
	// 头像图片
    private String hPic;

    // 时间轴列表
    private List<FsGetUserTimeLineOutDTOTimeListVo> timeList;
    
    // 背景图片
    private String topicThumb;

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
	}

	public String getTopicThumb() {
		return topicThumb;
	}

	public void setTopicThumb(String topicThumb) {
		this.topicThumb = topicThumb;
	}

	public List<FsGetUserTimeLineOutDTOTimeListVo> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<FsGetUserTimeLineOutDTOTimeListVo> timeList) {
		this.timeList = timeList;
	}
    
    
}