package com.njkj.yulian;

import android.app.Application;
import android.content.Context;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

import com.baidu.mapapi.SDKInitializer;
import com.njkj.yulian.core.crash.CrashHandler;
import com.njkj.yulian.core.crash.CustomActivityOnCrash;

public class MainApplication extends Application {

	private static Context sCtx;
	private CrashHandler crashHandler;// 异常处理

	@Override
	public void onCreate() {
		super.onCreate();
		sCtx = getApplicationContext();

		ShareSDK.initSDK(this);
		
		SDKInitializer.initialize(this);// 百度
		// TODO (暂不开启)
//		crashHandler = CrashHandler.getInstance();// 全局异常捕获
//		crashHandler.init(sCtx);
		
	     CustomActivityOnCrash.install(this);

		JPushInterface.init(this); // 初始化 JPush
		JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志

	}

	public static Context getContext() {
		return sCtx;
	}

}