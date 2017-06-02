package com.njkj.yulian.ui.activity.store;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.constant.DataContant;
import com.njkj.yulian.entity.GoodsContentEntity;
import com.njkj.yulian.entity.GoodsEntity;
import com.njkj.yulian.ui.activity.ShoppActivity;
import com.njkj.yulian.ui.adapter.GoodsAdapter;
import com.njkj.yulian.ui.gui.fab.ShowHideOnScroll;
import com.njkj.yulian.ui.gui.observablescrollview.ObservableListView;
import com.njkj.yulian.ui.gui.observablescrollview.ObservableScrollViewCallbacks;
import com.njkj.yulian.ui.gui.observablescrollview.ScrollState;
import com.njkj.yulian.ui.gui.observablescrollview.ScrollUtils;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity.store
 * 
 * @Description:商品详情 (视差效果)
 * 
 * @date 2016-6-14 下午2:55:27
 * 
 * @version 1.0 ==============================
 */
public class StoreDetailActivity extends FragmentActivity implements
		ObservableScrollViewCallbacks, OnItemClickListener, OnClickListener {

	private ImageView mImageView;
	private ImageView iv_back;
	private View mToolbarView;
	private View mListBackgroundView;
	private ObservableListView mListView;

	private int mParallaxImageHeight;
	private GoodsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_parallax);

		mImageView = (ImageView) findViewById(R.id.image);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		mToolbarView = findViewById(R.id.toolbar);
		mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0,
				getResources().getColor(R.color.primary)));

		//拉伸图
		mImageView.setImageResource(R.drawable.logo);

		mParallaxImageHeight = getResources().getDimensionPixelSize(
				R.dimen.parallax_image_height);

		mListView = (ObservableListView) findViewById(R.id.list);
		mListView.setScrollViewCallbacks(this);
		View paddingView = new View(this);
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT, mParallaxImageHeight);
		paddingView.setLayoutParams(lp);
		// paddingView.setClickable(true);

		mListBackgroundView = findViewById(R.id.list_background);
		mListView.addHeaderView(paddingView);

		View fab = findViewById(R.id.iv_shoppingcart);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 购物车
				startActivity(new Intent(StoreDetailActivity.this,
						ShoppActivity.class));
			}
		});
		mListView.setOnTouchListener(new ShowHideOnScroll(fab));

		initData();
	}

	private void initData() {
		iv_back.setOnClickListener(this);
		// TODO 获取数据
		getStoreMessage();
		mListView.setOnItemClickListener(this);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		onScrollChanged(mListView.getCurrentScrollY(), false, false);

	}

	@Override
	public void onScrollChanged(int scrollY, boolean firstScroll,
			boolean dragging) {
		int baseColor = getResources().getColor(R.color.primary);
		float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
		mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha,
				baseColor));
		ViewHelper.setTranslationY(mImageView, -scrollY / 2);
		// Translate list background
		ViewHelper.setTranslationY(mListBackgroundView,
				Math.max(0, -scrollY + mParallaxImageHeight));
	}

	@Override
	public void onDownMotionEvent() {
	}

	@Override
	public void onUpOrCancelMotionEvent(ScrollState scrollState) {
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_back:
			// finish
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 推荐信息点击
		System.out.println("Postion:" + position);
	}

	// 获取商品信息+推荐
	private void getStoreMessage() {
		// TODO
		GoodsContentEntity contentEntity = new GoodsContentEntity();
		// 头部信息
		GoodsEntity entity = new GoodsEntity();
		entity.goodsName = "鸡尾酒戒";
		entity.goodDetail = "非常非常靓丽的鸡尾酒戒,你值得拥有";
		entity.curPrice = "285$";
		entity.collections = "10";
		entity.forks = "26";
		entity.designer = "Mr.Qiu";
		entity.goodsUrl = DataContant.STORES[0];

		// 相似信息
		ArrayList<GoodsEntity> simlar = new ArrayList<GoodsEntity>();
		for (int i = 0; i < 2; i++) {
			GoodsEntity goodEntity = new GoodsEntity();
			goodEntity.curPrice = "28" + i * 10 + "5$";
			goodEntity.oriPrice = "28" + i * 15 + "5$";
			goodEntity.designer = DataContant.COMEFROM[i];
			goodEntity.goodsUrl = DataContant.STORES[i + 2];
			goodEntity.typeId = "1003";
			simlar.add(goodEntity);
		}
		contentEntity.goods = entity;
		contentEntity.simlar = simlar;

		setMessage(contentEntity);
	}

	// 设置信息
	private void setMessage(GoodsContentEntity contentEntity) {
		if (contentEntity == null) {
			return;
		}
		// 设置头部图(商品图)
		Picasso.with(MainApplication.getContext())
				.load(contentEntity.goods.goodsUrl).into(mImageView);
		if (adapter == null) {
			adapter = new GoodsAdapter(contentEntity);
			mListView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}

	}

}
