package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.entity.TopicEntity;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:首页
 * 
 * @date 2016-3-24 下午2:47:20
 * 
 * @version 1.0 ==============================
 */
public class HomeAdapter extends BaseAdapter {
	protected static final String TAG = "HomeAdapter";
	private ArrayList<TopicEntity> mTopicUrlList;
	private LayoutInflater mInflater;
	private Context context;
	private String mUserId = "";
	private ViewHolder viewHolder;

	public interface OnForkCallBack {
		void onFork(int position, View view);

		void onUser(int position);
	}

	public OnForkCallBack onForkCallBack;

	public void setOnForkCallBack(OnForkCallBack onForkCallBack) {
		this.onForkCallBack = onForkCallBack;
	}

	public HomeAdapter(ArrayList<TopicEntity> mTopicUrlList) {
		this.context = MainApplication.getContext();
		this.mTopicUrlList = mTopicUrlList;
		this.mInflater = LayoutInflater.from(MainApplication.getContext());
		mUserId = ConfigDao.getInstance().getString("userID");
	}

	@Override
	public int getCount() {
		return mTopicUrlList.size();
	}

	// 9867
	@Override
	public Object getItem(int position) {
		return mTopicUrlList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// 对应位置类型
		if (convertView == null) {
			// 找到布局
			convertView = mInflater.inflate(R.layout.activity_topic, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (!TextUtils.isEmpty(mTopicUrlList.get(position).hPic)) {
			// 第一种普通的情况
			Picasso.with(context).load(mTopicUrlList.get(position).hPic)
					.placeholder(R.drawable.logo).config(Bitmap.Config.RGB_565)
					.fit().error(R.drawable.logo).into(viewHolder.iv_icon);
		} else {
			viewHolder.iv_icon.setImageResource(R.drawable.logo);
		}

		if (!TextUtils.isEmpty(mTopicUrlList.get(position).themePic)) {
			Picasso.with(context).load(mTopicUrlList.get(position).themePic)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565).fit()
					.error(R.drawable.empty_photo).into(viewHolder.iv_themePic);
		}
		viewHolder.tv_nickName.setText(mTopicUrlList.get(position).nickName);
		viewHolder.tv_createDescription
				.setText(mTopicUrlList.get(position).createDescription);
		viewHolder.tv_createTime
				.setText(mTopicUrlList.get(position).createTime);

		if (mTopicUrlList.get(position).isFork.equals("1")
				|| mTopicUrlList.get(position).userID.equals(mUserId))
			// 已经关注了
			viewHolder.iv_fork.setVisibility(View.GONE);
		else
			viewHolder.iv_fork.setVisibility(View.VISIBLE);
		// 关注点击事件
		viewHolder.iv_fork.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onForkCallBack.onFork(position, view);
			}
		});
		// 用户头像点击事件
		viewHolder.iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onForkCallBack.onUser(position);
			}
		});

		return convertView;
	}

	class ViewHolder {
		CircleImageView iv_icon;
		TextView tv_nickName;
		TextView tv_createTime;
		TextView tv_createDescription;
		ImageView iv_fork;
		ImageView iv_themePic;
		ImageView iv_State;

		public ViewHolder(View view) {
			iv_icon = (CircleImageView) view.findViewById(R.id.iv_icon);
			tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
			tv_createTime = (TextView) view.findViewById(R.id.tv_createTime);
			tv_createDescription = (TextView) view
					.findViewById(R.id.tv_createDescription);
			iv_fork = (ImageView) view.findViewById(R.id.iv_fork);
			iv_themePic = (ImageView) view.findViewById(R.id.iv_themePic);
			iv_State = (ImageView) view.findViewById(R.id.iv_State);
		}
	}

}
