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
import com.njkj.yulian.entity.LatlngEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.controller
 * 
 * @Description:地图相关
 * 
 * @date 2016-6-23 上午11:17:32
 * 
 * @version 1.0 ==============================
 */
public class MapController extends BaseController {

	private final String TAG = "MapController";

	/**
	 * 
	 * @Title: getNearUsers
	 * @Description: 获取附近的人
	 * @param @param url
	 * @param @param pageCount
	 * @param @param currentPage
	 * @param @param userId
	 * @param @param latitude
	 * @param @param longitude
	 * @param @param radius
	 * @param @param callback
	 * @return void
	 * @throws
	 */
	public void getNearUsers(final String url, final int pageCount,
			final int currentPage, final String userId, final double latitude,
			final double longitude, final int radius,
			final SimpleCallback callback) {
		ArrayMap<String, Object> params = new ArrayMap<String, Object>();
		params.put("pageCount", pageCount);
		params.put("currentPage", currentPage);
		params.put("userId", userId);
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("radius", radius);
		JSONObject jsonObject = new JSONObject(params);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<LatlngEntity>>() {
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
				headers.put("Accept-Encoding", "gzip");
				headers.put("application/json", "charset=UTF-8");
				return headers;
			}
		};
		mNetManager.addToRequestQueue(jsonRequest, TAG);
	}
}
