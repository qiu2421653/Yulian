package com.njkj.yulian.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.gui.RectRoundImageView;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:更多标签
 * 
 * @date 2016-5-7 下午12:18:48
 * 
 * @version 1.0 ==============================
 */
public class MoreItemAdapter extends BaseAdapter {

	protected static final String TAG = "FavorAdapter";

	private TopicDetailEntity moreTagEntity;
	private LayoutInflater mInflater;
	private Context mContext;
	private static final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_COUNT = 2;

	public MoreItemAdapter(Context ctx, TopicDetailEntity moreTagEntity) {
		this.mContext = ctx;
		this.moreTagEntity = moreTagEntity;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (moreTagEntity.urlList.size() > 5)
			return 5;
		else
			return moreTagEntity.urlList.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	/** 该方法返回多少个不同的布局 */
	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	/** 根据position返回相应的Item */
	@Override
	public int getItemViewType(int position) {
		if (position == getCount() - 1)
			return TYPE_ONE;
		else
			return TYPE_TWO;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		ViewDrawHolder viewDrawHolder = null;
		// 对应位置类型
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TYPE_ONE:
				// 加载view
				convertView = mInflater.inflate(R.layout.activity_more, null);
				viewDrawHolder = new ViewDrawHolder(convertView);
				convertView.setTag(viewDrawHolder);
				break;
			case TYPE_TWO:
				// 加载view
				convertView = mInflater.inflate(R.layout.activity_tag_hpic,
						null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
				break;
			}
		} else {
			switch (type) {
			case TYPE_ONE:
				viewDrawHolder = (ViewDrawHolder) convertView.getTag();
				break;
			case TYPE_TWO:
				viewHolder = (ViewHolder) convertView.getTag();
				break;
			}
		}

		switch (type) {
		case TYPE_ONE:
			// viewHolder.iv_icon.setImageResource(R.drawable.tag_more);
			break;
		case TYPE_TWO:
			Picasso.with(mContext)
					.load(moreTagEntity.urlList.get(position).url)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.error(R.drawable.empty_photo).into(viewHolder.iv_icon);

			break;
		}

		return convertView;
	}

	class ViewHolder {
		RectRoundImageView iv_icon;

		public ViewHolder(View view) {
			iv_icon = (RectRoundImageView) view.findViewById(R.id.iv_icon);
		}
	}

	// 自己绘制的一个布局
	class ViewDrawHolder {
		public ViewDrawHolder(View view) {
		}
	}
}