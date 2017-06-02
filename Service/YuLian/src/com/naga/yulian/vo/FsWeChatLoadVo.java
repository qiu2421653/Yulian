package com.naga.yulian.vo;

/**
 * 对帖子打赏传入参数Vo
 * 
 * @author wangyan
 *
 */
public class FsWeChatLoadVo {
	
	// 头像地址
	private String headimgurl;
	
	// 用户昵称
	private String nickname;
	
	// 微信id
	private String openid;
	
	// 性别
	private int sex;

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

   
	
}