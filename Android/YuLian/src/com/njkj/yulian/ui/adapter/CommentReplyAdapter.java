package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.CommentEntity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:评论|子视图
 * 
 * @date 2016-5-27 上午11:01:38
 * 
 * @version 1.0 ==============================
 */
public class CommentReplyAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<CommentEntity> replyList;
	private ViewChildHolder viewchildHolder;
	private OnClickListener replyToReplyListener;
	private int parentPosition = -1;

	public CommentReplyAdapter(int parentPosition,
			ArrayList<CommentEntity> replyList,
			OnClickListener replyToReplyListener) {
		this.inflater = LayoutInflater.from(MainApplication.getContext());
		this.replyList = replyList;
		this.parentPosition = parentPosition;
		this.replyToReplyListener = replyToReplyListener;
	}

	@Override
	public int getCount() {
		return replyList.size();
	}

	@Override
	public CommentEntity getItem(int position) {
		return replyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_children, null);
			viewchildHolder = new ViewChildHolder(convertView);
			convertView.setTag(viewchildHolder);
		} else {
			viewchildHolder = (ViewChildHolder) convertView.getTag();
		}
		// 名
		viewchildHolder.tv_nickName.setText(replyList.get(position).fromName);
		// 名
		viewchildHolder.tv_toName.setText(replyList.get(position).toName);
		// 设置时间间隔
		viewchildHolder.tv_time.setText(replyList.get(position).timeLag);
		viewchildHolder.tv_createDesc.setText(replyList.get(position).comment);

		viewchildHolder.rl_group.setTag(R.id.tag_first, parentPosition);
		viewchildHolder.rl_group.setTag(R.id.tag_second, position);
		viewchildHolder.rl_group.setOnClickListener(replyToReplyListener);
		return convertView;
	}

	class ViewChildHolder {
		TextView tv_nickName;
		TextView tv_toName;
		TextView tv_time;
		TextView tv_createDesc;
		RelativeLayout rl_group;

		public ViewChildHolder(View view) {
			rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
			tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
			tv_toName = (TextView) view.findViewById(R.id.tv_toName);
			tv_time = (TextView) view.findViewById(R.id.tv_time);
			tv_createDesc = (TextView) view.findViewById(R.id.tv_createDesc);
		}
	}

}
