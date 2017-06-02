package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.ReportEntity;
import com.njkj.yulian.entity.TopicDetailEntity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:举报
 * 
 * @date 2016-5-12 下午4:13:04
 * 
 * @version 1.0 ==============================
 */
public class ReportAdapter extends BaseAdapter {

	protected static final String TAG = "ReportAdapter";

	private ArrayList<ReportEntity> reportlList;
	private LayoutInflater mInflater;
	private Context mContext;

	public ReportAdapter(Context ctx, ArrayList<ReportEntity> reportlList) {
		this.mContext = ctx;
		this.reportlList = reportlList;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return reportlList.size();
	}

	@Override
	public Object getItem(int position) {
		return reportlList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_report_tag, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_name.setText(reportlList.get(position).typeName);

		return convertView;
	}

	class ViewHolder {
		TextView tv_name;

		public ViewHolder(View view) {
			tv_name = (TextView) view.findViewById(R.id.tv_name);

		}
	}

}
