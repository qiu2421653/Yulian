package com.naga.yulian.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import freemarker.template.SimpleDate;

public class FsGetGoldDetailVo {

	// 当前操作数据
	private String curAmount;

	// 时间
	private String dateTime;

	// 类型
	private String description;

	// 本次操作数据
	private String oprAmount;

	// 打赏类型
	private String type;

	public String getCurAmount() {
		return curAmount;
	}

	public void setCurAmount(String curAmount) {
		this.curAmount = curAmount;
	}

	public String getDateTime() {

		return dateTime;
	}

	public void setDateTime(String dateTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date parse = format.parse(dateTime);
			this.dateTime = format.format(parse);
		} catch (ParseException e) {
			this.dateTime = dateTime;
			e.printStackTrace();
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOprAmount() {
		return oprAmount;
	}

	public void setOprAmount(String oprAmount) {
		this.oprAmount = oprAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}