package com.njkj.yulian.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.BaseActivity;

public class PayDetailActivity extends BaseActivity {
	private Button icon_right;
	private Button icon1_right;
	private Button pay_submit;
	private int num=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paydetail_list);
		setHeaderBackground(R.color.white);
		setHeaderTitle(R.string.myintegral_pay);
		setHeaderLeftText();
		initView();
	}

	private void initView() {
		icon_right = (Button) findViewById(R.id.icon_right);
		icon1_right = (Button) findViewById(R.id.icon1_right);
		pay_submit=(Button) findViewById(R.id.pay_submit);
		icon1_right.setOnClickListener(this);
		icon_right.setOnClickListener(this);
		pay_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.icon1_right:// 微信
			num=0;
			icon1_right.setBackgroundResource(R.drawable.duigou);
			icon_right.setBackgroundResource(R.drawable.yuan);
			break;
		case R.id.icon_right:// 支付宝
			num=1;
			icon1_right.setBackgroundResource(R.drawable.yuan);
			icon_right.setBackgroundResource(R.drawable.duigou);
			break;
		case R.id.pay_submit://确认充值
			if(num==1)
				showShortToast("调起支付宝支付");
				else
			   showShortToast("调起微信支付");
			break;
		default:
			break;
		}
	}
}
