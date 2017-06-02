package com.njkj.yulian.core.net;

import android.text.TextUtils;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.core.lib.volley.Cache;
import com.njkj.yulian.core.lib.volley.Request;
import com.njkj.yulian.core.lib.volley.RequestQueue;
import com.njkj.yulian.core.lib.volley.VolleyLog;
import com.njkj.yulian.core.lib.volley.toolbox.ClearCacheRequest;
import com.njkj.yulian.core.lib.volley.toolbox.Volley;

/**
 * 网络请求队列(Volley)
 * */
public class NetRequestQueue {
	public static final String TAG = "NetRequestQueue";
	// 请求队列对象
	private RequestQueue mRequestQueue;

	private static class Holder {
		static NetRequestQueue sEngine = new NetRequestQueue();
	}

	// 实例化
	public static NetRequestQueue getInstance() {
		return Holder.sEngine;
	}

	private NetRequestQueue() {
	}

	// 拿到请求队列
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley
					.newRequestQueue(MainApplication.getContext());
		}

		return mRequestQueue;
	}

	// 加入到消息队列(带tag)
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		VolleyLog.d("Adding request to queue: %s", req.getUrl());
		// req.setShouldCache(false);
		getRequestQueue().add(req);
	}

	// 加入到消息队列(无tag)
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		// req.setShouldCache(false);
		// req.setRetryPolicy(new DefaultRetryPolicy(30000, 1, 1.0f));
		getRequestQueue().add(req);
	}

	// 释放待定的请求
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	// 清空已有的缓存文件
	public void clearCache(Runnable r) {
		Cache cache = getRequestQueue().getCache();
		ClearCacheRequest cReq = new ClearCacheRequest(cache, r);
		addToRequestQueue(cReq);
	}

}
