package com.njkj.yulian.entity;

import java.util.ArrayList;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.entity
 * 
 * @Description:商品内容
 * 
 * @date 2016-6-14 下午3:51:35
 * 
 * @version 1.0 ==============================
 */
public class GoodsContentEntity extends BaseEntity {

	private static final long serialVersionUID = -8952815169173833683L;

	public GoodsEntity goods;

	public ArrayList<GoodsEntity> simlar;// 相似

	public ArrayList<GoodsEntity> goodsList;// 相似
}
