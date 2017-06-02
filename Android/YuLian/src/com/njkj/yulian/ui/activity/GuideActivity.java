package com.njkj.yulian.ui.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.ImageView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.ui.adapter.GuidePagerAdapter;
import com.njkj.yulian.ui.adapter.GuidePagerAdapter.OnStartCallBack;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:引导页
 * 
 * @date 2016-6-21 下午2:28:58
 * 
 * @version 1.0 ==============================
 */
public class GuideActivity extends Activity implements OnStartCallBack {

	private GuidePagerAdapter mAdapter;
	private ViewPager mViewPager;
	private ImageView[] mIndicatorPoint;
	private int mIndicatorIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ac_guide);
		initUI();
	}

	private void initUI() {
		// 图片集合
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(R.drawable.bg_user_guid_1);
		l.add(R.drawable.bg_user_guid_2);
		l.add(R.drawable.bg_user_guid_3);
		l.add(R.drawable.bg_user_guid_4);
		// 找到Viewpage
		mViewPager = (ViewPager) findViewById(R.id.hvp_pager);
		// 实例化Adapter
		mAdapter = new GuidePagerAdapter();
		// 设置adapter
		mViewPager.setAdapter(mAdapter);
		// 设置监听回调
		mAdapter.setOnStartCallBack(this);
	
		mAdapter.setListData(l);
		
		mViewPager.setOffscreenPageLimit(l.size());

	}

	@Override
	public void onStartCall() {
		startActivity(new Intent(MainApplication.getContext(),
				MainActivity.class));
		ConfigDao.getInstance().setBoolean("first_start_app", false);
		finish();
	}

	@Override
	public void onLoginCall() {
		Intent intent = new Intent(MainApplication.getContext(),
				LoginActivity.class);
		intent.putExtra("isGuide", true);
		// 登录
		startActivity(intent);
		ConfigDao.getInstance().setBoolean("first_start_app", false);
		finish();
	}
}
