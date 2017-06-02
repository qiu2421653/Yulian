package com.njkj.yulian.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njkj.yulian.R;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:禹恋积分
 * 
 * @date 2016-4-28 下午5:02:19
 * 
 * @version 1.0 ==============================
 */
public class IntegralFragment extends BaseFragment {
	TextView tv_rules;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.rules_fragment, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		tv_rules = (TextView) view.findViewById(R.id.tv_rules);
	}

	@Override
	protected void initData() {
		tv_rules.setText(getString(R.string.integral));
	}

	@Override
	protected void initOnClick() {
	}

	@Override
	public void onMyClick(View view) {
	}
}
