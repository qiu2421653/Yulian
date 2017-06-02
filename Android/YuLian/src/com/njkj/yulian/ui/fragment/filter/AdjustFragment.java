package com.njkj.yulian.ui.fragment.filter;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
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
import com.njkj.yulian.entity.EventEntity.Adjust;
import com.njkj.yulian.entity.FilterEntity;
import com.njkj.yulian.ui.fragment.BaseFragment;
import com.njkj.yulian.utils.GPUImageFilterTools;
import com.njkj.yulian.utils.GPUImageFilterTools.OnGpuImageFilterChosenListener;
import com.squareup.picasso.Picasso;

/***
 * 
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.fragment.filter
 * 
 * @Description:滤镜饱和度的Fragment
 * 
 * @date 2016-4-13 下午4:26:06
 * 
 * @version 1.0 ==============================
 */
public class AdjustFragment extends BaseFragment {
	LinearLayout menuLayout;// 分类
	ArrayMap<String, Adjust> aList = new ArrayMap<String, Adjust>();
	Adjust adjustEntity;
	String[] fontNames;

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
		fontNames = getResources().getStringArray(R.array.fontName);
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
		// 图片集合
		String[] adjustUrl = getResources().getStringArray(R.array.adjustUrl);
		for (int i = 0; i != adjustUrl.length; ++i) {
			View view = LayoutInflater.from(getContext()).inflate(
					R.layout.view_adjust, null);
			// 头像
			ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
			// 描述
			TextView desc = (TextView) view.findViewById(R.id.tv_desc);
			// 加载图片
			Picasso.with(mContext).load(adjustUrl[i]).into(icon);
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
			final Integer tag = (Integer) view.getTag();
			GPUImageFilterTools.chooseFont(getActivity(), tag,
					new OnGpuImageFilterChosenListener() {
						@Override
						public void onGpuImageFilterChosenListener(
								final GPUImageFilter filter) {

							if (aList.get(String.valueOf(tag)) == null) {
								adjustEntity = new Adjust(new FilterEntity(
										filter));
								aList.put(tag.toString(), adjustEntity);
							}
							aList.get(String.valueOf(tag)).filterEntity.filterName = fontNames[tag];

							Adjust adjust = aList.get(String.valueOf(tag));
							// EventBus 传递
							EventBus.getDefault().post(adjust);
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
