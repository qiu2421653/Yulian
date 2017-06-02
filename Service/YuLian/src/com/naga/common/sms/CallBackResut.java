package com.naga.common.sms;

/**
 * 调用语音验证返回结果
 * @author sks
 *
 */
public class CallBackResut {
	public CallBackResut(){
	}
	
	/** 错误码: SUCCESS、成功，其他均失败 */
	private String result;
	/** 错误描述 */
	private String msg;
	/** 返回发送唯一标识 */
	private String sid;
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the sid
	 */
	public String getSid() {
		return sid;
	}
	/**
	 * @param sid the sid to set
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}
}
