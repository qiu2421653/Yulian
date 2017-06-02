package com.njkj.yulian.entity;

public class MallEntity extends BaseEntity {
	private String name;// 商品名称
	private String integral;// 积分
	private String price;// 参考价格
	public String mailId;// 商品Id
	public String typeId;// 类别Id
	public String comeFrom;// 来源
	
	

	public String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	private int image;// 图片
}
