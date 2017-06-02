package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:搜索用户
 * 
 * @date 2016-4-5 下午1:31:34
 * 
 * @version 1.0 ==============================
 */
public class SearchUserEntity extends BaseEntity {

	private static final long serialVersionUID = 8527669417893680148L;

	public String hPic;
	public String isFork;// 是否关注过
	public String nickName;
	/**
	 * 图片集合
	 * */
	public ArrayList<PictureEntity> urlList = new ArrayList<PictureEntity>();

	public String userID;

	// 推荐用户集合
	public ArrayList<SearchUserEntity> recommend;

	public ArrayList<SearchUserEntity> outDTO;

}
