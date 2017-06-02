package com.njkj.yulian.ui.fragment.article;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.fragment.BaseFragment;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.article
 * 
 * @Description:文章 (一级)
 * 
 * @date 2016-6-12 下午7:55:58
 * 
 * @version 1.0 ==============================
 */
public class ArticleFragment extends BaseFragment implements
		OnPageChangeListener {
	// 子标签
	private TextView tv_hot, tv_new, tv_first, tv_double, tv_love, tv_our,
			tv_free;
	// 放入到集合中
	private List<TextView> lists = new ArrayList<TextView>();
	// 定义一个viewPager
	private ViewPager viewPager;// 滑动ViewPager
	// 声明集合保存所有的标签页
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private MyFragmentPagerAdapter adapter;
	private FragmentManager fm;
	private HorizontalScrollView horizontal_Scroll;// 水平滚动条
	private int lastPosition = 0;// 上次位置
	private int currentPosition = 0;// 当前位置

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_article, container,
				false);
		return view;
	}

	/**
	 * 初始化
	 * */
	@Override
	protected void initViews(View view) {
		// 获取ViewPager
		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		// 水平滚动条目
		horizontal_Scroll = (HorizontalScrollView) view
				.findViewById(R.id.horizontal_Scroll1);
		// 获取单选按钮并保存到集合中
		tv_hot = (TextView) view.findViewById(R.id.tv_hot);
		tv_new = (TextView) view.findViewById(R.id.tv_new);
		tv_first = (TextView) view.findViewById(R.id.tv_first);
		tv_double = (TextView) view.findViewById(R.id.tv_double);
		tv_love = (TextView) view.findViewById(R.id.tv_love);
		tv_our = (TextView) view.findViewById(R.id.tv_our);
		tv_free = (TextView) view.findViewById(R.id.tv_free);
	}

	@Override
	protected void initData() {
		// 初始化标签对象数据
		initFragment();

		lists.add(tv_hot);
		lists.add(tv_new);
		lists.add(tv_first);
		lists.add(tv_double);
		lists.add(tv_love);
		lists.add(tv_our);
		lists.add(tv_free);
		// 获取标签管理对象
		fm = getChildFragmentManager();
		// 创建适配器对象
		adapter = new MyFragmentPagerAdapter(fm);
		// 绑定适配器到ViewPager
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(lists.size()-1);
		viewPager.setCurrentItem(currentPosition);// 设置默认页卡
		// 设置ViewPager的选中状态改变事件
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	protected void initOnClick() {
		// 设置文本的单击事件
		tv_hot.setOnClickListener(this);
		tv_new.setOnClickListener(this);
		tv_first.setOnClickListener(this);
		tv_double.setOnClickListener(this);
		tv_love.setOnClickListener(this);
		tv_our.setOnClickListener(this);
		tv_free.setOnClickListener(this);
	}

	@Override
	public void onMyClick(View view) {
		switch (view.getId()) {
		case R.id.tv_hot:// 好友
			viewPager.setCurrentItem(0, true);
			setCheck(0);
			break;
		case R.id.tv_new://
			viewPager.setCurrentItem(1, true);
			setCheck(1);
			break;
		case R.id.tv_first://
			setCheck(2);
			viewPager.setCurrentItem(2, true);
			break;
		case R.id.tv_double://
			setCheck(3);
			viewPager.setCurrentItem(3, true);
			break;
		case R.id.tv_love://
			setCheck(4);
			viewPager.setCurrentItem(4, true);
			break;
		case R.id.tv_our://
			setCheck(5);
			viewPager.setCurrentItem(5, true);
			break;
		case R.id.tv_free://
			setCheck(6);
			viewPager.setCurrentItem(6, true);
			break;
		}
	}

	/* 初始化标签对象数据 */
	private void initFragment() {
		TagTopicFragment fragmentTag;
		HotTopicFragment fragmentHot = new HotTopicFragment();// 热门类标签页面
		NewTopicFragment fragmentNew = new NewTopicFragment();// 新上榜类标签页面
		fragments.add(fragmentHot);
		fragments.add(fragmentNew);
		for (int i = 0; i < 5; i++) {
			fragmentTag = new TagTopicFragment(i);// 各类标签页面
			fragments.add(fragmentTag);
		}
	}

	/**
	 * 设置选中与未选中的控件
	 */
	private void setCheck(int index) {
		TextView currentView = null;
		TextView otherView = null;
		// 设置当前位置
		currentPosition = index;
		// 循环单选按钮集合,设置选中控件
		for (int i = 0; i < lists.size(); i++) {
			// 设置选中的控件
			if (i == index) {
				currentView = lists.get(i);
				currentView.setTextColor(getResources()
						.getColor(R.color.bluedc));
			} else {// 设置未选中控件
				otherView = lists.get(i);
				otherView.setTextColor(getResources().getColor(
						R.color.common_tv));
			}
		}
		// 题头滚动
		if (lastPosition < currentPosition) {
			horizontal_Scroll.smoothScrollTo(
					(int) lists.get(index).getLeft() - 5, 0);

		} else if (lastPosition > currentPosition) {
			horizontal_Scroll.smoothScrollTo((int) lists.get(index).getLeft()
					- lists.get(index).getWidth() - 25, 0);
		}
		// 改变上次位置
		lastPosition = currentPosition;
	}

	private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	/**
	 * 当前页卡状态发生改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	/**
	 * 当前页卡被滚动和触摸滑动的时候调用 arg1表示当前页卡的偏移量
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	/**
	 * 一个新的页卡被选中的时候调用
	 */
	@Override
	public void onPageSelected(int arg0) {
		// 设置当前选中的按钮
		setCheck(arg0);
	}
}
