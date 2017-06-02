package com.njkj.yulian.ui.fragment.filter;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.core.lib.event.EventBus;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.utils.GPUImageFilterTools;
import com.njkj.yulian.utils.GPUImageFilterTools.OnGpuImageFilterChosenListener;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.filter
 * 
 * @Description:水印
 * 
 * @date 2016-4-13 下午5:19:39
 * 
 * @version 1.0 ==============================
 */
public class StickerFragment extends BaseFragment {
	LinearLayout menuLayout;// 分类

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.font_fragment, null);
		return view;
	}

	@Override
	protected void initViews(View view) {

	}

	@Override
	protected void initData() {
		EventBus.getDefault().register(this);
		addFont();
	}

	@Override
	protected void initOnClick() {
	}

	@Override
	public void onMyClick(View view) {
	}

	public void onEventMainThread(GPUImageFilter filter) {
	}

	// 亮度...
	private void addFont() {
		menuLayout.setVisibility(View.VISIBLE);
		menuLayout.removeAllViews();
		String[] fontNames = getResources().getStringArray(R.array.fontName);
		// int[] adJustIcon = getResources().getIntArray(R.array.adJustIcon);
		for (int i = 0; i != fontNames.length; ++i) {
			View view = LayoutInflater.from(getContext()).inflate(
					R.layout.view_adjust, null);
			// 头像
			ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
			// 描述
			TextView desc = (TextView) view.findViewById(R.id.tv_desc);
			// icon.setImageResource(adJustIcon[i]);
			desc.setText(fontNames[i]);
			view.setTag(i);
			view.setOnClickListener(fontListener);
			menuLayout.addView(view);
		}
	}

	// 颜色饱和度
	android.view.View.OnClickListener fontListener = new android.view.View.OnClickListener() {
		@Override
		public void onClick(final android.view.View view) {
			Integer tag = (Integer) view.getTag();
			GPUImageFilterTools.chooseFont(getActivity(), tag,
					new OnGpuImageFilterChosenListener() {
						@Override
						public void onGpuImageFilterChosenListener(
								final GPUImageFilter filter) {
							// EventBus 传递
							EventBus.getDefault().post(filter);
						}
					});
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
