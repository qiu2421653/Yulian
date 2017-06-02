package com.njkj.yulian.entity;

import java.util.List;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:禹币
 * 
 * @date 2016-5-17 下午3:21:14
 * 
 * @version 1.0 ==============================
 */
public class GoldEntity extends BaseEntity {

	private static final long serialVersionUID = 5825873972720344265L;

	public String currency;// 禹币
	public String gold;// 禹币
	public String score;// 积分
	public String totalReply;// 评论数
	public String totalPraise;// 点赞数
	public String hPic;// 头像图片
	public String rankDesc;// 我的排名

	public String isAll;// 是否完成全部任务

	public List<IntegralEntity> fsGEtGoldRankVoList;// 排名list
	public List<GoldDetailEntity> fsGetGoldDetailVo;// 禹币明细

}
