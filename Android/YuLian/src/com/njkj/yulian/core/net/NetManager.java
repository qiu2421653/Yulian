package com.njkj.yulian.core.net;

import com.njkj.yulian.core.lib.volley.Cache.Entry;
import com.njkj.yulian.core.lib.volley.DefaultRetryPolicy;
import com.njkj.yulian.core.lib.volley.Request;
import com.njkj.yulian.core.lib.volley.RequestQueue;
import com.njkj.yulian.core.lib.volley.toolbox.JsonRequest;
import com.njkj.yulian.utils.CLog;

/**
 * 网络管理
 * */
public class NetManager {

	public static final String TAG = "NetManager";
	// 网络请求队列(Volley)
	private NetRequestQueue mRequestQueue;

	private static class NetManagerHolder {
		private static NetManager sEngine = new NetManager();
	}

	// 拿到实例
	public static NetManager getInstance() {
		return NetManagerHolder.sEngine;
	}

	private NetManager() {
		mRequestQueue = NetRequestQueue.getInstance();
	}

	/**
	 * 加入到消息队列中
	 * */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setRetryPolicy(new DefaultRetryPolicy(50*1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		if (mRequestQueue != null) {
			mRequestQueue.addToRequestQueue(req, tag);
		}
		// 打印log
		try {
			if (CLog.DEBUG) {
				JsonRequest<T> cReq = (JsonRequest<T>) req;
				CLog.d(TAG, "url:" + cReq.getUrl());
				CLog.d(TAG, "body:" + new String(cReq.getBody()));
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 加入到消息队列中
	 * */
	public <T> void addToRequestQueue(Request<T> req) {
		req.setRetryPolicy(new DefaultRetryPolicy(50*1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		
		if (mRequestQueue != null) {
			mRequestQueue.addToRequestQueue(req);
		}
		// 打印log
		try {
			if (CLog.DEBUG) {
				GsonRequest<T> cReq = (GsonRequest<T>) req;
				CLog.d(TAG, "url=[ " + cReq.getUrl());
				CLog.d(TAG, "getBody=[ " + new String(cReq.getBody()));
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 清理缓存
	 * */
	public void clearCache(Runnable r) {
		if (mRequestQueue != null) {
			mRequestQueue.clearCache(r);
		}
	}

	/**
	 * 拿到缓存中url对应的数据
	 * 
	 * @param url
	 * @return
	 */
	public Entry getCache(String url) {
		return getRequestQueue().getCache().get(url);
	}

	/**
	 * 拿到消息队列
	 * */
	public RequestQueue getRequestQueue() {
		return mRequestQueue.getRequestQueue();
	}
}
