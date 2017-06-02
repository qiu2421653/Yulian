package com.njkj.yulian.ui.activity;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.dialog.MyIntegrallDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MyIntegralPay extends BaseActivity {
	LinearLayout myintegral_num1, myintegral_num2, myintegral_num3,
			myintegral_num4, myintegral_num5, myintegral_num6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myintegral_pay);
		setHeaderBackground(R.color.white);
		setHeaderTitle(R.string.myintegral_pay);
		setHeaderLeftText();
		initView();
		initData();
	}

	private void initData() {

	}

	private void initView() {
		myintegral_num1 = (LinearLayout) findViewById(R.id.myintegral_num1);
		myintegral_num2 = (LinearLayout) findViewById(R.id.myintegral_num2);
		myintegral_num3 = (LinearLayout) findViewById(R.id.myintegral_num3);
		myintegral_num4 = (LinearLayout) findViewById(R.id.myintegral_num4);
		myintegral_num5 = (LinearLayout) findViewById(R.id.myintegral_num5);
		myintegral_num6 = (LinearLayout) findViewById(R.id.myintegral_num6);
		myintegral_num1.setOnClickListener(this);
		myintegral_num2.setOnClickListener(this);
		myintegral_num3.setOnClickListener(this);
		myintegral_num4.setOnClickListener(this);
		myintegral_num5.setOnClickListener(this);
		myintegral_num6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyIntegrallDialog  dialog=new MyIntegrallDialog(MyIntegralPay.this,R.style.MyDialogStyle);
				dialog.show();
 			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int num=0;
		switch (v.getId()) {
		case R.id.myintegral_num1:
			num=10;
			break;
		case R.id.myintegral_num2:
			num=30;
			break;
		case R.id.myintegral_num3:
			num=50;
			break;
		case R.id.myintegral_num4:
			num=100;
			break;
		case R.id.myintegral_num5:
			num=300;
			break;
		}
		startActivity(new Intent(MyIntegralPay.this,PayDetailActivity.class));
	}
}
