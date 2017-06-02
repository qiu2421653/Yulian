package com.njkj.yulian.ui.activity.search;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.fragment.search.SearchContentFragment;
import com.njkj.yulian.ui.fragment.search.SearchTagFragment;
import com.njkj.yulian.ui.fragment.search.SearchUserFragment;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian
 * 
 * @Description:搜索页面
 * 
 * @date 2016-4-5 上午9:41:18
 * 
 * @version 1.0 ==============================
 */
public class SearchActivity extends FragmentActivity implements
		OnClickListener, OnPageChangeListener {
	private static final String TAG = "SearchActivity";
	ViewPager view_pager;
	TextView tv_user;
	TextView tv_tag;
	TextView tv_content;
	EditText search_box;
	ImageView back;

	FragmentAdapter mFragmentAdapter;
	SearchTagFragment tagFragment;
	SearchUserFragment userFragment;
	SearchContentFragment contentFragment;

	List<Fragment> mFragmentList = new ArrayList<Fragment>();
	ArrayList<TextView> tvList = new ArrayList<TextView>();
	/** ViewPager的当前选中页 */
	int currentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initData();
		initOnClick();
	}

	private void initView() {
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		tv_user = (TextView) findViewById(R.id.tv_user);
		tv_tag = (TextView) findViewById(R.id.tv_tag);
		tv_content = (TextView) findViewById(R.id.tv_content);
		search_box = (EditText) findViewById(R.id.search_box);
		back = (ImageView) findViewById(R.id.back);

	}

	private void initData() {
		tagFragment = new SearchTagFragment();
		userFragment = new SearchUserFragment();
		contentFragment = new SearchContentFragment();

		mFragmentList.add(userFragment);
		mFragmentList.add(tagFragment);
		mFragmentList.add(contentFragment);

		tvList.add(tv_user);
		tvList.add(tv_tag);
		tvList.add(tv_content);
		onSearchUserCallBack = userFragment;
		onSearchTagCallBack = tagFragment;
		onSearchContentCallBack = contentFragment;

		mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
		view_pager.setOffscreenPageLimit(mFragmentList.size());// 设置缓存数量
		view_pager.setAdapter(mFragmentAdapter);
		view_pager.setCurrentItem(currentIndex);

		setTvCol(currentIndex);
	}

	private void initOnClick() {
		view_pager.setOnPageChangeListener(this);
		back.setOnClickListener(this);
		tv_user.setOnClickListener(this);
		tv_tag.setOnClickListener(this);
		tv_content.setOnClickListener(this);
		// 输入框的文本变化监听
		search_box.addTextChangedListener(new MyTextWatcher());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.tv_user:
			// 用户
			view_pager.setCurrentItem(0, true);
			break;
		case R.id.tv_tag:
			// 标签
			view_pager.setCurrentItem(1, true);
			break;
		case R.id.tv_content:
			// 标签
			view_pager.setCurrentItem(2, true);
			break;
		}
	}

	// 设置选择字体颜色
	private void setTvCol(int position) {
		int len = tvList.size();
		for (int i = 0; i < len; i++) {
			/** 未被选中的字体颜色 */
			tvList.get(i).setTextColor(
					getResources().getColor(R.color.common_tv));
		}
		/** 被选中的字体颜色 */
		tvList.get(position).setTextColor(
				getResources().getColor(R.color.bluelight));
	}

	class FragmentAdapter extends FragmentPagerAdapter {
		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}
	}

	@Override
	public void onPageSelected(int position) {
		search_box.setText("");
		search_box.setHint(getString(R.string.search_box_hint));
		currentIndex = position;
		setTvCol(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	/**
	 * EditText输入框监听事件
	 * */
	class MyTextWatcher implements TextWatcher {
		String keyword;

		@Override
		public void afterTextChanged(Editable s) {// 输入框内容改变时执行
			keyword = s.toString();
			switch (currentIndex) {
			case 0:
				onSearchUserCallBack.onSearchCallBack(keyword);
				break;
			case 1:
				onSearchTagCallBack.onSearchCallBack(keyword);
				break;
			case 2:
				onSearchContentCallBack.onSearchCallBack(keyword);
				break;
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
	}

	public interface onSearchUserCallBack {
		void onSearchCallBack(String keyWork);
	}

	onSearchUserCallBack onSearchUserCallBack;

	public interface onSearchTagCallBack {
		void onSearchCallBack(String keyWork);
	}

	onSearchTagCallBack onSearchTagCallBack;

	public interface onSearchContentCallBack {
		void onSearchCallBack(String keyWork);
	}

	onSearchContentCallBack onSearchContentCallBack;
}
