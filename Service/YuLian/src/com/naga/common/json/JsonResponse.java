package com.naga.common.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Json返回对象
 *
 */
public class JsonResponse {

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    /** 执行结果 */
    private String success;
    
    /** 返回数据 */
    private Object result;
    
    /** 校验错误内容List */
    private List<ApiException> exceptions = new ArrayList<ApiException>();
    
    /**
     * 成功
     * @return Json返回对象
     */
    public JsonResponse success() {
        this.success = TRUE;
        return this;
    }

    /**
     * 成功
     * @param result 返回数据
     * @return Json返回对象
     */
    public JsonResponse success(Object result) {
    	this.success = TRUE;
        this.result = result;
        return this;
    }

    /**
     * 失败
     * @return Json返回对象
     */
    public JsonResponse failure() {
        this.success = FALSE;
        return this;
    }

    /**
     * 失败
     * @param message 消息内容
     * @param exceptions 校验错误内容List
     * @return Json返回对象
     */
    public JsonResponse failure(List<ApiException> exceptions) {
        this.success = FALSE;
        this.exceptions = exceptions;
        return this;
    }
    
    /**
     * 失败
     * @param message 消息内容
     * @param exceptions 校验错误内容List
     * @return Json返回对象
     */
    public JsonResponse failure(ApiException exception) {
        this.success = FALSE;
        this.exceptions.add(exception);
        return this;
    }

	/**
	 * @return the success
	 */
	public String getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(String success) {
		this.success = success;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the exceptions
	 */
	public List<ApiException> getExceptions() {
		return exceptions;
	}

	/**
	 * @param exceptions the exceptions to set
	 */
	public void setExceptions(List<ApiException> exceptions) {
		this.exceptions = exceptions;
	}

}