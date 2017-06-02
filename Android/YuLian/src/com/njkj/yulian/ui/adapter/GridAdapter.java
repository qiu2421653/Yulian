package com.njkj.yulian.ui.adapter;

import java.util.List;

import com.njkj.yulian.R;
import com.njkj.yulian.ui.activity.EvaluationActivity;
import com.njkj.yulian.ui.activity.PhotoActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {
	private LayoutInflater listContainer;
	private boolean shape;
	private int selectedPosition = -1;
	private List<Bitmap> bmp;
	Context context;

	public class ViewHolder {
		public ImageView image;
		public Button bt;
	}

	public GridAdapter(Context context,List<Bitmap> bmp) {
		listContainer = LayoutInflater.from(context);
		this.context=context;
		this.bmp=bmp;
	}
	@Override
	public int getCount() {
		if (bmp.size() < 6) {
			return bmp.size() + 1;
		} else {
			return bmp.size();
		}
	}
	@Override
	public Object getItem(int arg0) {

		return bmp.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {

		return arg0;
	}
	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}
	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}
	/**
	 * ListView Item设置
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int sign = position;
		// 自定义视图
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 获取list_item布局文件的视图

			convertView = listContainer.inflate(
					R.layout.item_published_grida, null);

			// 获取控件对象
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_grida_image);
			holder.bt = (Button) convertView
					.findViewById(R.id.item_grida_bt);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == bmp.size()) {
			holder.image.setImageBitmap(BitmapFactory.decodeResource(
					context.getResources(), R.drawable.icon_addpic_unfocused));
			holder.bt.setVisibility(View.GONE);
			if (position == 6) {
				holder.image.setVisibility(View.GONE);
			}
		} else {
			holder.image.setImageBitmap(bmp.get(position));
			holder.bt.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					PhotoActivity.bitmap.remove(sign);
					bmp.get(sign).recycle();
					bmp.remove(sign);
					((EvaluationActivity)context).drr.remove(sign);
					((EvaluationActivity)context).gridviewInit();
				}
			});
		}

		return convertView;
	}

}