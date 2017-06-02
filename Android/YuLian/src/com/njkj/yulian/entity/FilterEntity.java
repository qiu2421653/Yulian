package com.njkj.yulian.entity;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:滤镜
 * 
 * @date 2016-4-14 上午11:47:46
 * 
 * @version 1.0 ==============================
 */
public class FilterEntity extends BaseEntity {

	public FilterEntity() {
	}

	public FilterEntity(GPUImageFilter gpuFilter) {
		this.gpuFilter = gpuFilter;
	}

	/**   
	 *
	 */

	private static final long serialVersionUID = -3746734447865554839L;

	/**
	 * 滤镜效果
	 */
	public GPUImageFilter gpuFilter;

	/**
	 * 滤镜名称
	 */
	public String filterName;

	/**
	 * 滤镜偏移值
	 */
	public float progress;
	/**
	 * 首次添加
	 */
	public boolean isFirst = true;
}
