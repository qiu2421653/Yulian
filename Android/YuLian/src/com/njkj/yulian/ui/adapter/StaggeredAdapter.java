package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.DuitangInfo;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description:瀑布流(SearchItemActivity)
 * 
 * @date 下午9:48:04
 * 
 * @version V1.0 ==============================
 * 
 */
public class StaggeredAdapter extends BaseAdapter {
	private ArrayList<DuitangInfo> mInfos;
	LayoutInflater layoutInflator;
	Context mContext;

	public StaggeredAdapter(Context context, ArrayList<DuitangInfo> mInfo) {
		this.mContext = MainApplication.getContext();
		layoutInflator = LayoutInflater.from(mContext);
		this.mInfos = mInfo;
	}

	@Override
	public int getCount() {
		return (mInfos == null ? 0 : mInfos.size());
	}

	@Override
	public Object getItem(int position) {
		return mInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflator.inflate(R.layout.infos_list, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder = (ViewHolder) convertView.getTag();

		holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, (int) mInfos
						.get(position).height));
		holder.msg.setText(mInfos.get(position).nickName);

		holder.tv_comment.setText(String.valueOf(mInfos.get(position).comment));
		holder.tv_fork.setText(String.valueOf(mInfos.get(position).fork));
		holder.tv_time.setText(mInfos.get(position).createTime);

		Picasso.with(mContext).load(mInfos.get(position).url)
				.placeholder(R.drawable.empty_photo)
				.config(Bitmap.Config.RGB_565).error(R.drawable.empty_photo)
				.into(holder.imageView);
		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView msg;
		TextView tv_fork;
		TextView tv_comment;
		TextView tv_time;

		public ViewHolder(View view) {
			imageView = (ImageView) view.findViewById(R.id.news_pic);
			msg = (TextView) view.findViewById(R.id.news_title);
			tv_fork = (TextView) view.findViewById(R.id.tv_fork);
			tv_time = (TextView) view.findViewById(R.id.tv_time);
			tv_comment = (TextView) view.findViewById(R.id.tv_comment);
		}
	}
}
