package com.naga.yulian.vo;

public class LoadInVo {
	/** 手机号码 */
	private String mobile;
	/** 登录密码 */
	private String passWord;
	/** FACE ID */
	private String faceID;
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
	 * @return the password
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * @return the faceID
	 */
	public String getFaceID() {
		return faceID;
	}

	/**
	 * @param faceID
	 *            the faceID to set
	 */
	public void setFaceID(String faceID) {
		this.faceID = faceID;
	}
}
