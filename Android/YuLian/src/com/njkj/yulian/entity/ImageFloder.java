package com.njkj.yulian.entity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:本地图片
 * 
 * @date 2016-4-8 下午1:49:00
 * 
 * @version 1.0 ==============================
 */
public class ImageFloder extends BaseEntity {

	private static final long serialVersionUID = -6677406945059837317L;

	/**
	 * 目录
	 */
	private String dir;

	/**
	 * 第一张图片地址
	 */
	private String firstImagePath;

	/**
	 * 图片名
	 */
	private String name;

	/**
	 * 图片数量
	 */
	private int count;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
		int lastIndexOf = this.dir.lastIndexOf("/");
		this.name = this.dir.substring(lastIndexOf);
	}

	public String getFirstImagePath() {
		return firstImagePath;
	}

	public void setFirstImagePath(String firstImagePath) {
		this.firstImagePath = firstImagePath;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
