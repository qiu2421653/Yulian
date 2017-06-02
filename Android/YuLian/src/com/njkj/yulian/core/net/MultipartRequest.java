package com.njkj.yulian.core.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.support.v4.util.ArrayMap;

import com.njkj.yulian.core.lib.volley.AuthFailureError;
import com.njkj.yulian.core.lib.volley.NetworkResponse;
import com.njkj.yulian.core.lib.volley.Request;
import com.njkj.yulian.core.lib.volley.Request.Method;
import com.njkj.yulian.core.lib.volley.Response;
import com.njkj.yulian.core.lib.volley.VolleyLog;
import com.njkj.yulian.core.lib.volley.toolbox.HttpHeaderParser;
import com.njkj.yulian.utils.CLog;

public class MultipartRequest extends Request<String> {

	private static final String TAG = "MultipartRequest";

	private MultipartEntity entity = new MultipartEntity();

	private final Response.Listener<String> mListener;

	private List<File> mFileParts;// 文件集合
	private String mFilePartName;// 上传的文件名字
	private Map<String, String> mParams;// 参数

	/**
	 * 单个文件
	 * 
	 * @param url
	 *            -网址
	 * @param errorListener
	 *            -错误监听
	 * @param listener
	 *            --正常返回监听
	 * @param filePartName
	 *            -文件上传的名称?
	 * @param file
	 *            -文件?
	 * @param params
	 *            -参数
	 */
	public MultipartRequest(String url, Response.ErrorListener errorListener,
			Response.Listener<String> listener, String filePartName, File file,
			Map<String, String> params) {
		super(Method.POST, url, errorListener);
		mFileParts = new ArrayList<File>();
		if (file != null) {
			mFileParts.add(file);
		}
		mFilePartName = filePartName;
		mListener = listener;
		mParams = params;// 参数
		buildMultipartEntity();// 创建文件实体
	}

	/**
	 * 多个文件，对应一个key
	 * 
	 * @param url
	 * @param errorListener
	 * @param listener
	 * @param filePartName
	 * @param files
	 * @param params
	 */
	public MultipartRequest(String url, Response.ErrorListener errorListener,
			Response.Listener<String> listener, String filePartName,
			List<File> files, Map<String, String> params) {
		super(Method.POST, url, errorListener);
		System.out.println("MultipartRequest:----------->");
		mFilePartName = filePartName;
		mListener = listener;
		mFileParts = files;
		mParams = params;
		buildMultipartEntity();
	}

	/**
	 * 创建文件实体
	 * */
	private void buildMultipartEntity() {
		System.out.println("buildMultipartEntity:---------------->");
		if (mFileParts != null && mFileParts.size() > 0) {
			for (File file : mFileParts) {
				entity.addPart(mFilePartName, new FileBody(file));
			}
			long l = entity.getContentLength();
			// 打印出上传的数量和长度
			CLog.d(TAG, mFileParts.size() + "个，长度：" + l);
		}
		try {
			if (mParams != null && mParams.size() > 0) {
				for (Map.Entry<String, String> entry : mParams.entrySet()) {
					entity.addPart(
							entry.getKey(),
							new StringBody(entry.getValue(), Charset
									.forName("UTF-8")));
				}
			}
		} catch (UnsupportedEncodingException e) {
			VolleyLog.e("UnsupportedEncodingException");
		}
	}

	@Override
	public String getBodyContentType() {
		return entity.getContentType().getValue();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			entity.writeTo(bos);
		} catch (IOException e) {
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		CLog.d(TAG, "parseNetworkResponse");
		if (VolleyLog.DEBUG) {
			if (response.headers != null) {
				for (Map.Entry<String, String> entry : response.headers
						.entrySet()) {
					VolleyLog.d(entry.getKey() + "=" + entry.getValue());
				}
			}
		}
		String parsed;
		try {
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Request#getHeaders()
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		VolleyLog.d("getHeaders");
		Map<String, String> headers = super.getHeaders();

		if (headers == null || headers.equals(Collections.emptyMap())) {
			headers = new ArrayMap<String, String>();
		}

		return headers;
	}

	@Override
	protected void deliverResponse(String response) {
		mListener.onResponse(response);
	}
}