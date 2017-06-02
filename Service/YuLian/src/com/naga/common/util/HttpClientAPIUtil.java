package com.naga.common.util;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.naga.common.exception.MySystemException;

/**
 * 从JAVA调用WebAPI的工具类
 */
public class HttpClientAPIUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientAPIUtil.class);
	

	/**
	 * 发送post请求
	 * @param url 目标url
	 * @param parameters post对象json
	 * @return 从目标url的返回结果json
	 */
	public static String post(String url,String parameters) {
		String body = null;
		logger.info(SpringContextUtil.getMessage("msg.common.10022",url,parameters));
		try {
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {

	            HttpPost httpPost = new HttpPost(url); 
	            httpPost.addHeader("Content-type","application/json; charset=utf-8");  
	            httpPost.setHeader("Accept", "application/json");
	    		httpPost.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
	    		long startTime = System.currentTimeMillis(); 
	            CloseableHttpResponse response = httpclient.execute(httpPost);
	            long endTime = System.currentTimeMillis();  
	            try {
	            	int statusCode = response.getStatusLine().getStatusCode();
	        		logger.info(SpringContextUtil.getMessage("msg.common.10023",url,statusCode,(endTime - startTime))); 
	        		if (statusCode != HttpStatus.SC_OK) {
	        			throw new MySystemException("msg.common.10024",url,response.getStatusLine());
	        		}
	                HttpEntity entity = response.getEntity();
	                body = EntityUtils.toString(entity);
	                EntityUtils.consume(entity);
	            } finally {
	            	response.close();
	            }
	        } finally {
	            httpclient.close();
	        }
	        logger.info(SpringContextUtil.getMessage("msg.common.10025",url,body));
		} catch (Exception e) {
			throw new MySystemException(e,"msg.common.10026",url,e.getMessage());
		}
		return body;
	}
}