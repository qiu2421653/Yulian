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

import com.njkj.yulian.R;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:搜索 (内容)
 * 
 * @date 2016-4-5 上午11:24:59
 * 
 * @version 1.0 ==============================
 */
public class SearchContentAdapter extends BaseAdapter {

	protected static final String TAG = "SearchContentAdapter";

	private ArrayList<TopicDetailEntity> mTopicUrlList;
	private LayoutInflater mInflater;
	private Context mContext;

	public SearchContentAdapter(Context ctx,
			ArrayList<TopicDetailEntity> mTopicUrlList) {
		this.mContext = ctx;
		this.mTopicUrlList = mTopicUrlList;
		// mInflater = ((Activity) mContext).getLayoutInflater();
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mTopicUrlList.size();
	}

	@Override
	public Object getItem(int position) {
		return mTopicUrlList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_search_content, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_title.setText(mTopicUrlList.get(position).nickName);
		viewHolder.tv_des.setText(mTopicUrlList.get(position).content);

		if (!TextUtils.isEmpty(mTopicUrlList.get(position).hPic)) {
			Picasso.with(mContext).load(mTopicUrlList.get(position).hPic)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.error(R.drawable.empty_photo).into(viewHolder.smallUrl);
		}
		if (position == mTopicUrlList.size() - 1)
			viewHolder.gray_line.setVisibility(View.GONE);
		else
			viewHolder.gray_line.setVisibility(View.VISIBLE);

		return convertView;
	}

	class ViewHolder {
		TextView tv_title;
		TextView tv_des;
		ImageView smallUrl;
		ImageView gray_line;

		public ViewHolder(View view) {
			tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_des = (TextView) view.findViewById(R.id.tv_des);
			smallUrl = (ImageView) view.findViewById(R.id.smallUrl);
			gray_line = (ImageView) view.findViewById(R.id.gray_line);

		}
	}

}
