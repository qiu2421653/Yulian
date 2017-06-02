package com.naga.common.json;

import java.io.Serializable;

/**
 * 校验错误内容
 *
 */
public class ApiException implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 错误ID */
    private String id;
    
    /** 错误消息内容 */
    private String message;

	/**
	 * 构造校验错误内容
	 * @param id 错误ID
	 * @param message 消息内容
	 */
	public ApiException(String id, String message) {
		this.id = id;
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}