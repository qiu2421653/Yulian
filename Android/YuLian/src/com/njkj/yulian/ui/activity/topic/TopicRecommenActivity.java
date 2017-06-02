package com.njkj.yulian.ui.activity.topic;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.ReqCode;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.controller.UserController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.CommentEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.SharedEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.activity.BaseActivity;
import com.njkj.yulian.ui.activity.ShareActivity;
import com.njkj.yulian.ui.activity.reward.RewardMoneyActivity;
import com.njkj.yulian.ui.adapter.RecommenAdapter;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnRefreshListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.DevUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity.topic
 * 
 * @Description:推荐广告
 * 
 * @date 2016-6-8 下午4:12:53
 * 
 * @version 1.0 ==============================
 */
public class TopicRecommenActivity extends BaseActivity implements
		OnLoadMoreListener, OnRefreshListener {
	protected static final String TAG = "TopicDetailActivity";

	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;
	RelativeLayout rl_input;

	RecommenAdapter recommenAdapter;

	Button tv_send;
	ImageView favor;
	ImageView forword;
	EditText et_send;
	private InputMethodManager manager; // 输入法管理

	TopicDetailEntity detailEntity;
	private int position = -1;// 用户对应位置(默认是楼主)
	private int forkCount = 10;
	private int forkPage = 0;
	private int commentCount = 5;
	private int commentPage = 0;
	TopicController topicController;
	UserController userController;
	private boolean isReply = false;
	private boolean isFavort = false;
	private String nickName;
	private String commentId;
	private String replyID;
	private String topicId;
	private int mGroupPosition = 0;// 父极下标
	private int childPosition = 0;// 子极下标

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_detail);
		setHeaderBackground1(R.color.white);
		setHeaderTitle(getString(R.string.app_name));

		setHeaderLeftText();
		initViews();
		initData();
		initOnClick();
	}

	private void initViews() {
		swipe_target = (ListView) findViewById(R.id.swipe_target);
		rl_input = (RelativeLayout) findViewById(R.id.rl_input);
		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		tv_send = (Button) findViewById(R.id.tv_send);
		favor = (ImageView) findViewById(R.id.favor);
		forword = (ImageView) findViewById(R.id.forword);
		et_send = (EditText) findViewById(R.id.et_send);
		// 初始化加载
		initLoading();
		bindLoadingView();
	}

	private void initData() {
		topicId = getIntent().getStringExtra("topicId");
		topicController = new TopicController();
		userController = new UserController();
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 刚进来设置不可以操作
		rl_input.setVisibility(View.GONE);

		initSoftInput();

		initRefresh();

		FsGetRecommenTopic(topicId);
	}

	private void initMessage() {
		// 0:没关注;1:关注
		if (detailEntity.isFork == 0) {
			favor.setImageResource(R.drawable.comment_emo_like_normal);
			isFavort = false;
		} else {
			favor.setImageResource(R.drawable.comment_emo_like_press);
			isFavort = true;
		}
		recommenAdapter = new RecommenAdapter(mContext, detailEntity,
				replyToCommentListener, replyToReplyListener);
		swipe_target.setAdapter(recommenAdapter);
		showSuccess();
		getTopicForks();
		getTopicComments();
		rl_input.setVisibility(View.VISIBLE);
	}

	private void initOnClick() {
		favor.setOnClickListener(this);
		tv_send.setOnClickListener(this);
		et_send.setOnClickListener(this);
		forword.setOnClickListener(this);
	}

	@Override
	public void onLoadMore() {
		// 获取帖子评论列表
		getTopicComments();
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
		FsGetRecommenTopic(topicId);
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_send:
			if (checkLogin()) {
				if (!isReply) {
					// 回复人
					commentId = "";
					replyID = "";
					nickName = "";
				}
				if (TextUtils.isEmpty(et_send.getText().toString())) {
					showShortToast("回复内容不能为空");
					return;
				}
				replyTopicComment(commentId, et_send.getText().toString(),
						replyID);
			}
			break;
		case R.id.title_left1:
			finish();
			break;
		case R.id.forword:
			// 分享
			reqSharedInfo();
			break;
		case R.id.favor:
			if (checkLogin()) {
				favor.setClickable(false);
				if (isFavort)
					setTopicFork(0);
				else
					setTopicFork(1);
			}
		case R.id.et_send:
			mGroupPosition = 0;
			break;
		}
	}

	/**
	 * 回复评论的监听（回复楼主）
	 */
	private OnClickListener replyToCommentListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 回复人
			mGroupPosition = (Integer) v.getTag();
			et_send.setText("");
			et_send.setFocusable(true);
			et_send.setFocusableInTouchMode(true);
			et_send.requestFocus();

			nickName = detailEntity.comments.get(mGroupPosition).nickName;
			commentId = detailEntity.comments.get(mGroupPosition).commentId;
			replyID = detailEntity.comments.get(mGroupPosition).userId;
			et_send.setHint("我 回复 " + nickName + ":");
			isReply = true;
			show();
		}
	};

	/**
	 * 互相回复的监听（楼中楼）
	 */
	private OnClickListener replyToReplyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mGroupPosition = (Integer) v.getTag(R.id.tag_first);
			childPosition = (Integer) v.getTag(R.id.tag_second);
			// 回复人内人
			et_send.setText("");
			et_send.setFocusable(true);
			et_send.setFocusableInTouchMode(true);
			et_send.requestFocus();

			nickName = detailEntity.comments.get(mGroupPosition).replyList
					.get(childPosition).fromName;
			commentId = detailEntity.comments.get(mGroupPosition).commentId;
			replyID = detailEntity.comments.get(mGroupPosition).replyList
					.get(childPosition).fromUserID;
			et_send.setHint("我 回复 " + nickName + ":");
			isReply = true;
			show();
		}
	};

	private void initSoftInput() {
		final RelativeLayout detailMainRL = (RelativeLayout) findViewById(R.id.home_news_detail_main_rl);
		detailMainRL.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						int heightDiff = detailMainRL.getRootView().getHeight()
								- detailMainRL.getHeight();
						if (heightDiff > DevUtils.getScreenHeight() * 0.3) { // 说明键盘是弹出状态
							// 键盘弹出状态
							CLog.e(TAG, "键盘弹出状态");
							favor.setVisibility(View.INVISIBLE);
							forword.setVisibility(View.INVISIBLE);
							tv_send.setVisibility(View.VISIBLE);
						} else {
							// 键盘收起状态
							CLog.e(TAG, "键盘收起状态");
							favor.setVisibility(View.VISIBLE);
							forword.setVisibility(View.VISIBLE);
							tv_send.setVisibility(View.INVISIBLE);
						}
					}
				});
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	private void show() {
		// 显示软键盘,控件ID可以是EditText,TextView
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.showSoftInput(et_send, 0);
	}

	// 获取推荐帖子
	private void FsGetRecommenTopic(final String topicId) {
		String userID = mConfigDao.getString("userID");
		topicController.getRecommen(getString(R.string.FsGetRecommen), topicId,
				userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showNoNet();
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								detailEntity = entity.result;
								if (detailEntity != null) {
									detailEntity.infoId = topicId;
									initMessage();
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

	/** 获取帖子点赞人列表 */
	private void getTopicForks() {
		topicController.getTopicForks(getString(R.string.FsGetTopicForks),
				forkCount, forkPage, detailEntity.infoId, detailEntity.userId,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								// 成功
								detailEntity.forks = entity.result.forks;
								recommenAdapter.notifyDataSetChanged();
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
					}
				});
	}

	/** 获取帖子评论列表 */
	private void getTopicComments() {
		topicController.getTopicComments(
				getString(R.string.FsGetTopicComments), commentCount,
				commentPage, detailEntity.infoId, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								if (entity.result.comments != null) {
									// 成功
									detailEntity.comments
											.addAll(entity.result.comments);
									commentPage++;
									recommenAdapter.notifyDataSetChanged();
								}

							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						overRefresh();
					}
				});
	}

	/** 获取用户信息 */
	private void getUserInfo() {
		String userID = mConfigDao.getString("userID");
		userController.getUserInfo(getString(R.string.FsGetUserInfo), userID,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								Intent intent = new Intent(mContext,
										RewardMoneyActivity.class);
								intent.putExtra("topicId", detailEntity.infoId);
								intent.putExtra("currency",
										entity.result.currency);
								startActivity(intent);

							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
					}
				});
	}

	/** 帖子内回复 */
	private void replyTopicComment(String commentId, String message,
			String replyId) {
		String userId = mConfigDao.getString("userID");
		topicController.replyTopicUser(getString(R.string.FsReplyTopicUser),
				commentId, detailEntity.infoId, message, userId, replyId,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<TopicDetailEntity> entity = (RetEntity<TopicDetailEntity>) data;
							if (entity.success) {
								if (entity.result.isSuccess == 1) {
									CommentEntity comment = new CommentEntity();
									comment.timeLag = "0分钟前";
									comment.comment = et_send.getText()
											.toString();
									comment.hPic = mConfigDao.getString("hPic");
									comment.userId = String.valueOf(0);
									if (!isReply) {
										// 回复的帖子
										comment.nickName = mConfigDao
												.getString("nickName");
										detailEntity.comments.add(0, comment);
									} else {
										// 回复人
										comment.fromName = mConfigDao
												.getString("nickName");
										comment.toName = nickName;
										if (detailEntity.comments
												.get(mGroupPosition).replyList == null) {
											detailEntity.comments
													.get(mGroupPosition).replyList = new ArrayList<CommentEntity>();
										}
										detailEntity.comments
												.get(mGroupPosition).replyList
												.add(comment);
									}
									recommenAdapter.notifyDataSetChanged();
								} else {
									showShortToast(getString(R.string.error));
								}
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
							hideKeyboard();
							isReply = false;
							mGroupPosition = 0;
							et_send.setText("");
							et_send.setHint(getString(R.string.send_comment));
						}
					}
				});
	}

	/** 点赞|取消赞 */
	private void setTopicFork(int isFork) {
		final String userID = mConfigDao.getString("userID");
		userController.setTopicFork(getString(R.string.FsSetTopicFork), userID,
				detailEntity.infoId, isFork, 1, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								if (isFavort) {
									for (CommentEntity forkEntity : detailEntity.forks) {
										if (forkEntity.userId.equals(userID)) {
											detailEntity.forks
													.remove(forkEntity);
										}
										break;
									}
									recommenAdapter.notifyDataSetChanged();
									showShortToast("取消赞");
									favor.setImageResource(R.drawable.comment_emo_like_normal);
								} else {
									showShortToast("您赞了ta");
									favor.setImageResource(R.drawable.comment_emo_like_press);
									CommentEntity userEntity = new CommentEntity();
									userEntity.hPic = mConfigDao
											.getString("hPic");
									userEntity.userId = userID;

									detailEntity.forks.add(0, userEntity);
									recommenAdapter.notifyDataSetChanged();
								}
								isFavort = !isFavort;
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						favor.setClickable(true);
					}
				});
	}

	/** 获取分享内容 */
	private void reqSharedInfo() {
		String userID = mConfigDao.getString("userID");
		showDialog();
		userController.reqSharedInfo(getString(R.string.FsSharedInfo), userID,
				0, detailEntity.infoId, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<SharedEntity> entity = (RetEntity<SharedEntity>) data;
							if (entity.success) {
								// 成功
								Intent intent = new Intent(
										TopicRecommenActivity.this,
										ShareActivity.class);
								Bundle bundle = new Bundle();
								bundle.putSerializable("sharedEntity",
										entity.result);
								bundle.putString("topicId", detailEntity.infoId);
								intent.putExtra("bundle", bundle);
								startAnimActivity(intent);
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	@Override
	protected void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		if (response == ReqCode.REQ_REPOST) {
			if (data != null) {
				boolean isState = data.getBooleanExtra("isRepost", true);
				if (isState) {
					Toast.makeText(this, "举报成功", 0).show();
				} else {
					Toast.makeText(this, "举报失败", 0).show();
				}
			}
		}
	}
}
