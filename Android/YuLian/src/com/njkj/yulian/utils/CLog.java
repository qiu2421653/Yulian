package com.njkj.yulian.utils;

/**
 * Log
 * 
 * @author Qiu
 * 
 */
public class CLog {

	public static boolean DEBUG = true;

	public static void d(String tag, String msg) {
		if (DEBUG) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			android.util.Log.e(tag, msg);
		}
	}

}
