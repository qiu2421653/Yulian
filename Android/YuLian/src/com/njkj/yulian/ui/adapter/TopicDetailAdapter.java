package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.TopicEntity;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.njkj.yulian.utils.CLog;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:主题详情Apapter
 * 
 * @date 2016-3-25 上午9:41:10
 * 
 * @version 1.0 ==============================
 */
public class TopicDetailAdapter extends BaseAdapter {

	protected static final String TAG = "TopicDetailAdapter";

	private ArrayList<TopicEntity> mTopicUrlList;
	private LayoutInflater mInflater;
	private Context mContext;

	public TopicDetailAdapter(Context ctx, ArrayList<TopicEntity> mTopicUrlList) {
		this.mContext = ctx;
		this.mTopicUrlList = mTopicUrlList;
		mInflater = LayoutInflater.from(ctx);
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
			convertView = mInflater.inflate(R.layout.activity_topic, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

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
