package com.njkj.yulian.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.constant.ReqCode;
import com.njkj.yulian.controller.SharedController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.SharedEntity;
import com.njkj.yulian.sharesdk.onekeyshare.OnekeyShare;
import com.njkj.yulian.ui.gui.loading.LoadingState;
import com.njkj.yulian.ui.gui.loading.LoadingView;
import com.njkj.yulian.ui.gui.loading.OnLoadingListener;
import com.njkj.yulian.ui.gui.loading.OnRetryListener;
import com.njkj.yulian.utils.NetUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description: 分享页
 * 
 * @date 下午10:29:36
 * 
 * @version V1.0 ==============================
 * 
 */
public class ShareActivity extends FragmentActivity implements
		View.OnClickListener, OnRetryListener, OnLoadingListener {
	private TextView tvFriend, tvTimeline, qrFriend, tvCopylink;
	private LinearLayout ll_bottom, ll_top;
	protected LoadingView fl_loading;
	private int screenWidth = 0;
	private static final int ANIM_TIME = 300;
	private OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
	private Context context;

	String topicId;
	SharedEntity sharedEntity;// 分享内容
	SharedController sharedController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		context = this;
		Bundle bundle = getIntent().getBundleExtra("bundle");
		topicId = bundle.getString("topicId");
		sharedEntity = (SharedEntity) bundle.getSerializable("sharedEntity");
		screenWidth = getScreenWidth(context);
		sharedController = new SharedController();
		initUI();
		if (sharedEntity != null)
			showSuccess();

	}

	private void initUI() {
		fl_loading = (LoadingView) findViewById(R.id.fl_loading);
		ll_top = (LinearLayout) findViewById(R.id.ll_top);
		ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
		tvFriend = (TextView) findViewById(R.id.wxFriend);
		tvTimeline = (TextView) findViewById(R.id.wxTimeline);
		qrFriend = (TextView) findViewById(R.id.qrFriend);
		tvCopylink = (TextView) findViewById(R.id.copyLink);

		tvFriend.setOnClickListener(this);
		tvTimeline.setOnClickListener(this);
		qrFriend.setOnClickListener(this);
		tvCopylink.setOnClickListener(this);
		ll_bottom.setOnClickListener(this);
		ll_top.setOnClickListener(this);
		bindLoadingView();

		tvFriend.getViewTreeObserver().addOnPreDrawListener(
				new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						tvFriend.getViewTreeObserver().removeOnPreDrawListener(
								this);
						tvFriend.setTranslationX(-screenWidth / 2);
						tvFriend.setTranslationY(-tvFriend.getHeight() * 2);
						return false;
					}
				});

		tvTimeline.getViewTreeObserver().addOnPreDrawListener(
				new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						tvTimeline.getViewTreeObserver()
								.removeOnPreDrawListener(this);
						tvTimeline.setTranslationX(screenWidth / 2);
						tvTimeline.setTranslationY(-tvFriend.getHeight() * 2);
						return false;
					}
				});
		qrFriend.getViewTreeObserver().addOnPreDrawListener(
				new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						qrFriend.getViewTreeObserver().removeOnPreDrawListener(
								this);
						qrFriend.setTranslationX(-screenWidth / 2);
						qrFriend.setTranslationY(tvFriend.getHeight() * 2);
						return false;
					}
				});
		tvCopylink.getViewTreeObserver().addOnPreDrawListener(
				new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						tvCopylink.getViewTreeObserver()
								.removeOnPreDrawListener(this);
						tvCopylink.setTranslationX(screenWidth / 2);
						tvCopylink.setTranslationY(tvFriend.getHeight() * 2);
						return false;
					}
				});

		tvFriend.post(new Runnable() {
			@Override
			public void run() {
				moveInAnim(false);
			}
		});
	}

	protected void bindLoadingView() {
		fl_loading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !")
				.withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
				.withErrorIco(R.drawable.ic_chat_empty)
				.withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !")
				.withbtnErrorText("去找回她!!!")
				.withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走")
				.withNoNetIco(R.drawable.ic_chat_empty)
				.withbtnNoNetText("网弄好了，重试")
				.withLoadingIco(R.drawable.loading_animation)
				.withLoadingText("加载中...").withOnRetryListener(this).build();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wxFriend:
			moveOutAnim(true, false, false);
			showShare("Wechat", sharedEntity);// 微信好友
			break;
		case R.id.wxTimeline:
			moveOutAnim(true, false, false);
			showShare("WechatMoments", sharedEntity);// 微信朋友圈
			break;
		case R.id.qrFriend:
			moveOutAnim(true, false, false);
			showShare("QQ", sharedEntity);// QQ
			break;
		case R.id.copyLink:
			moveOutAnim(true, false, true);
			Intent intent = new Intent(this, ReportActivity.class);
			intent.putExtra("topicId", topicId);
			startActivity(intent);
			finish();
			break;
		case R.id.ll_top:
		case R.id.ll_bottom:
			moveOutAnim(true, false, true);
			break;
		default:
			break;
		}
	}

	private void moveInAnim(boolean isHideCode) {
		ObjectAnimator friendAnimatorX = ObjectAnimator.ofFloat(tvFriend,
				"TranslationX", 0);
		ObjectAnimator friendAnimatorY = ObjectAnimator.ofFloat(tvFriend,
				"TranslationY", 0);
		ObjectAnimator timelineAnimatorX = ObjectAnimator.ofFloat(tvTimeline,
				"TranslationX", 0);
		ObjectAnimator timelineAnimatorY = ObjectAnimator.ofFloat(tvTimeline,
				"TranslationY", 0);
		ObjectAnimator qrcodeAnimatorX = ObjectAnimator.ofFloat(qrFriend,
				"TranslationX", 0);
		ObjectAnimator qrcodeAnimatorY = ObjectAnimator.ofFloat(qrFriend,
				"TranslationY", 0);
		ObjectAnimator copyAnimatorX = ObjectAnimator.ofFloat(tvCopylink,
				"TranslationX", 0);
		ObjectAnimator copyAnimatorY = ObjectAnimator.ofFloat(tvCopylink,
				"TranslationY", 0);

		AnimatorSet set = new AnimatorSet();
		set.setDuration(ANIM_TIME);

		set.setInterpolator(new FastOutSlowInInterpolator());
		set.playTogether(friendAnimatorX, friendAnimatorY, timelineAnimatorX,
				timelineAnimatorY, qrcodeAnimatorX, qrcodeAnimatorY,
				copyAnimatorX, copyAnimatorY);

		set.start();
	}

	private void moveOutAnim(boolean isFinishActivity, boolean isShowCode,
			final boolean isRepost) {
		ObjectAnimator friendAnimatorX = ObjectAnimator.ofFloat(tvFriend,
				"TranslationX", -screenWidth / 2);
		ObjectAnimator friendAnimatorY = ObjectAnimator.ofFloat(tvFriend,
				"TranslationY", -tvFriend.getHeight() * 2);

		ObjectAnimator timelineAnimatorX = ObjectAnimator.ofFloat(tvTimeline,
				"TranslationX", screenWidth / 2);
		ObjectAnimator timelineAnimatorY = ObjectAnimator.ofFloat(tvTimeline,
				"TranslationY", -tvFriend.getHeight() * 2);
		ObjectAnimator qrcodeAnimatorX = ObjectAnimator.ofFloat(qrFriend,
				"TranslationX", -screenWidth / 2);
		ObjectAnimator qrcodeAnimatorY = ObjectAnimator.ofFloat(qrFriend,
				"TranslationY", tvFriend.getHeight() * 2);
		ObjectAnimator copyAnimatorX = ObjectAnimator.ofFloat(tvCopylink,
				"TranslationX", screenWidth / 2);
		ObjectAnimator copyAnimatorY = ObjectAnimator.ofFloat(tvCopylink,
				"TranslationY", tvFriend.getHeight() * 2);

		AnimatorSet set = new AnimatorSet();
		set.setDuration(ANIM_TIME);

		set.playTogether(friendAnimatorX, friendAnimatorY, timelineAnimatorX,
				timelineAnimatorY, qrcodeAnimatorX, qrcodeAnimatorY,
				copyAnimatorX, copyAnimatorY);

		if (isFinishActivity) {
			set.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					if (isRepost) {
						Intent intent = new Intent();
						intent.putExtra("isRepost", true);
						setResult(ReqCode.REQ_REPOST, intent);
					}
					finish();
					overridePendingTransition(0, 0);
				}
			});
		}
		set.start();
	}

	private void back() {
		// 关闭
		moveOutAnim(true, false, false);
	}

	/**
	 * @param platform
	 *            需要启动的平台名称
	 * @exception 分享功能
	 *                (需注册自己的AppKey-qq\sina\weixin)
	 * */
	private void showShare(String platform, SharedEntity sharedInfo) {
		// ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 隐藏编辑页面
		oks.setSilent(true);
		// 设置编辑页面的显示模式为Dialog模式
		oks.setDialogMode();
		// 关闭sso授权
		// oks.disableSSOWhenAuthorize();
		// 需要传入title--待定
		oks.setTitle(sharedInfo.title);
		oks.setSiteUrl(sharedInfo.siteUrl);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用--是否需要动态传入地址 ？？
		oks.setTitleUrl(sharedInfo.siteUrl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(sharedInfo.text);
		// 设置网络图片地址
		oks.setImageUrl(sharedInfo.imageUrl);
		// url仅在微信（包括好友和朋友圈）中使用,微信不绕过审核分享链接
		oks.setUrl(sharedInfo.siteUrl);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(sharedInfo.comment);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(sharedInfo.site);
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(sharedInfo.siteUrl);
		oks.setPlatform(platform);
		oks.setVenueName("Yulian");
		oks.setVenueDescription("Love Story");
		// 启动分享GUI
		oks.show(this);
	}

	@SuppressLint("NewApi")
	private int getScreenWidth(Context c) {
		if (screenWidth == 0) {
			WindowManager wm = (WindowManager) c
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			screenWidth = size.x;
		}
		return screenWidth;
	}

	/**
	 * 获得分享的信息
	 */
	private void getSharedInfo() {
		String userId = ConfigDao.getInstance().getString("userID");
		sharedController.getShareInfo(getString(R.string.FsSharedInfo), userId,
				topicId, true, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data != null) {
							RetEntity<SharedEntity> entity = (RetEntity<SharedEntity>) data;
							if (entity.success) {
								sharedEntity = entity.result;
								// 获得数据不为空
								if (sharedEntity != null) {
									showSuccess();
								} else {
									showEmpty();
								}
							} else {
								// fail
								showFaild();
							}
						} else {
							// 网络没取得数据
							showNoNet();
						}
					}
				});
	}

	@Override
	public void showSuccess() {
		fl_loading.setVisibility(View.GONE);
		ll_bottom.setVisibility(View.VISIBLE);
		ll_top.setVisibility(View.VISIBLE);
	}

	@Override
	public void showEmpty() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_EMPTY);
		ll_bottom.setVisibility(View.GONE);
		ll_top.setVisibility(View.GONE);
	}

	@Override
	public void showFaild() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_ERROR);
		ll_bottom.setVisibility(View.GONE);
		ll_top.setVisibility(View.GONE);

	}

	@Override
	public void showNoNet() {
		fl_loading.setVisibility(View.VISIBLE);
		fl_loading.setState(LoadingState.STATE_NO_NET);
		ll_bottom.setVisibility(View.GONE);
		ll_top.setVisibility(View.GONE);
	}

	@Override
	public void onRetry() {
		// 重新取得
		getSharedInfo();
	}

	@Override
	public boolean checkNet() {
		return NetUtils.isNetworkAvailable(MainApplication.getContext());
	}

	@Override
	public void onBackPressed() {
		back();
	}
}
