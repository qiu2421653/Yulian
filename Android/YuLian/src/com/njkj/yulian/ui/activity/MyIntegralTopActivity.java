package com.njkj.yulian.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.DataContant;
import com.njkj.yulian.controller.GoldController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.GoldEntity;
import com.njkj.yulian.entity.IntegralEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.ui.gui.CircleImageView;
import com.squareup.picasso.Picasso;

/***
 * 积分排行榜
 * 
 * @author fx
 * 
 */
public class MyIntegralTopActivity extends BaseActivity {
	private RelativeLayout invis;
	private List<IntegralEntity> list;
	private IntegralEntity Listentity;
	private ListView lv;
	private GoldController controller;
	private TextView integraltop_name;// 我的名字
	private TextView integraltop_jf;// 我的积分
	private TextView integraltop_mc;// 我的排名
	private ImageView integral_img;// 我的头像

	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integraltop);
		setHeaderLeftText();
		setHeaderTitle("禹币排行榜");
		initView();
		controller = new GoldController();
		getGoldRank("0", "10");// 当前页数，一页显示的个数
	}

	private void initView() {
		invis = (RelativeLayout) findViewById(R.id.invis);
		integraltop_name = (TextView) findViewById(R.id.integraltop_name);
		integraltop_jf = (TextView) findViewById(R.id.integraltop_jf);
		integraltop_mc = (TextView) findViewById(R.id.integraltop_mc);
		integraltop_name = (TextView) findViewById(R.id.integraltop_name);
		integral_img = (ImageView) findViewById(R.id.integral_img);

		lv = (ListView) findViewById(R.id.lv);
		list = new ArrayList<IntegralEntity>();
		/*
		 * lv.setOnScrollListener(new OnScrollListener() {
		 * 
		 * @Override public void onScrollStateChanged(AbsListView view, int
		 * scrollState) {
		 * 
		 * }
		 * 
		 * @Override public void onScroll(AbsListView view, int
		 * firstVisibleItem,int visibleItemCount, int totalItemCount) { if
		 * (firstVisibleItem >= 0) { invis.setVisibility(View.VISIBLE); } else {
		 * 
		 * invis.setVisibility(View.GONE); } } });
		 */
	}

	/** 禹币排行榜 */
	private void getGoldRank(String currentPage, String pageCount) {
		String userID = mConfigDao.getString("userID");
		showShortToast(getString(R.string.loading));
		controller.getGoldRank(getString(R.string.FsGetGoldRank), userID,
				currentPage, pageCount, new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							showShortToast(getString(R.string.error));
						} else {
							RetEntity<GoldEntity> entity = (RetEntity<GoldEntity>) data;
							if (entity.success) {
								GoldEntity result = entity.result;
								setMessage(result);
							} else {
								showShortToast(entity.exceptions.get(0).message);
							}
						}
						hideProgress();
					}
				});
	}

	/**
	 * @Title: setMessage
	 * @Description: 设置信息
	 * @param @param result
	 * @return void
	 * @throws
	 */
	private void setMessage(GoldEntity result) {
		if (list == null)
			list = result.fsGEtGoldRankVoList;
		else
			list.addAll(result.fsGEtGoldRankVoList);

		if (adapter == null) {
			adapter = new MyAdapter();
			lv.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}

		integraltop_jf.setText(result.gold);
		integraltop_mc.setText(result.rankDesc);
		integraltop_name.setText(mConfigDao.getString("nickName"));
		if (!TextUtils.isEmpty(result.hPic)) {
			Picasso.with(mContext).load(result.hPic).config(Config.RGB_565)
					.into(integral_img);
		}
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(MyIntegralTopActivity.this)
						.inflate(R.layout.myintegral_item, null);
				viewHolder.image = (CircleImageView) convertView
						.findViewById(R.id.integral_image);
				viewHolder.name = (TextView) convertView
						.findViewById(R.id.name);
				viewHolder.number = (TextView) convertView
						.findViewById(R.id.number);
				viewHolder.level = (TextView) convertView
						.findViewById(R.id.level);
				viewHolder.level_num = (TextView) convertView
						.findViewById(R.id.level_num);
				viewHolder.left_num1 = (LinearLayout) convertView
						.findViewById(R.id.left_num1);
				viewHolder.jifen = (TextView) convertView
						.findViewById(R.id.jifen);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			// ImageLoader.getInstance().displayImage(
			// DataContant.HEADIMG[position], viewHolder.image);

			Picasso.with(mContext).load(list.get(position).hPic)
					.placeholder(R.drawable.empty_photo)
					.config(Bitmap.Config.RGB_565)
					.error(R.drawable.empty_photo).into(viewHolder.image);

			viewHolder.name.setText(list.get(position).nickName);
			viewHolder.number.setText(position + 1 + "");
			viewHolder.level.setText(list.get(position).rankDesc);
			viewHolder.level_num.setText(list.get(position).top);
			viewHolder.jifen.setText("拥有" + list.get(position).gold + "禹币");
			if (position < 3) {
				viewHolder.left_num1
						.setBackgroundResource(R.drawable.integral_item_smal);
				viewHolder.number.setTextColor(MyIntegralTopActivity.this
						.getResources().getColor(R.color.myintegral_titlebar));
			} else {
				viewHolder.left_num1
						.setBackgroundResource(R.drawable.integral_item_numbig);
				viewHolder.number.setTextColor(MyIntegralTopActivity.this
						.getResources().getColor(R.color.integral_img_bottom));
			}
			return convertView;
		}

	}

	class ViewHolder {
		TextView number;// 排名
		CircleImageView image;// 头像
		TextView name;// 昵称
		TextView level, level_num, jifen;// 等级，等级数,积分
		LinearLayout left_num1;
	}

}
