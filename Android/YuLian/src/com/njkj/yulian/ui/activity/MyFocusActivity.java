package com.njkj.yulian.ui.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.controller.UserController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.MyFocusEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.adapter.MyFocusAdapter;
import com.njkj.yulian.ui.adapter.MyFocusAdapter.onDeleteCallBack;
import com.njkj.yulian.ui.gui.loading.OnLoadingListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnRefreshListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;

/**
 * @Description:我的关注
 */
public class MyFocusActivity extends BaseActivity implements
		OnLoadMoreListener, OnItemClickListener, OnLoadingListener,
		OnRefreshListener {

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	ArrayList<MyFocusEntity> mUserList;
	MyFocusAdapter adapter;

	int currentPage = 0;
	int pageCount = 10;

	UserController userController;
	TopicController topicController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favort);
		setHeaderTitle("我的关注");
		setHeaderLeftText();
		initViews();
		initData();
		initOnClick();
	}

	@Override
	protected void onResume() {
		currentPage = 0;
		getForkUsers();
		super.onResume();
	}

	private void initViews() {
		swipe_target = (ListView) findViewById(R.id.swipe_target);
		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		// 初始化加载
		initLoading();
		bindLoadingView();
	}

	private void initData() {
		initRefresh();
		topicController = new TopicController();
		userController = new UserController();
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
		// 其他人爱情
		Intent intent = new Intent(mContext, OtherLoveActivity.class);
		intent.putExtra("userID", mUserList.get(position).userID);
		startAnimActivity(intent);
	}

	/**
	 * 删除回调
	 */
	public onDeleteCallBack onDeleteCallBack = new onDeleteCallBack() {
		@Override
		public void onDelete(int position) {
			delete(position);
		}
	};

	/** 对用户取消关注 */
	private void reqTopicCareful(String expID, String isCareful,
			final int position) {
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
								showShortToast("取消关注成功");
								mUserList.remove(position);
								adapter.notifyDataSetChanged();
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
	 * @Title: getForkUsers
	 * @Description: 获取关注人列表
	 * @param
	 * @return void
	 * @throws
	 */
	private void getForkUsers() {
		String userID = mConfigDao.getString("userID");
		if (currentPage != 0)
			showDialog();
		userController.getForkUsers(getString(R.string.FsGetForkUsers),
				currentPage, pageCount, userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage != 0)
								showShortToast(getString(R.string.error));
							else
								showNoNet();
						} else {
							RetEntity<MyFocusEntity> entity = (RetEntity<MyFocusEntity>) data;
							if (entity.success) {
								setMessage(entity.result.outDTO);
							} else {
								showFaild();
							}
						}
						hideProgress();
						overRefresh();
					}
				});
	}

	/**
	 * @Title: setMessage
	 * @Description:设置信息
	 * @param @param userList
	 * @return void
	 * @throws
	 */
	private void setMessage(ArrayList<MyFocusEntity> userList) {
		if (mUserList == null) {
			mUserList = userList;
		} else {
			mUserList.clear();
			mUserList.addAll(userList);
		}
		if (adapter == null) {
			adapter = new MyFocusAdapter(mContext, this, mUserList);
			adapter.setOnDeleteCallBack(onDeleteCallBack);
			swipe_target.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		currentPage++;
		showSuccess();
	}

	/**
	 * @Title: delete
	 * @Description: TODO
	 * @param @param position
	 * @return void
	 * @throws
	 */
	private void delete(final int position) {
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle(getResources().getString(R.string.hint))
				.setMessage("是否取消关注？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 被关注用户ID
						String expID = mUserList.get(position).userID;
						reqTopicCareful(expID, "0", position);
						dialog.dismiss();
					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		dialog.show();
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
		getForkUsers();
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
		getForkUsers();
	}
}
