package com.njkj.yulian.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.controller.LoveContraller;
import com.njkj.yulian.controller.TopicController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.LoveListEntity;
import com.njkj.yulian.entity.OtherLoveEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.entity.UserEntity;
import com.njkj.yulian.ui.activity.topic.TopicActivity;
import com.njkj.yulian.ui.adapter.OtherLoveAdapter;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.njkj.yulian.utils.PullToZoomListView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:其他人爱情故事
 * 
 * @date 2016-6-2 上午9:37:20
 * 
 * @version 1.0 ==============================
 */
public class OtherLoveActivity extends BaseActivity implements
		OnItemClickListener {

	private PullToZoomListView headergridView;
	private CircleImageView mylovenew_image;// header头像
	private Button mylovenew_gzh;// header关注
	private ImageView personalnew_sex;
	private TextView mylovenew_mylove, mylovenew_focuslove;// 我的爱情,关注的爱情
	private TextView forkNum, funsNum, personalnew_username;
	private OtherLoveAdapter adapter;
	private LoveContraller contraller;
	private TopicController topicController;
	private OtherLoveEntity otherEntity;
	private String userID;// 对方的ID
	private boolean isSelf = true;// true:对方;false:对方关注
	private boolean isFork = true;// true:已关注;false:没关注

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.otherlove_new);
		setHeaderLeftText();
		setHeaderTitle(getString(R.string.app_name));
		initViews();
		initData();
	}

	protected void initViews() {
		contraller = new LoveContraller();
		topicController = new TopicController();
		headergridView = (PullToZoomListView) findViewById(R.id.mylovenew_listview);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.mylove_header, null);
		personalnew_sex = (ImageView) view.findViewById(R.id.personalnew_sex);
		personalnew_username = (TextView) view
				.findViewById(R.id.personalnew_username);
		forkNum = (TextView) view.findViewById(R.id.mylovenew_myfocus);
		funsNum = (TextView) view.findViewById(R.id.mylovenew_focusmy);
		headergridView.getHeaderRView().addView(view);
		headergridView.getHeaderView().setImageResource(
				R.drawable.mylove_newtitle);

		headergridView.getHeaderView().setScaleType(
				ImageView.ScaleType.CENTER_CROP);
		initheaderView(view);
		
		
	}

	private void initheaderView(View view) {
		mylovenew_image = (CircleImageView) view
				.findViewById(R.id.mylovenew_image);
		mylovenew_gzh = (Button) view.findViewById(R.id.mylovenew_gzh);
		mylovenew_mylove = (TextView) view.findViewById(R.id.mylovenew_mylove);
		mylovenew_focuslove = (TextView) view
				.findViewById(R.id.mylovenew_focuslove);
		mylovenew_mylove.setText("Ta的爱情");
		mylovenew_focuslove.setText("Ta的关注");
		mylovenew_mylove.setOnClickListener(this);
		mylovenew_focuslove.setOnClickListener(this);
		mylovenew_gzh.setOnClickListener(this);
		headergridView.setOnItemClickListener(this);
	}

	protected void initData() {
		userID = getIntent().getStringExtra("userID");
		// 获取对方爱情
		getOtherLove(userID);
		//如果是自己,就不显示关注按钮 
		String mUserID = mConfigDao.getString("userID");
		if (mUserID.equals(userID)) {
			mylovenew_gzh.setVisibility(View.GONE);
			return;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left1:
			finish();
			break;
		case R.id.mylovenew_gzh:
			if (checkLogin()) {
				// 关注未关注
				if (isFork) {
					reqTopicCareful(userID, "0");
				} else {
					reqTopicCareful(userID, "1");
				}
			}
			break;
		case R.id.mylovenew_mylove:// 对方的爱情
			mylovenew_mylove.setTextColor(this.getResources().getColor(
					R.color.mylove_type));
			mylovenew_focuslove.setTextColor(this.getResources().getColor(
					R.color.white));
			getOtherLove(userID);// 获取对方的爱情
			break;
		case R.id.mylovenew_focuslove:// 对方关注的爱情
			mylovenew_focuslove.setTextColor(this.getResources().getColor(
					R.color.mylove_type));
			mylovenew_mylove.setTextColor(this.getResources().getColor(
					R.color.white));
			getForksLove(userID);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position == 0)
			return;
		Intent intent = null;
		intent = new Intent(MainApplication.getContext(), TopicActivity.class);
		// mTopicUrlList;
		intent.putExtra("topicId", otherEntity.outDTO.get(position - 1).topicId);
		intent.putExtra("userID", userID);
		startAnimActivity(intent);

	}

	// 获取对方的爱情经历
	private void getOtherLove(String userID) {
		//
		String createrID = mConfigDao.getString("userID");
		showDialog();
		contraller.getOtherLoveStory(getString(R.string.FsGetOtherLoveStory),
				userID, createrID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<OtherLoveEntity> entity = (RetEntity<OtherLoveEntity>) data;
							if (entity.success) {
								otherEntity = entity.result;
								setMessage();
								initUserInfo();
							}
						}
						hideProgress();
					}

				});
	}

	/**
	 * 
	 * @Title: reqTopicCareful
	 * @Description: 对用户设置关注
	 * @param @param expID
	 * @param @param isCareful
	 * @return void
	 */
	private void reqTopicCareful(String userID, String isCareful) {
		String mUserID = mConfigDao.getString("userID");
		if (mUserID.equals(userID)) {
			showShortToast("不能对自己设置关注");
			return;
		}
		showDialog();
		topicController.reqTopicCareful(getString(R.string.FsSetTopicCareful),
				userID, isCareful, mUserID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<UserEntity> entity = (RetEntity<UserEntity>) data;
							if (entity.success) {
								if (!isFork) {
									showShortToast("关注成功!");
									otherEntity.isFollow = "1";
									otherEntity.funsNum = String.valueOf(Integer
											.parseInt(otherEntity.funsNum) + 1);
								} else {
									showShortToast("取消成功!");
									otherEntity.isFollow = "0";
									otherEntity.funsNum = String.valueOf(Integer
											.parseInt(otherEntity.funsNum) - 1);
								}
								isFork = !isFork;
								initUserInfo();
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
	 * @Title:getForksLove
	 * @Description: 获取对方关注的爱情
	 * @param @param userID
	 * @return void
	 */
	private void getForksLove(String userID) {
		showDialog();
		contraller.getMyloveDetail(getString(R.string.FsGetForkLoveStory),
				userID, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<LoveListEntity> entity = (RetEntity<LoveListEntity>) data;
							if (entity.success) {
								if (otherEntity.outDTO != null) {
									otherEntity.outDTO.clear();
									otherEntity.outDTO
											.addAll(entity.result.outDTO);
								} else {
									otherEntity.outDTO = entity.result.outDTO;
								}
								setMessage();
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
	 * @Title: setMessage
	 * @Description: 处理用户个人信息
	 * @return void
	 */
	private void initUserInfo() {

		personalnew_username.setText(otherEntity.userInfo.name);
		Picasso.with(mContext).load(otherEntity.userInfo.hPic)
				.placeholder(R.drawable.empty_photo)
				.config(Bitmap.Config.RGB_565).error(R.drawable.empty_photo)
				.into(mylovenew_image);
		forkNum.setText(otherEntity.forkNum);
		funsNum.setText(otherEntity.funsNum);
		// 性别
		if (!TextUtils.isEmpty(otherEntity.userInfo.sex)) {
			if (otherEntity.userInfo.sex.equals("1"))
				// 男
				personalnew_sex.setBackgroundResource(R.drawable.mylove_sex);
			else if (otherEntity.userInfo.sex.equals("2"))
				// 女
				personalnew_sex.setBackgroundResource(R.drawable.mylove_femail);
			else
				// 未知
				personalnew_sex.setBackgroundResource(R.drawable.unknow);
		}
		// 关注
		if (otherEntity.isFollow.equals("0")) {
			isFork = false;
			mylovenew_gzh.setBackgroundResource(R.drawable.myylove_gzh);
		} else {
			isFork = true;
			mylovenew_gzh.setBackgroundResource(R.drawable.mylove_ygzh);
		}

	}

	@SuppressWarnings("unused")
	private void setMessage() {
		adapter = new OtherLoveAdapter(otherEntity.outDTO);
		headergridView.setAdapter(adapter);
	}

}
