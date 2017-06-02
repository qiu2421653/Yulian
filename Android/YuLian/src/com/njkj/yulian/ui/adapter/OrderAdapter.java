package com.njkj.yulian.ui.adapter;

import java.util.List;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.OrderEntity;
import com.njkj.yulian.ui.activity.EvaluationActivity;
import com.njkj.yulian.ui.activity.ShoppActivity;
import com.njkj.yulian.ui.fragment.OrderFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter{
	private List<OrderEntity> list;
	private Context context;
	public  OrderAdapter(Context context,List<OrderEntity> list){
		this.list=list;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.order_item, null);
			viewHolder.img=(ImageView) convertView.findViewById(R.id.order_image);
			viewHolder.title=(TextView) convertView.findViewById(R.id.order_name);
			viewHolder.price=(TextView) convertView.findViewById(R.id.order_integral);
			viewHolder.time=(TextView) convertView.findViewById(R.id.order_time);
			viewHolder.order_pj=(TextView) convertView.findViewById(R.id.order_pj);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.title.setText("aaa");
		viewHolder.price.setText("123");
		viewHolder.time.setText("2016/5/11");
		viewHolder.order_pj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent intent=	new Intent(context,EvaluationActivity.class);
			intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView title;
		TextView price;
		TextView time;
		TextView order_pj;
	}
}
