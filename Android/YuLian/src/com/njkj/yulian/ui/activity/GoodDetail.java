package com.njkj.yulian.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.utils.WrapContentHeightViewPager;

public class GoodDetail extends Activity implements OnClickListener {
	WrapContentHeightViewPager viewpager;
	LinearLayout viewpager_bottom;
	Button mPreSelectedBt, submit, close_layout;
	Button gooddetail_jian, gooddetail_add;// 数量加减
	TextView gooddetail_jifen;// 积分
	TextView goodetail_price;
	TextView gooddetail_edit;
	LinearLayout bottom_layout;
	private int[] img = {};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gooddetail);
		initView();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		viewpager = (WrapContentHeightViewPager) findViewById(R.id.viewpager);
		viewpager_bottom = (LinearLayout) findViewById(R.id.viewpager_bottom);
		gooddetail_jian = (Button) findViewById(R.id.gooddetail_jian);
		gooddetail_add = (Button) findViewById(R.id.gooddetail_add);
		gooddetail_edit = (TextView) findViewById(R.id.gooddetail_edit);
		gooddetail_jifen = (TextView) findViewById(R.id.gooddetail_jifen);
		bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
		close_layout = (Button) findViewById(R.id.close_layout);
		goodetail_price = (TextView) findViewById(R.id.goodetail_price);
		goodetail_price.setText("市场参考价:399.00元");
		goodetail_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		close_layout.setOnClickListener(this);
		submit = (Button) findViewById(R.id.submit);
		gooddetail_add.setOnClickListener(this);
		gooddetail_jian.setOnClickListener(this);
		submit.setOnClickListener(this);

		List<ImageView> list = new ArrayList<ImageView>();
		for (int i = 0; i < img.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			// imageView.setScaleType(ScaleType.CENTER_INSIDE);
			imageView.setBackgroundResource(img[i]);
			list.add(imageView);
		}
		viewpager.setAdapter(new PagerAdapter(list));
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
					Button currentBt = (Button) viewpager_bottom.getChildAt(0);
					currentBt.setBackgroundResource(R.drawable.white_icon);
				} else {
					Button currentBt = (Button) viewpager_bottom.getChildAt(0);
					currentBt.setBackgroundResource(R.drawable.gray_icom);
				}
				if (mPreSelectedBt != null) {
					mPreSelectedBt.setBackgroundResource(R.drawable.gray_icom);
				}

				Button currentBt = (Button) viewpager_bottom.getChildAt(arg0);
				currentBt.setBackgroundResource(R.drawable.white_icon);
				mPreSelectedBt = currentBt;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		for (int i = 0; i < list.size(); i++) {
			Button bt = new Button(this);
			LinearLayout.LayoutParams viewgroup = new LinearLayout.LayoutParams(
					15, 15);
			viewgroup.setMargins(0, 0, 10, 0);
			bt.setLayoutParams(viewgroup);
			if (i == 0)
				bt.setBackgroundResource(R.drawable.white_icon);
			else
				bt.setBackgroundResource(R.drawable.gray_icom);
			viewpager_bottom.addView(bt);
		}
	}

	class PagerAdapter extends android.support.v4.view.PagerAdapter {
		private List<ImageView> list;

		public PagerAdapter(List<ImageView> list) {
			this.list = list;
		}

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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			if (bottom_layout.getVisibility() == View.VISIBLE
					&& "确认兑换".equals(submit.getText().toString())) {
				bottom_layout.setVisibility(View.GONE);
				startActivity(new Intent(this, GoodDetailAddress.class));
				submit.setText("我要兑换");
			} else {
				bottom_layout.setVisibility(View.VISIBLE);
				submit.setText("确认兑换");
			}
			break;
		case R.id.close_layout:
			bottom_layout.setVisibility(View.GONE);
			submit.setText("我要兑换");
			break;
		case R.id.gooddetail_add:
			int num = Integer.parseInt(gooddetail_edit.getText().toString());
			num++;
			gooddetail_edit.setText(num + "");
			break;
		case R.id.gooddetail_jian:
			int num1 = Integer.parseInt(gooddetail_edit.getText().toString());
			if (num1 != 1) {
				num1--;
				gooddetail_edit.setText(num1 + "");
			}
			break;
		default:
			break;
		}
	}

}
