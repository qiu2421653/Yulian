package com.njkj.yulian.ui.fragment.topic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.TopicAdapter;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnRefreshListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;

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
public class TopicFragment extends BaseFragment implements OnLoadMoreListener,
		OnRefreshListener, OnItemClickListener {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	WeakReference<ArrayList<TopicDetailEntity>> weakReference;
	ArrayList<TopicDetailEntity> mTopicList;
	TopicAdapter topicDetaiAdapter;

	private int currentPage = 0;// 分页请求，当前页数
	private int pageCount = 15;// 每页显示数
	public final static int SET_NEWSLIST = 0;
	private TopicController topicController;
	private String topicID = "";

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		topicID = bundle.getString("topicId");
	}

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_topic_list, null);
		return view;
	}

	@Override
	public void onMyClick(View view) {
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

	@SuppressLint("NewApi")
	@Override
	protected void initData() {
		initRefresh();
		// 缓解加载慢的问题
		topicController = new TopicController();
		// 请求数据
		getUserTopicList();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			// 可见
			if (mTopicList != null && mTopicList.size() != 0) {
				handler.obtainMessage(SET_NEWSLIST).sendToTarget();
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SET_NEWSLIST:
				if (topicDetaiAdapter == null) {
					topicDetaiAdapter = new TopicAdapter(mTopicList);
					swipe_target.setAdapter(topicDetaiAdapter);
				} else {
					topicDetaiAdapter.notifyDataSetChanged();
				}
				showSuccess();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void initOnClick() {
		swipe_target.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mContext, TopicRewardActivity.class);
		intent.putExtra("topicId", mTopicList.get(position).infoId);
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

	// 获取数据
	private void getUserTopicList() {
		String userID = mConfigDao.getString("userID");
		topicController.getUserTopicList(
				getString(R.string.FsGetUserTopicList), currentPage, pageCount,
				topicID, userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage == 0)
								showNoNet();
							else {
								showShortToast(getString(R.string.error));
							}
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								if (entity.result.topicList != null
										&& entity.result.topicList.size() > 0) {
									currentPage++;
									if (mTopicList == null)
										mTopicList = entity.result.topicList;
									else
										mTopicList
												.addAll(entity.result.topicList);
									handler.obtainMessage(SET_NEWSLIST)
											.sendToTarget();
								} else {
									if (currentPage == 0) {
										showEmpty();
									}
								}
							} else {
								showFaild();
							}
						}
						overRefresh();
					}
				});
	}

	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		topicDetaiAdapter = null;
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

	@Override
	public void onLoadMore() {
		getUserTopicList();
	}

	@Override
	public void onRetry() {
		// 重新获取数据
		getUserTopicList();
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
	public void onRefresh() {
		swipeToLoadLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				overRefresh();
			}
		}, 1500);
	}
}
