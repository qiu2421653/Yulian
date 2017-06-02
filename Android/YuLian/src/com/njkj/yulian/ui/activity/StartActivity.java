package com.njkj.yulian.ui.activity;

import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.map.BaseLocActivity;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.Md5;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:开屏页
 * 
 * @date 2016-3-30 上午11:44:25
 * 
 * @version 1.0 ==============================
 */
public class StartActivity extends BaseLocActivity {

	private final String TAG = "StartActivity";

	private View mContentView;
	private AlphaAnimation alphaAnim;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContentView = View.inflate(this, R.layout.activity_ac_splash, null);
		setContentView(mContentView);
		initUI();
	}

	/**
	 * API 来设置别名(JPush)
	 * 
	 * @param context
	 * @param alias
	 *            有效的别名
	 * @param callback
	 * */
	@Override
	protected void onStart() {
		super.onStart();
		try {
			String userId = mConfigDao.getString("userID");
			if (TextUtils.isEmpty(userId)) {
				return;
			}
			// 加密一次
			// userId = Md5.getMD5(userId);
			userId=userId.replace('-', '_');
			CLog.e(TAG, "userId:" + userId);
			// 设置别名
			JPushInterface.setAliasAndTags(getApplicationContext(), userId,
					null, new TagAliasCallback() {
						String logs = "";

						@Override
						public void gotResult(int code, String alias,
								Set<String> tags) {
							switch (code) {
							case 0:
								logs = "Set tag and alias success" + "   "
										+ alias;
								Log.e(TAG, logs);
								break;
							case 6002:
								logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
								Log.e(TAG, logs);
								break;
							default:
								logs = "Failed with errorCode = " + code;
								Log.e(TAG, logs);
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置动画效果
	 */
	private void initUI() {
		alphaAnim = new AlphaAnimation(0.3f, 1.0f);
		// 动画两秒
		alphaAnim.setDuration(2000);
		mContentView.setAnimation(alphaAnim);
		alphaAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				redirectTo();
			}
		});
	}

	// 重定向
	private void redirectTo() {
		boolean isFirst = mConfigDao.getBoolean("first_start_app");
		if (isFirst) {
			// 引导页
			startActivity(new Intent(MainApplication.getContext(),
					GuideActivity.class));
		} else {
			// 显示主页
			startActivity(new Intent(MainApplication.getContext(),
					MainActivity.class));
		}
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onDestroy() {
		if (alphaAnim != null) {
			alphaAnim = null;
		}
		if (mContentView != null) {
			mContentView = null;
		}
		super.onDestroy();
	}
}
