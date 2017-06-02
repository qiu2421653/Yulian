package com.naga.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.naga.common.util.MyCommonUtil;

/**
 * 跨域管理用Filter
 */
public class CorsFilter implements Filter {
    /** 允许访问的客户端域名 */
    private String allowOrigin;
    /** 允许访问的方法名 */
    private String allowMethods;
    /** 是否允许请求带有验证信息 */
    private String allowCredentials;
    /** 允许服务端访问的客户端请求头 */
    private String allowHeaders;
    /** 允许客户端访问的服务端响应头 */
    private String exposeHeaders;

    /**
     * 初期化处理
     * @param filterConfig 过滤器配置
     * @throws ServletException Servlet异常
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
    }

    /**
     * 设置响应头信息
     * @param req 请求信息
     * @param res 响应信息
     * @param chain 过滤器链表
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (!MyCommonUtil.isEmpty(allowOrigin)) {
            // 允许访问的域名集合
            List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
            if (allowOriginList.size() > 0) {
                // 当前请求的域名
                String currentOrigin = request.getHeader("Origin");
                // 当前请求的域名在允许访问的域名集合中存在的场合
                if (allowOriginList.contains(currentOrigin)) {
                    // 将当前请求的域名放入Access-Control-Allow-Origin响应头
                    response.setHeader("Access-Control-Allow-Origin", currentOrigin);
                }
            }
        }
        if (!MyCommonUtil.isEmpty(allowMethods)) {
            // 将允许访问的方法名放入Access-Control-Allow-Methods响应头
            response.setHeader("Access-Control-Allow-Methods", allowMethods);
        }
        if (!MyCommonUtil.isEmpty(allowCredentials)) {
            // 设置是否允许请求带有验证信息
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
        }
        if (!MyCommonUtil.isEmpty(allowHeaders)) {
            // 将允许服务端访问的客户端请求头放入Access-Control-Allow-Headers响应头
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        if (!MyCommonUtil.isEmpty(exposeHeaders)) {
            // 将允许客户端访问的服务端响应头放入Access-Control-Expose-Headers响应头
            response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
        }
        chain.doFilter(req, res);
    }

    /**
     * 销毁处理
     */
    @Override
    public void destroy() {
    }
}
