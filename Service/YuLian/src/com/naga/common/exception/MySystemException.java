package com.naga.common.exception;

import com.naga.common.util.SpringContextUtil;

/**
 * 自定义系统异常类
 * 不通过系统管理员的干预，无法恢复的异常。
 * 系统异常的message不会返回调用端，只会输出在log文件中。
 */
public class MySystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Object[] params;
	
	private String msgCode;

    /**
     * 构造系统异常
     * @param e 引发系统异常的源异常
     * @param msgCode message编号/message内容
     * @param parms message参数
     */
    public MySystemException(Throwable e,String msgCode,Object... parms) {
    	super(msgCode,e);
    	this.params = parms;
    	this.msgCode = msgCode;
    }

    /**
     * 构造系统异常
     * @param msgCode message编号/message内容
     * @param parms message参数
     */
    public MySystemException(String msgCode,Object... parms) {
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
