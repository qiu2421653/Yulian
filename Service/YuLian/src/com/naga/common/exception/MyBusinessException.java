package com.naga.common.exception;

import com.naga.common.util.SpringContextUtil;

/**
 * 自定义业务异常类
 * 业务执行时点发生的异常，通过用户操作可以达到正确执行效果的异常
 * 业务异常的message会返回调用端
 */
public class MyBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Object[] params;
	
	private String msgCode;

    /**
     * 构造业务异常
     * @param e 引发业务异常的源异常
     * @param msgCode message编号/message内容
     * @param parms message参数
     */
    public MyBusinessException(Throwable e,String msgCode,Object... parms) {
    	super(msgCode,e);
    	this.params = parms;
    	this.msgCode = msgCode;
    }

    /**
     * 构造业务异常
     * @param msgCode message编号/message内容
     * @param parms message参数
     */
    public MyBusinessException(String msgCode,Object... parms) {
    	super(msgCode);
    	this.params = parms;
    	this.msgCode = msgCode;
    }
    
    @Override
    public String getMessage() {
    	String message = "";
    	try {
    		message = SpringContextUtil.getMessage(msgCode, params);
		} catch (Throwable te) {
			// message取得失败的时候，msgCode作为message内容
			message = msgCode;
		}
    	return message;
    }

	/**
	 * @return the msgCode
	 */
	public String getMsgCode() {
		return msgCode;
	}
}
