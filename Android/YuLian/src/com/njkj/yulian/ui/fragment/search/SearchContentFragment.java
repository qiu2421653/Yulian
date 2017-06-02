package com.njkj.yulian.ui.fragment.search;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.SearchController;
import com.njkj.yulian.core.callback.OnRefresh;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.activity.search.SearchActivity.onSearchContentCallBack;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.SearchContentAdapter;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.ui.gui.PullToRefreshLayout;
import com.njkj.yulian.ui.gui.PullableListView;
import com.njkj.yulian.ui.gui.loading.LoadingState;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnRefreshListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;
import com.njkj.yulian.utils.NetUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.search
 * 
 * @Description:搜索(内容)
 * 
 * @date 2016-4-5 上午10:07:16
 * 
 * @version 1.0 ==============================
 */
public class SearchContentFragment extends BaseFragment implements
		OnRefreshListener, OnLoadMoreListener, OnItemClickListener,
		onSearchContentCallBack {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	SearchContentAdapter adapter;
	SearchController searchController;
	boolean isFirst = true;
	ArrayList<TopicDetailEntity> contentDTO;
	private String keyWork;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_search_fragment, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		swipe_target = (ListView) view.findViewById(R.id.swipe_target);
		swipeToLoadLayout = (SwipeToLoadLayout) view
				.findViewById(R.id.swipeToLoadLayout);
		// 初始化加载
		initLoading(view);
		bindLoadingView();
	}

	@Override
	protected void initData() {
		initRefresh();
		searchController = new SearchController();
		contentDTO = new ArrayList<TopicDetailEntity>();
	}

	@Override
	protected void initOnClick() {
		swipe_target.setOnItemClickListener(this);
	}

	@Override
	public void onMyClick(View view) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mContext, TopicRewardActivity.class);
		intent.putExtra("topicId", contentDTO.get(position).infoID);
		startAnimActivity(intent);
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
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			if (isFirst) {
				getSearchContent();
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onLoadMore() {
		// 上拉加载更多

		if (TextUtils.isEmpty(keyWork)) {
			getSearchContent();
		} else {
			reqSearchContent(keyWork);
		}
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
	public void showSuccess() {
		fl_loading.setVisibility(View.GONE);
		swipeToLoadLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void showEmpty() {
		swipeToLoadLayout.setVisibility(View.GONE);
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_EMPTY);
	}

	@Override
	public boolean checkNet() {
		return NetUtils.isNetworkAvailable(mContext);
	}

	@Override
	public void showFaild() {
		swipeToLoadLayout.setVisibility(View.GONE);
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_ERROR);
	}

	@Override
	public void showNoNet() {
		swipeToLoadLayout.setVisibility(View.GONE);
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_NO_NET);
	}

	@Override
	public void onRetry() {
		// 重新获取数据
		getSearchContent();
	}

	/** 获取推荐内容 */
	private void getSearchContent() {
		String userID = mConfigDao.getString("userID");
		searchController.getSearchContent(
				getString(R.string.FsGetSearchContent), userID,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showNoNet();
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								// 获取成功
								ArrayList<TopicDetailEntity> outDTO = entity.result.outDTO;
								if (outDTO != null) {
									setMessage(outDTO);
									isFirst = false;
								} else {
									showEmpty();
								}
							} else {
								showFaild();
							}
						}
						overRefresh();
					}
				});
	}

	/** 搜索content */
	private void reqSearchContent(String keyWork) {
		String userID = mConfigDao.getString("userID");

		searchController.reqSearchContent(getString(R.string.FsSearchContent),
				userID, keyWork, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showNoNet();
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								// 获取成功
								ArrayList<TopicDetailEntity> outDTO = entity.result.outDTO;
								if (outDTO != null) {
									setMessage(outDTO);
									isFirst = false;
								} else {
									showEmpty();
								}
							} else {
								showFaild();
							}
							overRefresh();
						}
					}
				});
	}

	// 设置信息
	private void setMessage(ArrayList<TopicDetailEntity> mTopicList) {
		contentDTO.clear();
		contentDTO.addAll(mTopicList);
		// 填充adapter
		// TODO 测试数据
		if (adapter == null) {
			adapter = new SearchContentAdapter(mContext, contentDTO);
			swipe_target.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		showSuccess();

	}

	@Override
	public void onSearchCallBack(String mKeyWork) {
		this.keyWork = mKeyWork;
		if (!TextUtils.isEmpty(keyWork)) {
			reqSearchContent(keyWork);
		}

	}
}
