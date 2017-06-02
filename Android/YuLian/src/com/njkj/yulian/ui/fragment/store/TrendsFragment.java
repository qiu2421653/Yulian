package com.njkj.yulian.ui.fragment.store;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.DataContant;
import com.njkj.yulian.entity.MallEntity;
import com.njkj.yulian.ui.activity.store.StoreTypeActivity;
import com.njkj.yulian.ui.adapter.TrendsAdapter;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.ui.gui.loading.LoadingState;
import com.njkj.yulian.utils.NetUtils;

/**
 * 
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description:流行
 * 
 * @date 下午7:58:25
 * 
 * @version V1.0 ==============================
 * 
 */
public class TrendsFragment extends BaseFragment implements OnItemClickListener {

	private static final String TAG = "TrendsFragment";

	GridView gridview;
	TrendsAdapter adapter;
	ArrayList<MallEntity> mUserList;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.activity_trends, container, false);
		return view;
	}

	@Override
	protected void initViews(View view) {
		gridview = (GridView) view.findViewById(R.id.gridview);
		// 初始化加载
		initLoading(view);
		bindLoadingView();
	}

	@Override
	protected void initData() {
		// TODO 模拟
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				setMessage();
				adapter = new TrendsAdapter(mUserList);
				gridview.setAdapter(adapter);
				showSuccess();
			}
		}, 1000);

	}

	@Override
	protected void initOnClick() {
		gridview.setOnItemClickListener(this);
	}

	@Override
	public void onMyClick(View view) {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		Intent intent = new Intent(mContext, StoreTypeActivity.class);
		intent.putExtra("mallEntity", mUserList.get(position));
		startActivity(intent);
	}

	@Override
	public void showSuccess() {
		super.showSuccess();
		gridview.setVisibility(View.VISIBLE);
	}

	@Override
	public void showEmpty() {
		super.showEmpty();
		gridview.setVisibility(View.GONE);
	}

	@Override
	public void showFaild() {
		super.showFaild();
		gridview.setVisibility(View.GONE);
	}

	@Override
	public void showNoNet() {
		super.showNoNet();
		gridview.setVisibility(View.GONE);
	}

	@Override
	public void onRetry() {
		// 重新获取数据
		setMessage();
	}

	/**
	 * 测试数据
	 */
	private void setMessage() {
		mUserList = new ArrayList<MallEntity>();
		MallEntity entity;
		for (int i = 0; i < 12; i++) {
			entity = new MallEntity();
			entity.url = DataContant.STORES[i];
			entity.typeId = String.valueOf(i);
			entity.setName(DataContant.STOREDESC[i]);
			mUserList.add(entity);
		}
	}
}
