package com.naga.yulian.vo;

/**
 * 
 * 附近的人
 * 
 * @author Qiu
 *
 */
public class FsGetNearUsersInVo {

	/** 用户Id */
	private String userId;

	/** 经度 */
	private double latitude;

	/** 纬度 */
	private double longitude;

	/** 当前页数 */
	private int currentPage;

	/** 每页显示数 */
	private int pageCount;

	/** 半径 */
	private int radius;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
