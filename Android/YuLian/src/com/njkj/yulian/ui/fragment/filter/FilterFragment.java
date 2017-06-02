package com.njkj.yulian.ui.fragment.filter;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njkj.yulian.R;
import com.njkj.yulian.core.lib.event.EventBus;
import com.njkj.yulian.entity.EventEntity.Filters;
import com.njkj.yulian.entity.FilterEntity;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.utils.GPUImageFilterTools;
import com.njkj.yulian.utils.GPUImageFilterTools.OnGpuImageFilterChosenListener;
import com.squareup.picasso.Picasso;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.filter
 * 
 * @Description:滤镜Fragment
 * 
 * @date 2016-4-13 下午5:56:51
 * 
 * @version 1.0 ==============================
 */
public class FilterFragment extends BaseFragment {
	LinearLayout menuLayout;// 分类
	Filters filtersEntity;

	ArrayMap<String, Filters> filterList = new ArrayMap<String, Filters>();

	String[] filterName;

	@Override
	public View onCreateMyView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.font_fragment, null);
		return view;
	}

	@Override
	protected void initViews(View view) {
		menuLayout = (LinearLayout) view.findViewById(R.id.menuLayout);
		menuLayout.setHorizontalScrollBarEnabled(true);

	}

	@Override
	protected void initData() {
		filterName = getResources().getStringArray(R.array.filterName);
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

	// 滤镜..
	private void addFont() {
		menuLayout.setVisibility(View.VISIBLE);
		menuLayout.removeAllViews();
		String[] filterUrl = getResources().getStringArray(R.array.filterUrl);
		for (int i = 0; i != filterName.length; ++i) {
			View view = LayoutInflater.from(getContext()).inflate(
					R.layout.view_filter, null);
			// 头像
			ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
			// 描述
			TextView desc = (TextView) view.findViewById(R.id.tv_desc);
			// 加载图片
			Picasso.with(mContext).load(filterUrl[i]).fit().into(icon);
			// icon.setImageResource(Constant.adJustIcon[0]);
			desc.setText(filterName[i]);
			view.setTag(i);
			view.setOnClickListener(fontListener);
			menuLayout.addView(view);
		}
	}

	android.view.View.OnClickListener fontListener = new android.view.View.OnClickListener() {
		@Override
		public void onClick(final android.view.View view) {
			final Integer tag = (Integer) view.getTag();
			// 选择滤镜效果
			GPUImageFilterTools.chooseFilter(getActivity(), tag,
					new OnGpuImageFilterChosenListener() {
						@Override
						public void onGpuImageFilterChosenListener(
								final GPUImageFilter filter) {
							if (filterList.get(String.valueOf(tag)) == null) {
								filtersEntity = new Filters(new FilterEntity(
										filter));
								filterList.put(tag.toString(), filtersEntity);
							}
							filterList.get(String.valueOf(tag)).filterEntity.filterName = filterName[tag];

							Filters filters = filterList.get(String
									.valueOf(tag));
							// EventBus 传递
							EventBus.getDefault().post(filters);
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
