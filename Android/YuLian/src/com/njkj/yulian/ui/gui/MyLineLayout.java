package com.njkj.yulian.ui.gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/11/20.
 */
public class MyLineLayout extends LinearLayout {
	OnMeasureListener onMeasureListener;
	int maxHeight = 0;
	int oldHeight;

	public MyLineLayout(Context context) {
		super(context);
	}

	public MyLineLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@SuppressLint("WrongCall")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = MeasureSpec.getSize(heightMeasureSpec);
		if (onMeasureListener != null) {
			onMeasureListener.onMeasure(maxHeight, oldHeight, height);
		}
		oldHeight = height;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 之所以，在这里记录 maxHeight的大小，是因为 onMeasure 中可能多次调用，中间可能会逐步出现
		// ActionBar，BottomVirtualKeyboard，
		// 所以 onMeasure中获取的maxHeight存在误差
		if (h > maxHeight) {
			maxHeight = h;
		}
		Log.d("SC_SIZE", String.format("Size Change %d %d", h, oldh));
	}

	interface OnMeasureListener {
		void onMeasure(int maxHeight, int oldHeight, int nowHeight);
	}
}