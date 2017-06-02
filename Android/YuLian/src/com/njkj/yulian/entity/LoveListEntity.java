package com.njkj.yulian.entity;

import java.util.List;

public class LoveListEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2514674764175144462L;

	public String isSuccess;// 0:失败;1:成功
	public List<LoveEntity> outDTO;// 我的爱情经历
	public String funsNum;// 粉丝
	public String forkNum;// 关注

	public UserEntity userInfo;

}
