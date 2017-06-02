package com.njkj.yulian.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 基类
 * 
 * @author Qiu
 * */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 2336647566504414823L;
	/**
	 * 是否成功
	 * */
	public boolean success;
	/**
	 * 异常集合
	 * */
	public ArrayList<Exceptions> exceptions;

	public static class Exceptions {
		/**
		 * 异常信息
		 * */
		public String message;
	}

	public String getErrorMsg() {
		if (exceptions != null && !exceptions.isEmpty())
			return exceptions.get(0).message;
		return "网络或服务器异常,请稍后再试";
	}
}
