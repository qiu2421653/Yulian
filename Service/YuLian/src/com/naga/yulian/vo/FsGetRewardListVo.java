package com.naga.yulian.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 返回打赏页列表VO
 * 
 * @author songwei
 *
 */
public class FsGetRewardListVo {

	/** 昵称 **/
	private String nickName;

	/** 打赏禹币 **/
	private String reward;

	/** 打赏日期 **/
	private String rewardDate;

	/** 打赏时间 **/
	private String rewardTime;
	
    // 评论时间
    private String timeLag;

	/** 主贴Id **/
	private String topicId;

	/** 主题图片 **/
	private String topicThumb;

	/** 用户ID **/
	private String userID;
	
	/** 用户ID **/
	private String hPic;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getRewardDate() {
		return rewardDate;
	}

	public void setRewardDate(String rewardDate) {
		this.rewardDate = rewardDate;
	}

	public String getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(String rewardTime) {
		this.rewardTime = rewardTime;
	}
	
	public String getTimeLag() {
		return timeLag;
	}

	@SuppressWarnings("deprecation")
	public void setTimeLag(String timeLag) {
		//2016-05-11 17:05:24.0
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1 = new Date();
		try {
			date1 = format.parse(timeLag);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date2 = new Date();
		int year1 = date1.getYear();
		int year2 = date2.getYear();
		int month1 = date1.getMonth();
		int month2 = date2.getMonth();
		int day1 = date1.getDate();
		int day2 = date2.getDate();
		int hours1 = date1.getHours();
		int hours2 = date2.getHours();
		int minutes1 = date1.getMinutes();
		int minutes2 = date2.getMinutes();
		
		if(year1 == year2 && month1 == month2 && day1 == day2){
			// 同年同月同日
			if(hours1 == hours2){
				// 1 时间相同 显示分钟前
				this.timeLag = minutes2-minutes1+"分钟前";
			}else{
				// 2 时间不同 显示小时前  
				this.timeLag = hours2-hours1+"小时前";
			}
		}else{
			// 不相同则显示月日
			this.timeLag = month1+1+"月"+day1+"日";
		}
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getTopicThumb() {
		return topicThumb;
	}

	public void setTopicThumb(String topicThumb) {
		this.topicThumb = topicThumb;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
	}

}