package com.naga.yulian.vo;

/**
 * 注册用户
 * 
 * @author sks
 *
 */
public class RegistVo {
	/** 电话号码 */
	private String mobile;
	/** 验证数字 */
	private String validCode;
	/** 电话号码 */
	private String passWord;

	// 经度
	private String latitude;
	// 纬度
	private String longitude;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the validCode
	 */
	public String getValidCode() {
		return validCode;
	}

	/**
	 * @param validCode
	 *            the validCode to set
	 */
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param passWord
	 *            the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}
