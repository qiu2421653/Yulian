//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.njkj.yulian.ui.gui.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SwipeLoadMoreFooterLayout extends FrameLayout implements
		SwipeLoadMoreTrigger, SwipeTrigger {
	public SwipeLoadMoreFooterLayout(Context context) {
		this(context, (AttributeSet) null);
	}

	public SwipeLoadMoreFooterLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeLoadMoreFooterLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void onLoadMore() {
	}

	public void onPrepare() {
	}

	public void onSwipe(int y, boolean isComplete) {
	}

	public void onRelease() {
	}

	public void complete() {
	}

	public void onReset() {
	}
}
