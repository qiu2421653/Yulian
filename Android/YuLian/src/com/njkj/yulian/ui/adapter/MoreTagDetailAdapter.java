package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.activity.AddConcernActivity;
import com.njkj.yulian.ui.activity.search.SearchItemActivity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:更多标签 (详细的)
 * 
 * @date 2016-5-7 下午12:04:24
 * 
 * @version 1.0 ==============================
 */
public class MoreTagDetailAdapter extends BaseAdapter {

	protected static final String TAG = "TopicDetailAdapter";

	private LayoutInflater mInflater;
	private Context mContext;
	ArrayList<TopicDetailEntity> tagEntities;

	public MoreTagDetailAdapter(Context ctx,
			ArrayList<TopicDetailEntity> tagEntities) {
		this.mContext = ctx;
		this.tagEntities = tagEntities;
		mInflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		return tagEntities.size();
	}

	@Override
	public Object getItem(int position) {
		return tagEntities.get(position);
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
			convertView = mInflater.inflate(R.layout.activity_moretag_item,
					null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_tag.setText(tagEntities.get(position).tag);
		MoreItemAdapter adapter = new MoreItemAdapter(mContext,
				tagEntities.get(position));
		viewHolder.gridview.setAdapter(adapter);
		//viewHolder.gridview.setOnItemClickListener(new OnGridViewListener());

		return convertView;
	}

	// 跳转到关注页面
	class OnGridViewListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
//			Intent intent = new Intent(mContext, SearchItemActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			mContext.startActivity(intent);
		}
	}

	class ViewHolder {
		GridView gridview;
		TextView tv_tag;

		public ViewHolder(View view) {
			gridview = (GridView) view.findViewById(R.id.gridview);
			tv_tag = (TextView) view.findViewById(R.id.tv_tag);
			gridview.setClickable(false);
			gridview.setPressed(false);
			gridview.setEnabled(false);
		}
	}
}
