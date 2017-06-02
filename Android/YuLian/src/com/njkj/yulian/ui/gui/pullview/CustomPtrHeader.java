package com.njkj.yulian.ui.gui.pullview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.njkj.yulian.MainApplication;
import com.njkj.yulian.R;
import com.njkj.yulian.ui.gui.swipetoloadlayout.SwipeRefreshHeaderLayout;
import com.njkj.yulian.ui.gui.titanic.Titanic;
import com.njkj.yulian.ui.gui.titanic.TitanicTextView;
import com.njkj.yulian.utils.TypefacesUtils;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.gui
 * 
 * @Description:下拉头部
 * 
 * @date 2016-3-31 下午3:22:30
 * 
 * @version 1.0 ==============================
 */
public class CustomPtrHeader extends SwipeRefreshHeaderLayout {

	private TitanicTextView tv;
	private Titanic titanic;

	public CustomPtrHeader(Context context) {
		super(context);
		init();
	}

	public CustomPtrHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomPtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.activity_titanic, this);
		tv = (TitanicTextView) findViewById(R.id.my_text_view);
		tv.setTypeface(TypefacesUtils.get(MainApplication.getContext(),
				"self.ttf"));
		titanic = new Titanic();
	}

	public void start() {
		titanic.start(tv);
	}

	public void cancle() {
		titanic.cancel();
	}

	@Override
	public void onRefresh() {
		start();
		super.onRefresh();
	}

	@Override
	public void onRelease() {
		super.onRelease();
	}

	@Override
	public void complete() {

		super.complete();
	}

	@Override
	public void onReset() {
		cancle();
		super.onReset();
	}

}