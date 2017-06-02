/**
 * 
 */
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
import com.njkj.yulian.core.lib.volley.VolleyError;
import com.njkj.yulian.core.lib.volley.toolbox.JsonObjectRequest;
import com.njkj.yulian.core.lib.volley.toolbox.JsonRequest;
import com.njkj.yulian.core.net.MultipartRequest;
import com.njkj.yulian.entity.MyFocusEntity;
import com.njkj.yulian.entity.ReportEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.SharedEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.controller
 * 
 * @Description:用户信息
 * 
 * @date 2016-5-16 下午6:16:58
 * 
 * @version 1.0 ==============================
 */
public class UserController extends BaseController {

	private final String TAG = "UserController";

	public UserController() {
	}

	/**
	 * 反馈
	 * 
	 * @param url
	 *            -接口地址
	 * @param userID
	 *            -用户Id
	 * @param feedback
	 *            -用户反馈内容
	 * 
	 * */
	public void addFeedback(final String url, final String userID,
			final String feedback, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userID", userID);// 用户id
		map.put("feedback", feedback);// 反馈的内容
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
	 * 获取用户信息
	 * 
	 * @param url
	 *            -接口地址
	 * @param userID
	 *            -用户Id
	 * 
	 * */
	public void getUserInfo(final String url, final String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		// com.njkj.yulian.ui.activity.map.put("userID", userID);// 用户id
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Method.GET,
				url + "?uuId=" + userID, jsonObject,
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
	 * 编辑用户信息
	 * 
	 * @param url
	 *            -接口地址
	 * @param userID
	 *            -用户Id
	 * 
	 * */
	public void editUserInfo(final String url, final String userID,
			String name, String sex, final List<File> files,
			final SimpleCallback callback) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("uuId", userID);// 用户id
		map.put("name", name);// 昵称
		map.put("sex", sex);// 性别
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
								new TypeToken<RetEntity<UserEntity>>() {
								}.getType()));
					}
				}, "files", files, map);
		mNetManager.addToRequestQueue(multipartRequest, TAG);
	}

	/**
	 * face++认证
	 * 
	 * @param url
	 *            -接口地址
	 * @param userID
	 *            -用户Id
	 * 
	 * */
	public void faceCertification(final String url, final String userID,
			final String faceId, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userID", userID);// 用户id
		map.put("faceID", faceId);// 用户id
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
	 * 点赞|取消赞
	 * 
	 * @param url
	 *            -接口地址
	 * @param userID
	 *            -用户Id
	 * 
	 * */
	public void setTopicFork(final String url, final String userID,
			final String infoId, final int isFork,final int status, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userId", userID);// 用户id
		map.put("infoId", infoId);
		map.put("isFork", isFork);
		map.put("status", status);
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
	 * 举报帖子
	 * 
	 * @param url
	 *            -接口地址
	 * @param userID
	 *            -用户Id
	 * 
	 * */
	public void reqReportTopic(final String url, final String userID,
			final String infoId, final String type,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("uuId", userID);// 用户id
		map.put("topicId", infoId);
		map.put("type", type);
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
	 * 获取分享内容
	 * 
	 * @param url
	 *            -接口地址
	 * @param isShareTopic
	 *            -主题分享 1：分享 ;0:下载
	 * @param topicId
	 *            -帖子 Id
	 * @param userID
	 *            -用户Id
	 * 
	 * */
	public void reqSharedInfo(final String url, final String userID,
			final int isShareTopic, final String topicId,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userID", userID);// 用户id
		map.put("topicId", topicId);
		map.put("isShareTopic", isShareTopic);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<SharedEntity>>() {
								}.getType()));
					}
				}, new Response.ErrorListener() {
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
	 * 获取分享内容
	 * 
	 * @param url
	 *            -接口地址
	 * @param isShareTopic
	 *            -主题分享 1：分享 ;0:下载
	 * @param topicId
	 *            -帖子 Id
	 * @param userID
	 *            -用户Id
	 * 
	 * */
	public void getReportList(final String url, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Method.GET,
				url + "?keyword=ACCUSATION_TOPIC", jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<ReportEntity>>() {
								}.getType()));
					}
				}, new Response.ErrorListener() {
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
	 * 获取我关注人员列表
	 * 
	 * @param url
	 *            -接口地址
	 * 
	 * */
	public void getForkUsers(final String url, final int currentPage,
			final int pageCount, final String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userID", userID);
		map.put("currentPage", currentPage);
		map.put("pageCount", pageCount);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<MyFocusEntity>>() {
								}.getType()));
					}
				}, new Response.ErrorListener() {
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
