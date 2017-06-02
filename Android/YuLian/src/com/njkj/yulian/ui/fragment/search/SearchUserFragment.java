package com.njkj.yulian.ui.fragment.search;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.SearchController;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.SearchUserEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.activity.OtherLoveActivity;
import com.njkj.yulian.ui.activity.search.SearchActivity.onSearchUserCallBack;
import com.njkj.yulian.ui.adapter.SearchUserAdapter;
import com.njkj.yulian.ui.adapter.SearchUserAdapter.OnForkUserCallBack;
import com.njkj.yulian.ui.fragment.BaseFragment;
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
 * @Description:搜索(用户)
 * 
 * @date 2016-4-5 上午10:07:16
 * 
 * @version 1.0 ==============================
 */
public class SearchUserFragment extends BaseFragment implements
		OnRefreshListener, OnLoadMoreListener, OnItemClickListener,
		onSearchUserCallBack {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	SearchUserAdapter adapter;
	SearchController searchController;
	TopicController topicController;
	ArrayList<SearchUserEntity> recommend;
	boolean isFirst = true;

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
		recommend = new ArrayList<SearchUserEntity>();
		topicController = new TopicController();
		searchController = new SearchController();

		getSearchUser();
		isFirst = false;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			if (!isFirst) {
				getSearchUser();
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	protected void initOnClick() {
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
	public void onMyClick(View view) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mContext, OtherLoveActivity.class);
		intent.putExtra("userID", recommend.get(position).userID);
		startAnimActivity(intent);
	}

	@Override
	public void onLoadMore() {
		if (TextUtils.isEmpty(keyWork)) {
			getSearchUser();
		} else {
			// 上拉加载更多
			reqSearchUser(keyWork);

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
		getSearchUser();
	}

	// 回调
	private OnForkUserCallBack onForkCallBack = new OnForkUserCallBack() {
		@Override
		public void onFork(int position) {
			// 用户ID
			String expID = recommend.get(position).userID;
			reqTopicCareful(expID, "1");
		}
	};

	/** 获取推荐用户 */
	private void getSearchUser() {
		String userID = mConfigDao.getString("userID");
		searchController.getSearchUser(getString(R.string.FsGetSearchHistory),
				userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showNoNet();
						} else {
							RetEntity<SearchUserEntity> entity = (RetEntity<SearchUserEntity>) data;
							if (entity.success) {
								if (entity.result.outDTO != null) {
									// 获取成功
									setMessage(entity.result.outDTO.get(0),
											true);
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

	/** 搜索用户 */
	private void reqSearchUser(String nickName) {
		String userID = mConfigDao.getString("userID");
		searchController.reqSearchUser(getString(R.string.FsSearchUser),
				userID, nickName, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showNoNet();
						} else {
							RetEntity<SearchUserEntity> entity = (RetEntity<SearchUserEntity>) data;
							if (entity.success) {
								if (entity.result.outDTO != null) {
									// 获取成功
									setMessage(entity.result.outDTO.get(0),
											true);
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

	/** 对用户设置关注 */
	private void reqTopicCareful(String expID, String isCareful) {
		String userID = mConfigDao.getString("userID");
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

	/**
	 * 
	 * 设置信息 <br>
	 * isRecommen:推荐|搜索
	 * */
	private void setMessage(SearchUserEntity result, boolean isRecommen) {
		recommend.clear();
		recommend.addAll(result.recommend);
		// 填充adapter
		if (adapter == null) {
			adapter = new SearchUserAdapter(mContext, recommend);
			swipe_target.setAdapter(adapter);
			adapter.setOnForkCallBack(onForkCallBack);
		} else {
			// adapter.setState(isRecommen);
			adapter.notifyDataSetChanged();
		}
		showSuccess();
	}

	@Override
	public void onSearchCallBack(String mKeyWork) {
		this.keyWork = mKeyWork;
		if (!TextUtils.isEmpty(keyWork)) {
			reqSearchUser(keyWork);
		}
	}
}
