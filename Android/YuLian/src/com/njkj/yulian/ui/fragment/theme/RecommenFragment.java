package com.njkj.yulian.ui.fragment.theme;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TopicEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.activity.MoreTagActivity;
import com.njkj.yulian.ui.activity.OtherLoveActivity;
import com.njkj.yulian.ui.activity.topic.TopicActivity;
import com.njkj.yulian.ui.adapter.HomeAdapter;
import com.njkj.yulian.ui.adapter.HomeAdapter.OnForkCallBack;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.ui.gui.RectRoundImageView;
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
 * @Package com.njkj.yulian.ui.fragment.-theme
 * 
 * @Description:推荐(二级)
 * 
 * @date 2016-6-12 下午7:25:45
 * 
 * @version 1.0 ==============================
 */
public class RecommenFragment extends BaseFragment implements
		OnRefreshListener, OnLoadMoreListener, OnItemClickListener {

	private static final String TAG = "HotFragment";

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	RectRoundImageView iv_goods;
	HomeAdapter homeAdapter;
	boolean isFirst = true;
	/**********************************************************************************************/

	private TopicController topicController;
	private int currentPage = 0;
	private int pageCount = 5;
	TopicEntity topEntity;
	ArrayList<TopicEntity> mTopicUrlList;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.acitvity_home, null);
		return view;
	}

	@SuppressWarnings("unchecked")
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
		topicController = new TopicController();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			// 可见
			if (isAdded() && isFirst) {
				// 请求数据
				getMessage();
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden && !isFirst) {
			// 获取主题列表
			currentPage = 0;
			mTopicUrlList.clear();
			getTopicList(false);
		}
	}

	private void getMessage() {
		// 判断有没有网络
		if (NetUtils.isNetworkAvailable(MainApplication.getContext())) {
			// 获取主题列表
			getTopicList(false);
			isFirst = false;
		} else {
			showShortToast(getString(R.string.net_error));
		}
	}

	@Override
	protected void initOnClick() {
		// tag_line2.setOnClickListener(this);
		swipe_target.setOnItemClickListener(this);

	}

	@Override
	public void onMyClick(View view) {
		switch (view.getId()) {
		case R.id.tag_line:
			// 更多标签
			startActivity(new Intent(mContext, MoreTagActivity.class));
			break;
		}
	}

	@Override
	public void onLoadMore() {
		// 上拉加载更多
		getTopicList(false);
	}

	@Override
	public void onRefresh() {
		onRetry();
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
		currentPage = 0;
		// 重新获取数据
		getTopicList(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 去掉头部后的下标位置
		Intent intent = null;
		intent = new Intent(MainApplication.getContext(), TopicActivity.class);
		// mTopicUrlList;
		intent.putExtra("topicId", mTopicUrlList.get(position).infoId);
		intent.putExtra("userID", mTopicUrlList.get(position).userID);
		startAnimActivity(intent);
	}

	// 图片选择回调
	private OnForkCallBack onForkCallBack = new OnForkCallBack() {
		// 关注
		@Override
		public void onFork(int position, View view) {
			if (checkLogin()) {
				view.setVisibility(View.GONE);
				// 用户ID
				String expID = mTopicUrlList.get(position).userID;
				mTopicUrlList.get(position).isFork = "1";
				reqTopicCareful(expID, "1");
			}
		}

		// 查看对方
		@Override
		public void onUser(int position) {
			// 用户ID
			Intent intent = new Intent(mContext, OtherLoveActivity.class);
			intent.putExtra("userID", mTopicUrlList.get(position).userID);
			startAnimActivity(intent);
		};
	};

	/**
	 * 
	 * @Title: initRefresh
	 * @Description: 初始化下拉上拉
	 * @return void
	 */
	private void initRefresh() {
		swipeToLoadLayout.setOnRefreshListener(this);
		swipeToLoadLayout.setOnLoadMoreListener(this);
		swipeToLoadLayout.setRefreshing(false);
		swipeToLoadLayout.setLoadingMore(false);
	}

	/**
	 * 
	 * @Title: overRefresh
	 * @Description: 结束刷新|加载
	 * @return void
	 */

	private void overRefresh() {
		swipeToLoadLayout.setRefreshing(false);
		swipeToLoadLayout.setLoadingMore(false);
	}

	// 获取主题列表
	private void getTopicList(final boolean isRefrsh) {
		String userID = mConfigDao.getString("userID");
		topicController.reqTopicList(
				getString(R.string.FsGetRecommendTopicList), currentPage,
				pageCount, userID, false, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage == 0)
								showNoNet();
							else
								showShortToast(getString(R.string.error));
						} else {
							RetEntity<TopicEntity> entity = (RetEntity<TopicEntity>) data;
							if (entity.success) {
								topEntity = entity.result;
								if (topEntity != null) {
									if (topEntity.topicList != null
											&& topEntity.topicList.size() > 0) {
										currentPage++;
									}
									if (mTopicUrlList == null)
										mTopicUrlList = topEntity.topicList;
									else {
										if (isRefrsh) {
											if (mTopicUrlList != null) {
												mTopicUrlList.clear();
											}
										}
										mTopicUrlList
												.addAll(topEntity.topicList);
									}
									setMessage();
								} else {
									if (currentPage == 0) {
										showEmpty();
									}
								}
							} else {
								showFaild();
							}
							overRefresh();
						}
					}
				});
	}

	/** 对用户设置关注 */
	private void reqTopicCareful(String expID, String isCareful) {
		String userID = mConfigDao.getString("userID");
		if (userID.equals(expID)) {
			showShortToast("不能对自己设置关注");
			return;
		}
		showDialog();
		topicController.reqTopicCareful(getString(R.string.FsSetTopicCareful),
				expID, isCareful, userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								showShortToast("关注成功!");
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	private void setMessage() {
		if (homeAdapter == null) {
			homeAdapter = new HomeAdapter(mTopicUrlList);
			homeAdapter.setOnForkCallBack(onForkCallBack);
			swipe_target.setAdapter(homeAdapter);
		}
		homeAdapter.notifyDataSetChanged();
		showSuccess();
	}

}
