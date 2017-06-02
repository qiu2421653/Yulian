package com.njkj.yulian.ui.fragment.theme;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.ui.fragment.store.TrendsFragment;
import com.njkj.yulian.ui.fragment.store.WomenFragment;
import com.njkj.yulian.utils.CLog;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.article
 * 
 * @Description:推荐外围(一级)
 * 
 * @date 2016-6-12 下午7:15:10
 * 
 * @version 1.0 ==============================
 */
public class ThemeFragment extends BaseFragment implements OnPageChangeListener {

	private ViewPager mPageVp;

	private List<Fragment> mFragmentList;
	private FragmentAdapter mFragmentAdapter;

	/**
	 * Tab显示内容TextView
	 */
	private TextView tv_hot, tv_recommend;
	/**
	 * Fragment
	 */
	private HotFragment mHot;// 热门
	private RecommenFragment mRecommen;// 推荐
	/**
	 * ViewPager的当前选中页
	 */
	private int currentIndex = 0;
	/**
	 * 第一次
	 */
	private boolean isFirst = true;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_theme, container, false);
		return view;
	}

	@Override
	protected void initViews(View view) {
		tv_hot = (TextView) view.findViewById(R.id.tv_hot);
		tv_recommend = (TextView) view.findViewById(R.id.tv_recommend);
		mPageVp = (ViewPager) view.findViewById(R.id.id_page_vp);
	}

	@Override
	protected void initData() {
		mFragmentList = new ArrayList<Fragment>();

		mHot = new HotFragment();
		mRecommen = new RecommenFragment();

		mFragmentList.add(mHot);
		mFragmentList.add(mRecommen);

		hotCallBack = mHot;

		mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(),
				mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setOffscreenPageLimit(mFragmentList.size());// 设置缓存数量
		mPageVp.setCurrentItem(currentIndex);
		mPageVp.setOnPageChangeListener(this);
	}

	@Override
	protected void initOnClick() {
		tv_hot.setOnClickListener(this);
		tv_recommend.setOnClickListener(this);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser && isFirst) {
			isFirst = false;
			hotCallBack.onHotCall(true);
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onMyClick(View view) {
		switch (view.getId()) {
		case R.id.tv_hot:
			mPageVp.setCurrentItem(0, true);
			break;
		case R.id.tv_recommend:
			mPageVp.setCurrentItem(1, true);
			break;
		}
	}

	class FragmentAdapter extends FragmentPagerAdapter {
		List<Fragment> fragmentList = new ArrayList<Fragment>();

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragmentList = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

	}

	/**
	 * 重置颜色
	 */
	private void resetTextView() {
		tv_hot.setTextColor(getResources().getColor(R.color.common_tv));
		tv_recommend.setTextColor(getResources().getColor(R.color.common_tv));
	}

	/**
	 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position, float offset, int offsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		resetTextView();
		switch (position) {
		case 0:
			tv_hot.setTextColor(getResources().getColor(R.color.bluedc));
			break;
		case 1:
			tv_recommend.setTextColor(getResources().getColor(R.color.bluedc));
			break;
		}
		currentIndex = position;
	}

	public interface onHotCallBack {
		void onHotCall(boolean state);
	}

	onHotCallBack hotCallBack;

}