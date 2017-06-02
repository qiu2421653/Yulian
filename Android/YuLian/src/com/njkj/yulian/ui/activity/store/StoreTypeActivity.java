package com.njkj.yulian.ui.activity.store;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.constant.DataContant;
import com.njkj.yulian.entity.MallEntity;
import com.njkj.yulian.ui.activity.ShoppActivity;
import com.njkj.yulian.ui.adapter.StoreTypeAdapter;
import com.njkj.yulian.ui.gui.fab.ShowHideOnScroll;
import com.njkj.yulian.ui.gui.loading.LoadingState;
import com.njkj.yulian.ui.gui.loading.LoadingView;
import com.njkj.yulian.ui.gui.loading.OnLoadingListener;
import com.njkj.yulian.ui.gui.loading.OnRetryListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;
import com.njkj.yulian.utils.NetUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description: 商品分类
 * 
 * @date 下午10:22:48
 * 
 * @version V1.0 ==============================
 * 
 */
public class StoreTypeActivity extends FragmentActivity implements
		OnLoadMoreListener, OnItemClickListener, OnClickListener,
		OnRetryListener, OnLoadingListener {

	private static final String TAG = "StoreTypeActivity";

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	ImageView iv_finish;
	TextView title_name;
	TextView tv_filter;// 筛选
	LoadingView fl_loading;

	StoreTypeAdapter mAdapter;
	ArrayList<MallEntity> mInfos;
	MallEntity mallEntity;
	int pageCount = 10;
	int currentPage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// setTheme(R.style.AppTheme_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_type);
		mallEntity = (MallEntity) getIntent()
				.getSerializableExtra("mallEntity");
		initViews();
		initData();
		initOnClick();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	private void initViews() {
		swipe_target = (ListView) findViewById(R.id.swipe_target);
		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		title_name = (TextView) findViewById(R.id.title_name);
		tv_filter = (TextView) findViewById(R.id.tv_filter);
		iv_finish = (ImageView) findViewById(R.id.iv_finish);

		// 初始化加载
		fl_loading = (LoadingView) findViewById(R.id.fl_loading);
		bindLoadingView();

		// 浮动按钮
		View fab = findViewById(R.id.iv_shoppingcart);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(StoreTypeActivity.this,
						ShoppActivity.class));
			}
		});
		swipe_target.setOnTouchListener(new ShowHideOnScroll(fab));
	}

	@SuppressLint("NewApi")
	private void initData() {
		initRefresh();
		// TODO 题头需要更换
		if (!TextUtils.isEmpty(mallEntity.getName()))
			title_name.setText(mallEntity.getName());
		else
			title_name.setText(getString(R.string.app_name));
		setMessage();
		mAdapter = new StoreTypeAdapter(mInfos);
		swipe_target.setAdapter(mAdapter);
		showSuccess();
	}

	private void initRefresh() {
		swipeToLoadLayout.setOnLoadMoreListener(this);
		swipeToLoadLayout.setLoadingMore(false);
	}

	private void overRefresh() {
		swipeToLoadLayout.setLoadingMore(false);
	}

	private void initOnClick() {
		swipe_target.setOnItemClickListener(this);
		tv_filter.setOnClickListener(this);
		iv_finish.setOnClickListener(this);
	}

	protected void bindLoadingView() {
		fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !")
				.withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
				.withErrorIco(R.drawable.ic_chat_empty)
				.withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !")
				.withbtnErrorText("去找回她!!!")
				.withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走")
				.withNoNetIco(R.drawable.ic_chat_empty)
				.withbtnNoNetText("网弄好了，重试")
				.withLoadingIco(R.drawable.loading_animation)
				.withLoadingText("加载中...").withOnRetryListener(this).build();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_finish:
			finish();
			break;
		case R.id.tv_filter:
			// 筛选
			break;
		case R.id.iv_shoppingcart:
			// 购物车
			startActivity(new Intent(this, ShoppActivity.class));
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(MainApplication.getContext(),
				StoreDetailActivity.class);
		intent.putExtra("mailId", "10001");
		startActivity(intent);

	}

	@Override
	public void onLoadMore() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
			}
		}, 2000);

	}

	@Override
	public void showSuccess() {
		fl_loading.setVisibility(View.GONE);
		swipeToLoadLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void showEmpty() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_EMPTY);
		swipeToLoadLayout.setVisibility(View.GONE);
	}

	@Override
	public boolean checkNet() {
		return NetUtils.isNetworkAvailable(MainApplication.getContext());
	}

	@Override
	public void showFaild() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_ERROR);
		swipeToLoadLayout.setVisibility(View.GONE);
	}

	@Override
	public void showNoNet() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_NO_NET);
		swipeToLoadLayout.setVisibility(View.GONE);
	}

	@Override
	public void onRetry() {
		// 重试
	}

	// TODO 测试数据
	private void setMessage() {
		mInfos = new ArrayList<MallEntity>();
		MallEntity entity;
		for (int i = 0; i < 14; i++) {
			entity = new MallEntity();
			entity.url = DataContant.cocktails[i];
			entity.setName(DataContant.STOREDESC[i]);
			entity.comeFrom = DataContant.COMEFROM[i];
			entity.setIntegral(10 * (i + 1) + 5 + "$");
			entity.setPrice(15 * (i + 1) + 5 + "￥");
			mInfos.add(entity);
		}
	}
}
