package com.njkj.yulian.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.njkj.yulian.R;
import com.njkj.yulian.core.callback.OnRefresh;
import com.njkj.yulian.entity.MallEntity;
import com.njkj.yulian.ui.activity.GoodDetailActivity;
import com.njkj.yulian.ui.adapter.MallAdapter;
import com.njkj.yulian.ui.gui.PullToRefreshLayout;
import com.njkj.yulian.ui.gui.PullableListView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment
 * 
 * @Description:商城
 * 
 * @date 2016-4-6 上午9:54:40
 * 
 * @version 1.0 ==============================
 */
public class MallFragment extends BaseFragment implements OnRefresh,
		OnItemClickListener {
	PullToRefreshLayout refresh_view;
	PullableListView item_lv;
	private List<MallEntity> list;
	private Spinner mall_all;
	private LinearLayout mall_top;
	private MallEntity entity;
	private int[] image = {};
	private String[] name = { "泰完美 925情侣戒指男女紧箍咒开口女戒指韩版银饰指环情人节礼物 金色",
			"罗泰老银匠银饰复古泰银嘎乌盒吊坠项链 s925银楞严咒经文圆筒挂坠配饰男女佛教饰品 单吊坠+皮绳",
			"唯一winy银手镯999纯银开口实心女手镯足银若水之荷银手镯女款送妈妈带证书节日礼物送女友 41克左右",
			"JPF 【真爱永恒】925银情侣对戒 情侣戒指 男女对戒开口戒一对银饰品首饰女生日爱的礼物",
			"安妮时尚 925银项链女四叶草镶祖母绿银吊坠 时尚锁骨链银饰品 送女友生日礼物 刻字定制 祖母绿",
			"佐卡伊 邂逅 钻戒钻石结婚戒指女戒 共30分D-E/SI" };
	private int[] price = { 128, 164, 218, 280, 399, 1999 };
	private String[] items = { "积分筛选", "0-999", "1000-2999", "3000-4999",
			"5000以上" };

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_mall, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		mall_top = (LinearLayout) view.findViewById(R.id.mall_top);
		mall_all = (Spinner) view.findViewById(R.id.mall_all);
		refresh_view = (PullToRefreshLayout) view
				.findViewById(R.id.refresh_view);
		item_lv = (PullableListView) view.findViewById(R.id.item_lv);
		refresh_view.setOnRefreshListener(this);
		mall_top.setOnClickListener(this);
		mall_all.setAdapter(new TestArrayAdapter(mContext, items));

	}

	@Override
	protected void initData() {
		list = new ArrayList<MallEntity>();
		for (int i = 0; i < 6; i++) {
			entity = new MallEntity();
			entity.setName(name[i]);
			entity.setIntegral(price[i] * 10 + "");
			entity.setPrice("市场参考价" + price[i] + "元");
			entity.setImage(image[i]);
			list.add(entity);
		}
		item_lv.setAdapter(new MallAdapter(getActivity(), list));
		item_lv.setOnItemClickListener(this);
	}

	@Override
	protected void initOnClick() {

	}

	@Override
	public void onMyClick(View view) {
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
			}
		}, 2000);

	}

	public class TestArrayAdapter extends ArrayAdapter<String> {
		private Context mContext;
		private String[] mStringArray;

		public TestArrayAdapter(Context context, String[] stringArray) {
			super(context, android.R.layout.simple_spinner_item, stringArray);
			mContext = context;
			mStringArray = stringArray;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			// 修改Spinner展开后的字体颜色
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.spinner_item, parent,
						false);
			}

			// 此处text1是Spinner默认的用来显示文字的TextView
			TextView tv = (TextView) convertView
					.findViewById(R.id.spinner_item);
			tv.setText(mStringArray[position]);
			tv.setTextSize(16f);
			return convertView;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 修改Spinner选择后结果的字体颜色
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			// 此处text1是Spinner默认的用来显示文字的TextView
			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(mStringArray[position]);
			tv.setTextSize(16f);
			tv.setTextColor(getResources().getColor(R.color.text_color));
			return convertView;
		}

	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(getActivity(), GoodDetailActivity.class));
	}
}
