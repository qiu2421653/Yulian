package com.njkj.yulian.ui.fragment.article;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.AdvertEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.entity.TopicHotEntity;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.TopicAdapter;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.ui.gui.advert.ConvenientBanner;
import com.njkj.yulian.ui.gui.loading.OnLoadingListener;
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
 * @Description:热门帖子
 * 
 * @date 2016-6-12 下午8:02:25
 * 
 * @version 1.0 ==============================
 */
public class HotTopicFragment extends BaseFragment implements
		OnLoadMoreListener, OnItemClickListener, OnLoadingListener,
		OnRefreshListener {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;
	View advertView;

	private ConvenientBanner convenientBanner;// 顶部广告栏控件
	private WeakReference<ArrayList<TopicDetailEntity>> weakReference;
	private ArrayList<TopicDetailEntity> mTopicList;
	private TopicHotEntity topicHotEntity;
	private TopicAdapter topicDetaiAdapter;

	private int currentPage = 0;// 分页请求，当前页数
	private int pageCount = 10;// 每页显示数
	public final static int SET_NEWSLIST = 0;
	private TopicController topicController;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_topic_list, null);
		// 广告
		advertView = LayoutInflater.from(mContext).inflate(
				R.layout.activity_advert, null);
		return view;
	}

	@Override
	public void onMyClick(View view) {
	}

	@Override
	protected void initViews(View view) {
		// 广告
		convenientBanner = (ConvenientBanner) advertView
				.findViewById(R.id.convenientBanner);
		swipe_target = (ListView) view.findViewById(R.id.swipe_target);
		swipeToLoadLayout = (SwipeToLoadLayout) view
				.findViewById(R.id.swipeToLoadLayout);
		// 添加广告头
		swipe_target.addHeaderView(advertView);
		// 初始化加载
		initLoading(view);
		bindLoadingView();
	}

	@SuppressLint("NewApi")
	@Override
	protected void initData() {
		initRefresh();
		topicController = new TopicController();
		// 请求数据
		getHotTopicList(false);
	}

	// 轮换广告
	private void initAdvert() {
		List<AdvertEntity> adverts = topicHotEntity.adverts;
		// TODO 网络加载例子
		convenientBanner.setPages(adverts).setPageIndicator(
				new int[] { R.drawable.ic_page_indicator,
						R.drawable.ic_page_indicator_focused })
		// 设置翻页的效果，不需要翻页效果可用不设
		// .setPageTransformer(Transformer.ZoomOutTranformer)
		;
	}

	@Override
	protected void initOnClick() {
		swipe_target.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mContext, TopicRewardActivity.class);
		intent.putExtra("topicId", mTopicList.get(position - 1).infoId);
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
	private void getHotTopicList(final boolean isRefrsh) {
		if (isRefrsh) {
			currentPage = 0;
		}
		topicController.getHotTopicList(getString(R.string.FsGetHotTopic),
				currentPage, pageCount, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage == 0 && !isRefrsh)
								showNoNet();
							else
								showShortToast(getString(R.string.error));
						} else {
							RetEntity<TopicHotEntity> entity = (RetEntity<TopicHotEntity>) data;
							if (entity.success) {
								topicHotEntity = entity.result;
								if (topicHotEntity != null) {
									if (topicHotEntity.topicList != null
											&& topicHotEntity.topicList.size() > 0) {
										currentPage++;
									}
									if (mTopicList == null)
										mTopicList = topicHotEntity.topicList;
									else {
										if (isRefrsh) {
											mTopicList.clear();
										}
										mTopicList
												.addAll(topicHotEntity.topicList);
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
						}
						overRefresh();
					}
				});
	}

	private void setMessage() {
		if (topicHotEntity.adverts != null && topicHotEntity.adverts.size() > 0) {
			// 轮换广告
			initAdvert();
		}
		// 主题内容
		if (topicDetaiAdapter == null) {
			topicDetaiAdapter = new TopicAdapter(mTopicList);
			swipe_target.setAdapter(topicDetaiAdapter);
		} else {
			topicDetaiAdapter.notifyDataSetChanged();
		}
		showSuccess();
	}

	@Override
	public void onPause() {
		super.onPause();
		// 停止翻页
		if (topicHotEntity != null) {
			if (topicHotEntity.adverts != null
					&& topicHotEntity.adverts.size() > 0) {
				convenientBanner.stopTurning();
			}
		}
	}

	// 开始自动翻页
	@Override
	public void onResume() {
		super.onResume();
		// 开始自动翻页
		if (topicHotEntity != null) {
			if (topicHotEntity.adverts != null
					&& topicHotEntity.adverts.size() > 0) {
				convenientBanner.startTurning(5000);
			}
		}
	}

	@Override
	public void onLoadMore() {
		getHotTopicList(false);
	}

	@Override
	public void onRefresh() {
		getHotTopicList(true);
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
		getHotTopicList(false);
	}

	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		topicDetaiAdapter = null;
	}

}
