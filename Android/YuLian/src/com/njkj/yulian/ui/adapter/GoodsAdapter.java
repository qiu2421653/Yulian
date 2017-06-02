package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.entity.GoodsContentEntity;
import com.njkj.yulian.entity.MallEntity;
import com.njkj.yulian.ui.adapter.CommentRewardAdapter.ViewItemHolder;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.adapter
 * 
 * @Description:商品内容
 * 
 * @date 2016-6-14 下午5:25:24
 * 
 * @version 1.0 ==============================
 */
public class GoodsAdapter extends BaseAdapter {
	GoodsContentEntity contentEntity;
	LayoutInflater mInflater;
	Context mContext;
	ViewTitleHolder viewTitleHolder;
	ViewSimlarHolder viewSimlarHolder;
	ViewShowHolder viewShowHolder;

	private static final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_THREE = 2,
			TYPE_COUNT = 3;

	public GoodsAdapter(GoodsContentEntity contentEntity) {
		this.mContext = MainApplication.getContext();
		this.mInflater = LayoutInflater.from(mContext);
		this.contentEntity = contentEntity;
	}

	@Override
	public int getCount() {
		return contentEntity.goodsList == null ? 12
				: (2 + contentEntity.goodsList.size());
	}

	/** 该方法返回多少个不同的布局 */
	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	/** 根据position返回相应的Item */
	@Override
	public int getItemViewType(int position) {
		if (position == 0)
			return TYPE_ONE;
		else if (position == 1)
			return TYPE_TWO;
		else
			return TYPE_THREE;
	}

	@Override
	public Object getItem(int position) {
		return null;
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
				convertView = mInflater.inflate(R.layout.goods_header, null);
				viewTitleHolder = new ViewTitleHolder(convertView);
				convertView.setTag(viewTitleHolder);
				break;
			case TYPE_TWO:
				// 加载view
				convertView = mInflater.inflate(R.layout.goods_simlar, null);
				viewSimlarHolder = new ViewSimlarHolder(convertView);
				convertView.setTag(viewSimlarHolder);
				break;
			case TYPE_THREE:
				// 加载view
				convertView = mInflater.inflate(R.layout.goods_show, null);
				viewShowHolder = new ViewShowHolder(convertView);
				convertView.setTag(viewShowHolder);
				break;
			}
		} else {
			switch (type) {
			case TYPE_ONE:
				viewTitleHolder = (ViewTitleHolder) convertView.getTag();
				break;
			case TYPE_TWO:
				viewSimlarHolder = (ViewSimlarHolder) convertView.getTag();
				break;
			case TYPE_THREE:
				viewShowHolder = (ViewShowHolder) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case TYPE_ONE:
			viewTitleHolder.tv_goodtitle.setText(contentEntity.goods.goodsName);
			viewTitleHolder.tv_cur.setText(contentEntity.goods.curPrice);
			viewTitleHolder.tv_designer.setText(contentEntity.goods.designer);
			viewTitleHolder.tv_goodDesc.setText(contentEntity.goods.goodDetail);
			viewTitleHolder.tv_fork.setText(contentEntity.goods.forks);
			break;
		case TYPE_TWO:
			// 坐
			Picasso.with(MainApplication.getContext())
					.load(contentEntity.simlar.get(0).goodsUrl)
					.into(viewSimlarHolder.iv_left);
			// 右
			Picasso.with(MainApplication.getContext())
					.load(contentEntity.simlar.get(1).goodsUrl)
					.into(viewSimlarHolder.iv_right);
			// 坐
			viewSimlarHolder.tv_cur_left
					.setText(contentEntity.simlar.get(0).curPrice);
			viewSimlarHolder.tv_ori_left
					.setText(contentEntity.simlar.get(0).oriPrice);
			viewSimlarHolder.tv_item_left
					.setText(contentEntity.simlar.get(0).designer);

			// 右
			viewSimlarHolder.tv_cur_right
					.setText(contentEntity.simlar.get(1).curPrice);
			viewSimlarHolder.tv_ori_right
					.setText(contentEntity.simlar.get(1).oriPrice);
			viewSimlarHolder.tv_item_right
					.setText(contentEntity.simlar.get(1).designer);
			break;
		case TYPE_THREE:
			if (position == 2)
				viewShowHolder.tv_show.setVisibility(View.VISIBLE);
			else
				viewShowHolder.tv_show.setVisibility(View.GONE);

			break;
		}
		return convertView;
	}

	// 头部信息
	class ViewTitleHolder {
		private Button btn_buy;
		private TextView tv_goodtitle;
		private TextView tv_cur;
		private TextView tv_designer;
		private TextView tv_goodDesc;
		private TextView tv_fork;
		private ImageView iv_fork;
		private ImageView iv_collection;

		public ViewTitleHolder(View view) {
			btn_buy = (Button) view.findViewById(R.id.btn_buy);
			tv_goodtitle = (TextView) view.findViewById(R.id.tv_goodtitle);
			tv_cur = (TextView) view.findViewById(R.id.tv_cur);
			tv_designer = (TextView) view.findViewById(R.id.tv_designer);
			tv_goodDesc = (TextView) view.findViewById(R.id.tv_goodDesc);
			tv_fork = (TextView) view.findViewById(R.id.tv_fork);
			iv_fork = (ImageView) view.findViewById(R.id.iv_fork);
			iv_collection = (ImageView) view.findViewById(R.id.iv_collection);
		}
	}

	// 相似栏
	class ViewSimlarHolder {
		LinearLayout ll_left;
		LinearLayout ll_right;

		ImageView iv_left;
		ImageView iv_right;

		TextView tv_cur_left;
		TextView tv_cur_right;

		TextView tv_ori_left;
		TextView tv_ori_right;

		TextView tv_item_left;
		TextView tv_item_right;

		public ViewSimlarHolder(View view) {
			// 左
			ll_left = (LinearLayout) view.findViewById(R.id.ll_left);
			iv_left = (ImageView) ll_left.findViewById(R.id.iv_item);
			tv_cur_left = (TextView) ll_left.findViewById(R.id.tv_cur);
			tv_ori_left = (TextView) ll_left.findViewById(R.id.tv_ori);
			tv_item_left = (TextView) ll_left.findViewById(R.id.tv_item);

			// 右
			ll_right = (LinearLayout) view.findViewById(R.id.ll_right);
			iv_right = (ImageView) ll_right.findViewById(R.id.iv_item);
			tv_cur_right = (TextView) ll_right.findViewById(R.id.tv_cur);
			tv_ori_right = (TextView) ll_right.findViewById(R.id.tv_ori);
			tv_item_right = (TextView) ll_right.findViewById(R.id.tv_item);
		}
	}

	// 买家秀
	class ViewShowHolder {
		TextView tv_show;

		public ViewShowHolder(View view) {
			tv_show = (TextView) view.findViewById(R.id.tv_show);
		}
	}
}
