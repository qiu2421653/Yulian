package com.naga.common.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * getInputStream()和getReader()
 * 都只能读取一次，由于RequestBody是流的形式读取，那么流读了一次就没有了，所以只能被调用一次。
 * 先将RequestBody保存，然后通过Servlet自带的HttpServletRequestWrapper类覆盖getReader()
 * 和getInputStream()方法，
 * 使流从保存的body读取。然后再Filter中将ServletRequest替换为ServletRequestWrapper
 */
public class RequestCachingWrapper extends HttpServletRequestWrapper {

    /** 保存输入流的body */
    private String _body;
    /** 请求信息 */
    private HttpServletRequest _request;

    /**
     * RequestCachingWrapper类的构造方法，将请求信息里的RequestBody的内容读取出来并保存。
     * @param request 请求信息
     * @throws IOException
     */
    public RequestCachingWrapper(HttpServletRequest request) throws IOException {
        super(request);
        _request = request;

        StringBuffer jsonStr = new StringBuffer();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonStr.append(line);
        }
        // 获取到提交测json，复制给requestBody
        _body = jsonStr.toString();
    }

    /**
     * 取得保存的RequestBody内容的输入流对象。
     * @return 取到的输入流对象
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(_body.getBytes());
        return new ServletInputStream() {
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener arg0) {
            }
        };
    }

    /**
     * 取得保存的RequestBody内容的BufferReader对象。
     * @return 取到的BufferReader对象
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}