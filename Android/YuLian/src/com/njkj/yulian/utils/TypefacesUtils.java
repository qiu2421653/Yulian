package com.njkj.yulian.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.utils
 * 
 * @Description:获取本地字库文件
 * 
 * @date 2016-3-31 下午3:11:12
 * 
 * @version 1.0 ==============================
 */
public class TypefacesUtils {
	private static final String TAG = "Typefaces";
	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	public static Typeface get(Context c, String assetPath) {
		synchronized (cache) {
			if (!cache.containsKey(assetPath)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							assetPath);
					cache.put(assetPath, t);
				} catch (Exception e) {
					Log.e(TAG, "Could not get typeface '" + assetPath
							+ "' because " + e.getMessage());
					return null;
				}
			}

			return cache.get(assetPath);
		}
	}
}