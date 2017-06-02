package com.njkj.yulian.ui.fragment.topic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.TimeEntity;
import com.njkj.yulian.ui.activity.topic.TopicRewardActivity;
import com.njkj.yulian.ui.adapter.TimeLineAdapter;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.ui.gui.ExtendedListView;
import com.njkj.yulian.ui.gui.ExtendedListView.OnPositionChangedListener;
import com.njkj.yulian.ui.gui.time.MenuRightAnimations;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity.timeline
 * 
 * @Description:时间轴
 * 
 * @date 2016-4-2 下午2:53:05
 * 
 * @version 1.0 ==============================
 */
public class TimeLineFragment extends BaseFragment implements
		OnPositionChangedListener, OnItemClickListener {

	private static final String TAG = "TimeLineFragment";

	ExtendedListView dataListView;

	View view;
	ImageView timeline_track;

	private TimeEntity messages;
	private TimeLineAdapter chatHistoryAdapter;
	public final static int SET_NEWSLIST = 0;
	private TopicController topicController;
	private boolean isFirst = true;
	String userID;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		userID = bundle.getString("userID");
	}

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_time_line, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		dataListView = (ExtendedListView) view.findViewById(R.id.list_view);
		timeline_track = (ImageView) view.findViewById(R.id.timeline_track);
		// 初始化加载
		initLoading(view);
		bindLoadingView();
	}

	@Override
	protected void initData() {
		topicController = new TopicController();
		MenuRightAnimations.initOffset(getActivity());
		dataListView.setCacheColorHint(Color.TRANSPARENT);
		dataListView.setOnPositionChangedListener(this);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			if (isFirst) {
				getTimeLineList();
			}
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SET_NEWSLIST:
				timeline_track.setVisibility(View.VISIBLE);
				if (chatHistoryAdapter == null) {
					chatHistoryAdapter = new TimeLineAdapter(messages);
					dataListView.setAdapter(chatHistoryAdapter);
				} else {
					chatHistoryAdapter.notifyDataSetChanged();
				}
				isFirst = false;
				showSuccess();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void initOnClick() {
		dataListView.setOnItemClickListener(this);
	}

	@Override
	public void onMyClick(View view) {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (messages.timeList != null && messages.timeList.size() > 1) {
			Intent intent = new Intent(mContext, TopicRewardActivity.class);
			intent.putExtra("topicId",
					messages.timeList.get(position - 1).topicId);
			startAnimActivity(intent);
		}
	}

	private float[] computMinAndHour(int currentMinute, int currentHour) {
		float minuteRadian = 6f * currentMinute;
		float hourRadian = 360f / 12f * currentHour;
		float[] rtn = new float[2];
		rtn[0] = minuteRadian;
		rtn[1] = hourRadian;
		return rtn;
	}

	private float[] lastTime = { 0f, 0f };

	private RotateAnimation[] computeAni(int min, int hour) {
		RotateAnimation[] rtnAni = new RotateAnimation[2];
		float[] timef = computMinAndHour(min, hour);
		RotateAnimation ra = new RotateAnimation(lastTime[0], timef[0],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setFillAfter(true);
		ra.setFillBefore(true);
		// 设置动画的执行时间
		ra.setDuration(800);
		rtnAni[0] = ra;
		lastTime[0] = timef[0];
		RotateAnimation ra2 = new RotateAnimation(lastTime[1], timef[1],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 设置动画的执行时间
		ra2.setFillAfter(true);
		ra2.setFillBefore(true);
		ra2.setDuration(800);
		rtnAni[1] = ra2;
		lastTime[1] = timef[1];
		return rtnAni;
	}

	@Override
	public void onPositionChanged(ExtendedListView listView,
			int firstVisiblePosition, View scrollBarPanel) {

	}

	@Override
	public void onScollPositionChanged(View scrollBarPanel, int top) {
	}

	/** 获取时间轴 */
	private void getTimeLineList() {
		topicController.getUserTimeLine(getString(R.string.FsGetUserTimeLine),
				userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showNoNet();
						} else {
							RetEntity<TimeEntity> entity = (RetEntity<TimeEntity>) data;
							if (entity.success) {
								messages = entity.result.outDTO;
								handler.obtainMessage(SET_NEWSLIST)
										.sendToTarget();
							} else {
								showFaild();
							}
						}
					}
				});
	}

	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		chatHistoryAdapter = null;
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

	@Override
	public void onRetry() {
		// 重新获取数据
		getTimeLineList();
	}

	@Override
	public void showSuccess() {
		super.showSuccess();
		dataListView.setVisibility(View.VISIBLE);
	}

	@Override
	public void showEmpty() {
		super.showEmpty();
		dataListView.setVisibility(View.GONE);
	}

	@Override
	public void showFaild() {
		super.showFaild();
		dataListView.setVisibility(View.GONE);
	}

	@Override
	public void showNoNet() {
		super.showNoNet();
		dataListView.setVisibility(View.GONE);
	}

}
