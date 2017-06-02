package com.njkj.yulian.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.entity.UserEntity;

/**
 * Dao层
 * 
 * @author Qiu
 */
public class ConfigDao {

	private static final String SETTING_INFO = "yulian";// 文件名
	private final SharedPreferences mSharePref;
	private final Editor mEditor;
	private static ConfigDao sInstance;

	private ConfigDao(Context context) {
		mSharePref = context.getSharedPreferences(SETTING_INFO,
				Context.MODE_PRIVATE);
		mEditor = mSharePref.edit();
	}

	// 创建
	public static ConfigDao getInstance() {
		if (sInstance == null) {
			synchronized (ConfigDao.class) {
				if (sInstance == null) {
					sInstance = new ConfigDao(MainApplication.getContext());
				}
			}
		}
		return sInstance;
	}

	public void removeAll() {
		mEditor.clear().commit();
	}

	// boolean
	public void setBoolean(String key, boolean b) {
		mEditor.putBoolean(key, b).commit();
	}

	public boolean getBoolean(String key) {
		return mSharePref.getBoolean(key, true);
	}

	// String
	public void setString(String key, String value) {
		mEditor.putString(key, value).commit();
	}

	public String getString(String key) {
		return mSharePref.getString(key, "");
	}

	// int
	public void setInteger(String key, int value) {
		mEditor.putInt(key, value).commit();
	}

	public int getInt(String key) {
		return mSharePref.getInt(key, -1);
	}

}
