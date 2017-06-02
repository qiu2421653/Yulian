package com.njkj.yulian.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.dialog.MyPlayDialog;

public class GoodDetailAddress extends Activity implements OnClickListener {
	Button address_play;
	RelativeLayout add_address;
	ImageView goodaddress_left;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gooddetail_address);
		initView();
	}

	private void initView() {
		address_play = (Button) findViewById(R.id.address_play);
		add_address = (RelativeLayout) findViewById(R.id.add_address);
		goodaddress_left = (ImageView) findViewById(R.id.goodaddress_left);
		goodaddress_left.setOnClickListener(this);
		address_play.setOnClickListener(this);
		add_address.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_play:// 立即支付
			MyPlayDialog dialog = new MyPlayDialog(this, R.style.MyDialogStyle);
			dialog.show();
			mHandler.sendEmptyMessage(1);
			break;
		case R.id.add_address:// 地址管理
			startActivity(new Intent(this, AddaddressActivity.class));
			break;
		case R.id.goodaddress_left:
			finish();
			break;
		default:
			break;
		}
	}


}
