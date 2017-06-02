package com.njkj.yulian.ui.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.CommentEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.adapter.AddConcernAdapter;
import com.njkj.yulian.ui.adapter.AddConcernAdapter.AddForkCallBack;
import com.njkj.yulian.ui.gui.PullToRefreshLayout;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:添加关注
 * 
 * @date 2016-3-28 下午2:58:04
 * 
 * @version 1.0 ==============================
 */
public class AddConcernActivity extends BaseActivity implements
		OnLoadMoreListener, OnItemClickListener {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	AddConcernAdapter adapter;
	ArrayList<CommentEntity> mUserList;
	TopicController topicController;
	private int forkCount = 10;
	private int forkPage = 0;
	private String infoId;
	private String userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_concern);
		setHeaderTitle(R.string.concern_detail);
		setHeaderBackground1(R.color.white);
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
		infoId = getIntent().getStringExtra("infoId");
		userId = getIntent().getStringExtra("userId");
		topicController = new TopicController();
		initRefresh();
		getTopicForks();
	}

	private void initOnClick() {
		swipe_target.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(mContext, OtherLoveActivity.class);
		intent.putExtra("userID", mUserList.get(position).userId);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}

	// 图片选择回调
	private AddForkCallBack onForkCallBack = new AddForkCallBack() {
		@Override
		public void onFork(int position) {
			// 用户ID
			String expID = mUserList.get(position).userId;
			// mUserList.get(position).isFork = true;
			reqTopicCareful(expID, "1");
		}
	};

	/** 获取帖子点赞人列表 */
	private void getTopicForks() {
		if (forkPage != 0)
			showDialog();
		topicController.getTopicForks(getString(R.string.FsGetTopicForks),
				forkCount, forkPage, infoId, userId, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (forkPage != 0)
								showShortToast(getString(R.string.error));
							else
								showNoNet();
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								// 成功
								setMessage(entity.result.forks);
							} else {
								if (forkPage != 0)
									showShortToast(entity.exceptions.get(0).message);
								else
									showFaild();
							}
						}
						hideProgress();
					}
				});
	}

	private void setMessage(ArrayList<CommentEntity> userList) {
		if (mUserList == null)
			mUserList = userList;
		else
			mUserList.addAll(userList);
		if (adapter == null) {
			adapter = new AddConcernAdapter(mContext, mUserList);
			adapter.setOnForkCallBack(onForkCallBack);
			swipe_target.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		forkPage++;
		showSuccess();
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

	@Override
	public void onLoadMore() {
		// 获取帖子评论列表
		getTopicForks();
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
		getTopicForks();
	}

	private void initRefresh() {
		swipeToLoadLayout.setOnLoadMoreListener(this);
		swipeToLoadLayout.setLoadingMore(false);
	}

	private void overRefresh() {
		swipeToLoadLayout.setLoadingMore(false);
	}
}
