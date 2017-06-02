package com.njkj.yulian.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.njkj.yulian.R;
import com.njkj.yulian.core.callback.OnRefresh;
import com.njkj.yulian.entity.OrderEntity;
import com.njkj.yulian.ui.activity.GoodDetailActivity;
import com.njkj.yulian.ui.adapter.OrderAdapter;
import com.njkj.yulian.ui.gui.PullToRefreshLayout;
import com.njkj.yulian.ui.gui.PullableListView;

public class OrderFragment extends BaseFragment implements OnRefresh,
		OnItemClickListener {
	PullToRefreshLayout refresh_view;
	PullableListView item_lv;
	OrderAdapter adapter;
	List<OrderEntity> list;
	View view;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_order, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		refresh_view = (PullToRefreshLayout) view
				.findViewById(R.id.refresh_view);
		item_lv = (PullableListView) view.findViewById(R.id.item_lv);
	}

	@Override
	protected void initData() {
		list = new ArrayList<OrderEntity>();
		for (int i = 0; i < 3; i++) {
			OrderEntity entity = new OrderEntity();
			list.add(entity);
		}
		refresh_view.setOnRefreshListener(this);
		adapter = new OrderAdapter(mContext, list);
		item_lv.setAdapter(adapter);
	}

	@Override
	protected void initOnClick() {
		item_lv.setOnItemClickListener(this);
	}

	@Override
	public void onMyClick(View view) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startAnimActivity(GoodDetailActivity.class);

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

}
