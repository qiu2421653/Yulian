package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.TagEntity;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:搜索(Tag)
 * 
 * @date 2016-4-5 上午10:47:10
 * 
 * @version 1.0 ==============================
 */
public class SearchTagAdapter extends BaseAdapter {

	protected static final String TAG = "SearchTagAdapter";

	private ArrayList<TagEntity> outDTO;
	private LayoutInflater mInflater;
	private Context mContext;

	public SearchTagAdapter(Context ctx, ArrayList<TagEntity> outDTO) {
		this.mContext = ctx;
		this.outDTO = outDTO;
		// mInflater = ((Activity) mContext).getLayoutInflater();
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return outDTO.size();
	}

	@Override
	public Object getItem(int position) {
		return outDTO.get(position);
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
			convertView = mInflater.inflate(R.layout.item_search_tag, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_name.setText(outDTO.get(position).tagDesc);

		return convertView;
	}

	class ViewHolder {
		TextView tv_name;

		public ViewHolder(View view) {
			tv_name = (TextView) view.findViewById(R.id.tv_name);

		}
	}

}
