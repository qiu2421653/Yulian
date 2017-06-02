package com.njkj.yulian.ui.activity.reward;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.controller.GoldController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.GoldEntity;
import com.njkj.yulian.entity.RetEntity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity.reward
 * 
 * @Description:打赏金额
 * 
 * @date 2016-4-1 下午2:58:28
 * 
 * @version 1.0 ==============================
 */
public class RewardMoneyActivity extends Activity implements OnClickListener {
	TextView tv_other;
	EditText et_input;
	TextView tv_score;
	TextView tv_submit;
	ImageView iv_close;

	String topicId, currency;
	GoldController goldController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rewardinput);
		initViews();
		initData();
		initOnClick();
	}

	private void initViews() {
		et_input = (EditText) findViewById(R.id.et_input);
		iv_close = (ImageView) findViewById(R.id.iv_close);
		tv_submit = (TextView) findViewById(R.id.tv_submit);
		tv_score = (TextView) findViewById(R.id.tv_score);
		showKeyboard();
	}

	private void initData() {
		goldController = new GoldController();
		topicId = getIntent().getStringExtra("topicId");
		currency = getIntent().getStringExtra("currency");
		tv_score.setText("你拥有" + currency + "禹币");
	}

	private void initOnClick() {
		tv_submit.setOnClickListener(this);
		iv_close.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_close:
			finish();
			hide();
			break;
		case R.id.tv_submit:
			// 提交
			String score = et_input.getText().toString();
			if ("0".equals(score)) {
				Toast.makeText(MainApplication.getContext(), "禹币数不能为0,请重新输入", 0)
						.show();
				return;
			}
			if (!TextUtils.isEmpty(score)) {
				reqReward(score);
			}
			break;
		}
	}

	// 显示软键盘
	private void showKeyboard() {
		et_input.requestFocus();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et_input, InputMethodManager.SHOW_FORCED);
			}
		}, 100);
	}

	private void hide() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0); // 强制隐藏键盘
	}

	/** 打赏 */
	private void reqReward(String reward) {
		String userID = ConfigDao.getInstance().getString("userID");
		goldController.reqReward(getString(R.string.FsReward), userID, topicId,
				reward, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							Toast.makeText(MainApplication.getContext(),
									getString(R.string.error), 0).show();
						} else {
							RetEntity<GoldEntity> entity = (RetEntity<GoldEntity>) data;
							if (entity.success) {
								Toast.makeText(MainApplication.getContext(),
										"打赏成功!", 0).show();
								hide();
								finish();
							} else {
								Toast.makeText(MainApplication.getContext(),
										entity.exceptions.get(0).message, 0)
										.show();
							}
						}
					}
				});
	}
}
