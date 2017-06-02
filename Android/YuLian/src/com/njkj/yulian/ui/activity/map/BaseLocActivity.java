package com.njkj.yulian.ui.activity.map;

import java.util.Timer;
import java.util.TimerTask;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.njkj.yulian.MainApplication;
import com.njkj.yulian.core.cache.ImageDownloader;
import com.njkj.yulian.core.lib.volley.VolleyError;
import com.njkj.yulian.core.lib.volley.toolbox.ImageLoader.ImageContainer;
import com.njkj.yulian.core.lib.volley.toolbox.ImageLoader.ImageListener;
import com.njkj.yulian.dao.ConfigDao;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * 
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment
 * 
 * @Description:定位功能模块
 * 
 * @date 2016-6-7 上午11:29:47
 * 
 * @version 1.0 ==============================
 */
public abstract class BaseLocActivity extends FragmentActivity {

	private final String TAG = "BaseLocActivity";

	public LocationClient mLocationClient; // 定位服务的客户端
	public MyLocationListener mMyLocationListener; // 定位请求回调接口
	public BDLocation mBDLocation; // 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
	private Timer timer;
	private int sum = 0;
	protected boolean isTimer = true;// 是否timer
	protected static final int SUCCESS = 2;// 重新定位成功
	protected static final int ERROR = -1;// 重新定位失败
	private boolean isMore = true;

	protected ConfigDao mConfigDao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestLocation(true);
		mConfigDao = ConfigDao.getInstance();
	}

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 3s后关闭定位(循环3次以保证准确性)
				if (sum >= 3) {
					unResisterLocation();
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void timer() {
		isTimer = false;
		timer = new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				sum++;
				handler.sendMessage(message);
			}
		};
		timer.schedule(task, 0, 1000); // 延时0ms后执行，1000ms执行一次
	}

	/**
	 * 请求定位
	 * */
	protected void requestLocation(boolean isMore) {
		if (mLocationClient != null) {
			if (!mLocationClient.isStarted()) {
				mLocationClient.start();
			}
		} else {
			mMyLocationListener = new MyLocationListener();
			mLocationClient = new LocationClient(MainApplication.getContext());
			mLocationClient.registerLocationListener(mMyLocationListener);
			mLocationClient.start();
		}
		initLocation();
		mLocationClient.requestLocation();
	}

	/**
	 * 配置定位SDK参数
	 * 设置定位参数包括：定位模式（高精度定位模式，低功耗定位模式和仅用设备定位模式），返回坐标类型，是否打开GPS，是否返回地址信息、
	 * 位置语义化信息、POI信息等等。
	 * */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		// 定位模式 -高精度定位模式
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系，
		option.setScanSpan(1000);// 设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		// option.setIgnoreKillProcess(true);//
		// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	// 定位回调监听
	private class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// 接受定位信息
			receiveLocation(location);
			// 循环多次(现在设置3次吧)
			if (isMore) {
				if (isTimer) {
					if (timer == null) {
						timer();
					}
				}
			}
		}
	}

	// 接受定位信息
	private void receiveLocation(BDLocation location) {
		Message message = new Message();
		if (location == null) {
			message.what = ERROR;
		} else {
			double latdbl = location.getLatitude();// 拿到纬度
			double londbl = location.getLongitude();// 拿到经度
			String latstr = String.valueOf(latdbl);
			String lonstr = String.valueOf(londbl);
			// 提交经纬度
			if (!TextUtils.isEmpty(lonstr) && !TextUtils.isEmpty(latstr)
					&& !lonstr.contains("E") && !latstr.contains("E")) {
				// 当经纬度有一项改变了就存到xml里
				if (!mConfigDao.getString("latitude").equals(latstr)
						|| !mConfigDao.getString("longitude").equals(lonstr)) {

					mConfigDao.setString("latitude", latstr);// 提交纬度
					mConfigDao.setString("longitude", lonstr);// 提交经度

					// 提交城市码
					if (!TextUtils.isEmpty(location.getCityCode())) {
						mConfigDao.setString("cityCode", location.getCityCode()
								.toString().trim());
					}
					// 提交城市
					if (!TextUtils.isEmpty(location.getCity())) {
						mConfigDao.setString("city", location.getCity()
								.toString().trim());
					}
					// 提交地址
					if (!TextUtils.isEmpty(location.getAddrStr())) {
						mConfigDao.setString("address", location.getAddrStr()
								.toString().trim());
					}
				}
				// 定位成功
				message.what = SUCCESS;
				message.obj = location;
			} else {
				message.what = ERROR;
			}
		}
		handler.sendMessage(message);
	};

	/**
	 * 取消之前注册的定位监听函数
	 * */
	public void unResisterLocation() {
		if (mLocationClient != null) {
			mLocationClient.unRegisterLocationListener(mMyLocationListener);
			mLocationClient.stop();
			mLocationClient = null;
		}
		if (timer != null) {
			timer.cancel();// 退出计时器
		}
		handler.removeCallbacksAndMessages(null);
		timer = null;
		isTimer = true;
		sum = 0;
	}

}
