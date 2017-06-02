package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:标签
 * 
 * @date 2016-5-17 上午11:18:37
 * 
 * @version 1.0 ==============================
 */
public class TagEntity extends BaseEntity {

	private static final long serialVersionUID = -993668701209378544L;

	/** 标签描述 */
	public String tagDesc;

	/** 标签ID */
	public String tagID;

	/** 标签url */
	public String url;

	/** 描述 */
	public String tag;
	
	public String code;

	public boolean isSelected = false;

	public ArrayList<TagEntity> tagDTO;

}
