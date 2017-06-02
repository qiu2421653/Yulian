package com.naga.yulian.vo;

/**
 * 
 * 附近的人
 * 
 * @author Qiu
 *
 */
public class FsGetNearUsersOutVo {

	/** 经度 */
	private String latitude;

	/** 纬度 */
	private String longitude;

	/** 头像 */
	private String hPic;

	/** 用户Id */
	private String userId;

	/** 距离 */
	private String distance;

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

	public String gethPic() {
		return hPic;
	}

	public void sethPic(String hPic) {
		this.hPic = hPic;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	
	
	

}
