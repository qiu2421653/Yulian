package com.njkj.yulian.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.fragment.store.MenFragment;
import com.njkj.yulian.ui.fragment.store.TrendsFragment;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description:商城
 * 
 * @date 下午7:55:42
 * 
 * @version V1.0 ==============================
 * 
 */
public class StoreFragment extends BaseFragment implements OnPageChangeListener {

	private static final String TAG = "StoreFragment";

	private ViewPager mPageVp;

	private List<Fragment> mFragmentList;
	private FragmentAdapter mFragmentAdapter;

	/**
	 * Tab显示内容TextView
	 */
	private TextView mCategoryTv, mOneTv;
	/**
	 * Tab的那个引导线
	 */
	private ImageView mTabLineIv;
	/**
	 * Fragment
	 */
	private TrendsFragment mTrends;
	private MenFragment mMen;
	/**
	 * ViewPager的当前选中页
	 */
	private int currentIndex = 0;
	/**
	 * 屏幕的宽度
	 */
	private int screenWidth;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_store, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		mCategoryTv = (TextView) view.findViewById(R.id.id_category_tv);
		mOneTv = (TextView) view.findViewById(R.id.id_one_tv);
		mTabLineIv = (ImageView) view.findViewById(R.id.id_tab_line_iv);
		mPageVp = (ViewPager) view.findViewById(R.id.id_page_vp);
	}

	@Override
	protected void initData() {
		mFragmentList = new ArrayList<Fragment>();

		mTrends = new TrendsFragment();
		mMen = new MenFragment();

		mFragmentList.add(mTrends);
		mFragmentList.add(mMen);

		mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(),
				mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setOffscreenPageLimit(mFragmentList.size());// 设置缓存数量
		mPageVp.setCurrentItem(currentIndex);
		// 测距
		initTabLineWidth();
	}

	@Override
	protected void initOnClick() {
		mCategoryTv.setOnClickListener(this);
		mOneTv.setOnClickListener(this);
		mPageVp.setOnPageChangeListener(this);
	}

	@Override
	public void onMyClick(View view) {
		switch (view.getId()) {
		case R.id.id_category_tv:
			mPageVp.setCurrentItem(0, true);
			break;
		case R.id.id_one_tv:
			mPageVp.setCurrentItem(1, true);
			break;
		}
	}

	/**
	 * 设置滑动条的宽度为屏幕的1/4(根据Tab的个数而定)
	 */
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getActivity().getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		lp.width = screenWidth / 4;
		mTabLineIv.setLayoutParams(lp);
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
		mOneTv.setTextColor(getResources().getColor(R.color.common_tv));
		mCategoryTv.setTextColor(getResources().getColor(R.color.common_tv));
	}

	/**
	 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {

	}

	/**
	 * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比 offsetPixels:当前页面偏移的像素位置
	 */
	@Override
	public void onPageScrolled(int position, float offset, int offsetPixels) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		/**
		 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来 设置mTabLineIv的左边距
		 * 滑动场景： 记4个页面, 从左到右分别为0,1,2,3 0->1; 1->2;2->3;3->2; 2->1; 1->0;
		 */
		if (currentIndex == 0 && position == 0)// 0->1
		{
			lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + (1 + currentIndex)
					* (screenWidth / 4));

		} else if (currentIndex == 1 && position == 0) // 1->0
		{
			lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 4) + (1 + currentIndex)
					* (screenWidth / 4));
		}
		mTabLineIv.setLayoutParams(lp);
	}

	@Override
	public void onPageSelected(int position) {
		resetTextView();
		switch (position) {
		case 0:
			mCategoryTv.setTextColor(getResources().getColor(R.color.black));
			break;
		case 1:
			mOneTv.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		currentIndex = position;
	}

}
