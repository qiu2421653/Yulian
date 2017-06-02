package com.njkj.yulian.entity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:用户信息
 * 
 * @date 2016-3-25 下午5:38:10
 * 
 * @version 1.0 ==============================
 */
public class UserEntity extends BaseEntity {
	private static final long serialVersionUID = 2464845512579356961L;

	/**
	 * 用户ID
	 * */
	public String uuid;
	public String userID;
	/**
	 * 头像地址
	 */
	public String hPic;
	/**
	 * 昵称
	 */
	public String nickName;

	/**
	 * 是否关注
	 */
	public boolean isConcern;
	/**
	 * 当前禹币
	 */
	public String currency;

	public String name;// 用户昵称
	public String sex;// 性别
	public String job;// 职业
	public String clas;// 用户等级
	public String point;// 积分
	public String face;// face++认证
	public String end; // 等级结束积分
	public String levelname; // 等级称谓

}
