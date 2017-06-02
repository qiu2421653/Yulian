package com.naga.yulian.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class LoadOutVo {
	/** UUID */
	private String uuid;
	/** 手机号码 */
	private String userId;
	/** 头像URL */
	private String hPic;
	/** 昵称 */
	private String name;
	/** 生日 */
	private String birthday;
	/** 性别 */
	private String sex;
	/** 职业 */
	private String job;
	/** 积分 */
	private String point;
	/** 货币 */
	private String currency;
	/** 级别 */
	private String clas;
	/** 备注 */
	private String remark;
	/** 微信号码 */
	private String vx;
	/** QQ号码 */
	private String qq;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String gethPic() {
		return hPic;
	}
	public void sethPic(String hPic) {
		this.hPic = hPic;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}
	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * @return the point
	 */
	public String getPoint() {
		return point;
	}
	/**
	 * @param point the point to set
	 */
	public void setPoint(String point) {
		this.point = point;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the clas
	 */
	public String getClas() {
		return clas;
	}
	/**
	 * @param clas the clas to set
	 */
	public void setClas(String clas) {
		this.clas = clas;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the vx
	 */
	public String getVx() {
		return vx;
	}
	/**
	 * @param vx the vx to set
	 */
	public void setVx(String vx) {
		this.vx = vx;
	}
	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
