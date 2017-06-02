package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.MyFocusEntity;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.squareup.picasso.Picasso;

public class MyFocusAdapter extends BaseAdapter {

	private ArrayList<MyFocusEntity> mUserList;
	private LayoutInflater mInflater;
	private Context mContext;
	Activity activity;

	public MyFocusAdapter(Context ctx, Activity activity,
			ArrayList<MyFocusEntity> mUserList) {
		this.mContext = ctx;
		this.mUserList = mUserList;
		mInflater = LayoutInflater.from(mContext);
		this.activity = activity;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			// 找到布局
			convertView = mInflater.inflate(R.layout.activitymyfocus, null);
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

		viewHolder.tv_nickName.setText(mUserList.get(position).nickName);

		viewHolder.rl_focus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDeleteCallBack.onDelete(position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		CircleImageView iv_icon;
		TextView tv_nickName;
		RelativeLayout rl_focus;

		public ViewHolder(View view) {
			iv_icon = (CircleImageView) view.findViewById(R.id.iv_icon);
			tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
			rl_focus = (RelativeLayout) view.findViewById(R.id.rl_focus);
		}
	}

	public interface onDeleteCallBack {
		void onDelete(int position);
	}

	private onDeleteCallBack onDeleteCallBack;

	public void setOnDeleteCallBack(onDeleteCallBack onDeleteCallBack) {
		this.onDeleteCallBack = onDeleteCallBack;
	}

}
