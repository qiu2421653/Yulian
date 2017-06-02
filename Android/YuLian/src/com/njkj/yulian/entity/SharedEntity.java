package com.njkj.yulian.entity;

/**
 * 分享信息
 * 
 * @author Qiu
 * 
 */
public class SharedEntity extends BaseEntity {

	private static final long serialVersionUID = -6202758671221367416L;

	/**
	 * comment是我对这条分享的评论，仅在人人网和QQ空间使用
	 */
	public String comment;
	/**
	 * 网络图片地址
	 */
	public String imageUrl;
	/**
	 * site是分享此内容的网站名称，仅在QQ空间使用
	 */
	public String site;
	/**
	 * siteUrl是分享此内容的网站地址，仅在QQ空间使用
	 */
	public String siteUrl;
	/**
	 * 是分享文本，所有平台都需要这个字段
	 */
	public String text;
	/**
	 * 需要传入title
	 */
	public String title;
	/**
	 * titleUrl是标题的网络链接，仅在人人网和QQ空间使用
	 */
	public String titleUrl;
	/**
	 * url仅在微信（包括好友和朋友圈）中使用
	 */
	public String url;

}
