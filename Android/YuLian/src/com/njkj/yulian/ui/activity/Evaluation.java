package com.njkj.yulian.ui.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.DataContant;
import com.njkj.yulian.core.callback.OnRefresh;
import com.njkj.yulian.entity.CommentEntity;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.CommentListAdapter;
import com.njkj.yulian.ui.gui.PullToRefreshLayout;
import com.njkj.yulian.ui.gui.PullableListView;

public class Evaluation extends Activity implements OnRefresh,
		OnItemClickListener {

	PullToRefreshLayout refresh_view;
	PullableListView item_lv;
	ArrayList<CommentEntity> mUserList;
	CommentListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation);
		initViews();
		initData();
		initOnClick();
	}

	private void initViews() {
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		item_lv = (PullableListView) findViewById(R.id.item_lv);
	}

	private void initData() {
		refresh_view.setOnRefreshListener(this);
		mUserList = getFavort();
		adapter = new CommentListAdapter(this, mUserList);
		item_lv.setAdapter(adapter);
	}

	private void initOnClick() {
		item_lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// startActivity(new Intent(this,TopicDetailActivity.class));
		Intent intent = new Intent(this, TopicRewardActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		// intent.putExtra("topicId", mUserList.get(position).topicId);
		startActivity(intent);
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
			}
		}, 2000);
	}

	// TODO 测试添加点赞人头像
	private ArrayList<CommentEntity> getFavort() {
		ArrayList<CommentEntity> arrayList = new ArrayList<CommentEntity>();
		CommentEntity CommentEntity;
		for (int i = 0; i < 11; i++) {
			CommentEntity = new CommentEntity();
			CommentEntity.createTime = "今天11:12";
			CommentEntity.hPic = DataContant.HEADIMG[i];
			CommentEntity.topicThumb = DataContant.PHOTOS[i];
			CommentEntity.nickName = DataContant.USERNAMES[i];
			CommentEntity.comment = "祝福 ,再接再励";
			arrayList.add(CommentEntity);
		}
		return arrayList;
	}
}
