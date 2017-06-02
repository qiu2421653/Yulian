package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.njkj.yulian.R;
import com.njkj.yulian.entity.LoveEntity;
import com.njkj.yulian.ui.activity.topic.TopicActivity;
import com.njkj.yulian.ui.gui.RectRoundImageView;
import com.njkj.yulian.utils.MyloveTheme;
import com.squareup.picasso.Picasso;

public class GAdapter extends BaseAdapter implements OnClickListener {

	private static final String TAG = "GAdapter";

	private Context context;
	private List<LoveEntity> list;
	private List<HorizontalScrollView> scroll;
	private MyloveTheme theme;
	private boolean type;

	public GAdapter(Context context, List<LoveEntity> list, MyloveTheme theme,
			boolean type) {
		this.context = context;
		this.list = list;
		scroll = new ArrayList<HorizontalScrollView>();
		this.theme = theme;
		this.type = type;
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
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.mylovenew_item, null);
			viewHolder.img = (RectRoundImageView) convertView
					.findViewById(R.id.title_image);
			viewHolder.mylovenew_type = (LinearLayout) convertView
					.findViewById(R.id.mylovenew_type);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.theme_title);
			viewHolder.mylove_delete = (TextView) convertView
					.findViewById(R.id.mylove_delete);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.theme_time);
			viewHolder.hscrollView = (HorizontalScrollView) convertView
					.findViewById(R.id.hscrollView);
			viewHolder.mylove_yc = (TextView) convertView
					.findViewById(R.id.mylove_yc);
			viewHolder.mylove_dq = (TextView) convertView
					.findViewById(R.id.mylove_dq);
			viewHolder.mylove_yctype = (ImageView) convertView
					.findViewById(R.id.mylove_yctype);
			viewHolder.mylove_key = (ImageView) convertView
					.findViewById(R.id.mylove_key);
			viewHolder.mylove_itemtz = (LinearLayout) convertView
					.findViewById(R.id.mylove_itemtz);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (type == true)
			viewHolder.mylovenew_type.setVisibility(View.VISIBLE);
		else
			viewHolder.mylovenew_type.setVisibility(View.GONE);
		Picasso.with(context).load(list.get(position).thumb)
				.placeholder(R.drawable.empty_photo)
				.config(Bitmap.Config.RGB_565).error(R.drawable.empty_photo)
				.into(viewHolder.img);
		if ("10".equals(list.get(position).isLock)) {// 当前
			viewHolder.mylove_yctype.setVisibility(View.GONE);
			viewHolder.mylove_key.setVisibility(View.GONE);
		} else if ("20".equals(list.get(position).isLock)) {// 非当前
			viewHolder.mylove_yctype.setVisibility(View.VISIBLE);
			viewHolder.mylove_key.setVisibility(View.GONE);
			viewHolder.mylove_yc.setText("隐藏");
		} else {// 隐藏
			viewHolder.mylove_yctype.setVisibility(View.VISIBLE);
			viewHolder.mylove_key.setVisibility(View.VISIBLE);
			viewHolder.mylove_yc.setText("显示");
		}
		viewHolder.title.setText(list.get(position).loveDesc);
		viewHolder.time.setText(list.get(position).createTime);
		scroll.add(viewHolder.hscrollView);
		// 删除
		viewHolder.mylove_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!"10".equals(list.get(position).isLock)) {
					delete(position);
				} else {
					Toast.makeText(context, "不可以删除当前经历", Toast.LENGTH_SHORT)
							.show();
				}
				scroll.get(position).smoothScrollTo(0, 20);
			}
		});
		// 隐藏
		viewHolder.mylove_yc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!"10".equals(list.get(position).isLock)) {
					if ("30".equals(list.get(position).isLock))
						theme.isType(true, list.get(position).topicId, "20");
					else {
						theme.isType(true, list.get(position).topicId, "30");
					}
				} else {
					Toast.makeText(context, "当前经历不可以隐藏", Toast.LENGTH_SHORT)
							.show();
				}
				scroll.get(position).smoothScrollTo(0, 20);
			}
		});
		// 当前
		viewHolder.mylove_dq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!"10".equals(list.get(position).isLock)) {
					theme.isCurrent(true, list.get(position).topicId);
				} else {
					Toast.makeText(context, "已经是当前经历了", Toast.LENGTH_SHORT)
							.show();
				}
				scroll.get(position).smoothScrollTo(0, 20);
			}
		});

		// 跳转详细页面
		viewHolder.mylove_itemtz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = null;
				intent = new Intent(context, TopicActivity.class);
				intent.putExtra("topicId", list.get(position).topicId);
				intent.putExtra("userID", list.get(position).userId);
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	// 删除按钮
	private void delete(final int position) {
		Dialog dialog = new AlertDialog.Builder(context).setMessage("是否删除？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						theme.isDelete(true, list.get(position).topicId);// true删除当前类
						/*
						 * list.remove(position); notifyDataSetChanged();
						 */
						scroll.get(position).smoothScrollTo(0, 20);
						dialog.dismiss();
					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(context, "已经取消", Toast.LENGTH_SHORT)
								.show();
						scroll.get(position).smoothScrollTo(0, 20);
						dialog.dismiss();
					}
				}).create();
		dialog.show();
	}

	static class ViewHolder {
		RectRoundImageView img;// 图片
		static HorizontalScrollView hscrollView;// 水平滚动条
		TextView title, mylove_delete;// 标题,删除
		TextView time;// 时间
		TextView mylove_yc;// 隐藏（文字）
		LinearLayout mylovenew_type;// 是否不然滑动
		TextView mylove_dq;// 当前
		ImageView mylove_yctype, mylove_key;// 隐藏
		LinearLayout mylove_itemtz;// 跳转到详细页面
	}

	@Override
	public void onClick(View v) {
		context.startActivity(new Intent(context, TopicActivity.class));
	}

	public List<HorizontalScrollView> getScrollView() {
		return scroll;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}
