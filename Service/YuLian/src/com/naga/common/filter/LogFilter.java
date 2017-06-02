package com.naga.common.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.naga.common.util.SpringContextUtil;
import com.naga.common.wrapper.RequestCachingWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志用Filter
 */
public class LogFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);
    /** 消息前缀 */
    private static final String MESSAGE_PREFIX = "["; 
    /** 消息 */
    public static final String MESSAGE_SUFFIX = "]";
    /** 分隔符 */
    public static final String SPACE_MARK = ";";
    
    /**
     * 初期化处理
     * @param filterConfig 过滤器配置
     * @throws ServletException Servlet异常
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 向日志中输出请求信息和响应信息
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
        
        String root = request.getContextPath();
        String uri = request.getRequestURI();
        boolean isApi = false;
        
        // 判断是否是WEB API访问
        if (uri.startsWith(root + "/api")) {
            isApi = true;
        }
        
        HttpServletRequest requestWrapper = new RequestCachingWrapper(request);
        
        // 打印请求信息
        outputRequest(requestWrapper, isApi);
        
        chain.doFilter(requestWrapper, res);  
        
        // 打印响应信息
        outputResponse(response, isApi);
    }

    /**
     * 销毁处理
     */
    @Override
    public void destroy() {
    }
    
    /**
     * 把Request请求信息输出到日志中
     * @param req Request请求
     * @param isApi (true:WEB API访问的场合 false:WEB API访问以为的场合)
     */
    private void outputRequest(HttpServletRequest request, boolean isApi) {
        // URL
        StringBuilder url = new StringBuilder();
        url.append(MESSAGE_PREFIX);
        url.append(request.getRequestURL());
        url.append(MESSAGE_SUFFIX);
        
        if (!isApi) {
            logger.info(SpringContextUtil.getMessage("msg.common.10027", url));
        } else {
            // 请求方法
            StringBuilder method = new StringBuilder();
            method.append(MESSAGE_PREFIX);
            method.append(request.getMethod());
            method.append(MESSAGE_SUFFIX);
            
            // 消息头
            StringBuilder headerInfo = new StringBuilder();
            headerInfo.append(MESSAGE_PREFIX);
            Enumeration<String> headerNames = request.getHeaderNames();
            String headerName = null;
            while (headerNames.hasMoreElements()) {
                headerName = headerNames.nextElement();
                headerInfo.append(headerName);
                headerInfo.append(MESSAGE_PREFIX);
                headerInfo.append(request.getHeader(headerName));
                headerInfo.append(MESSAGE_SUFFIX);
                headerInfo.append(SPACE_MARK);
            }
            // 删除最后一个分隔符
            if (headerInfo.toString().endsWith(SPACE_MARK)) {
                headerInfo.delete(headerInfo.length() - 1, headerInfo.length());
            }
            headerInfo.append(MESSAGE_SUFFIX);
            // 请求参数
            StringBuilder param = new StringBuilder();
            param.append(MESSAGE_PREFIX);
            try 
            {
                BufferedReader bufferedReader = request.getReader();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    param.append(line);
                }
            } catch (IOException e) {
                String message = SpringContextUtil.getMessage("msg.common.10003");
                logger.error(message, e);
            }
            param.append(MESSAGE_SUFFIX);
            // 把编集好的Request信息输出到日志中
            logger.info(SpringContextUtil.getMessage("msg.common.10028", url, method, headerInfo, param));
        }
    }
    
    /**
     * 把Response响应信息输出到日志中
     * @param res Response响应
     * @param isApi (true:WEB API访问的场合 false:WEB API访问以为的场合)
     */
    private void outputResponse(HttpServletResponse response, boolean isApi) {
        // HTTP状态码
        StringBuilder statusCode = new StringBuilder();
        statusCode.append(MESSAGE_PREFIX);
        statusCode.append(response.getStatus());
        statusCode.append(MESSAGE_SUFFIX);
        
        if (!isApi) {
            logger.info(SpringContextUtil.getMessage("msg.common.10029", statusCode));
        } else {
            // 消息头
            StringBuilder headerInfo = new StringBuilder();
            headerInfo.append(MESSAGE_PREFIX);
            Collection<String> headerNames = response.getHeaderNames();
            for (String headerName : headerNames) {
                headerInfo.append(headerName);
                headerInfo.append(MESSAGE_PREFIX);
                headerInfo.append(response.getHeader(headerName));
                headerInfo.append(MESSAGE_SUFFIX);
                headerInfo.append(SPACE_MARK);
            }
            // 删除最后一个分隔符
            if (headerInfo.toString().endsWith(SPACE_MARK)) {
                headerInfo.delete(headerInfo.length() - 1, headerInfo.length());
            }
            headerInfo.append(MESSAGE_SUFFIX);
            logger.info(SpringContextUtil.getMessage("msg.common.10030", statusCode, headerInfo));
        }
    }
}
