/**
 * 
 */
package com.njkj.yulian.controller;

import org.json.JSONObject;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.core.lib.gson.reflect.TypeToken;
import com.njkj.yulian.core.lib.volley.AuthFailureError;
import com.njkj.yulian.core.lib.volley.Request.Method;
import com.njkj.yulian.core.lib.volley.Response;
import com.njkj.yulian.core.lib.volley.VolleyError;
import com.njkj.yulian.core.lib.volley.toolbox.JsonObjectRequest;
import com.njkj.yulian.core.lib.volley.toolbox.JsonRequest;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

/**
 * 
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.controller
 * 
 * @Description:登录登出控制器
 * 
 * @date 2016-5-17 上午9:02:48
 * 
 * @version 1.0 ==============================
 */
public class LoginController extends BaseController {

	private final String TAG = "LoginController";

	/**
	 * 请求验证码
	 * 
	 * @param url
	 *            -接口地址
	 * 
	 * @param mobile
	 *            手机号
	 * */
	public void reqValid(final String url, final String mobile,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("mobile", mobile);// 手机号
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<UserEntity>>() {
								}.getType()));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
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
	 * 注册用户
	 * 
	 * @param url
	 * @param mobile
	 *            --手机号
	 * @param passWord
	 *            --密码
	 * @param validCode
	 *            --验证码
	 * @param callback
	 */
	public void reqRegist(final String url, final String mobile,
			final String passWord, final String validCode,
			final String latitude, final String longitude,
			final SimpleCallback callback) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("mobile", mobile);
		map.put("passWord", passWord);
		map.put("validCode", validCode);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<UserEntity>>() {
								}.getType()));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
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
	 * 修改密码
	 * 
	 * @param url
	 * @param mobile
	 *            --手机号
	 * @param passWord
	 *            --密码
	 * @param validCode
	 *            --验证码
	 * @param callback
	 */
	public void reqUpdatePwd(final String url, final String mobile,
			final String passWord, final String validCode,
			final SimpleCallback callback) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("mobile", mobile);
		map.put("passWord", passWord);
		map.put("validCode", validCode);
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<UserEntity>>() {
								}.getType()));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
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
	 * 密码登录
	 * 
	 * @param url
	 * @param mobile
	 *            --手机号
	 * @param passWord
	 *            --密码
	 * @param callback
	 */
	public void reqLogin(final String url, final String mobile,
			final String passWord, final String latitude,
			final String longitude, final SimpleCallback callback) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("mobile", mobile);
		map.put("passWord", passWord);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		JSONObject jsonObject = new JSONObject(map);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<UserEntity>>() {
								}.getType()));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
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
