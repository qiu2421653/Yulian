package com.njkj.yulian.ui.adapter;

import java.util.List;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.MallEntity;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MallAdapter extends BaseAdapter{
	private Context context;
	private List<MallEntity> list;
	public  MallAdapter(Context context,List<MallEntity> list){
		this.context=context;
		this.list=list;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.myall_item, null);
			viewHolder.image=(ImageView) convertView.findViewById(R.id.mall_image);
			viewHolder.name=(TextView) convertView.findViewById(R.id.mall_name);
			viewHolder.integral=(TextView) convertView.findViewById(R.id.mall_integral);
			viewHolder.price=(TextView) convertView.findViewById(R.id.mall_price);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.image.setImageResource(list.get(position).getImage());
		viewHolder.name.setText(list.get(position).getName());
		viewHolder.integral.setText(list.get(position).getIntegral());
		viewHolder.price.setText(list.get(position).getPrice());
		viewHolder.price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  
		
		return convertView;
	}
	class ViewHolder{
		ImageView image;
		TextView name,integral,price;
	}
}
