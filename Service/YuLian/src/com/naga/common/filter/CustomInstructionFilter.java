package com.naga.common.filter;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.naga.common.json.ApiException;
import com.naga.common.json.JsonResponse;

public class CustomInstructionFilter extends HandlerInterceptorAdapter {
	
	private final static String[] UN_FILTER_METHOD = new String[] {
			"/api/FsRegist", "/api/FsUpdatePwd", "/api/FsLoad", "/api/FsFaceLoad" };

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
//	    	String requestUri = request.getRequestURI();  
//	        String contextPath = request.getContextPath();  
//	        String url = requestUri.substring(contextPath.length());  
//	        
//	        System.out.println("requestUri:"+requestUri);    
//	        System.out.println("contextPath:"+contextPath);    
//	        System.out.println("url:"+url);    
//	        Object mobile = request.getSession().getAttribute("LoginMobile");
//	        if(mobile == null){
//	        	List<String> ufMethod = Arrays.asList(UN_FILTER_METHOD);
//	        	if(!ufMethod.contains(url)){
//	        		JsonResponse json = new JsonResponse().failure(new ApiException("System", "非登录用户"));
//	            	JSONObject responseJSONObject = JSONObject.fromObject(json); 
//	            	response.setCharacterEncoding("UTF-8");  
//	                response.setContentType("application/json; charset=utf-8"); 
//	            	response.getWriter().append(responseJSONObject.toString()).close();
//	            	return false;
//	        	}
//	        }
        return true;
    }
}
