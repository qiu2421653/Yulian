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
import com.njkj.yulian.entity.ReWardEntity;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:评论人列表
 * 
 * @date 2016-3-28 下午6:32:24
 * 
 * @version 1.0 ==============================
 */
public class RewardListAdapter extends BaseAdapter {

	protected static final String TAG = "CommentListAdapter";

	private ArrayList<ReWardEntity> mUserList;
	private LayoutInflater mInflater;
	private Context mContext;

	public RewardListAdapter(Context ctx, ArrayList<ReWardEntity> mUserList) {
		this.mUserList = mUserList;
		mContext = MainApplication.getContext();
		mInflater = LayoutInflater.from(MainApplication.getContext());
	}

	@Override
	public int getCount() {
		return (mUserList == null ? 0 : mUserList.size());
	}

	@Override
	public Object getItem(int position) {
		return mUserList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			// 找到布局
			convertView = mInflater
					.inflate(R.layout.activity_reward_list, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (!TextUtils.isEmpty(mUserList.get(position).hPic)) {
			Picasso.with(mContext).load(mUserList.get(position).hPic)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.error(R.drawable.empty_photo).into(viewHolder.iv_icon);
		}

		if (!TextUtils.isEmpty(mUserList.get(position).topicThumb)) {
			Picasso.with(mContext).load(mUserList.get(position).topicThumb)
					.config(Bitmap.Config.RGB_565).centerCrop()
					.resize(200, 200).into(viewHolder.iv_comment);
		}
		viewHolder.tv_nickName.setText(mUserList.get(position).nickName);

		viewHolder.tv_createTime.setText(mUserList.get(position).timeLag);
		viewHolder.tv_content.setText(mUserList.get(position).reward + "禹币");
		return convertView;
	}

	class ViewHolder {
		CircleImageView iv_icon;
		TextView tv_nickName;
		TextView tv_createTime;
		TextView tv_content;
		ImageView iv_comment;

		public ViewHolder(View view) {
			iv_icon = (CircleImageView) view.findViewById(R.id.iv_icon);
			tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
			tv_createTime = (TextView) view.findViewById(R.id.tv_createTime);
			tv_content = (TextView) view.findViewById(R.id.tv_content);
			iv_comment = (ImageView) view.findViewById(R.id.iv_comment);
		}
	}

}
