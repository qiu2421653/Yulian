package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.MallEntity;
import com.njkj.yulian.utils.TypefacesUtils;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description:瀑布流(StoreTypeActivity)
 * 
 * @date 下午9:48:04
 * 
 * @version V1.0 ==============================
 * 
 */
public class StoreTypeAdapter extends BaseAdapter {
	private ArrayList<MallEntity> mInfos;
	LayoutInflater layoutInflator;
	Context mContext;

	public StoreTypeAdapter(ArrayList<MallEntity> mInfo) {
		this.mContext = MainApplication.getContext();
		layoutInflator = LayoutInflater.from(mContext);
		this.mInfos = mInfo;
	}

	@Override
	public int getCount() {
		return (mInfos == null ? 0 : mInfos.size());
	}

	@Override
	public Object getItem(int position) {
		return mInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflator
					.inflate(R.layout.item_store_type, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |
		// Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
		// textView.getPaint().setFlags(0); // 取消设置的的划线
		if (!TextUtils.isEmpty(mInfos.get(position).url)) {
			Picasso.with(mContext).load(mInfos.get(position).url)
					.config(Bitmap.Config.RGB_565).into(holder.iv_item);
		}
		holder.tv_cur.setText(mInfos.get(position).getIntegral());
		// holder.tv_ori.setText(mInfos.get(position).getPrice());
		// // 设置中划线并加清晰
		// holder.tv_ori.getPaint().setFlags(
		// Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		holder.tv_item.setText(mInfos.get(position).comeFrom);
		holder.iv_collection.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
			}
		});
		holder.iv_frok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				holder.tv_fork.setText("21");
			}
		});

		holder.tv_cur.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 进入到购买页
			}
		});
		return convertView;
	}

	class ViewHolder {
		ImageView iv_item;// 图片
		TextView tv_cur;// 当前价格
		// TextView tv_ori;// 市场价格
		TextView tv_item;// 来源
		TextView tv_desc;// 描述
		TextView tv_fork;// 点赞数

		ImageView iv_frok;// 点赞
		ImageView iv_collection;// 收藏

		public ViewHolder(View view) {
			iv_item = (ImageView) view.findViewById(R.id.iv_item);
			tv_cur = (TextView) view.findViewById(R.id.tv_cur);
			// tv_ori = (TextView) view.findViewById(R.id.tv_ori);
			tv_item = (TextView) view.findViewById(R.id.tv_item);
			tv_desc = (TextView) view.findViewById(R.id.tv_desc);
			tv_fork = (TextView) view.findViewById(R.id.tv_fork);

			iv_frok = (ImageView) view.findViewById(R.id.iv_frok);
			iv_collection = (ImageView) view.findViewById(R.id.iv_collection);

			tv_item.setTypeface(TypefacesUtils.get(mContext,
					"Satisfy-Regular.ttf"));
		}
	}
}
