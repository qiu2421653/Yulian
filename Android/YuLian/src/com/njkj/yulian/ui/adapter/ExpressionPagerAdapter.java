package com.njkj.yulian.ui.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:表情
 * 
 * @date 2016-3-29 上午11:09:57
 * 
 * @version 1.0 ==============================
 */
public class ExpressionPagerAdapter extends PagerAdapter {

	private List<View> views;

	public ExpressionPagerAdapter(List<View> views) {
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1));
		return views.get(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));

	}

}
