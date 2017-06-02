package com.njkj.yulian.controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.JPushEntity;
import com.njkj.yulian.ui.activity.topic.TopicRecommenActivity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.controller
 * 
 * @Description:状态栏
 * 
 * @date 2016-6-23 下午2:21:10
 * 
 * @version 1.0 ==============================
 */
public class StatusBarController extends BaseController {
	/**
	 * 默认状态栏
	 */
	private final static int NOTICE_ID_MSG = 0;

	private NotificationManager mNotificationManager;

	private Context mContext;

	private static StatusBarController mStatusBarManager;

	public synchronized static StatusBarController getInstance() {
		if (null == mStatusBarManager) {
			mStatusBarManager = new StatusBarController();
		}
		return mStatusBarManager;
	}

	private StatusBarController() {
		mContext = MainApplication.getContext();
		mNotificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	/**
	 * 更新状态栏
	 */
	public void notifySysMsg(JPushEntity wapper, String json) {
		// 消息通知栏
		NotificationManager mNotificationManager = (NotificationManager) MainApplication
				.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		// 通知栏设置
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				MainApplication.getContext());
		mBuilder.setContentTitle(wapper.title)// 设置通知栏标题
				.setContentText(wapper.msg).setTicker(wapper.msg) // 通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
				.setPriority(Notification.PRIORITY_DEFAULT) // 设置该通知优先级
				.setAutoCancel(true)// 设置这个标志当用户单击面板就可以让通知将自动取消
				.setSmallIcon(R.drawable.logo);// 设置通知小ICON

		// 启动设置(声音|震动)
		mBuilder.setDefaults(getSetting());

		Notification notification = mBuilder.build();
		// 自动取消
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		// 定义下拉通知栏时要展现的内容信息-点击跳转intent
		Intent data = new Intent(mContext, TopicRecommenActivity.class);
		data.putExtra("topicId", wapper.topicId);

		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				data, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(mContext, wapper.title, wapper.msg,
				contentIntent);
		// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
		mNotificationManager.notify(1, notification);
	}

	public void cancelMsgNotification() {
		if (mNotificationManager != null)
			mNotificationManager.cancel(NOTICE_ID_MSG);
	}

	public void cancelAllNotification() {
		if (mNotificationManager != null)
			mNotificationManager.cancelAll();
	}

	/**
	 * 关闭通知
	 */
	public void cancelNotification(int id) {
		mNotificationManager.cancel(id);
	}

	/**
	 * 根据code返回对应的Activity类??还是返回Intent好?? 在这里将Json串进行二次封装??会不会更好 <br/>
	 * 暂定这样吧,等后续业务需求变更，再次修改 <br/>
	 * 2016-6-23 14:20<br/>
	 * Qiu
	 * 
	 * @return
	 */
	private Class<?> getCode(String code) {
		return TopicRecommenActivity.class;
	}

	/**
	 * 获取设置
	 * 
	 * @return
	 */
	private int getSetting() {
		ConfigDao mConfigDao = ConfigDao.getInstance();
		boolean isSound = mConfigDao.getBoolean("isSound");
		boolean isVibration = mConfigDao.getBoolean("isVibration");

		if (isSound && isVibration) {
			return NotificationCompat.DEFAULT_ALL;
		}

		if (isSound) {
			return NotificationCompat.DEFAULT_SOUND;
		}

		if (isVibration) {
			return NotificationCompat.DEFAULT_VIBRATE;
		}

		return NotificationCompat.DEFAULT_LIGHTS;

	}

}
