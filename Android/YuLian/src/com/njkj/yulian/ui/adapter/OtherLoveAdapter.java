package com.njkj.yulian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.LoveEntity;
import com.njkj.yulian.ui.gui.RectRoundImageView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:其他人爱情
 * 
 * @date 2016-6-2 上午9:55:19
 * 
 * @version 1.0 ==============================
 */
public class OtherLoveAdapter extends BaseAdapter {

	private static final String TAG = "OtherLoveAdapter";

	private Context context;
	private List<LoveEntity> list;
	private LayoutInflater inflater;

	public OtherLoveAdapter(List<LoveEntity> list) {
		this.context = MainApplication.getContext();
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup arg2) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.otherlove_item, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Picasso.with(context).load(list.get(position).thumb)
				.placeholder(R.drawable.empty_photo)
				.config(Bitmap.Config.RGB_565).error(R.drawable.empty_photo)
				.into(viewHolder.title_image);

		viewHolder.theme_title.setText(list.get(position).loveDesc);
		viewHolder.theme_time.setText(list.get(position).createTime);

		return convertView;
	}

	static class ViewHolder {
		RectRoundImageView title_image;// 图片
		TextView theme_title;// 标题
		TextView theme_time;// 时间

		public ViewHolder(View convertView) {
			title_image = (RectRoundImageView) convertView
					.findViewById(R.id.title_image);
			theme_title = (TextView) convertView.findViewById(R.id.theme_title);
			theme_time = (TextView) convertView.findViewById(R.id.theme_time);
		}
	}

}
