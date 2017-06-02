package com.njkj.yulian.ui.fragment;

import java.security.acl.Owner;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.ReqCode;
import com.njkj.yulian.controller.LoveContraller;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.LoveEntity;
import com.njkj.yulian.entity.LoveListEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.ui.activity.LoveThemeActivity;
import com.njkj.yulian.ui.adapter.GAdapter;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.njkj.yulian.utils.CLog;
import com.njkj.yulian.utils.DevUtils;
import com.njkj.yulian.utils.MyloveTheme;
import com.njkj.yulian.utils.PullToZoomListView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment
 * 
 * @Description:故事
 * 
 * @date 2016-4-6 上午9:44:17
 * 
 * @version 1.0 ==============================
 */
@SuppressLint("NewApi")
public class StoryFragment extends BaseFragment {

	private static final String TAG = "StoryFragment";

	private PullToZoomListView headergridView;
	private View view;// gridviewtitle部分
	private View v;// 主题view
	private CircleImageView mylovenew_image;// header头像
	private Button mylovenew_gzh;// header关注
	private LinearLayout mylovenew_theme;// 添加主题
	private LinearLayout mylovenew_fork;// 关注 占位
	private TextView mylovenew_mylove, mylovenew_focuslove;// 我的爱情,关注的爱情
	private TextView forkNum, funsNum, personalnew_username;
	private ImageView personalnew_sex;
	private GAdapter adapter;
	private int z = 1;
	private boolean type = true;
	private boolean isOwn = true;// 自己的爱情

	LoveContraller contraller;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mylove_new, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		contraller = new LoveContraller();
		headergridView = (PullToZoomListView) view
				.findViewById(R.id.mylovenew_listview);
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.mylove_header, null);
		personalnew_username = (TextView) view
				.findViewById(R.id.personalnew_username);
		forkNum = (TextView) view.findViewById(R.id.mylovenew_myfocus);
		funsNum = (TextView) view.findViewById(R.id.mylovenew_focusmy);
		personalnew_sex = (ImageView) view.findViewById(R.id.personalnew_sex);
		headergridView.getHeaderRView().addView(view);
		headergridView.getHeaderView().setImageResource(
				R.drawable.mylove_newtitle);

		headergridView.getHeaderView().setScaleType(
				ImageView.ScaleType.CENTER_CROP);
		v = LayoutInflater.from(getActivity()).inflate(
				R.layout.mylove_header_yj, null);
		headergridView.addHeaderView(v);

		initheaderView(view);
		headergridView.setOnScrollListener(new OnScrollListener() {

			// ListView的状态改变时触发
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:// 空闲状态

					break;
				case OnScrollListener.SCROLL_STATE_FLING:// 滚动状态
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 触摸后滚动
					List<HorizontalScrollView> scroll = adapter.getScrollView();
					for (int i = 0; i < scroll.size(); i++) {
						HorizontalScrollView scrollView = scroll.get(i);
						scrollView.smoothScrollTo(0, 20);
					}
					break;
				}
			}

			// 正在滚动
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});

	}

	private void initheaderView(View view) {
		mylovenew_image = (CircleImageView) view
				.findViewById(R.id.mylovenew_image);
		mylovenew_gzh = (Button) view.findViewById(R.id.mylovenew_gzh);
		mylovenew_theme = (LinearLayout) v.findViewById(R.id.mylovenew_theme);
		mylovenew_fork = (LinearLayout) v.findViewById(R.id.mylovenew_fork);
		mylovenew_mylove = (TextView) view.findViewById(R.id.mylovenew_mylove);
		mylovenew_focuslove = (TextView) view
				.findViewById(R.id.mylovenew_focuslove);
		mylovenew_image.setOnClickListener(this);
		mylovenew_mylove.setOnClickListener(this);
		mylovenew_focuslove.setOnClickListener(this);
		mylovenew_theme.setOnClickListener(this);
		mylovenew_gzh.setVisibility(View.GONE);
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initOnClick() {

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			if (isOwn)
				getMyLove(getString(R.string.FsGetMyLoveStory), isOwn);// 获取我的爱情
			else
				getMyLove(getString(R.string.FsGetForkLoveStory), isOwn);// 获取我的爱情
		}
	}

	@Override
	public void onMyClick(View v) {
		switch (v.getId()) {
		case R.id.mylovenew_image:// 头像
			break;
		case R.id.mylovenew_gzh:// 关注未关注
			if (type == true) {
				mylovenew_gzh.setBackgroundResource(R.drawable.mylove_ygzh);
				type = false;
			} else {
				mylovenew_gzh.setBackgroundResource(R.drawable.myylove_gzh);
				type = true;
			}
			break;
		case R.id.mylovenew_theme:// 添加主题
			Intent intent = new Intent(getActivity(), LoveThemeActivity.class);
			startActivityForResult(intent, ReqCode.REQ_ADDTHEME);
			break;
		case R.id.mylovenew_mylove:// 我的爱情
			isOwn = true;
			mylovenew_theme.setVisibility(View.VISIBLE);
			mylovenew_fork.setVisibility(View.GONE);
			mylovenew_mylove.setTextColor(this.getResources().getColor(
					R.color.mylove_type));
			mylovenew_focuslove.setTextColor(this.getResources().getColor(
					R.color.white));
			getMyLove(getString(R.string.FsGetMyLoveStory), isOwn);// 获取我的爱情
			break;
		case R.id.mylovenew_focuslove:// 关注爱情
			isOwn = false;
			mylovenew_theme.setVisibility(View.GONE);
			mylovenew_fork.setVisibility(View.VISIBLE);
			mylovenew_focuslove.setTextColor(this.getResources().getColor(
					R.color.mylove_type));
			mylovenew_mylove.setTextColor(this.getResources().getColor(
					R.color.white));
			getMyLove(getString(R.string.FsGetForkLoveStory), isOwn);// guan
			break;
		}
	}

	// 获取我的爱情(关注的爱情根据url不同获取不同的信息)
	private void getMyLove(String url, final boolean type) {
		String userID = mConfigDao.getString("userID");
		showDialog();
		contraller.getMyloveDetail(url, userID, new SimpleCallback() {
			@Override
			public void onCallback(Object data) {
				if (data == null) {
					showShortToast(getString(R.string.error));
				} else {
					RetEntity<LoveListEntity> entity = (RetEntity<LoveListEntity>) data;
					if (entity.success) {
						List<LoveEntity> result = (List<LoveEntity>) entity.result.outDTO;
						if (type == true) {
							adapter = new GAdapter(getActivity(), result,
									new MyloveTheme() {
										// 判断是否是删除
										@Override
										public void isDelete(boolean type,
												String id) {
											if (type == true) {
												deleteMyLove(id);
											}
										}

										// 判断是否是当前
										@Override
										public void isCurrent(boolean type,
												String id) {
											if (type == true)
												currentMyLove(id);
										}

										// 判断是否删除
										@Override
										public void isType(boolean type,
												String id, String islock) {
											typeMyLove(id, islock);

										}
									}, true);
							forkNum.setText(entity.result.forkNum);
							funsNum.setText(entity.result.funsNum);
							if (entity.result.userInfo != null) {
								if (!TextUtils
										.isEmpty(entity.result.userInfo.sex)) {
									if (entity.result.userInfo.sex.equals("2"))
										personalnew_sex
												.setBackgroundResource(R.drawable.mylove_femail);
									else if (entity.result.userInfo.sex
											.equals("1"))
										personalnew_sex
												.setBackgroundResource(R.drawable.mylove_sex);
									else
										personalnew_sex
												.setBackgroundResource(R.drawable.unknow);
								}
								personalnew_username
										.setText(entity.result.userInfo.name);
								if (!TextUtils
										.isEmpty(entity.result.userInfo.hPic))
									Picasso.with(mContext)
											.load(entity.result.userInfo.hPic)
											.placeholder(R.drawable.logo)
											.config(Bitmap.Config.RGB_565)
											.resize(DevUtils.dip2px(mContext,
													120),
													DevUtils.dip2px(mContext,
															120)).centerCrop()
											.error(R.drawable.logo)
											.into(mylovenew_image);
							}
						} else {
							adapter = new GAdapter(getActivity(), result,
									new MyloveTheme() {
										@Override
										public void isDelete(boolean type,
												String id) {

										}

										@Override
										public void isCurrent(boolean type,
												String id) {

										}

										@Override
										public void isType(boolean type,
												String id, String islock) {

										}
									}, false);
						}
						headergridView.setAdapter(adapter);
					} else {
						showShortToast(entity.exceptions.get(0).message);
					}
				}
				hideProgress();
			}
		});
	}

	// 删除
	public void deleteMyLove(String id) {
		showDialog();
		contraller.getMyloveDelete(getString(R.string.FsGetStoryDelete), id,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<LoveListEntity> entity = (RetEntity<LoveListEntity>) data;
							if (entity.success) {
								showShortToast("删除成功");
								getMyLove(getString(R.string.FsGetMyLoveStory),
										true);// 获取我的爱情
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});

	}

	// 当前
	public void currentMyLove(String id) {
		showDialog();
		contraller.getMyloveDelete(getString(R.string.FsGetStoryCurrent), id,
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<LoveListEntity> entity = (RetEntity<LoveListEntity>) data;
							if (entity.success) {
								if (entity.result.isSuccess.equals("1")) {
									showShortToast("已经设置成当前");
									getMyLove(
											getString(R.string.FsGetMyLoveStory),
											true);// 获取我的爱情
								} else {
									showShortToast("已经设置成当前");
								}

							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	// 隐藏与显示
	public void typeMyLove(String id, final String islock) {
		showDialog();
		contraller.getMyloveType(getString(R.string.FsGetStoryType), id,
				islock, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<LoveListEntity> entity = (RetEntity<LoveListEntity>) data;
							if (entity.success) {
								if ("30".equals(islock))
									showShortToast("已经隐藏");
								else
									showShortToast("已经显示");
								getMyLove(getString(R.string.FsGetMyLoveStory),
										true);// 获取我的爱情
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_CANCELED)
			return;
		if (resultCode == resultCode) {
			getMyLove(getString(R.string.FsGetMyLoveStory), isOwn);// 获取我的爱情
		}
	}

}
