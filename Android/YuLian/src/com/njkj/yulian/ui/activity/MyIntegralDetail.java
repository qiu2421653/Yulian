package com.njkj.yulian.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.controller.GoldController;
import com.njkj.yulian.core.callback.SimpleCallback;
import com.njkj.yulian.entity.GoldDetailEntity;
import com.njkj.yulian.entity.GoldEntity;
import com.njkj.yulian.entity.RetEntity;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnLoadMoreListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.OnRefreshListener;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeToLoadLayout;

/***
 * 积分明细
 * 
 * @author fx
 * 
 */
public class MyIntegralDetail extends BaseActivity implements
		OnLoadMoreListener, OnItemClickListener, OnRefreshListener {
	ListView swipe_target;
	SwipeToLoadLayout swipeToLoadLayout;

	GoldController controller;
	List<GoldDetailEntity> Listentity;
	private int currentPage = 0;
	private int pageCount = 15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integraldetail);
		setHeaderLeftText();
		setHeaderTitle("禹币明细");
		initView();
		initData();
	}

	private void initView() {
		swipe_target = (ListView) findViewById(R.id.swipe_target);
		swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
		// 初始化加载
		initLoading();
		bindLoadingView();
	}

	private void initData() {
		initRefresh();
		controller = new GoldController();
		getGoldDetail();

	}

	// 获取禹币积分明细
	private void getGoldDetail() {
		String userID = mConfigDao.getString("userID");
		if (currentPage != 0)
			showDialog();
		controller.getGoldDetail(getString(R.string.FsGetGoldDetail), userID,
				String.valueOf(currentPage), String.valueOf(pageCount),
				new SimpleCallback() {
					@Override
					public void onCallback(Object data) {
						if (data == null) {
							if (currentPage != 0)
								showNoNet();
							else
								showShortToast(getString(R.string.error));
						} else {
							RetEntity<GoldEntity> entity = (RetEntity<GoldEntity>) data;
							if (entity.success) {
								GoldEntity result = entity.result;
								Listentity = result.fsGetGoldDetailVo;
								swipe_target.setAdapter(new IntegrallAdapter());
								showSuccess();
							} else {
								if (currentPage == 0)
									showFaild();
								else
									showShortToast(entity.getErrorMsg());
							}
						}
						hideProgress();
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void showSuccess() {
		super.showSuccess();
		swipeToLoadLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void showEmpty() {
		super.showEmpty();
		swipeToLoadLayout.setVisibility(View.GONE);

	}

	@Override
	public void showFaild() {
		super.showFaild();
		swipeToLoadLayout.setVisibility(View.GONE);
	}

	@Override
	public void showNoNet() {
		super.showNoNet();
		swipeToLoadLayout.setVisibility(View.GONE);
	}

	@Override
	public void onRetry() {
		// 重新获取数据
		getGoldDetail();
	}

	@Override
	public void onRefresh() {
		swipeToLoadLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				overRefresh();
			}
		}, 1500);
	}

	@Override
	public void onLoadMore() {
		getGoldDetail();
	}

	private void initRefresh() {
		swipeToLoadLayout.setOnRefreshListener(this);
		swipeToLoadLayout.setOnLoadMoreListener(this);
		swipeToLoadLayout.setLoadingMore(false);
		swipeToLoadLayout.setRefreshing(false);
	}

	private void overRefresh() {
		swipeToLoadLayout.setLoadingMore(false);
		swipeToLoadLayout.setRefreshing(false);
	}

	class IntegrallAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Listentity.size();
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
				convertView = LayoutInflater.from(MyIntegralDetail.this)
						.inflate(R.layout.myintegrall_item, null);
				viewHolder.money = (TextView) convertView
						.findViewById(R.id.myintegrall_money);
				viewHolder.dateTime = (TextView) convertView
						.findViewById(R.id.myintegrall_time);
				viewHolder.description = (TextView) convertView
						.findViewById(R.id.myintegrall_description);
				viewHolder.oprAmount = (TextView) convertView
						.findViewById(R.id.myintegrall_oprAmount);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.money.setText(Listentity.get(position).curAmount);
			viewHolder.dateTime.setText(Listentity.get(position).dateTime);
			viewHolder.description
					.setText(Listentity.get(position).description);
			viewHolder.oprAmount.setText(Listentity.get(position).oprAmount);

			return convertView;
		}

	}

	class ViewHolder {
		TextView money;// 当前金额
		TextView dateTime;// 时间
		TextView description;// 用途
		TextView oprAmount;// 消费的金额

	}

}
