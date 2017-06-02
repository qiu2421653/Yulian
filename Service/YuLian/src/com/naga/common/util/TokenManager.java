package com.naga.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

/**
 * Token管理类
 */
public class TokenManager {

    private static Map<String, List<String>> tokenMap = new ConcurrentHashMap<>();

    public static final String TOKEN_MANAGER_NAME = "tokenManager";
    /** 超时时间 （毫秒） */
    @Value("${system.authority.token.timeout}")
    private long tokenTimeout = 0;
    /** Cookie路径 */
    @Value("${system.authority.cookiePath}")
    private String cookiePath = "";
    /** 存放token的Cookie名 */
    @Value("${system.authority.tokenName}")
    private String tokenName = null;
    
    /**
     * 生成Token,然后把Token追加到response的Cookie中
     * @param response 响应信息
     * @param userId 用户ID
     */
    public void createTokenForApi(HttpServletResponse response, String userId) {
        // 取得当前时间
        Date now = new Date();
        long nowTime = now.getTime();
        // 生成token
        String token = MyCommonUtil.makeUUID();
        if (tokenMap == null) {
        	tokenMap = new ConcurrentHashMap<>();
        }
        List<String> value = new ArrayList<String>();
        // 设置token对应的用户ID
        value.add(userId);
        // 设置token失效的时间
        value.add(String.valueOf(nowTime + tokenTimeout));
        tokenMap.put(token, value);
        // 把token设点Cookie中
        Cookie cookie = new Cookie(tokenName, token);
        // 设置Cookie失效时间（单位：秒）
        cookie.setMaxAge(Integer.parseInt(String.valueOf(tokenTimeout)) / 1000);
        // 设置Cookie路径
        cookie.setPath(cookiePath);
        // 禁止客户端利用Js读取Cookie
        cookie.setHttpOnly(true);
        // 把Cookie追加到响应信息中
        response.addCookie(cookie);
    }

    /**
     * 验证客户端传过来的Token是否有效
     * @param token
     * @return 验证结果（true：Token有效   false：Token无效）
     */
    public boolean checkToken(String token) {
        if (tokenMap == null) {
        	return false;
        }
        // 判断token是否有效
        if (!MyCommonUtil.isEmpty(token) && tokenMap.containsKey(token)) {
            // 取得当前时间
            Date now = new Date();
            long nowTime = now.getTime();
            // 取得token的失效时间
            long checkDate = Long.parseLong(tokenMap.get(token).get(1));
            // 如果token已经失效，删除token，返回false
            if (nowTime >= checkDate) {
                tokenMap.remove(token);
                return false;
            }
            // token有效的场合，更新token的失效时间
            List<String> value = tokenMap.get(token);
            value.remove(1);
            value.add(String.valueOf(nowTime + tokenTimeout));
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 取得token对应的用户ID
     * @param token 
     * @return 取到的用户ID
     */
    public String getUserIdByToken(String token) {
        String userId = "";
        List<String> value = tokenMap.get(token);
        if (value != null) {
            userId = value.get(0);
        }
        return userId;
    }
    
    /**
     * 定时清理超时的Token (执行处理的间隔时间在root-context.xml中配置)
     */
    public void tokenCleanTask() {
        // 取得系统时间
        Date now = new Date();
        long nowTime = now.getTime();
        // 判断Map中的token是否超时
        for (String token : tokenMap.keySet()) {
            // 取得token的失效时间
            long checkDate = Long.parseLong(tokenMap.get(token).get(1));
            // 当前时间大于等于token的失效时间的场合，删除token
            if (nowTime >= checkDate) {
                tokenMap.remove(token);
            }
        }
    }
}
