package com.naga.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.naga.common.exception.MyBusinessException;
import com.naga.common.exception.MySystemException;
import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;
import com.naga.common.util.SpringContextUtil;

/**
 * 统一处理异常的Controller类
 */
@ControllerAdvice
@ResponseBody
public class MyControllerAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    	String message = SpringContextUtil.getMessage("msg.common.10006");
        logger.error(message,e);
        ApiException exception = new ApiException("msg.common.10006", message);
        return new JsonResponse().failure(exception);
    }
    
    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JsonResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    	String message = SpringContextUtil.getMessage("msg.common.10005");
        logger.error(message,e);
        ApiException exception = new ApiException("msg.common.10005", message);
        return new JsonResponse().failure(exception);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public JsonResponse handleHttpMediaTypeNotSupportedException(Exception e) {
    	String message = SpringContextUtil.getMessage("msg.common.10004");
        logger.error(message,e);
        ApiException exception = new ApiException("msg.common.10004", message);
        return new JsonResponse().failure(exception);
    }
    
    /**
     * //403 FORBIDDEN 服务器已经理解请求，但是拒绝执行它。
     * 200 - accepted
     * 业务异常发生时，返回业务异常message
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(MyBusinessException.class)
    public JsonResponse handleException(MyBusinessException e) {
    	String message = SpringContextUtil.getMessage("msg.common.10001");
        logger.error(message);
        ApiException exception = new ApiException("msg.common.10001", message);
        return new JsonResponse().failure(exception);
    }
    
    /**
     * //403 FORBIDDEN 服务器已经理解请求，但是拒绝执行它。
     * 200 - accepted
     * 系统异常发生时，不返回系统异常message
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(MySystemException.class)
    public JsonResponse handleException(MySystemException e) {
    	String message = SpringContextUtil.getMessage("msg.common.10002");
        logger.error(message,e);
        ApiException exception = new ApiException("msg.common.10002", message);
        return new JsonResponse().failure(exception);
    }
    
    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public JsonResponse handleException(Throwable e) {
    	String message = SpringContextUtil.getMessage("msg.common.10003");
        logger.error(message,e);
        ApiException exception = new ApiException("msg.common.10003", message);
        return new JsonResponse().failure(exception);
    }
}
