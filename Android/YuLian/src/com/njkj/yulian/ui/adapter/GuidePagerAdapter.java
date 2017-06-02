package com.njkj.yulian.ui.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.dao.ConfigDao;
import com.njkj.yulian.ui.activity.MainActivity;

/**
 * 引导adapter
 * 
 * @author Qiu
 * */
public class GuidePagerAdapter extends PagerAdapter {

	private ArrayList<Integer> mImageUrlList;// 图片集合
	private LayoutInflater mInflater;
	private Context mContext;

	public GuidePagerAdapter() {
		this.mContext = MainApplication.getContext();
		this.mInflater = LayoutInflater.from(mContext);
	}

	// 设置图片集
	public void setListData(ArrayList<Integer> list) {
		this.mImageUrlList = list;
		notifyDataSetChanged();
	}

	public Integer getItem(int position) {
		if (mImageUrlList == null)
			return null;
		return mImageUrlList.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return this.mImageUrlList != null ? this.mImageUrlList.size() : 0;
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		// 找到布局
		View imageLayout = mInflater.inflate(R.layout.layout_guide_pager_item,
				view, false);
		// 找到图片
		ImageView imageView = (ImageView) imageLayout
				.findViewById(R.id.niv_main);
		// 取得对应的drawable Id
		Integer entity = mImageUrlList.get(position);
		// 设置图片背景
		imageView.setBackgroundResource(entity);
		// 添加到容器中
		view.addView(imageLayout, 0);
		// 如果是最后一个位置
		if (position == getCount() - 1) {
			// 取得按钮-直接进入
			Button btn_enter = (Button) imageLayout
					.findViewById(R.id.btn_enter);
			// 显示按钮
			btn_enter.setVisibility(View.VISIBLE);
			// 设置监听-直接进入
			btn_enter.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onStartCallBack.onStartCall();
				}
			});

			// 取得按钮-登录
			Button btn_login = (Button) imageLayout
					.findViewById(R.id.btn_login);
			// 显示按钮
			btn_login.setVisibility(View.VISIBLE);
			// 设置监听-登录
			btn_login.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onStartCallBack.onLoginCall();
				}
			});
		}
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	public interface OnStartCallBack {
		void onStartCall();

		void onLoginCall();
	}

	OnStartCallBack onStartCallBack;

	public void setOnStartCallBack(OnStartCallBack onStartCallBack) {
		this.onStartCallBack = onStartCallBack;
	}

}