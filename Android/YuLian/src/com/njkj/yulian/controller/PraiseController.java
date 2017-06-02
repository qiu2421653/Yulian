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
import com.njkj.yulian.entity.PraiseEntity;
import com.njkj.yulian.entity.RetEntity;
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
 * @Description:赞
 * 
 * @date 2016-5-17 下午3:58:42
 * 
 * @version 1.0 ==============================
 */
public class PraiseController extends BaseController {

	private final String TAG = "CommentController";

	/**
	 * 获取点赞列表集合
	 * 
	 * @param url
	 * @param callback
	 */
	public void getForksList(final String url, int currentPage, int pageCount,
			String userID, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userId", userID);
		map.put("currentPage", currentPage);
		map.put("pageCount", pageCount);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Method.POST,
				url, jsonObject, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<PraiseEntity>>() {
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
