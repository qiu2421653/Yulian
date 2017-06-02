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
import com.njkj.yulian.entity.CommentEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TagEntity;
import com.njkj.yulian.entity.TopicEntity;
import com.njkj.yulian.entity.UpgradeEntity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description: 标签
 * 
 * @date 下午10:00:38
 * 
 * @version V1.0 ==============================
 * 
 */
public class TagController extends BaseController {

	private final String TAG = "TagController";

	/**
	 * 新建标签
	 * 
	 * @param url
	 * @param tag
	 * @param userID
	 * @param callback
	 */
	public void createTag(final String url, final String tag, String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userId", userID);
		map.put("tag", tag);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TagEntity>>() {
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
	 * 获得可使用的标签
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void getUsedTags(final String url, final String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userID", userID);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TagEntity>>() {
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
