package com.njkj.yulian.core.net;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

import android.support.v4.util.ArrayMap;

import com.njkj.yulian.core.lib.gson.JsonSyntaxException;
import com.njkj.yulian.core.lib.volley.AuthFailureError;
import com.njkj.yulian.core.lib.volley.NetworkResponse;
import com.njkj.yulian.core.lib.volley.ParseError;
import com.njkj.yulian.core.lib.volley.Request;
import com.njkj.yulian.core.lib.volley.Response;
import com.njkj.yulian.core.lib.volley.Response.ErrorListener;
import com.njkj.yulian.core.lib.volley.Response.Listener;
import com.njkj.yulian.core.lib.volley.toolbox.HttpHeaderParser;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

public class GsonRequest<T> extends Request<T> {
	private final Listener<T> mListener;
	private Class<T> mClassOfT;
	private Type mTypeOfT;
	private String mTag;

	/**
	 * 
	 * @param method
	 *            {@link Method}
	 * @param url
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method, String url, Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	// 构造
	public GsonRequest(String url, Listener<T> listener,
			ErrorListener errorListener) {
		this(Method.POST, url, listener, errorListener);
	}

	public void setClassOfT(Class<T> classOfT) {
		this.mClassOfT = classOfT;
	}

	public void setTypeOfT(Type typeOfT) {
		this.mTypeOfT = typeOfT;
	}

	@Override
	public Request<?> setTag(Object tag) {
		this.mTag = tag == null ? "easd" : tag.toString();
		return this;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			CLog.d(mTag, jsonString);
			T parsedGSON = null;
			if (mClassOfT != null) {
				parsedGSON = JsonUtils.fromJson(jsonString, mClassOfT);
			} else if (mTypeOfT != null) {
				parsedGSON = JsonUtils.fromJson(jsonString, mTypeOfT);
			}
			return Response.success(parsedGSON,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		ArrayMap<String, String> headers = new ArrayMap<String, String>();
		headers.put("Accept-Encoding", "gzip");
		return headers;
	}

}