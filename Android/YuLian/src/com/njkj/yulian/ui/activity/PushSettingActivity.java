package com.njkj.yulian.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.gui.togglebutton.button.ToggleButton;
import com.njkj.yulian.ui.gui.togglebutton.button.ToggleButton.OnToggleChanged;
import com.njkj.yulian.utils.CLog;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:推送设置
 * 
 * @date 2016-6-20 下午3:14:43
 * 
 * @version 1.0 ==============================
 */
public class PushSettingActivity extends BaseActivity {

	protected static final String TAG = "PushSettingActivity";

	LinearLayout ll_push_open;// 接收推送显示设置

	ToggleButton tb_reveicer;// 接收推送

	ToggleButton remind_by_voice;// 声音设置

	ToggleButton remind_by_shake;// 震动设置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_setting);
		setHeaderTitle(R.string.push_setting);
		setHeaderLeftText();
		initView();
		initData();
	}

	private void initView() {
		ll_push_open = (LinearLayout) findViewById(R.id.ll_push_open);

		tb_reveicer = (ToggleButton) findViewById(R.id.tb_reveicer);
		remind_by_voice = (ToggleButton) findViewById(R.id.remind_by_voice);
		remind_by_shake = (ToggleButton) findViewById(R.id.remind_by_shake);

	}

	private void initData() {
		// 接收消息的
		tb_reveicer.setOnToggleChanged(onReveicer);
		// 推送声音设置
		remind_by_voice.setOnToggleChanged(onSound);
		// 推送震动设置
		remind_by_shake.setOnToggleChanged(onVibrate);
		// 默认设置
		setDefault();
	}

	/**
	 * 
	 * @Title: setDefault
	 * @Description: 设置默认显示
	 * @return void
	 */
	private void setDefault() {
		boolean isNotificatoin = mConfigDao.getBoolean("isNotificatoin");
		boolean isSound = mConfigDao.getBoolean("isSound");
		boolean isVibration = mConfigDao.getBoolean("isVibration");
		// 可以接收时，显示设置
		if (isNotificatoin) {
			// 切换无动画
			tb_reveicer.toggle(false);
			tb_reveicer.setToggleOn();
			ll_push_open.setVisibility(View.VISIBLE);
			if (isSound) {
				remind_by_voice.toggle(false);
				remind_by_voice.setToggleOn();
			} else
				remind_by_voice.setToggleOff();
			if (isVibration) {
				remind_by_shake.toggle(false);
				remind_by_shake.setToggleOn();
			} else
				remind_by_shake.setToggleOff();
		} else {
			tb_reveicer.setToggleOff();
			ll_push_open.setVisibility(View.GONE);
		}
	}

	// 接收消息
	OnToggleChanged onReveicer = new OnToggleChanged() {
		@Override
		public void onToggle(boolean on) {
			if (on)
				ll_push_open.setVisibility(View.VISIBLE);
			else
				ll_push_open.setVisibility(View.GONE);
			mConfigDao.setBoolean("isNotificatoin", on);
		}
	};

	// 接收声音
	OnToggleChanged onSound = new OnToggleChanged() {
		@Override
		public void onToggle(boolean on) {
			mConfigDao.setBoolean("isSound", on);
		}
	};

	// 接收震动
	OnToggleChanged onVibrate = new OnToggleChanged() {
		@Override
		public void onToggle(boolean on) {
			CLog.e(TAG, "on:" + on);
			mConfigDao.setBoolean("isVibration", on);
		}
	};
}
