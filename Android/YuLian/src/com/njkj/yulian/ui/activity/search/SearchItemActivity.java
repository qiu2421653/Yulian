package com.njkj.yulian.ui.activity.search;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.DuitangInfo;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.activity.BaseActivity;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.StaggeredAdapter;
import com.njkj.yulian.ui.gui.pla.XListView;
import com.njkj.yulian.ui.gui.pla.lib.internal.PLA_AdapterView;
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
 * @Description:搜索(指定标签的搜索)
 * 
 * @date 2016-3-31 下午2:50:49
 * 
 * @version 1.0 ==============================
 */
public class SearchItemActivity extends BaseActivity
		implements
		com.njkj.yulian.ui.gui.pla.lib.internal.PLA_AdapterView.OnItemClickListener,
		OnRefreshListener, OnLoadMoreListener {

	private static final String TAG = "SearchTestActivity";

	SwipeToLoadLayout swipeToLoadLayout;
	XListView swipe_target;
	StaggeredAdapter mAdapter;
	ArrayList<DuitangInfo> mInfos;
	boolean isFirst = true;
	int firstVisibleItem = 0;
	TopicDetailEntity topEntity;
	TopicController topicController;
	int pageCount = 10;
	int currentPage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_detail);
		topEntity = (TopicDetailEntity) getIntent().getSerializableExtra(
				"topicDetailEntity");
		// TODO 题头需要更换
		if (!TextUtils.isEmpty(topEntity.tag))
			setHeaderTitle(topEntity.tag);
		else
			setHeaderTitle(getString(R.string.app_name));
		setHeaderLeftText();
		initViews();
		initData();
		initOnClick();
	}

	private void initViews() {
		swipe_target = (XListView) findViewById(R.id.swipe_target);
		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
	}

	@SuppressLint("NewApi")
	private void initData() {
		topicController = new TopicController();
		initRefresh();
		getTagList();
	}

	private void initOnClick() {
		swipe_target.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(MainApplication.getContext(),
				TopicRewardActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intent.putExtra("topicId", mInfos.get(position).topicId);
		startAnimActivity(intent);

	}

	@Override
	public void onRefresh() {
		swipeToLoadLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				overRefresh();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		getTagList();
	}

	private void initRefresh() {
		swipeToLoadLayout.setOnRefreshListener(this);
		swipeToLoadLayout.setOnLoadMoreListener(this);
		swipeToLoadLayout.setRefreshing(false);
		swipeToLoadLayout.setLoadingMore(false);

	}

	private void overRefresh() {
		swipeToLoadLayout.setRefreshing(false);
		swipeToLoadLayout.setLoadingMore(false);
	}

	/** 获取对应标签集合 */
	private void getTagList() {
		String userID = mConfigDao.getString("userID");
		String tagID = topEntity.tagId;
		showDialog();
		topicController.getTagList(getString(R.string.FsGetTagList), pageCount,
				currentPage, tagID, userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<DuitangInfo> entity = (RetEntity<DuitangInfo>) data;
							if (entity.success) {
								// 成功
								setMessage(entity.result.tagList);
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
						overRefresh();
					}
				});
	}

	private void setMessage(ArrayList<DuitangInfo> infos) {
		if (currentPage == 0 && mInfos != null) {
			mInfos.clear();
		}
		if (mInfos == null)
			this.mInfos = infos;
		else
			this.mInfos.addAll(infos);
		if (mAdapter == null) {
			mAdapter = new StaggeredAdapter(mContext, mInfos);
			swipe_target.setAdapter(mAdapter);
		}
		if (infos != null) {
			currentPage++;
		}
		mAdapter.notifyDataSetChanged();
	}
}
