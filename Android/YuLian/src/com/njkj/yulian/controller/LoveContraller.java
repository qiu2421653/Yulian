package com.njkj.yulian.controller;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

import android.support.v4.util.ArrayMap;

import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.core.lib.gson.reflect.TypeToken;
import com.njkj.yulian.core.lib.volley.AuthFailureError;
import com.njkj.yulian.core.lib.volley.Request.Method;
import com.njkj.yulian.core.lib.volley.Response;
import com.njkj.yulian.core.lib.volley.Response.ErrorListener;
import com.njkj.yulian.core.lib.volley.Response.Listener;
import com.njkj.yulian.core.lib.volley.VolleyError;
import com.njkj.yulian.core.lib.volley.toolbox.JsonObjectRequest;
import com.njkj.yulian.core.lib.volley.toolbox.JsonRequest;
import com.njkj.yulian.core.net.MultipartRequest;
import com.njkj.yulian.entity.LoveEntity;
import com.njkj.yulian.entity.LoveListEntity;
import com.njkj.yulian.entity.OtherLoveEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

public class LoveContraller extends BaseController {
	private final String TAG = "LoveContraller";

	/**
	 * 我的爱情接口
	 * 
	 * 
	 */
	public void getMyloveDetail(final String url, final String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userID", userID);// 参数传递userID借口这个值代表uuId
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<LoveListEntity>>() {
								}.getType()));

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						CLog.d(TAG, error.toString());
						onCallback(callback, null);
					}
				}) {
			@Override
			public ArrayMap<String, String> getHeaders()
					throws AuthFailureError {
				ArrayMap<String, String> headers = new ArrayMap<String, String>();
				headers.put("application/json", "charset=UTF-8");
				return headers;
			}
		};
		mNetManager.addToRequestQueue(jsonRequest, TAG);
	}

	/**
	 * 我的爱情删除接口(当前)
	 * 
	 * 
	 */
	public void getMyloveDelete(final String url, final String id,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("themeId", id);
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<LoveListEntity>>() {
								}.getType()));

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						CLog.d(TAG, error.toString());
						onCallback(callback, null);
					}
				}) {
			@Override
			public ArrayMap<String, String> getHeaders()
					throws AuthFailureError {
				ArrayMap<String, String> headers = new ArrayMap<String, String>();
				headers.put("application/json", "charset=UTF-8");
				return headers;
			}
		};
		mNetManager.addToRequestQueue(jsonRequest, TAG);
	}

	/**
	 * 我的爱情隐藏显示
	 * 
	 * 
	 */
	public void getMyloveType(final String url, final String id, String type,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("themeId", id);
		map.put("type", type);
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<LoveListEntity>>() {
								}.getType()));

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						CLog.d(TAG, error.toString());
						onCallback(callback, null);
					}
				}) {
			@Override
			public ArrayMap<String, String> getHeaders()
					throws AuthFailureError {
				ArrayMap<String, String> headers = new ArrayMap<String, String>();
				headers.put("application/json", "charset=UTF-8");
				return headers;
			}
		};
		mNetManager.addToRequestQueue(jsonRequest, TAG);
	}

	/**
	 * 提交用户信息
	 * 
	 * @param url
	 *            -接口地址
	 * @param userID
	 *            -用户Id
	 * 
	 * */
	public void getSubmit(final String url, final List<File> files,
			String userID, String loveDesc, final SimpleCallback callback) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("userID", userID);// 用户id
		map.put("loveDesc", loveDesc);// 发布信息
		map.put("createDate", "");
		MultipartRequest multipartRequest = new MultipartRequest(url,
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						CLog.d(TAG, "出错了..");
						onCallback(callback, null);
					}
				}, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// 链接成功响应信息
						CLog.d(TAG, "response:" + response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<LoveEntity>>() {
								}.getType()));
					}
				}, "files", files, map);
		mNetManager.addToRequestQueue(multipartRequest, TAG);
	}

	/**
	 * 获得对方的爱情经历
	 * 
	 * @param url
	 * @param createrID
	 *            --我的ID
	 * @param userID
	 *            --对方 ID
	 * 
	 */
	public void getOtherLoveStory(final String url, final String userID,
			final String createrID, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userID", userID);
		map.put("createrID", createrID);
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<OtherLoveEntity>>() {
								}.getType()));

					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						CLog.d(TAG, error.toString());
						onCallback(callback, null);
					}
				}) {
			@Override
			public ArrayMap<String, String> getHeaders()
					throws AuthFailureError {
				ArrayMap<String, String> headers = new ArrayMap<String, String>();
				headers.put("application/json", "charset=UTF-8");
				return headers;
			}
		};
		mNetManager.addToRequestQueue(jsonRequest, TAG);
	}
}
