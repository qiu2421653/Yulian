package com.njkj.yulian.ui.activity.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.njkj.yulian.R;
import com.njkj.yulian.ui.gui.CircularImage;

/**
 * 生成所需的图片
 * */
public abstract class BaseBitMapActivity extends BaseLocActivity {

	private final String TAG = "BaseLocationActivity";

	protected BitmapDescriptor baiNor;// 百度的小图标
	private LayoutInflater inflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = getLayoutInflater();
		init();
	}

	private void init() {
		// 百度图片
		baiNor = BitmapDescriptorFactory
				.fromResource(R.drawable.comment_emo_like_press);
	}

	/**
	 * 添加自定义marker_转换为
	 * 
	 * @param layoutsource_布局文件
	 * @param bitmap_填充图
	 * @return
	 */
	public View addViewByXml(Bitmap bitmap) {
		View view = inflater.inflate(R.layout.activity_map, null);
		CircularImage userPic = (CircularImage) view
				.findViewById(R.id.iv_persion);
		userPic.setImageBitmap(bitmap);
		return view;
	}

	// 添加自定义marker_转换为bitmap
	protected Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		return view.getDrawingCache();
	}

	/**
	 * 
	 * @Title: getBitmapPlusNor
	 * @Description: 生成坐标图片(网络)
	 * @param @param entity
	 * @param @return
	 * @return BitmapDescriptor
	 * @throws
	 */
	protected BitmapDescriptor getBitmapDescript(Bitmap bitmap) {
		Bitmap mBitmap = convertViewToBitmap(addViewByXml(bitmap));
		return BitmapDescriptorFactory.fromBitmap(mBitmap);
	}

	@Override
	public void onDestroy() {
		// 回收 bitmap 资源
		if (baiNor != null) {
			baiNor.recycle();
			baiNor = null;
		}
		super.onDestroy();
	}
}
