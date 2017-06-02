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
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UpgradeEntity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.naga.love.controller
 * 
 * @Description:版本控制
 * 
 * @date 2016-5-16 上午9:10:41
 * 
 * @version 1.0 ==============================
 */
public class UpgradeController extends BaseController {

	private final String TAG = "UpgradeController";

	/**
	 * 检测版本-获得App最新版本号
	 * 
	 * @param url
	 * @param version
	 *            -版本
	 * @param channel
	 *            -渠道("999":本站)
	 * @param type
	 *            -类型("1":"Android","2":"IOS","3":"windowsPhone")
	 * @param deviceId
	 *            -设备号
	 * @param userId
	 *            -用户Id
	 * @param callback
	 */
	public void reqUpgrade(final String url, int type,
			final SimpleCallback callback) {
		ArrayMap<String, Object> params = new ArrayMap<String, Object>();
		params.put("system", type); // 传入系统类型0.Android 1.IOS //2.WindowsPhone
		JSONObject jsonObject = new JSONObject(params);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<UpgradeEntity>>() {
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
