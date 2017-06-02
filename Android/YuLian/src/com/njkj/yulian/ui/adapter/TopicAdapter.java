package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.activity.search.SearchItemActivity;
import com.njkj.yulian.utils.SmileUtils;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:主题Apapter
 * 
 * @date 2016-3-25 上午9:41:10
 * 
 * @version 1.0 ==============================
 */
public class TopicAdapter extends BaseAdapter {

	protected static final String TAG = "TopicDetailAdapter";

	private ArrayList<TopicDetailEntity> mTopiclList;
	private LayoutInflater mInflater;
	private Context mContext;

	public TopicAdapter(ArrayList<TopicDetailEntity> mTopicUrlList) {
		this.mContext = MainApplication.getContext();
		this.mTopiclList = mTopicUrlList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mTopiclList.size();
	}

	@Override
	public Object getItem(int position) {
		return mTopiclList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// 当前Item类型
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.activity_topic2_item, null);
			viewHolder = new ViewHolder(convertView);
			// 找到布局
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		TopicDetailEntity topicDetailEntity = mTopiclList.get(position);
		// 设置一般信息
		viewHolder.tv_topic_title.setText(mTopiclList.get(position).nickName);
		viewHolder.tv_topic_comment.setText(String.valueOf(mTopiclList
				.get(position).isComment));
		viewHolder.tv_topic_fork.setText(String.valueOf(mTopiclList
				.get(position).isFork));
		if (!TextUtils.isEmpty(mTopiclList.get(position).tag)) {
			viewHolder.tv_topic_tag.setVisibility(View.VISIBLE);
			viewHolder.tv_topic_tag.setText(mTopiclList.get(position).tag);
			viewHolder.tv_topic_tag.setTag(position);
		} else {
			viewHolder.tv_topic_tag.setVisibility(View.GONE);
		}

		if (mTopiclList.get(position).url != null) {
			Picasso.with(mContext).load(mTopiclList.get(position).url).resize(210, 210)
					.centerCrop().config(Bitmap.Config.RGB_565)
					.into(viewHolder.iv_thumb);
		}

		if (!TextUtils.isEmpty(mTopiclList.get(position).content)) {
			// 设置内容
			Spannable span = SmileUtils.getSmiledText(mContext,
					mTopiclList.get(position).content);
			viewHolder.tv_topic_desc.setText(span, BufferType.SPANNABLE);
		} else {
			viewHolder.tv_topic_desc.setText("");
		}
		viewHolder.tv_createTime.setText(mTopiclList.get(position).createTime);

		viewHolder.tv_topic_tag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				int tag = (Integer) view.getTag();
				Intent intent = new Intent(mContext, SearchItemActivity.class);
				intent.putExtra("topicDetailEntity", mTopiclList.get(tag));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		/** 图片 */
		public ImageView iv_thumb;
		/** 用户名 */
		public TextView tv_topic_title;
		/** Tag */
		public TextView tv_topic_tag;
		/** 描述 */
		public TextView tv_topic_desc;
		/** 创建时间 */
		public TextView tv_createTime;
		/** 评论数 */
		public TextView tv_topic_comment;
		/** 点赞 */
		public TextView tv_topic_fork;

		public ViewHolder(View view) {
			tv_topic_title = (TextView) view.findViewById(R.id.tv_topic_title);
			tv_topic_tag = (TextView) view.findViewById(R.id.tv_topic_tag);
			tv_topic_desc = (TextView) view.findViewById(R.id.tv_topic_desc);
			tv_createTime = (TextView) view.findViewById(R.id.tv_createTime);
			tv_topic_comment = (TextView) view
					.findViewById(R.id.tv_topic_comment);
			tv_topic_fork = (TextView) view.findViewById(R.id.tv_topic_fork);
			iv_thumb = (ImageView) view.findViewById(R.id.iv_thumb);
		}

	}
}
