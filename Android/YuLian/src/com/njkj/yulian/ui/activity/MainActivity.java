package com.njkj.yulian.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.core.task.ActivityTaskManager;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.ExperienceEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.ui.activity.upload.UploadInfoActivity;
import com.njkj.yulian.ui.fragment.HomeFragment;
import com.njkj.yulian.ui.fragment.PersonalFragment;
import com.njkj.yulian.ui.fragment.StoreFragment;
import com.njkj.yulian.ui.fragment.StoryFragment;
import com.njkj.yulian.ui.gui.CustomClipLoading;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:程序入口页面
 * 
 * @date 2016-4-8 下午1:29:57
 * 
 * @version 1.0 ==============================
 */
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener {

	private static final String TAG = "MainActivity";

	HomeFragment homeFragment;// 首页
	StoryFragment storyFragment;// 故事
	PersonalFragment personalFragment;// 我的
	// MallFragment mallFragment;// 商城
	StoreFragment storeFragment;// 商城

	private Fragment[] fragments;

	private ImageView[] imagebuttons;
	private TextView[] textviews;
	private int currentPosition = 0;// 当前位置
	private int lastPosition = 0;// 上一次 位置
	private long exitTime = 0;
	private TopicController topicController;
	private CustomClipLoading dialog;// 进度条

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		initView();
		setDefaultFragment(); // 设置默认的fragment
	}

	@Override
	protected void onResume() {
		super.onResume();
		imagebuttons[4].setEnabled(true);
	}

	private void initView() {

		imagebuttons = new ImageView[5];
		imagebuttons[0] = (ImageView) findViewById(R.id.iv_home);
		imagebuttons[1] = (ImageView) findViewById(R.id.iv_mall);
		imagebuttons[2] = (ImageView) findViewById(R.id.iv_story);
		imagebuttons[3] = (ImageView) findViewById(R.id.iv_personal);
		imagebuttons[4] = (ImageView) findViewById(R.id.iv_upload);
		imagebuttons[0].setSelected(true);

		textviews = new TextView[5];
		textviews[0] = (TextView) findViewById(R.id.tv_home);
		textviews[1] = (TextView) findViewById(R.id.tv_mall);
		textviews[2] = (TextView) findViewById(R.id.tv_story);
		textviews[3] = (TextView) findViewById(R.id.tv_personal);
		textviews[4] = (TextView) findViewById(R.id.tv_upload);
		textviews[0].setTextColor(getResources().getColor(R.color.white));

		findViewById(R.id.rl_home).setOnClickListener(this);
		findViewById(R.id.rl_mall).setOnClickListener(this);
		findViewById(R.id.rl_story).setOnClickListener(this);
		findViewById(R.id.rl_personal).setOnClickListener(this);
		findViewById(R.id.rl_upload).setOnClickListener(this);

		ActivityTaskManager.getInstance().putActivity(getRunningActivityName(),
				this);
	}

	private void setDefaultFragment() {
		topicController = new TopicController();
		homeFragment = new HomeFragment();
		// mallFragment = new MallFragment();
		storeFragment = new StoreFragment();
		storyFragment = new StoryFragment();
		personalFragment = new PersonalFragment();

		fragments = new Fragment[] { homeFragment, storeFragment,
				storyFragment, personalFragment };
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment, homeFragment)
				.add(R.id.fragment, storeFragment)
				.add(R.id.fragment, storyFragment)
				.add(R.id.fragment, personalFragment).hide(storeFragment)
				.hide(storyFragment).hide(personalFragment).show(homeFragment)
				.commit();
	}

	@Override
	public void onClick(View v) {
		imagebuttons[currentPosition].setSelected(false);
		textviews[currentPosition].setTextColor(getResources().getColor(
				R.color.common_tv));
		switch (v.getId()) {
		case R.id.rl_home:
			// 广场
			if (currentPosition == 0) {
				break;
			}
			currentPosition = 0;
			setCurrentFragment();
			break;
		case R.id.rl_personal:
			// 我的
			if (currentPosition == 3) {
				break;
			}
			currentPosition = 3;
			setCurrentFragment();
			break;
		case R.id.rl_mall:
			if (checkLogin()) {
				// 商城
				if (currentPosition == 1) {
					break;
				}
				Toast.makeText(MainApplication.getContext(), "等级不够,努力升级哟", 0)
						.show();
			}
			// currentPosition = 1;
			// setCurrentFragment();
			break;
		case R.id.rl_story:
			if (checkLogin()) {
				// 故事
				if (currentPosition == 2) {
					break;
				}
				currentPosition = 2;
				setCurrentFragment();
			}
			break;
		case R.id.rl_upload:
//			if (checkLogin()) {
//				// 中间上传
//				checkExperience();
//			}
			startActivity(new Intent(MainActivity.this,
			UploadInfoActivity.class));
		}
		// 把当前tab设为选中状态
		imagebuttons[currentPosition].setSelected(true);
		textviews[currentPosition].setTextColor(getResources().getColor(
				R.color.white));
	}

	/**
	 * 
	 * @Title: setCurrentFragment
	 * @Description: 切换当前页面
	 * @param
	 * @return void
	 * @throws
	 */
	private void setCurrentFragment() {
		if (currentPosition != lastPosition) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			if (!fragments[currentPosition].isAdded()) {
				trx.hide(fragments[lastPosition]).add(R.id.fragment,
						fragments[currentPosition]);
			} else {
				trx.hide(fragments[lastPosition]);
			}
			trx.show(fragments[currentPosition]).commit();
			lastPosition = currentPosition;
		}
	}

	/** 检测经历 */
	private void checkExperience() {
		String userID = ConfigDao.getInstance().getString("userID");
		showDialog();
		topicController.checkExperience(getString(R.string.FsCheckExperience),
				userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							Toast.makeText(MainApplication.getContext(),
									getString(R.string.error), 0).show();
						} else {
							RetEntity<ExperienceEntity> entity = (RetEntity<ExperienceEntity>) data;
							if (entity.success) {
								// 经历Id
								startActivity(new Intent(MainActivity.this,
										UploadInfoActivity.class));
								imagebuttons[4].setEnabled(false);
							} else {
								Toast.makeText(MainApplication.getContext(),
										entity.getErrorMsg(), 0).show();
							}
						}
						hideProgress();
					}
				});
	}

	/**
	 * 双击退出
	 * */
	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - exitTime) > 1000) {
			Toast.makeText(MainApplication.getContext(), "再按一次退出程序 ", 0).show();
			exitTime = System.currentTimeMillis();
			return;
		} else {
			try {
				// 退出释放内存
				System.gc();
				android.os.Process.killProcess(android.os.Process.myPid());
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				this.startActivity(intent);
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onBackPressed();
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

	/**
	 * 检查登录
	 * */
	protected boolean checkLogin() {
		// 如果用户id==null则是没登录
		if (TextUtils.isEmpty(ConfigDao.getInstance().getString("userID"))) {
			// 跳转到登录页面
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			return false;
		}
		return true;
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
}
