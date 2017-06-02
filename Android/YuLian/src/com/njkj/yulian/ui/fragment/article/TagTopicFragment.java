package com.njkj.yulian.ui.fragment.article;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.njkj.yulian.entity.TopicNewEntity;
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
 * @Package com.njkj.yulian.ui.fragment.article
 * 
 * @Description:标签帖子
 * 
 * @date 2016-6-13 上午10:48:23
 * 
 * @version 1.0 ==============================
 */
public class TagTopicFragment extends BaseFragment implements
		OnLoadMoreListener, OnItemClickListener, OnRefreshListener {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	private ArrayList<TopicDetailEntity> mTopicList;
	private TopicAdapter topicDetaiAdapter;

	private int currentPage = 0;// 分页请求，当前页数
	private int pageCount = 10;// 每页显示数
	public final static int SET_NEWSLIST = 0;
	private TopicController topicController;

	private int position = 0;

	public TagTopicFragment() {
	}

	public TagTopicFragment(int position) {
		this.position = position;
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

	@Override
	protected void initData() {
		initRefresh();
		// 缓解加载慢的问题
		topicController = new TopicController();

	}

	/**
	 * 
	 * @Title: getTagCode
	 * @Description: 获取tag(传A|B|C|D|E)
	 * @param @param position
	 * @param @return
	 * @return String
	 * @throws
	 */
	private String getTagCode(int position) {
		if (isAdded()) {
			String[] stringArray = getResources().getStringArray(
					R.array.tagArray);
			return stringArray[position];
		}
		return "";
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			// 可见
			if (mTopicList != null && mTopicList.size() != 0) {
				handler.obtainMessage(SET_NEWSLIST).sendToTarget();
			} else {
				if (isAdded()) {
					// 请求数据
					getTagTopicList(getTagCode(position), false);
				}

			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SET_NEWSLIST:
				swipe_target.setVisibility(View.VISIBLE);
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
	private void getTagTopicList(String tagCode, final boolean isRefrsh) {
		if (isRefrsh) {
			currentPage = 0;
		}
		topicController.getTagTopicList(getString(R.string.FsGetTagsTopic),
				currentPage, pageCount, tagCode, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage == 0 && !isRefrsh)
								showNoNet();
							else
								showShortToast(getString(R.string.error));
						} else {
							RetEntity<TopicNewEntity> entity = (RetEntity<TopicNewEntity>) data;
							if (entity.success) {
								if (entity.result != null) {
									if (entity.result.topicList != null
											&& entity.result.topicList.size() > 0) {
										currentPage++;
									}
									if (mTopicList == null)
										mTopicList = entity.result.topicList;
									else {
										if (isRefrsh) {
											mTopicList.clear();
										}
										mTopicList
												.addAll(entity.result.topicList);
									}

									setMessage();
								} else {
									showEmpty();
								}
							} else {
								showFaild();
								// showShortToast(entity.exceptions.get(0).message);
							}
						}
						overRefresh();
					}
				});
	}

	private void setMessage() {
		// 主题内容
		if (topicDetaiAdapter == null) {
			topicDetaiAdapter = new TopicAdapter(mTopicList);
			swipe_target.setAdapter(topicDetaiAdapter);
		} else {
			topicDetaiAdapter.notifyDataSetChanged();
		}
		handler.obtainMessage(SET_NEWSLIST).sendToTarget();
		showSuccess();
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
		getTagTopicList(getTagCode(position), false);
	}

	@Override
	public void onRefresh() {
		getTagTopicList(getTagCode(position), true);
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
		getTagTopicList(getTagCode(position), false);
	}

}
