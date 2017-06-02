package com.njkj.yulian.ui.fragment.filter;

import java.util.LinkedList;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.js.photosdk.filter.GPUImageView;
import com.njkj.yulian.R;
import com.njkj.yulian.core.lib.event.EventBus;
import com.njkj.yulian.entity.EventEntity.EditAdjust;
import com.njkj.yulian.entity.FilterEntity;
import com.njkj.yulian.ui.activity.image.ImageEditActivity;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.utils.GPUImageFilterTools.FilterAdjuster;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.filter
 * 
 * @Description:编辑饱和滤镜
 * 
 * @date 2016-4-14 上午9:08:10
 * 
 * @version 1.0 ==============================
 */
public class EditAdjustFragment extends BaseFragment implements
		OnSeekBarChangeListener {
	private static final String TAG = "EditAdjustment";
	ImageView imagecancel;
	ImageView imageconfirm;
	SeekBar startPointSeekBar;
	TextView effect_progress;
	TextView effect_name;
	int mProgress = 0;// 进度

	private GPUImageFilter mFilter;
	private GPUImageView gpuimage;
	private GPUImageFilter filter;
	private FilterAdjuster mFilterAdjuster;

	LinkedList<GPUImageFilter> mFilterList;
	FilterEntity filterEntity;
	EditAdjust adjust = new EditAdjust();
	private GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup();// filterGroup

	public EditAdjustFragment(GPUImageView gpuimage,
			LinkedList<GPUImageFilter> mFilterList) {
		this.gpuimage = gpuimage;
		this.mFilterList = mFilterList;
	}

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_panel_adjust, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		imagecancel = (ImageView) view.findViewById(R.id.imagecancel);
		imageconfirm = (ImageView) view.findViewById(R.id.imageconfirm);
		startPointSeekBar = (SeekBar) view.findViewById(R.id.startPointSeekBar);
		effect_progress = (TextView) view.findViewById(R.id.effect_progress);
		effect_name = (TextView) view.findViewById(R.id.effect_name);
	}

	@Override
	protected void initData() {
		EventBus.getDefault().register(this);
		startPointSeekBar.setOnSeekBarChangeListener(this);
		startPointSeekBar.setMax(100);
		startPointSeekBar.setProgress(0);
		effect_name.setText(filterEntity.filterName);
	}

	@Override
	protected void initOnClick() {
		imagecancel.setOnClickListener(this);
		imageconfirm.setOnClickListener(this);
	}

	@Override
	public void onMyClick(View view) {
		adjust.filterEntity = filterEntity;
		adjust.filterAdjuster = mFilterAdjuster;
		switch (view.getId()) {
		case R.id.imagecancel:
			// 取消关闭
			if (adjust.filterEntity.isFirst) {
				mFilterList.remove(filter);
			}
			adjust.isCancel = true;
			EventBus.getDefault().post(adjust);
			clear();
			break;
		case R.id.imageconfirm:
			// 保存
			adjust.isCancel = false;
			adjust.filterEntity.isFirst = false;
			adjust.filterEntity.progress = mProgress;
			EventBus.getDefault().post(adjust);
			break;
		}
	}

	public void onEventMainThread(GPUImageFilter filter) {
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mProgress = progress;
		if (mFilterAdjuster != null) {
			mFilterAdjuster.adjust(progress);
		}
		gpuimage.requestRender();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		effect_progress.setText(mProgress + "%");
	}

	// 选择一个滤镜
	private void switchFilterTo(final GPUImageFilter filter) {
		if (mFilter == null
				|| (filter != null && !mFilter.getClass().equals(
						filter.getClass()))) {
			mFilter = filter;
			mFilterAdjuster = new FilterAdjuster(mFilter);
			updateFilterGroup();
		}
	}

	// 更新混合
	private void updateFilterGroup() {
		// 恢复默认
		gpuimage.setFilter(new GPUImageFilter());
		gpuImageFilterGroup.setFilters(mFilterList);
		gpuimage.setFilter(gpuImageFilterGroup);
		gpuimage.requestRender();
	}

	// 设置滤镜
	public void setFilter(FilterEntity mFilterEntity) {
		// 当前滤镜
		this.filter = mFilterEntity.gpuFilter;
		this.filterEntity = mFilterEntity;
		switchFilterTo(filter);
	}

	// 归位
	private void clear() {
		mProgress = 0;
		mFilter = null;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO
		effect_name.setText(filterEntity.filterName);
		startPointSeekBar.setProgress((int) filterEntity.progress);
		effect_progress.setText((int) filterEntity.progress + "%");
		if (mFilterAdjuster != null) {
			mFilterAdjuster.adjust((int) filterEntity.progress);
		}
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
