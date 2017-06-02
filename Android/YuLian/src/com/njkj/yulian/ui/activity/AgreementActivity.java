package com.njkj.yulian.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.njkj.yulian.R;

/**
 * ==============================
 * 
 * @author Qiu
 * 
 * @Package com.njkj.yulian.ui.activity
 * 
 * @Description:用户协议
 * 
 * @date 2016-6-28 上午10:34:54
 * 
 * @version 1.0 ==============================
 */
public class AgreementActivity extends BaseActivity {
	WebView mwebview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agreement);
		setHeaderTitle(R.string.agreement);
		initView();
		initData();

	}

	private void initView() {
		mwebview = (WebView) findViewById(R.id.mwebview);
	}

	private void initData() {
		String url = getString(R.string.FsAgreement);
		// 代码加载
		WebSettings webSettings = mwebview.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(false);
		// 加载需要显示的网页
		mwebview.loadUrl(url);
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		mwebview.setWebViewClient(new webViewClient());
	}

	// Web视图
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	// 设置回退
	// 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mwebview.canGoBack()) {
			mwebview.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		if (mwebview != null) {
			mwebview = null;
		}
		finish();// 结束退出程序
		return false;
	}
}
