package com.naga.yulian.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Score {

	private String uuid;

	private String fkId;

	private Date updateDate;

	private int post;

	private int comment;

	private int fork;

	private int tag;

	private int store;

	private int firstFollow;

	private int report;

	private int isAll;

	private int myScore;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getFork() {
		return fork;
	}

	public void setFork(int fork) {
		this.fork = fork;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getStore() {
		return store;
	}

	public void setStore(int store) {
		this.store = store;
	}

	public int getFirstFollow() {
		return firstFollow;
	}

	public void setFirstFollow(int firstFollow) {
		this.firstFollow = firstFollow;
	}

	public int getReport() {
		return report;
	}

	public void setReport(int report) {
		this.report = report;
	}

	public int getIsAll() {
		return isAll;
	}

	public void setIsAll(int isAll) {
		this.isAll = isAll;
	}

	public int getMyScore() {
		return myScore;
	}

	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}
	
	

}