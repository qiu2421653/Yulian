/**
 * 
 */
package com.njkj.yulian.controller;

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
import com.njkj.yulian.entity.GoldEntity;
import com.njkj.yulian.entity.ReWardEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.naga.love.controller
 * 
 * @Description:禹币积分
 * 
 * @date 2016-5-16 上午9:10:41
 * 
 * @version 1.0 ==============================
 */
public class GoldController extends BaseController {

	private final String TAG = "UpgradeController";

	/**
	 * 获得禹币积分
	 * 
	 * @param url
	 * @param userID
	 * @param callback
	 */
	public void reqGetGold(final String url, String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> params = new ArrayMap<String, Object>();
		// params.put("userID", userID);
		JSONObject jsonObject = new JSONObject(params);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Method.GET,
				url + "?uuId=" + userID, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<GoldEntity>>() {
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
	 * 获得禹币积分明细
	 * 
	 * @param url
	 * @param userID
	 * @param callback
	 */
	public void getGoldDetail(final String url, String userID,
			String currentPage, String pageCount, final SimpleCallback callback) {
		ArrayMap<String, Object> params = new ArrayMap<String, Object>();
		params.put("currentPage", currentPage);
		params.put("pageCount", pageCount);
		params.put("uuId", userID);
		JSONObject jsonObject = new JSONObject(params);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<GoldEntity>>() {
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
	 * 获得禹币排行
	 * 
	 * @param url
	 * @param userID
	 * @param callback
	 */
	public void getGoldRank(final String url, String userID,
			String currentPage, String pageCount, final SimpleCallback callback) {
		ArrayMap<String, Object> params = new ArrayMap<String, Object>();
		params.put("currentPage", currentPage);
		params.put("pageCount", pageCount);
		params.put("uuId", userID);
		JSONObject jsonObject = new JSONObject(params);
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<GoldEntity>>() {
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
	 * 打赏
	 * 
	 * @param url
	 * @param userID
	 * @param callback
	 */
	public void reqReward(final String url, final String userID,
			final String infoId, final String reward,
			final SimpleCallback callback) {
		ArrayMap<String, Object> params = new ArrayMap<String, Object>();
		params.put("userId", userID);
		params.put("infoId", infoId);
		params.put("reward", reward);
		JSONObject jsonObject = new JSONObject(params);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<GoldEntity>>() {
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
	 * 获取打赏页列表集合
	 * 
	 * @param url
	 * @param userID
	 * @param callback
	 */
	public void getRewardList(final String url, final String userID,
			final int currentPage, final int pageCount,
			final SimpleCallback callback) {
		ArrayMap<String, Object> params = new ArrayMap<String, Object>();
		params.put("uuId", userID);
		params.put("currentPage", currentPage);
		params.put("pageCount", pageCount);
		JSONObject jsonObject = new JSONObject(params);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<ReWardEntity>>() {
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
