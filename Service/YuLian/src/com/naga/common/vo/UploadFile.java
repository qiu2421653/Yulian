package com.naga.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传文件的基本属性
 * @author HHB
 */
@SuppressWarnings("serial")
public class UploadFile implements Serializable {

	private String fileRealName = "";// 真实文件名

	private String fileType = "";// 文件类型

	private String filePath = "";

	private long fileSize;// 文件大小

	private Date addTime;// 创建时间

	private int height;

	private int width;

	public UploadFile() {
		super();
	}

	public String getFileRealName() {
		return fileRealName;
	}

	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	

}