package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.TagEntity;
import com.njkj.yulian.utils.DevUtils;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:首页Recycle的填充
 * 
 * @date 2016-5-7 上午11:30:04
 * 
 * @version 1.0 ==============================
 */
public class MoreTagAdapter extends
		RecyclerView.Adapter<MoreTagAdapter.ViewHolder> {

	public interface OnItemClickLitener {
		void onItemClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	private LayoutInflater mInflater;
	private ArrayList<TagEntity> firstTags;

	public MoreTagAdapter(Context context, ArrayList<TagEntity> firstTags) {
		mInflater = LayoutInflater.from(context);
		this.firstTags = firstTags;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View arg0) {
			super(arg0);
		}

		ImageView mImg;
		TextView mTxt;
	}

	@Override
	public int getItemCount() {
		return firstTags.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = mInflater.inflate(R.layout.activity_index_moretag_item,
				viewGroup, false);
		ViewHolder viewHolder = new ViewHolder(view);

		viewHolder.mImg = (ImageView) view
				.findViewById(R.id.id_index_gallery_item_image);
		viewHolder.mTxt = (TextView) view.findViewById(R.id.tv_item_tag);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
		// 第一种普通的情况
		Picasso.with(MainApplication.getContext())
				.load(firstTags.get(position).url)
				.placeholder(R.drawable.empty_photo)
				.config(Bitmap.Config.RGB_565)
				.error(R.drawable.empty_photo)
				.resize(DevUtils.dip2px(MainApplication.getContext(), 120),
						DevUtils.dip2px(MainApplication.getContext(), 100))
				.centerCrop().into(viewHolder.mImg);
		viewHolder.mTxt.setText(firstTags.get(position).tag);

		if (mOnItemClickLitener != null) {
			viewHolder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickLitener.onItemClick(viewHolder.itemView,
							position);
				}
			});

		}

	}
}
