package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.WomenEntity;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description:女士
 * 
 * @date 下午9:56:05
 * 
 * @version V1.0 ==============================
 * 
 */
public class WomenAdapter extends BaseAdapter {

	protected static final String TAG = "TrendsAdapter";

	private ArrayList<WomenEntity> mUserList;
	private LayoutInflater mInflater;
	private Context mContext;

	public WomenAdapter(ArrayList<WomenEntity> mUserList) {
		this.mContext = MainApplication.getContext();
		this.mUserList = mUserList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mUserList.size();
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
		ViewHolder viewHolder = null;
		// 对应位置类型
		if (convertView == null) {
			// 加载view
			convertView = mInflater.inflate(R.layout.item_women, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (!TextUtils.isEmpty(mUserList.get(position).url)) {
			Picasso.with(mContext).load(mUserList.get(position).url)
					.config(Bitmap.Config.RGB_565).fit()
					.into(viewHolder.iv_item);
		}
		viewHolder.tv_item.setText(mUserList.get(position).name);

		return convertView;
	}

	class ViewHolder {
		ImageView iv_item;
		TextView tv_item;

		public ViewHolder(View view) {
			iv_item = (ImageView) view.findViewById(R.id.iv_item);
			tv_item = (TextView) view.findViewById(R.id.tv_item);
		}
	}

}
