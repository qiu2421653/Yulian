package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.CommentEntity;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:点赞人员列表
 * 
 * @date 2016-3-25 下午5:36:06
 * 
 * @version 1.0 ==============================
 */
public class FavorAdapter extends BaseAdapter {

	protected static final String TAG = "FavorAdapter";

	private ArrayList<CommentEntity> mUserList;
	private LayoutInflater mInflater;
	private Context mContext;
	private static final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_COUNT = 2;

	public FavorAdapter(ArrayList<CommentEntity> mUserList) {
		this.mContext = MainApplication.getContext();
		this.mUserList = mUserList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return (mUserList == null ? 1 : mUserList.size() + 1);
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	/** 该方法返回多少个不同的布局 */
	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	/** 根据position返回相应的Item */
	@Override
	public int getItemViewType(int position) {
		if (mUserList == null) {
			return 0;
		} else if (position < mUserList.size())
			return TYPE_TWO;
		else
			return TYPE_ONE;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		ViewDrawHolder viewDrawHolder = null;
		// 对应位置类型
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TYPE_ONE:
				// 加载view
				convertView = mInflater.inflate(R.layout.favort_number, null);
				viewDrawHolder = new ViewDrawHolder(convertView);
				convertView.setTag(viewDrawHolder);
				break;
			case TYPE_TWO:
				// 加载view
				convertView = mInflater.inflate(R.layout.activity_hpic, null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
				break;
			}
		} else {
			switch (type) {
			case TYPE_ONE:
				viewDrawHolder = (ViewDrawHolder) convertView.getTag();
				break;
			case TYPE_TWO:
				viewHolder = (ViewHolder) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case TYPE_ONE:
			viewDrawHolder.tv_favort_number.setText(String.valueOf(mUserList
					.size()));
			break;
		case TYPE_TWO:
			if (!TextUtils.isEmpty(mUserList.get(position).hPic)) {
				Picasso.with(mContext).load(mUserList.get(position).hPic)
						.placeholder(R.drawable.logo)
						.config(Bitmap.Config.RGB_565).error(R.drawable.logo)
						.into(viewHolder.iv_icon);
			}
			break;
		}

		return convertView;
	}

	class ViewHolder {
		CircleImageView iv_icon;

		public ViewHolder(View view) {
			iv_icon = (CircleImageView) view.findViewById(R.id.iv_icon);
		}
	}

	// 自己绘制的一个布局
	class ViewDrawHolder {
		TextView tv_favort_number;

		public ViewDrawHolder(View view) {
			tv_favort_number = (TextView) view
					.findViewById(R.id.tv_favort_number);
		}
	}
}
