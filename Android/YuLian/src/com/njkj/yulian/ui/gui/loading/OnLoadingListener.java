package com.njkj.yulian.ui.gui.loading;

/**
 * 加载状态
 */
public interface OnLoadingListener {
	void showSuccess();

	void showEmpty();

	boolean checkNet();

	void showFaild();

	void showNoNet();
}
