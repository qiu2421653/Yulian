package com.njkj.yulian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.TimeEntity;
import com.njkj.yulian.entity.TopicDetailEntity;
import com.njkj.yulian.ui.gui.CircularImage;
import com.njkj.yulian.utils.DevUtils;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description: 时间轴
 * 
 * @date 下午9:48:25
 * 
 * @version V1.0 ==============================
 * 
 */
public class TimeLineAdapter extends BaseAdapter {
	protected static final String TAG = "ChattingAdapter";

	private LayoutInflater mInflater;
	private Context mContext;
	private TimeEntity msgs;
	ViewHolder holder;
	ViewContentHolder holdContentlder;
	ViewMoreHolder moreHolder;

	private static final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_THREE = 2,
			TYPE_COUNT = 3;

	public TimeLineAdapter(TimeEntity messages) {
		super();
		this.mContext = MainApplication.getContext();
		this.msgs = messages;
		mInflater = LayoutInflater.from(mContext);

	}

	@Override
	public int getCount() {
		return msgs.timeList == null ? 1 : msgs.timeList.size() + 1;
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
		if (position == 0) {
			// 头
			return TYPE_ONE;
		} else {
			if (msgs.timeList.get(position - 1).urlList.size() == 1) {
				// 一张图
				return TYPE_TWO;
			} else if (msgs.timeList.get(position - 1).urlList.size() > 1) {
				// 两张图
				return TYPE_THREE;
			}
		}
		return TYPE_TWO;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 对应位置类型
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TYPE_ONE:
				// 加载view
				convertView = mInflater.inflate(R.layout.time_line_cover_row,
						null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
				break;
			case TYPE_TWO:
				// 一张图
				convertView = mInflater.inflate(R.layout.activity_time_one,
						null);
				holdContentlder = new ViewContentHolder(convertView);
				convertView.setTag(holdContentlder);
				break;
			case TYPE_THREE:
				// 两张图
				convertView = mInflater.inflate(R.layout.activity_time_two,
						null);
				moreHolder = new ViewMoreHolder(convertView);
				convertView.setTag(moreHolder);
				break;
			}
		} else {
			switch (type) {
			case TYPE_ONE:
				holder = (ViewHolder) convertView.getTag();
				break;
			case TYPE_TWO:
				holdContentlder = (ViewContentHolder) convertView.getTag();
				break;
			case TYPE_THREE:
				moreHolder = (ViewMoreHolder) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case TYPE_ONE:
			// 背景图片
			if (!TextUtils.isEmpty(msgs.topicThumb)) {
				Picasso.with(mContext)
						.load(msgs.topicThumb)
						.placeholder(R.drawable.empty_photo)
						.config(Bitmap.Config.RGB_565)
						.error(R.drawable.empty_photo)
						.resize(DevUtils.dip2px(mContext, 400),
								DevUtils.dip2px(mContext, 200)).centerCrop()
						.into(holder.iv_cover);
			}
			// 头像
			if (!TextUtils.isEmpty(msgs.hPic)) {
				Picasso.with(mContext).load(msgs.hPic)
						.placeholder(R.drawable.logo)
						.config(Bitmap.Config.RGB_565)
						.error(R.drawable.logo).into(holder.avatar);
			}
			break;
		case TYPE_TWO:
			// 一张图
			if (msgs.timeList.get(position - 1).urlList != null
					&& msgs.timeList.get(position - 1).urlList.size() > 0) {
				Picasso.with(mContext)
						.load(msgs.timeList.get(position - 1).urlList.get(0).url)
						.placeholder(R.drawable.empty_photo)
						.config(Bitmap.Config.RGB_565)
						.error(R.drawable.empty_photo)
						.into(holdContentlder.iv_first);
			}
			holdContentlder.tv_tag.setText(msgs.timeList.get(position - 1).tag);
			holdContentlder.tv_content
					.setText(msgs.timeList.get(position - 1).content);
			break;
		case TYPE_THREE:
			// 两张图
			Picasso.with(mContext)
					.load(msgs.timeList.get(position - 1).urlList.get(0).url)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.error(R.drawable.empty_photo).into(moreHolder.iv_first);

			Picasso.with(mContext)
					.load(msgs.timeList.get(position - 1).urlList.get(1).url)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.error(R.drawable.empty_photo).into(moreHolder.iv_second);

			moreHolder.tv_tag.setText(msgs.timeList.get(position - 1).tag);
			String content = msgs.timeList.get(position - 1).content;

			moreHolder.tv_content.setText(content.substring(0,
					content.length() / 2));
			moreHolder.tv_second
					.setText(content.substring(content.length() / 2));
			break;
		}
		return convertView;
	}

	// 头部
	class ViewHolder {
		CircularImage avatar;//头像
		ImageView iv_cover;//背景

		public ViewHolder(View view) {
			iv_cover = (ImageView) view.findViewById(R.id.iv_cover);
			avatar = (CircularImage) view.findViewById(R.id.avatar);
		}
	}

	// 内容(一张图)
	class ViewContentHolder {
		TextView tv_content;
		ImageView iv_first;
		TextView tv_tag;

		public ViewContentHolder(View view) {
			tv_tag = (TextView) view.findViewById(R.id.tv_tag);
			tv_content = (TextView) view.findViewById(R.id.tv_content);
			iv_first = (ImageView) view.findViewById(R.id.iv_first);
		}
	}

	// 内容(两张图)
	class ViewMoreHolder {
		TextView tv_content;
		TextView tv_second;
		TextView tv_tag;
		ImageView iv_first;
		ImageView iv_second;

		public ViewMoreHolder(View view) {
			tv_content = (TextView) view.findViewById(R.id.tv_content);
			tv_second = (TextView) view.findViewById(R.id.tv_second);
			tv_tag = (TextView) view.findViewById(R.id.tv_tag);
			iv_first = (ImageView) view.findViewById(R.id.iv_first);
			iv_second = (ImageView) view.findViewById(R.id.iv_second);
		}
	}

}
