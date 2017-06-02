package com.njkj.yulian.ui.fragment.store;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.njkj.yulian.R;
import com.njkj.yulian.constant.DataContant;
import com.njkj.yulian.entity.MallEntity;
import com.njkj.yulian.entity.WomenEntity;
import com.njkj.yulian.ui.adapter.WomenAdapter;
import com.njkj.yulian.ui.fragment.BaseFragment;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Description: 女士
 * 
 * @date 下午9:53:27
 * 
 * @version V1.0 ==============================
 * 
 */
public class WomenFragment extends BaseFragment implements OnItemClickListener {

	ListView swipe_target;
	WomenAdapter adapter;
	ArrayList<WomenEntity> mUserList;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_women, container, false);
		return view;
	}

	@Override
	protected void initViews(View view) {
		swipe_target = (ListView) view.findViewById(R.id.swipe_target);
	}

	@Override
	protected void initData() {
		setMessage();
		adapter = new WomenAdapter(mUserList);
		swipe_target.setAdapter(adapter);

	}

	@Override
	protected void initOnClick() {
		swipe_target.setOnItemClickListener(this);
	}

	@Override
	public void onMyClick(View view) {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	/**
	 * 测试数据
	 */
	private void setMessage() {
		mUserList = new ArrayList<WomenEntity>();
		WomenEntity entity;
		for (int i = 0; i < 8; i++) {
			entity = new WomenEntity();
			entity.url = DataContant.STORES[i];
			entity.name = DataContant.STOREDESC[i];
			mUserList.add(entity);
		}
	}
}
