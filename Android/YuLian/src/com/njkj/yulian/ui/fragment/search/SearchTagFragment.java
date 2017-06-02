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
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TagEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.activity.search.SearchActivity.onSearchTagCallBack;
import com.njkj.yulian.ui.activity.search.SearchItemActivity;
import com.njkj.yulian.ui.adapter.SearchTagAdapter;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnRefreshListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.search
 * 
 * @Description:搜索(标签)
 * 
 * @date 2016-4-5 上午10:07:16
 * 
 * @version 1.0 ==============================
 */
public class SearchTagFragment extends BaseFragment implements
		OnRefreshListener, OnLoadMoreListener, OnItemClickListener,
		onSearchTagCallBack {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	SearchTagAdapter adapter;
	SearchController searchController;
	boolean isFirst = true;
	private ArrayList<TagEntity> tagDTO;
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
		tagDTO = new ArrayList<TagEntity>();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			if (isFirst) {
				// 可见时
				getSearchTag();
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
		TopicDetailEntity topEntity = new TopicDetailEntity();
		topEntity.tagId = tagDTO.get(position).tagID;
		topEntity.tag = tagDTO.get(position).tagDesc;
		Intent intent = new Intent(mContext, SearchItemActivity.class);
		intent.putExtra("topicDetailEntity", topEntity);
		startAnimActivity(intent);
	}

	@Override
	public void onLoadMore() {
		// 上拉加载更多
		if (TextUtils.isEmpty(keyWork)) {
			getSearchTag();
		} else {
			reqSearchTag(keyWork);
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
		getSearchTag();
	}

	/** 获取推荐标签 */
	private void getSearchTag() {
		String userID = mConfigDao.getString("userID");
		searchController.getSearchTag(getString(R.string.FsGetSearchTag),
				userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showNoNet();
						} else {
							RetEntity<TagEntity> entity = (RetEntity<TagEntity>) data;
							if (entity.success) {
								if (entity.result.tagDTO != null) {
									// 获取成功
									setMessage(entity.result.tagDTO);
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

	/** 搜索Tag */
	private void reqSearchTag(String keyWork) {
		String userID = mConfigDao.getString("userID");
		searchController.reqSearchTag(getString(R.string.FsSearchTag), userID,
				keyWork, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showNoNet();
						} else {
							RetEntity<TagEntity> entity = (RetEntity<TagEntity>) data;
							if (entity.success) {
								if (entity.result.tagDTO != null) {
									// 获取成功
									setMessage(entity.result.tagDTO);
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

	// 设置信息
	private void setMessage(ArrayList<TagEntity> outDTO) {
		tagDTO.clear();
		tagDTO.addAll(outDTO);
		// 填充adapter
		// TODO 测试数据
		if (adapter == null) {
			adapter = new SearchTagAdapter(mContext, tagDTO);
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
			reqSearchTag(keyWork);
		}
	}
}
