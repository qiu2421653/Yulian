package com.njkj.yulian.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.CommentActivity;
import com.njkj.yulian.ui.activity.MyFocusActivity;
import com.njkj.yulian.ui.activity.MyIntegralActivity;
import com.njkj.yulian.ui.activity.PersonalActivity;
import com.njkj.yulian.ui.activity.PraiseActivity;
import com.njkj.yulian.ui.activity.SettingActivity;
import com.njkj.yulian.ui.activity.reward.RewardActivity;
import com.njkj.yulian.utils.UrlUtils;

@SuppressLint("NewApi")
public class PersonalFragment extends BaseFragment {
	private TextView tab1, tab3, tab4, tab5, tab6, tab7, rl_store;

	private LinearLayout title_right;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.personal, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		tab1 = (TextView) view.findViewById(R.id.tab1);
		tab3 = (TextView) view.findViewById(R.id.tab3);
		tab4 = (TextView) view.findViewById(R.id.tab4);
		tab5 = (TextView) view.findViewById(R.id.tab5);
		tab6 = (TextView) view.findViewById(R.id.tab6);
		tab7 = (TextView) view.findViewById(R.id.tab7);
		rl_store = (TextView) view.findViewById(R.id.rl_store);
		title_right = (LinearLayout) view.findViewById(R.id.ll_right_opr);
	}

	@Override
	protected void initData() {
		title_right.setVisibility(View.VISIBLE);
	}

	@Override
	protected void initOnClick() {
		tab1.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);
		tab5.setOnClickListener(this);
		tab6.setOnClickListener(this);
		tab7.setOnClickListener(this);
		rl_store.setOnClickListener(this);
		title_right.setOnClickListener(this);

	}

	@Override
	public void onMyClick(View view) {
		if (view.getId() == R.id.ll_right_opr) { // 设置
			startAnimActivity(SettingActivity.class);
			return;
		}
		if (checkLogin()) {
			switch (view.getId()) {
			case R.id.tab1:
				// 个人信息
				startActivity(new Intent(getActivity(), PersonalActivity.class));
				break;
			case R.id.tab3:
				// 打赏
				startAnimActivity(RewardActivity.class);
				break;
			case R.id.tab4:
				// 评论
				startAnimActivity(CommentActivity.class);
				break;
			case R.id.tab5:
				// 点赞
				startAnimActivity(PraiseActivity.class);
				break;
			case R.id.tab6:
				// 禹币积分
				startAnimActivity(MyIntegralActivity.class);
				break;
			case R.id.tab7:
				// 我的关注
				startAnimActivity(MyFocusActivity.class);
				break;
			case R.id.rl_store:
				// 商城
				showShortToast("等级不够,努力升级哟");
				break;
			}
		}
	}

	// 获取存储信息
	public boolean getSharePreference() {
		SharedPreferences mySharedPreferences = getActivity()
				.getSharedPreferences(UrlUtils.SHARENAME, Activity.MODE_PRIVATE);
		return mySharedPreferences.getBoolean(UrlUtils.SHARETYPE, false);
	}
}
