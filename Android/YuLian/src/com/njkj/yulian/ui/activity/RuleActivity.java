package com.njkj.yulian.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.fragment.GoldFragment;
import com.njkj.yulian.ui.fragment.IntegralFragment;
import com.njkj.yulian.ui.gui.smoothTab.SmoothTabTitle;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:规则
 * 
 * @date 2016-4-29 上午8:54:31
 * 
 * @version 1.0 ==============================
 */
public class RuleActivity extends FragmentActivity implements OnClickListener {

	ImageView btn_back;
	ViewPager view_pager;
	SmoothTabTitle title;// 实现的平滑滚动title

	ArrayList<Fragment> mFragmentList;

	MyPagerAdapter myPagerAdapter;
	int currentIndex = 0;// ViewPager的当前选中页

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule);
		initView();
		initData();
		initClick();

	}

	private void initView() {
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		btn_back = (ImageView) findViewById(R.id.btn_back);
		title = (SmoothTabTitle) findViewById(R.id.title);
	}

	private void initData() {
		mFragmentList = new ArrayList<Fragment>();

		mFragmentList.add(new GoldFragment());
		mFragmentList.add(new IntegralFragment());

		myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		view_pager.setOffscreenPageLimit(mFragmentList.size());// 设置缓存数量
		view_pager.setAdapter(myPagerAdapter);
		view_pager.setCurrentItem(currentIndex);
		title.setViewPager(view_pager);
	}

	private void initClick() {
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}

	class MyPagerAdapter extends FragmentPagerAdapter {

		private final List<String> pages = new ArrayList<String>();

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			pages.add("禹币规则");// 设置titlebar元素的文本内容 和个数
			pages.add("积分规则");
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return pages.get(position);
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}
	}

}
