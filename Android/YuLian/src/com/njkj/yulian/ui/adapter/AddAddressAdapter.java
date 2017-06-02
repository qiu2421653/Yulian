package com.njkj.yulian.ui.adapter;

import java.util.List;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.AddressEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddAddressAdapter extends BaseAdapter {
	private List<AddressEntity> list;
	private Context context;

	public AddAddressAdapter(Context context, List<AddressEntity> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.address_item, null);
			viewHolder.address_bg = (LinearLayout) convertView
					.findViewById(R.id.address_bg);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.address_name);
			viewHolder.phone = (TextView) convertView
					.findViewById(R.id.address_phone);
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.address);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();
		if (position == 0) {
			viewHolder.address_bg.setBackgroundColor(context.getResources()
					.getColor(R.color.address_bg));
			viewHolder.name.setText(list.get(position).getName());
			viewHolder.name.setTextColor(context.getResources().getColor(
					R.color.white));
			viewHolder.phone.setText(list.get(position).getPhone());
			viewHolder.phone.setTextColor(context.getResources().getColor(
					R.color.white));
			viewHolder.address.setText(list.get(position).getAddress());
			viewHolder.address.setTextColor(context.getResources().getColor(
					R.color.white));
		} else {
			viewHolder.address_bg.setBackgroundColor(context.getResources()
					.getColor(R.color.white));
			viewHolder.name.setText(list.get(position).getName());
			viewHolder.phone.setText(list.get(position).getPhone());
			viewHolder.address.setText(list.get(position).getAddress());
		}
		return convertView;
	}

	class ViewHolder {
		LinearLayout address_bg;
		TextView name;
		TextView phone;
		TextView address;

	}
}
