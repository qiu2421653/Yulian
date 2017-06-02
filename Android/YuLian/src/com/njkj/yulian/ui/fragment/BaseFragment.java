package com.njkj.yulian.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.ui.activity.LoginActivity;
import com.njkj.yulian.ui.gui.CustomClipLoading;
import com.njkj.yulian.ui.gui.loading.LoadingState;
import com.njkj.yulian.ui.gui.loading.LoadingView;
import com.njkj.yulian.ui.gui.loading.OnLoadingListener;
import com.njkj.yulian.ui.gui.loading.OnRetryListener;
import com.njkj.yulian.utils.NetUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description: BaseFragment
 * 
 * @date 下午9:50:18
 * 
 * @version V1.0 ==============================
 * 
 */
@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment implements OnClickListener,
		OnRetryListener, OnLoadingListener {
	private Toast mToast;
	public Context mContext;
	public ConfigDao mConfigDao;
	protected CustomClipLoading dialog;

	protected LoadingView fl_loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		mConfigDao = ConfigDao.getInstance();
		mContext = MainApplication.getContext();
		try {
			view = onCreateMyView(inflater, container, savedInstanceState);
			initViews(view);
			initData();
			initOnClick();
		} catch (Exception e) {
			// 处理|网络上传异常
			e.printStackTrace();
		}
		return view;
	}

	/**
	 * 创建视图
	 * 
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	public abstract View onCreateMyView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	/**
	 * 初始化组件
	 */
	protected abstract void initViews(View view);

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 初始化单击监听
	 */
	protected abstract void initOnClick();

	/**
	 * 自定义监听
	 * 
	 * @param view
	 */
	public abstract void onMyClick(View view);

	@Override
	public void onClick(View view) {
		try {
			onMyClick(view);
		} catch (Exception e) {
			// 异常处理|网络
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: initLoading
	 * @Description: 设置LoadingView
	 */
	protected void initLoading(View view) {
		fl_loading = (LoadingView) view.findViewById(R.id.fl_loading);
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
	 * 得到当前运行Activity名
	 * 
	 * @return
	 */
	private String getRunningActivityName() {
		ActivityManager activityManager = (ActivityManager) getActivity()
				.getSystemService(Context.ACTIVITY_SERVICE);
		String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
				.getClassName();
		return runningActivity.substring(runningActivity.lastIndexOf(".") + 1);
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
		startActivity(new Intent(getActivity(), cla));
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
			dialog = new CustomClipLoading(getActivity(),
					R.drawable.loading_animation);
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

}
