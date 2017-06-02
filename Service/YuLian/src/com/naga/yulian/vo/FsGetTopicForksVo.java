package com.naga.yulian.vo;

/**
 * 获取点赞列表传参Vo
 * 
 * @author wangyan
 *
 */
public class FsGetTopicForksVo {
	
	// 点赞条数
    private int forkCount;
    
    // 帖子Id
    private String infoId;
    
    // 点赞页数
    private int forkPage;
    
    // 用户id
    private String userId;

	public int getForkCount() {
		return forkCount;
	}

	public void setForkCount(int forkCount) {
		this.forkCount = forkCount;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public int getForkPage() {
		return forkPage;
	}

	public void setForkPage(int forkPage) {
		this.forkPage = forkPage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



}