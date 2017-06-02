package com.njkj.yulian.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.njkj.yulian.MainApplication;

public class DevUtils {

	// 获取屏幕的宽度
	public static int getScreenWidth() {
		android.view.WindowManager wm = (android.view.WindowManager) MainApplication
				.getContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param activity
	 *            Activity
	 * @return 屏幕高度
	 */
	public static int getScreenHeight() {
		android.view.WindowManager wm = (android.view.WindowManager) MainApplication
				.getContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

}
