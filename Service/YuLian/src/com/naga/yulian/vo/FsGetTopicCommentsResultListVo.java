package com.naga.yulian.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取评论列表结果集Vo
 * 
 * @author wangyan
 *
 */
public class FsGetTopicCommentsResultListVo {
    
    // uuid
    private String uuid;
    
    // 评论内容
    private String comment;
    
    // 用户昵称
    private String nickName;
    
    // 用户头像
    private String hPic;
    
    // 评论时间
    private String timeLag;
    
    // 用户id
    private String userId;
    
    // 回复内容
    private String replyComment;
    
    // 回复人id
    private String fromUserId;
    
    // 回复人姓名
    private String fromName;
    
    // 被回复人id
    private String toUserId;
    
    // 被回复人姓名
    private String toName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReplyComment() {
		return replyComment;
	}

	public void setReplyComment(String replyComment) {
		this.replyComment = replyComment;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

    
}