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
import com.njkj.yulian.entity.DuitangInfo;
import com.njkj.yulian.entity.ExperienceEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TimeEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.entity.TopicEntity;
import com.njkj.yulian.entity.TopicHotEntity;
import com.njkj.yulian.entity.TopicNewEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.entity.VideoEntity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.JsonUtils;

/***
 * 
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.controller
 * 
 * @Description:故事|主题
 * 
 * @date 2016-5-16 上午9:26:17
 * 
 * @version 1.0 ==============================
 */
public class TopicController extends BaseController {

	private final String TAG = "TopicController";

	/**
	 * 获得主题列表
	 * 
	 * @param url
	 * @param currentPage
	 *            --当前页
	 * @param pageCount
	 *            --每页显示数
	 * @param userID
	 *            --用户ID
	 * @param isShowAll
	 *            --是否全部显示(true:广告+推荐)
	 * @param callback
	 */
	public void reqTopicList(final String url, int currentPage, int pageCount,
			String userID, boolean isShowAll, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("pageCount", pageCount);
		map.put("userID", userID);
		map.put("isShowAll", isShowAll);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicEntity>>() {
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
				headers.put("Accept-Encoding", "gzip,deflate");
				headers.put("application/json", "charset=UTF-8");
				return headers;
			}
		};
		mNetManager.addToRequestQueue(jsonRequest, TAG);
	}

	/**
	 * 对用户关注
	 * 
	 * @param url
	 * @param expID
	 *            -- 被关注人Id
	 * @param isCareful
	 *            --0:false;1:true
	 * @param userID
	 *            --关注人
	 * @param callback
	 */
	public void reqTopicCareful(final String url, String expID,
			String isCareful, String userID, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("userID", userID);
		map.put("expID", expID);
		map.put("isCareful", isCareful);
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
				headers.put("Accept-Encoding", "gzip,deflate");
				headers.put("application/json", "charset=UTF-8");
				return headers;
			}
		};
		mNetManager.addToRequestQueue(jsonRequest, TAG);
	}

	/**
	 * 获取指定用户主题详情列表
	 * 
	 * @param url
	 * @param currentPage
	 *            --当前页
	 * @param pageCount
	 *            --每页显示数
	 * @param userID
	 *            --用户ID
	 * @param isShowAll
	 *            --是否全部显示(true:广告+推荐)
	 * @param callback
	 */
	public void getUserTopicList(final String url, int currentPage,
			int pageCount, String topicID, String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("pageCount", pageCount);
		map.put("userId", userID);
		map.put("topicId", topicID);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicDetailEntity>>() {
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
	 * 
	 * @Title: getHotTopicList
	 * @Description: 获取热门帖子List
	 * @param @param url
	 * @param @param currentPage
	 * @param @param pageCount
	 * @param @param callback
	 * @return void
	 * @throws
	 */
	public void getHotTopicList(final String url, int currentPage,
			int pageCount, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
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
								new TypeToken<RetEntity<TopicHotEntity>>() {
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
	 * 
	 * @Title: getNewTopicList
	 * @Description: 获取新上榜帖子List
	 * @param @param url
	 * @param @param currentPage
	 * @param @param pageCount
	 * @param @param callback
	 * @return void
	 * @throws
	 */
	public void getNewTopicList(final String url, int currentPage,
			int pageCount, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
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
								new TypeToken<RetEntity<TopicNewEntity>>() {
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
	 * 
	 * @Title: getHotTopicList
	 * @Description: 获取对应tag的主题List
	 * @param @param url
	 * @param @param currentPage
	 * @param @param pageCount
	 * @param @param callback
	 * @return void
	 * @throws
	 */
	public void getTagTopicList(final String url, int currentPage,
			int pageCount, final String tagCode, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("pageCount", pageCount);
		map.put("tagCode", tagCode);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicNewEntity>>() {
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
	 * 获得用户时间轴列表
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void getUserTimeLine(final String url, String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		// com.njkj.yulian.ui.activity.map.put("userId", userID);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Method.GET,
				url + "?userId=" + userID, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TimeEntity>>() {
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
	 * 获得指定用户主题视频集合
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void getUserTopicMovie(final String url, int currentPage,
			int pageCount, String topicID, String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("pageCount", pageCount);
		map.put("userId", userID);
		map.put("topicId", topicID);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<VideoEntity>>() {
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
	 * 获取帖子点赞人列表
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void getTopicForks(final String url, int forkCount, int forkPage,
			String infoId, String userId, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("forkCount", forkCount);
		map.put("forkPage", forkPage);
		map.put("infoId", infoId);
		map.put("userId", infoId);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicDetailEntity>>() {
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
	 * 获取帖子评论列表
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void getTopicComments(final String url, int commentCount,
			int commentPage, String infoId, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("commentCount", commentCount);
		map.put("commentPage", commentPage);
		map.put("infoId", infoId);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicDetailEntity>>() {
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
	 * 帖子内回复某用户
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void replyTopicUser(final String url, final String commentId,
			final String infoId, final String message, final String userId,
			final String replyId, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("commentId", commentId);
		map.put("infoId", infoId);
		map.put("message", message);
		map.put("replyID", replyId);
		map.put("userID", userId);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicDetailEntity>>() {
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
	 * 获取对应标签集合
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void getTagList(final String url, int pageCount, int currentPage,
			String tagID, final String userID, final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("pageCount", pageCount);
		map.put("currentPage", currentPage);
		map.put("tagID", tagID);
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
								new TypeToken<RetEntity<DuitangInfo>>() {
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
	 * 获取帖子详情
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void getTopic(final String url, String topicId, final String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("topicId", topicId);
		map.put("userId", userID);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicDetailEntity>>() {
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
	 * 获取推荐帖子详情
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void getRecommen(final String url,final String topicId,final String userID,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("topicId", topicId);
		map.put("userId", userID);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicDetailEntity>>() {
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
	 * 检测经历
	 * 
	 * @param url
	 * @param userID
	 *            --用户ID
	 * @param callback
	 */
	public void checkExperience(final String url, final String userID,
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
								new TypeToken<RetEntity<ExperienceEntity>>() {
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
	 * 上传图片
	 * 
	 * @param url
	 *            -接口地址
	 * @param files
	 *            -传入的文件集
	 * @param userID
	 *            -用户id
	 * */
	public void upLoadImage(final String url, final List<File> files,
			final String userId, final String content, final String tag,
			final SimpleCallback callback) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("userId", userId);
		map.put("content", content);
		map.put("tag", tag);
		System.out.println("upLoadImage:------------------->");
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
	 * 获取第一次推荐标签
	 * 
	 * @param url
	 * @param callback
	 */
	public void getFirstList(final String url, int pageCount, int currentPage,
			final SimpleCallback callback) {
		ArrayMap<String, Object> map = new ArrayMap<String, Object>();
		map.put("pageCount", pageCount);
		map.put("currentPage", currentPage);
		JSONObject jsonObject = new JSONObject(map);

		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CLog.d(TAG, response.toString());
						onCallback(callback, JsonUtils.fromJson(
								response.toString(),
								new TypeToken<RetEntity<TopicDetailEntity>>() {
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
