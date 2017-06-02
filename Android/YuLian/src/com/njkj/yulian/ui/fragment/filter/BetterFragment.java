package com.njkj.yulian.ui.fragment.filter;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.njkj.yulian.R;
import com.njkj.yulian.core.lib.event.EventBus;
import com.njkj.yulian.entity.EventEntity.Better;
import com.njkj.yulian.entity.FilterEntity;
import com.njkj.yulian.ui.fragment.BaseFragment;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.filter
 * 
 * @Description: 推荐
 * 
 * @date 2016-4-13 下午5:19:39
 * 
 * @version 1.0 ==============================
 */
public class BetterFragment extends BaseFragment {
	LinearLayout origin;// 原图
	LinearLayout person;// 人物
	LinearLayout food;// 食物
	LinearLayout landscape;// 风景
	GPUImageBrightnessFilter filter;
	Better originEntity;
	Better personEntity;
	Better foodEntity;
	Better landscapeEntity;

	ImageView[] imagebuttons;
	View[] viewLines;

	ArrayMap<String, Better> betterList = new ArrayMap<String, Better>();

	int tag = 0;
	private Better better;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_panel_better, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		origin = (LinearLayout) view.findViewById(R.id.origin);
		person = (LinearLayout) view.findViewById(R.id.person);
		food = (LinearLayout) view.findViewById(R.id.food);
		landscape = (LinearLayout) view.findViewById(R.id.landscape);

		imagebuttons = new ImageView[4];
		imagebuttons[0] = (ImageView) view.findViewById(R.id.origin_image);
		imagebuttons[1] = (ImageView) view.findViewById(R.id.person_image);
		imagebuttons[2] = (ImageView) view.findViewById(R.id.food_image);
		imagebuttons[3] = (ImageView) view.findViewById(R.id.landscape_image);

		viewLines = new View[4];
		viewLines[0] = view.findViewById(R.id.origin_line);
		viewLines[1] = view.findViewById(R.id.person_line);
		viewLines[2] = view.findViewById(R.id.food_line);
		viewLines[3] = view.findViewById(R.id.landscape_line);

	}

	@Override
	protected void initData() {
		EventBus.getDefault().register(this);
		filter = new GPUImageBrightnessFilter();
	}

	@Override
	protected void initOnClick() {
		origin.setOnClickListener(this);
		person.setOnClickListener(this);
		food.setOnClickListener(this);
		landscape.setOnClickListener(this);
	}

	@Override
	public void onMyClick(View view) {
		imagebuttons[tag].setSelected(false);
		viewLines[tag].setVisibility(View.GONE);
		Better type = null;
		switch (view.getId()) {
		case R.id.origin:
			tag = 0;
			// 原图
			filter.setBrightness(0f);
			break;
		case R.id.person:
			tag = 1;
			// 人物
			filter.setBrightness(0.1f);
			break;
		case R.id.food:
			tag = 2;
			// 食物
			filter.setBrightness(0.15f);
			break;
		case R.id.landscape:
			tag = 3;
			// 风景
			filter.setBrightness(0.2f);
			break;
		}

		imagebuttons[tag].setSelected(true);
		viewLines[tag].setVisibility(View.VISIBLE);
		if (betterList.get(tag) == null) {
			better = new Better(new FilterEntity(filter));
			betterList.put(String.valueOf(tag), better);
		}

		betterList.get(String.valueOf(tag)).filterEntity.filterName = getResources()
				.getStringArray(R.array.filterName)[tag];

		Better better = betterList.get(String.valueOf(tag));
		// EventBus 传递
		EventBus.getDefault().post(better);
	}

	public void onEventMainThread(int count) {
	}

	@Override
	public void onStart() {
		imagebuttons[tag].setSelected(true);
		viewLines[tag].setVisibility(View.VISIBLE);
		super.onStart();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
