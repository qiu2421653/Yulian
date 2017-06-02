package com.njkj.yulian.utils;

import java.lang.reflect.Type;

import com.njkj.yulian.core.lib.gson.Gson;
import com.njkj.yulian.core.lib.gson.JsonSyntaxException;

/**
 * 
 * Json
 * 
 * @author Qiu
 * 
 */
public class JsonUtils {

	private static Gson sGson;

	static {
		if (sGson == null) {
			sGson = new Gson();
		}
	}

	public static String toJson(Object obj) {
		String res = null;
		if (sGson != null) {
			res = sGson.toJson(obj);
		}
		return res;
	}

	public static <T> T fromJson(String json, Class<T> classOfT)
			throws JsonSyntaxException {
		T t = null;
		if (sGson != null) {
			t = sGson.fromJson(json, classOfT);
		}
		return t;
	}

	public static <T> T fromJson(String json, Type typeOfT)
			throws JsonSyntaxException {
		T t = null;
		if (sGson != null) {
			t = sGson.fromJson(json, typeOfT);
		}
		return t;
	}

}
