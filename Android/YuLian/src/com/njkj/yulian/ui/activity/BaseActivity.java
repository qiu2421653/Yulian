package com.njkj.yulian.ui.activity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.core.task.ActivityTaskManager;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.model.CityModel;
import com.njkj.yulian.model.DistrictModel;
import com.njkj.yulian.model.ProvinceModel;
import com.njkj.yulian.service.XmlParserHandler;
import com.njkj.yulian.ui.gui.CustomClipLoading;
import com.njkj.yulian.ui.gui.loading.LoadingState;
import com.njkj.yulian.ui.gui.loading.LoadingView;
import com.njkj.yulian.ui.gui.loading.OnLoadingListener;
import com.njkj.yulian.ui.gui.loading.OnRetryListener;
import com.njkj.yulian.utils.NetUtils;
import com.njkj.yulian.utils.UrlUtils;

public abstract class BaseActivity extends FragmentActivity implements
		OnClickListener, OnRetryListener, OnLoadingListener {
	protected ImageView tLeft;
	protected TextView tCenter;
	protected TextView tv_right;
	protected LinearLayout tRight;
	protected TextView tv_Left;
	protected TextView title_right_text;

	private RelativeLayout rl_general_header_bar;
	private View titlebar_bottomview;

	private Toast mToast; // Toast
	protected Context mContext; // 全局上下文
	public ActivityTaskManager taskManager;// Activity管理器
	public ConfigDao mConfigDao; // 存储xml

	protected LoadingView fl_loading;

	private CustomClipLoading dialog;// 进度条

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = MainApplication.getContext();
		taskManager = ActivityTaskManager.getInstance();
		taskManager.putActivity(getRunningActivityName(), this);
		mConfigDao = ConfigDao.getInstance();
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(R.layout.title);
		ViewGroup container = (ViewGroup) findViewById(R.id.title_content_view);
		View.inflate(this, layoutResID, container);
		rl_general_header_bar = (RelativeLayout) findViewById(R.id.rl_general_header_bar);
		titlebar_bottomview = findViewById(R.id.titlebar_bottomview);
		tLeft = (ImageView) findViewById(R.id.title_left1);
		tCenter = (TextView) findViewById(R.id.title_center);
		title_right_text = (TextView) findViewById(R.id.title_right_text);
		tRight = (LinearLayout) findViewById(R.id.title_right);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_Left = (TextView) findViewById(R.id.tv_title_left);

		tLeft.setOnClickListener(this);
		tRight.setOnClickListener(this);
		tv_Left.setOnClickListener(this);
		title_right_text.setOnClickListener(this);
	}

	/**
	 * 得到当前运行Activity名
	 * 
	 * @return
	 */
	private String getRunningActivityName() {
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
				.getClassName();
		return runningActivity.substring(runningActivity.lastIndexOf(".") + 1);
	}

	public void setHeaderBackground(int color) {
		rl_general_header_bar
				.setBackgroundColor(getResources().getColor(color));
		tLeft.setImageResource(R.drawable.image_edit_back2);
		tCenter.setTextColor(getResources().getColor(R.color.white));
		title_right_text.setTextColor(getResources().getColor(R.color.white));
		titlebar_bottomview.setBackgroundColor(getResources().getColor(
				R.color.black10));
	}

	public void setHeaderBackground1(int color) {
		rl_general_header_bar
				.setBackgroundColor(getResources().getColor(color));
		tLeft.setImageResource(R.drawable.image_edit_back2);
		tCenter.setTextColor(getResources().getColor(R.color.black));
		titlebar_bottomview.setVisibility(View.VISIBLE);
	}

	public void setHearLeft(int resource) {
		tLeft.setVisibility(View.VISIBLE);
		tv_Left.setVisibility(View.GONE);
		tLeft.setImageResource(resource);
	}

	public void setHeaderTitle(int resId) {
		tCenter.setText(resId);
	}

	public void setHeaderTitle(String title) {
		tCenter.setText(title);
	}

	public void setHeaderRightText(int resId) {
		title_right_text.setVisibility(View.VISIBLE);
		title_right_text.setText(resId);
		tRight.setVisibility(View.GONE);
	}

	public void setHeaderRightVisible() {
		tRight.setVisibility(View.VISIBLE);
		tv_right.setBackgroundResource(R.drawable.title_right);
	}

	public void setHeaderRightVisible(int resources) {
		tRight.setVisibility(View.VISIBLE);
		tv_right.setBackgroundResource(resources);
	}

	public void setHeaderLeftInVisible() {
		tLeft.setVisibility(View.INVISIBLE);
	}

	public void setHeaderRightGone() {
		tRight.setVisibility(View.GONE);
	}

	public void setHeaderRightText(String text) {
		tRight.setVisibility(View.GONE);
		title_right_text.setVisibility(View.VISIBLE);
		title_right_text.setText(text);
	}

	public void setHeaderLeftText() {
		tLeft.setVisibility(View.VISIBLE);
	}

	public void setHeaderLeftText(int resId) {
		tLeft.setVisibility(View.GONE);
		tv_Left.setVisibility(View.VISIBLE);
		tv_Left.setText(resId);
	}

	public void setHeaderLeftText(String text) {
		tLeft.setVisibility(View.GONE);
		tv_Left.setVisibility(View.VISIBLE);
		tv_Left.setText(text);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left1:
			finish();
			break;
		case R.id.title_center:

			break;
		case R.id.title_right:

			break;
		default:

			break;
		}

	}

	/**
	 * 跳转Intent
	 * */
	protected void startAnimActivity(Intent intent) {
		startActivity(intent);
	}

	/**
	 * 跳转Intent
	 * */
	protected void startAnimActivity(Class<?> cla) {
		startActivity(new Intent(this, cla));
	}

	/**
	 * 显示Toast
	 * */
	protected void showShortToast(String msg) {
		showToast(msg);
	}

	private void showToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	private void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

	/**
	 * 显示进度条
	 * */
	protected void showDialog() {
		if (dialog == null) {
			dialog = new CustomClipLoading(this, R.drawable.loading_animation);
		}
		if (!isFinishing()) {
			dialog.show();
		}
	}

	/**
	 * 隐藏进度条
	 * */
	protected void hideProgress() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	/**
	 * 
	 * @Title: initLoading
	 * @Description: 设置LoadingView
	 */
	protected void initLoading() {
		fl_loading = (LoadingView) findViewById(R.id.fl_loading);
	}

	protected void bindLoadingView() {
		fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !")
				.withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
				.withErrorIco(R.drawable.ic_chat_empty)
				.withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !")
				.withbtnErrorText("去找回她!!!")
				.withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走")
				.withNoNetIco(R.drawable.ic_chat_empty)
				.withbtnNoNetText("网弄好了，重试")
				.withLoadingIco(R.drawable.loading_animation)
				.withLoadingText("加载中...").withOnRetryListener(this).build();
	}

	@Override
	public void showSuccess() {
		fl_loading.setVisibility(View.GONE);
	}

	@Override
	public void showEmpty() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_EMPTY);
	}

	@Override
	public boolean checkNet() {
		return NetUtils.isNetworkAvailable(mContext);
	}

	@Override
	public void showFaild() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_ERROR);
	}

	@Override
	public void showNoNet() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_NO_NET);
	}

	@Override
	public void onRetry() {
		// 重试
	}

	/**
	 * 检查登录
	 * */
	protected boolean checkLogin() {
		// 如果用户id==null则是没登录
		if (TextUtils.isEmpty(mConfigDao.getString("userID"))) {
			// 跳转到登录页面
			startAnimActivity(LoginActivity.class);
			return false;
		}
		return true;
	}

	// 将信息存储
	public void inputSharePreference() {
		SharedPreferences mySharedPreferences = getSharedPreferences(
				UrlUtils.SHARENAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putBoolean(UrlUtils.SHARETYPE, true);
		editor.commit();
	}

	// 将信息存储
	public void inputSharePreference(String key, String name) {
		SharedPreferences mySharedPreferences = getSharedPreferences(
				UrlUtils.SHARENAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString(key, name);
		editor.commit();
	}

	// 获取存储信息
	public boolean getSharePreference() {
		SharedPreferences mySharedPreferences = getSharedPreferences(
				UrlUtils.SHARENAME, Activity.MODE_PRIVATE);
		return mySharedPreferences.getBoolean(UrlUtils.SHARETYPE, false);
	}

	// 获取存储信息
	public String getSharePreference(String key) {
		SharedPreferences mySharedPreferences = getSharedPreferences(
				UrlUtils.SHARENAME, Activity.MODE_PRIVATE);
		return mySharedPreferences.getString(key, "");
	}

	// 删除存储信息
	public void deleteSharePreference(String key) {
		SharedPreferences mySharedPreferences = getSharedPreferences(
				UrlUtils.SHARENAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}

	// 删除存储信息
	public void deleteSharePreference() {
		SharedPreferences mySharedPreferences = getSharedPreferences(
				UrlUtils.SHARENAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.clear().commit();
	}

	/**
	 * 读取本地json文件放置乱码
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String readLocalJson(Context context, String fileName) {
		String jsonString = "";
		String resultString = "";
		try {
			InputStream inputStream = context.getResources().getAssets()
					.open(fileName);
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			resultString = new String(buffer, "GB2312");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
	}

	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName = "";

	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode = "";

	/**
	 * 解析省市区的XML数据
	 */

	protected void initProvinceDatas() {
		List<ProvinceModel> provinceList = null;
		AssetManager asset = getAssets();
		try {
			InputStream input = asset.open("province_data.xml");
			// 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			// */ 初始化默认选中的省、市、区
			if (provinceList != null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList != null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0)
							.getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			// */
			mProvinceDatas = new String[provinceList.size()];
			for (int i = 0; i < provinceList.size(); i++) {
				// 遍历所有省的数据
				mProvinceDatas[i] = provinceList.get(i).getName();
				List<CityModel> cityList = provinceList.get(i).getCityList();
				String[] cityNames = new String[cityList.size()];
				for (int j = 0; j < cityList.size(); j++) {
					// 遍历省下面的所有市的数据
					cityNames[j] = cityList.get(j).getName();
					List<DistrictModel> districtList = cityList.get(j)
							.getDistrictList();
					String[] distrinctNameArray = new String[districtList
							.size()];
					DistrictModel[] distrinctArray = new DistrictModel[districtList
							.size()];
					for (int k = 0; k < districtList.size(); k++) {
						// 遍历市下面所有区/县的数据
						DistrictModel districtModel = new DistrictModel(
								districtList.get(k).getName(), districtList
										.get(k).getZipcode());
						// 区/县对于的邮编，保存到mZipcodeDatasMap
						mZipcodeDatasMap.put(districtList.get(k).getName(),
								districtList.get(k).getZipcode());
						distrinctArray[k] = districtModel;
						distrinctNameArray[k] = districtModel.getName();
					}
					// 市-区/县的数据，保存到mDistrictDatasMap
					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
				}
				// 省-市的数据，保存到mCitisDatasMap
				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {

		}
	}

	@Override
	protected void onDestroy() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
		super.onDestroy();
	}
}
