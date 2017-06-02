package com.njkj.yulian.ui.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.PraiseController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.PraiseEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.PraiseAdapter;
import com.njkj.yulian.ui.adapter.PraiseAdapter.onPraiseCallBack;
import com.njkj.yulian.ui.gui.loading.OnLoadingListener;
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
 * @Description:赞(查看被人点赞的页)
 * 
 * @date 2016-3-28 下午6:04:46
 * 
 * @version 1.0 ==============================
 */
public class PraiseActivity extends BaseActivity implements OnLoadMoreListener,
		OnItemClickListener, OnLoadingListener, onPraiseCallBack,OnRefreshListener {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	ArrayList<PraiseEntity> mUserList;
	PraiseAdapter adapter;
	PraiseController controller;
	int currentPage = 0;
	int pageCount = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favort);
		setHeaderTitle(R.string._favort);
		setHeaderLeftText();
		initViews();
		initOnClick();
		initData();
	}

	private void initViews() {
		swipe_target = (ListView) findViewById(R.id.swipe_target);
		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		// 初始化加载
		initLoading();
		bindLoadingView();
	}

	private void initData() {
		controller = new PraiseController();
		initRefresh();
		getForkList();
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
		Intent Intent = new Intent(this, TopicRewardActivity.class);
		Intent.putExtra("topicId", mUserList.get(position).topicId);
		startAnimActivity(Intent);
	}

	// 获取点赞列表
	private void getForkList() {
		String userID = mConfigDao.getString("userID");
		controller.getForksList(getString(R.string.FsGetForksList),
				currentPage, pageCount, userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage != 0)
								showShortToast(getString(R.string.error));
							else
								showNoNet();
						} else {
							RetEntity<PraiseEntity> entity = (RetEntity<PraiseEntity>) data;
							if (entity.success) {
								// 成功
								setMessage(entity.result.fsGetForksVo);
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
	private void setMessage(ArrayList<PraiseEntity> userList) {
		if (mUserList == null)
			mUserList = userList;
		else
			mUserList.addAll(userList);
		if (adapter == null) {
			adapter = new PraiseAdapter(mContext, mUserList);
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
		getForkList();
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
		getForkList();
	}

	@Override
	public void onPraiseCall(int position) {
		Intent intent = new Intent(mContext, OtherLoveActivity.class);
		intent.putExtra("userID", mUserList.get(position).userID);
		startAnimActivity(intent);
	}

}
