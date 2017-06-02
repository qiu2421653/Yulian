package com.njkj.yulian.controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.njkj.yulian.entity.JPushEntity;
import com.njkj.yulian.utils.JsonUtils;

/**
 * 推送消息
 * 
 * @author Qiu
 * 
 */
public final class PushController extends BaseController {

	public final String TAG = "PushController";
	private static PushController pushController;
	/**
	 * 线程操作
	 */
	private HandlerThread mHandlerThread;
	private MsgHandler mHandler;
	public static final int MSG_ORDER_STATUS = 1;

	public static PushController getInstance() {
		if (pushController == null) {
			pushController = new PushController();
		}
		return pushController;
	}

	public PushController() {
		mHandlerThread = new HandlerThread("msgThread");
		mHandlerThread.start();
		mHandler = new MsgHandler(mHandlerThread.getLooper());
	}

	private static class MsgHandler extends Handler {
		public MsgHandler(Looper looper) {
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			JPushEntity entity = (JPushEntity) data.getSerializable("entity");
			String json = data.getString("json");
			switch (msg.what) {
			case MSG_ORDER_STATUS:
				StatusBarController.getInstance().notifySysMsg(entity, json);
				break;
			}
			super.handleMessage(msg);
		}
	}

	/**
	 * 接收message
	 * 
	 * @param message
	 * @param customdata
	 */
	public void onMessage(String message, String customdata) {
		if (TextUtils.isEmpty(message)) {
			return;
		}
		try {
			Log.d(TAG, "message=" + message + "\ncustomdata=" + customdata);
			JPushEntity entity = null;
			try {
				entity = JsonUtils.fromJson(message, JPushEntity.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (entity == null) {
				entity = new JPushEntity();
			}
			sendMsgToWorkThread(MSG_ORDER_STATUS, entity, customdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMsgToWorkThread(int what, JPushEntity entity,
			String customdata) {
		if (mHandler != null && entity != null) {
			Message imMsg = mHandler.obtainMessage();
			Bundle data = new Bundle();
			data.putString("json", customdata);
			data.putSerializable("entity", entity);
			imMsg.setData(data);
			imMsg.what = MSG_ORDER_STATUS;
			imMsg.sendToTarget();
		}
	}
}
