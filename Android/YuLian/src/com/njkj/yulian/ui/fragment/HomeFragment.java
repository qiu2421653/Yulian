package com.njkj.yulian.ui.fragment;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.UpgradeController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.core.helper.UpgradeHelper;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UpgradeEntity;
import com.njkj.yulian.ui.activity.map.MapActivity;
import com.njkj.yulian.ui.activity.search.SearchActivity;
import com.njkj.yulian.ui.fragment.article.ArticleFragment;
import com.njkj.yulian.ui.fragment.theme.ThemeFragment;
import com.njkj.yulian.utils.AppUtil;
import com.njkj.yulian.utils.CLog;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment
 * 
 * @Description:主页
 * 
 * @date 2016-6-12 下午6:57:45
 * 
 * @version 1.0 ==============================
 */
public class HomeFragment extends BaseFragment implements OnPageChangeListener {

	protected static final String TAG = "HomeFragment";

	private ViewPager mPageVp;

	private List<Fragment> mFragmentList;
	private FragmentAdapter mFragmentAdapter;

	/**
	 * Tab显示内容TextView
	 */
	private TextView id_article_tv, id_stopy_tv;

	/**
	 * 功能按键
	 */
	private LinearLayout ll_search, ll_scan;

	/**
	 * Fragment
	 */
	private ArticleFragment mAricle;
	private ThemeFragment mTheme;
	/**
	 * ViewPager的当前选中页
	 */
	private int currentIndex = 0;

	private UpgradeController mUpgradeController; // 获取版本更新
	private UpgradeHelper mUpgradeHelper; // 版本更新下载

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_theme, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		id_article_tv = (TextView) view.findViewById(R.id.id_article_tv);
		id_stopy_tv = (TextView) view.findViewById(R.id.id_stopy_tv);
		mPageVp = (ViewPager) view.findViewById(R.id.id_page_vp);
		ll_search = (LinearLayout) view.findViewById(R.id.ll_search);
		ll_scan = (LinearLayout) view.findViewById(R.id.ll_scan);
	}

	@Override
	protected void initData() {
		mFragmentList = new ArrayList<Fragment>();

		mAricle = new ArticleFragment();
		mTheme = new ThemeFragment();

		mFragmentList.add(mAricle);
		mFragmentList.add(mTheme);

		mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(),
				mFragmentList);

		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setOffscreenPageLimit(mFragmentList.size());// 设置缓存数量
		mPageVp.setCurrentItem(currentIndex);
		// 测距
		// initTabLineWidth();
		mUpgradeHelper = new UpgradeHelper(getActivity());
		mUpgradeController = new UpgradeController();
		reqUpgrade();
	}

	@Override
	protected void initOnClick() {
		mPageVp.setOnPageChangeListener(this);
		id_article_tv.setOnClickListener(this);
		id_stopy_tv.setOnClickListener(this);
		ll_search.setOnClickListener(this);
		ll_scan.setOnClickListener(this);
	}

	@Override
	public void onMyClick(View view) {
		switch (view.getId()) {
		case R.id.id_article_tv:
			mPageVp.setCurrentItem(0, true);
			break;
		case R.id.id_stopy_tv:
			mPageVp.setCurrentItem(1, true);
			break;
		case R.id.ll_scan:
			// 地图
			if (checkLogin()) {
				startAnimActivity(MapActivity.class);
			}
			break;
		case R.id.ll_search:
			// 广场
			startAnimActivity(SearchActivity.class);
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
		id_article_tv.setTextColor(getResources().getColor(R.color.common_tv));
		id_stopy_tv.setTextColor(getResources().getColor(R.color.common_tv));
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

	}

	@Override
	public void onPageSelected(int position) {
		resetTextView();
		switch (position) {
		case 0:
			id_article_tv.setTextColor(getResources().getColor(R.color.bluedc));
			break;
		case 1:
			id_stopy_tv.setTextColor(getResources().getColor(R.color.bluedc));
			break;
		}
		currentIndex = position;
	}

	// 版本更新
	private void reqUpgrade() {
		double version = AppUtil.getVersionName();
		int system = getResources().getInteger(R.integer.syscode);
		mUpgradeController.reqUpgrade(getString(R.string.FsUpGrade), system,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						// 有数据存在
						if (data != null) {
							RetEntity<UpgradeEntity> upgradeBean = (RetEntity<UpgradeEntity>) data;
							if (upgradeBean.success) {
								if (upgradeBean.result.forced == 0) {
									CLog.e(TAG, "不强制更新");
									// TODO 版本对比,更新
									double curVersion = AppUtil
											.getVersionName();
									double netVersion = Double
											.parseDouble(upgradeBean.result.versionno);
									if (curVersion < netVersion) {
										// 需要更新
										// 设置描述
										mUpgradeHelper.confNorUpdate(
												upgradeBean.result.remarks,
												false);
										mConfigDao.setString("new_verison_url",
												upgradeBean.result.path);
									}
								} else {
									CLog.e(TAG, "强制更新");
									// 设置描述
									mUpgradeHelper.confNorUpdate(
											upgradeBean.result.remarks, true);
									mConfigDao.setString("new_verison_url",
											upgradeBean.result.path);
								}
							} else {
								// 出错了,不提示用户
								CLog.d(TAG, "false");
							}
						} else {
							showShortToast(getString(R.string.error));
						}
					}
				});
	}
}
