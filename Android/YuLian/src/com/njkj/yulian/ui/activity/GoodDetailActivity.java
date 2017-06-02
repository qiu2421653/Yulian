package com.njkj.yulian.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.njkj.yulian.R;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class GoodDetailActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	ViewPager viewPager;
	LocalActivityManager manager = null;
	ImageView left, right;
	TextView num1, num2, num3;
	View address_indicator1, address_indicator2, address_indicator3;
	List<View> list;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		initView();
		initData();
	}

	// 初始化数据
	private void initData() {

	}

	// 初始化view
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.good_viewpager);
		address_indicator1 = findViewById(R.id.address_indicator1);
		address_indicator2 = findViewById(R.id.address_indicator2);
		address_indicator3 = findViewById(R.id.address_indicator3);
		left = (ImageView) findViewById(R.id.good_left);
		num1 = (TextView) findViewById(R.id.num1);
		num2 = (TextView) findViewById(R.id.num2);
		num3 = (TextView) findViewById(R.id.num3);
		list = new ArrayList<View>();
		Intent intent = new Intent(this, GoodDetail.class);
		list.add(getView("GoodDetailActivity", intent));
		Intent intent2 = new Intent(this, PhoneDetail.class);
		list.add(getView("GoodDetailActivity", intent2));
		Intent intent3 = new Intent(this, Evaluation.class);
		list.add(getView("GoodDetailActivity", intent3));
		viewPager.setAdapter(new PagerAdapter());
		setonclick();
	}

	private void setonclick() {
		viewPager.setOnPageChangeListener(this);
		left.setOnClickListener(this);
		num1.setOnClickListener(this);
		num2.setOnClickListener(this);
		num3.setOnClickListener(this);
		address_indicator1.setVisibility(View.VISIBLE);
		address_indicator2.setVisibility(View.INVISIBLE);
		address_indicator3.setVisibility(View.INVISIBLE);
		num1.setTextColor(getResources().getColor(R.color.text_color));
	}

	/**
	 * 通过activity获取视图
	 */
	private View getView(String activity, Intent intent) {
		return manager.startActivity(activity, intent).getDecorView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.good_left:
			finish();
			break;
		case R.id.num1:// 第一个选项卡
			viewPager.setCurrentItem(0);
			num1.setTextColor(getResources().getColor(R.color.text_color));
			num2.setTextColor(getResources().getColor(R.color.black));
			num3.setTextColor(getResources().getColor(R.color.black));
			address_indicator1.setVisibility(View.VISIBLE);
			address_indicator2.setVisibility(View.INVISIBLE);
			address_indicator3.setVisibility(View.INVISIBLE);
			break;
		case R.id.num2:// 第二个选项卡
			viewPager.setCurrentItem(1);
			num2.setTextColor(getResources().getColor(R.color.text_color));
			num1.setTextColor(getResources().getColor(R.color.black));
			num3.setTextColor(getResources().getColor(R.color.black));
			address_indicator1.setVisibility(View.INVISIBLE);
			address_indicator2.setVisibility(View.VISIBLE);
			address_indicator3.setVisibility(View.INVISIBLE);
			break;
		case R.id.num3:// 第三个选项卡
			viewPager.setCurrentItem(2);
			num3.setTextColor(getResources().getColor(R.color.text_color));
			num2.setTextColor(getResources().getColor(R.color.black));
			num1.setTextColor(getResources().getColor(R.color.black));
			address_indicator1.setVisibility(View.INVISIBLE);
			address_indicator2.setVisibility(View.INVISIBLE);
			address_indicator3.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	class PagerAdapter extends android.support.v4.view.PagerAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			ViewPager pViewPager = (ViewPager) container;
			pViewPager.removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			ViewPager pViewPager = (ViewPager) container;
			pViewPager.addView(list.get(position));
			return list.get(position);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (arg0 == 0) {
			address_indicator1.setVisibility(View.VISIBLE);
			address_indicator2.setVisibility(View.INVISIBLE);
			address_indicator3.setVisibility(View.INVISIBLE);
			num1.setTextColor(getResources().getColor(R.color.text_color));
			num2.setTextColor(getResources().getColor(R.color.black));
			num3.setTextColor(getResources().getColor(R.color.black));
		} else if (arg0 == 1) {
			address_indicator1.setVisibility(View.INVISIBLE);
			address_indicator2.setVisibility(View.VISIBLE);
			address_indicator3.setVisibility(View.INVISIBLE);
			num2.setTextColor(getResources().getColor(R.color.text_color));
			num1.setTextColor(getResources().getColor(R.color.black));
			num3.setTextColor(getResources().getColor(R.color.black));
		} else if (arg0 == 2) {
			address_indicator1.setVisibility(View.INVISIBLE);
			address_indicator2.setVisibility(View.INVISIBLE);
			address_indicator3.setVisibility(View.VISIBLE);
			num3.setTextColor(getResources().getColor(R.color.text_color));
			num2.setTextColor(getResources().getColor(R.color.black));
			num1.setTextColor(getResources().getColor(R.color.black));
		}
	}

	@Override
	public void onPageSelected(int arg0) {

	}

}
