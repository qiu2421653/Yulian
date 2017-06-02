package com.naga.yulian.vo;

/**
 * 获取分享内容传入参数Vo
 * 
 * @author wangyan
 *
 */
public class FsSharedInfoVo {

    // 用户id
    private String userId;
    
    // 帖子id
    private String topicId;
    
    // 是否分享文章
    private int isShareTopic;

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

	public int getIsShareTopic() {
		return isShareTopic;
	}

	public void setIsShareTopic(int isShareTopic) {
		this.isShareTopic = isShareTopic;
	}

}