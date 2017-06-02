package com.njkj.yulian.core.callback;

import com.njkj.yulian.ui.gui.PullToRefreshLayout;

/**
 * 刷新加载回调接口
 * 
 * @author chenjing
 * 
 */
public interface OnRefresh {
	/**
	 * 刷新操作
	 */
	void onRefresh(PullToRefreshLayout pullToRefreshLayout);

	/**
	 * 加载操作
	 */
	void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
}