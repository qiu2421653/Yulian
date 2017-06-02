package com.njkj.yulian.ui.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.OnRefresh;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.activity.search.SearchItemActivity;
import com.njkj.yulian.ui.adapter.MoreTagDetailAdapter;
import com.njkj.yulian.ui.gui.PullToRefreshLayout;
import com.njkj.yulian.ui.gui.PullableListView;
import com.njkj.yulian.utils.CLog;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:更多推荐标签
 * 
 * @date 2016-5-23 下午5:47:25
 * 
 * @version 1.0 ==============================
 */
public class MoreTagActivity extends BaseActivity implements OnRefresh,
		OnItemClickListener {

	protected static final String TAG = "MoreTagActivity";

	PullToRefreshLayout refresh_view;
	PullableListView item_lv;
	MoreTagDetailAdapter adapter;
	ArrayList<TopicDetailEntity> tagEntities;
	TopicController topicController;
	int pageCount = 10;
	int currentPage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_tag);
		setHeaderTitle(R.string.more_tag);
		initViews();
		initData();
		initListener();
	}

	private void initViews() {
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		item_lv = (PullableListView) findViewById(R.id.item_lv);
	}

	private void initData() {
		topicController = new TopicController();
		getFirstTags();
	}

	private void initListener() {
		refresh_view.setOnRefreshListener(this);
		item_lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mContext, SearchItemActivity.class);
		intent.putExtra("topicDetailEntity", tagEntities.get(position));
		startAnimActivity(intent);
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// 上拉加载更多
		new Handler().postDelayed(new Runnable() {
			// 模拟下
			@Override
			public void run() {
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);

			}
		}, 1500);
	}

	/** 抓取(第一次)数据 */
	private void getFirstTags() {
		showDialog();

		topicController.getFirstList(getString(R.string.FsGetFirstList),
				pageCount, currentPage, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								if (!entity.result.outDTO.isEmpty()) {
									setMessage(entity.result.outDTO);
								}
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	/** 设置信息 */
	private void setMessage(ArrayList<TopicDetailEntity> tagList) {
		if (tagEntities == null)
			tagEntities = tagList;
		else
			tagEntities.addAll(tagList);

		if (adapter == null) {
			adapter = new MoreTagDetailAdapter(mContext, tagEntities);
			item_lv.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}
}
