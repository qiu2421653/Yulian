package com.njkj.yulian.ui.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.CommentController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.CommentEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.CommentListAdapter;
import com.njkj.yulian.ui.adapter.CommentListAdapter.onHpicCallBack;
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
 * @Description:评论(查看被人评论的页)
 * 
 * @date 2016-3-28 下午7:09:20
 * 
 * @version 1.0 ==============================
 */
public class CommentActivity extends BaseActivity implements
		OnLoadMoreListener, OnItemClickListener, OnLoadingListener,
		onHpicCallBack, OnRefreshListener {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	ArrayList<CommentEntity> mUserList;
	CommentListAdapter adapter;
	CommentController commentController;
	int currentPage = 0;
	int pageCount = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favort);
		setHeaderTitle(R.string.evaluation);
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
		commentController = new CommentController();
		initRefresh();
		getCommentList();
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
		Intent intent = new Intent(mContext, TopicRewardActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intent.putExtra("topicId", mUserList.get(position).topicId);
		startAnimActivity(intent);
	}

	// 获取评论列表
	private void getCommentList() {
		String userID = mConfigDao.getString("userID");
		commentController.getCommentList(getString(R.string.FsGetCommentList),
				currentPage, pageCount, userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage != 0)
								showShortToast(getString(R.string.error));
							else
								showEmpty();
						} else {
							RetEntity<CommentEntity> entity = (RetEntity<CommentEntity>) data;
							if (entity.success) {
								// 成功
								setMessage(entity.result.fsGetCommentVo);
								showSuccess();
							} else {
								showFaild();
							}
						}
						overRefresh();
					}
				});
	}

	// 设置信息
	private void setMessage(ArrayList<CommentEntity> list) {
		if (mUserList == null)
			mUserList = list;
		else
			mUserList.addAll(list);
		if (adapter == null) {
			adapter = new CommentListAdapter(mContext, list);
			adapter.setCallBack(this);
			swipe_target.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		currentPage++;
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
		getCommentList();
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
		getCommentList();
	}

	@Override
	public void onHpicCall(int position) {
		// 用户ID
		Intent intent = new Intent(mContext, OtherLoveActivity.class);
		intent.putExtra("userID", mUserList.get(position).userID);
		startAnimActivity(intent);
	}
}
