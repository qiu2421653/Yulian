package com.njkj.yulian.ui.activity;

import com.njkj.yulian.R;

import android.os.Bundle;

public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		setHeaderLeftText();
		setHeaderTitle(R.string.about);
	}
}
