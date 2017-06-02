package com.njkj.yulian.ui.activity.reward;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.controller.GoldController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.ReWardEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.ui.activity.BaseActivity;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.RewardListAdapter;
import com.njkj.yulian.ui.gui.loading.OnLoadingListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnRefreshListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:打赏页列表
 * 
 * @date 2016-4-1 下午2:15:40
 * 
 * @version 1.0 ==============================
 */
public class RewardActivity extends BaseActivity implements OnLoadMoreListener,
		OnItemClickListener, OnLoadingListener, OnRefreshListener {
	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;
	ArrayList<ReWardEntity> mUserList;
	RewardListAdapter adapter;
	GoldController goldController;
	int currentPage = 0;
	int pageCount = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favort);
		setHeaderTitle(R.string.reward);
		setHeaderLeftText();
		initViews();
		initData();
		initOnClick();
	}

	private void initViews() {
		swipe_target = (ListView) findViewById(R.id.swipe_target);
		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		// 初始化加载
		initLoading();
		bindLoadingView();
	}

	private void initData() {
		initRefresh();
		goldController = new GoldController();
		getRewardList();
	}

	private void initOnClick() {
		swipe_target.setOnItemClickListener(this);
	}
	private void initRefresh() {
		swipeToLoadLayout.setOnRefreshListener(this);
		swipeToLoadLayout.setOnLoadMoreListener(this);
		swipeToLoadLayout.setLoadingMore(false);
		swipeToLoadLayout.setRefreshing(false);
	}

	private void overRefresh() {
		swipeToLoadLayout.setLoadingMore(false);
		swipeToLoadLayout.setRefreshing(false);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, TopicRewardActivity.class);
		intent.putExtra("topicId", mUserList.get(position).topicId);
		startAnimActivity(intent);
	}

	/** 获取打赏页列表集合 */
	private void getRewardList() {
		if (currentPage != 0)
			showDialog();
		String userID = mConfigDao.getString("userID");
		goldController.getRewardList(getString(R.string.FsGetRewardList),
				userID, currentPage, pageCount, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage == 0)
								showNoNet();
							else
								showShortToast(getString(R.string.error));
						} else {
							RetEntity<ReWardEntity> entity = (RetEntity<ReWardEntity>) data;
							if (entity.success) {
								setMessage(entity.result.fsGetRewardListVo);
							} else {
								showFaild();
							}
						}
						hideProgress();
						overRefresh();
					}
				});
	}

	private void setMessage(ArrayList<ReWardEntity> mRrewardList) {
		currentPage++;
		if (mUserList == null)
			mUserList = mRrewardList;
		else
			mUserList.addAll(mRrewardList);
		if (adapter == null) {
			adapter = new RewardListAdapter(MainApplication.getContext(),
					mUserList);
			swipe_target.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		showSuccess();
	}

	@Override
	public void onRefresh() {
		swipeToLoadLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				overRefresh();
			}
		}, 1500);
	}

	@Override
	public void onLoadMore() {
		// 重新获取数据
		getRewardList();
	}

	@Override
	public void showSuccess() {
		super.showSuccess();
		swipeToLoadLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void showEmpty() {
		super.showEmpty();
		swipeToLoadLayout.setVisibility(View.GONE);
	}

	@Override
	public void showFaild() {
		super.showFaild();
		swipeToLoadLayout.setVisibility(View.GONE);
	}

	@Override
	public void showNoNet() {
		super.showNoNet();
		swipeToLoadLayout.setVisibility(View.GONE);
	}

	@Override
	public void onRetry() {
		// 重新获取数据
		getRewardList();
	}

}
