package com.njkj.yulian.controller;

import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.core.net.NetManager;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description:网络请求封装
 * 
 * @date 下午9:51:28
 * 
 * @version V1.0 ==============================
 * 
 */
public class BaseController {

	public NetManager mNetManager;

	public BaseController() {
		mNetManager = NetManager.getInstance();
	}

	public void onCallback(SimpleCallback callback, Object data) {
		if (callback != null) {
			callback.onCallback(data);
		}
	}
}