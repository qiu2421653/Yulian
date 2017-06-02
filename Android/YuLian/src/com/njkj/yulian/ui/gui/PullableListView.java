package com.njkj.yulian.ui.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.njkj.yulian.core.callback.Pullable;

/**
 * 自定义ListView支持上拉刷新
 * 
 * @author Qiu
 * 
 */
public class PullableListView extends ListView implements Pullable {

	public PullableListView(Context context) {
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		return false;
	}

	@Override
	public boolean canPullUp() {
		if (getCount() == 0 || getCount() < 10) {
			// 没有item的时候不可以上拉加载
			return false;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			// 滑到底部了
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}
}
