package com.njkj.yulian.ui.activity.topic;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.search.SearchActivity;
import com.njkj.yulian.ui.fragment.topic.TimeLineFragment;
import com.njkj.yulian.ui.fragment.topic.TopicFragment;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian
 * 
 * @Description:个人主题列表
 * 
 * @date 2016-3-25 上午9:21:52
 * 
 * @version 1.0 ==============================
 */

public class TopicActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {
	ImageView concern_icon;// 全部
	ImageView time_icon;// 时间轴
	ImageView search; // 搜索
	ImageView iv_back;

	TextView tv_all;// 列表
	TextView tv_time;// 时间轴

	ViewPager view_pager;//
	RelativeLayout top_Text;// 提示框

	TopicFragment topicFragment;
	TimeLineFragment timeLineFragment;

	RelativeLayout view_line;// 分割线

	private TranslateAnimation mShowAction, mShowViewAction;// 显示动画
	private TranslateAnimation mHiddenAction, mHiddenViewAction;// 隐藏动画
	private FragmentAdapter mFragmentAdapter;
	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private ArrayList<ImageView> ivList = new ArrayList<ImageView>();
	private ArrayList<TextView> tvList = new ArrayList<TextView>();
	private int currentIndex = 0;// ViewPager的当前选中页
	private boolean isShow = true;// 显示动画
	private Bundle bundle;

	/** 界面底部的选中菜单按钮资源(图片) */
	int[] select_on = { R.drawable.concern_icon_pre,
			R.drawable.square_icon_pre, R.drawable.move_pre };
	/** 界面底部的未选中菜单按钮资源 (图片) */
	int[] select_off = { R.drawable.concern_icon_nor,
			R.drawable.square_icon_nor, R.drawable.move_nor };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_home_page);
		initViews();
		initOnClick();
		initData();
	}

	private void initViews() {
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		view_pager.setOffscreenPageLimit(2);
		concern_icon = (ImageView) findViewById(R.id.concern_icon);
		time_icon = (ImageView) findViewById(R.id.time_icon);
		search = (ImageView) findViewById(R.id.search);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		top_Text = (RelativeLayout) findViewById(R.id.top_Text);

		tv_all = (TextView) findViewById(R.id.tv_all);
		tv_time = (TextView) findViewById(R.id.tv_time);

		view_line = (RelativeLayout) findViewById(R.id.view_line);

		tvList.add(tv_all);
		tvList.add(tv_time);
	}

	private void initData() {
		String infoId = getIntent().getStringExtra("topicId");
		String userID = getIntent().getStringExtra("userID");
		bundle = new Bundle();
		bundle.putString("topicId", infoId);
		bundle.putString("userID", userID);
		topicFragment = new TopicFragment();
		timeLineFragment = new TimeLineFragment();
		mFragmentList.add(topicFragment);
		mFragmentList.add(timeLineFragment);
		ivList.add(concern_icon);
		ivList.add(time_icon);

		mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
		view_pager.setOffscreenPageLimit(ivList.size());// 设置缓存数量
		view_pager.setAdapter(mFragmentAdapter);
		view_pager.setCurrentItem(currentIndex);
		topicFragment.setArguments(bundle);
		timeLineFragment.setArguments(bundle);

		setClickIcon(currentIndex);
		setTextCl(0);
		// 初始化动画
		initAnimation();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 显示动画
				show();
			}
		}, 300);
	}

	private void initOnClick() {
		concern_icon.setOnClickListener(this);
		time_icon.setOnClickListener(this);
		// move_icon.setOnClickListener(this);
		search.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		view_pager.setOnPageChangeListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.concern_icon:
			// 全部的
			view_pager.setCurrentItem(0, true);
			if (isShow) {
				show();
				setTextCl(0);
			}
			break;
		case R.id.time_icon:
			// 时间轴的
			view_pager.setCurrentItem(1, true);
			if (isShow) {
				show();
				setTextCl(1);
			}
			break;
		case R.id.search:
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_back:
			finish();
			break;
		}
	}

	// 设置选择图标
	private void setClickIcon(int position) {
		int len = ivList.size();
		for (int i = 0; i < len; i++) {
			ivList.get(i).setImageResource(select_off[i]);
		}
		ivList.get(position).setImageResource(select_on[position]);
	}

	@Override
	public void onPageSelected(int position) {
		currentIndex = position;
		setTextCl(position);
		setClickIcon(position);
		if (isShow) {
			show();
		}
	}

	@Override
	public void onPageScrollStateChanged(int position) {
	}

	@Override
	public void onPageScrolled(int position, float offset, int offsetPixels) {
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

	// 设置字体颜色
	private void setTextCl(int position) {
		int len = tvList.size();
		for (int i = 0; i < len; i++) {
			tvList.get(i).setTextColor(
					getResources().getColor(R.color.common_tv));
		}
		tvList.get(position).setTextColor(
				getResources().getColor(R.color.bluedc));
	}

	// 切换显示
	private void show() {
		isShow = false;
		top_Text.startAnimation(mShowAction);
		view_line.startAnimation(mShowViewAction);

		top_Text.setVisibility(View.VISIBLE);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				top_Text.startAnimation(mHiddenAction);
				view_line.startAnimation(mHiddenViewAction);
				mHiddenAction.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						isShow = true;
						top_Text.setVisibility(View.GONE);
					}
				});

			}
		}, 1300);
	}

	private void initAnimation() {
		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(700);

		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1f);
		mHiddenAction.setDuration(700);

		mShowViewAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				1.0f);
		mShowAction.setDuration(700);

		mHiddenViewAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
				-0f);
		mHiddenAction.setDuration(700);
	}

}
