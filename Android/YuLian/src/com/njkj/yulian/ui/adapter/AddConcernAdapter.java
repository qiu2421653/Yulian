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

import com.njkj.yulian.R;
import com.njkj.yulian.entity.CommentEntity;
import com.njkj.yulian.ui.adapter.HomeAdapter.OnForkCallBack;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:添加关注
 * 
 * @date 2016-3-28 下午3:08:55
 * 
 * @version 1.0 ==============================
 */
public class AddConcernAdapter extends BaseAdapter {

	protected static final String TAG = "FavorAdapter";

	private ArrayList<CommentEntity> mUserList;
	private LayoutInflater mInflater;
	private Context mContext;

	public AddConcernAdapter(Context ctx, ArrayList<CommentEntity> mUserList) {
		this.mContext = ctx;
		this.mUserList = mUserList;
		mInflater = LayoutInflater.from(mContext);
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
			convertView = mInflater.inflate(R.layout.activity_concern_item,
					null);
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
		viewHolder.identify_.setText(mUserList.get(position).timeLag);
		if (mUserList.get(position).isFollowed.equals("1"))
			viewHolder.iv_fork.setVisibility(View.VISIBLE);
		else
			viewHolder.iv_fork.setVisibility(View.GONE);

		viewHolder.iv_fork.setOnClickListener(new OnMyclick(position));
		return convertView;
	}

	class ViewHolder {
		CircleImageView iv_icon;
		TextView tv_nickName;
		TextView identify_;
		ImageView iv_fork;

		public ViewHolder(View view) {
			iv_icon = (CircleImageView) view.findViewById(R.id.iv_icon);
			tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
			identify_ = (TextView) view.findViewById(R.id.identify_);
			iv_fork = (ImageView) view.findViewById(R.id.iv_fork);
		}
	}

	class OnMyclick implements OnClickListener {
		int position;

		public OnMyclick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			v.setVisibility(View.GONE);
			addForkCallBack.onFork(position);
		}
	}

	public interface AddForkCallBack {
		void onFork(int position);
	}

	public AddForkCallBack addForkCallBack;

	public void setOnForkCallBack(AddForkCallBack addForkCallBack) {
		this.addForkCallBack = addForkCallBack;
	}
}
