package com.njkj.yulian.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.njkj.yulian.R;

public class PhoneDetail  extends Activity{
	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonedetail);
		webView=(WebView) findViewById(R.id.webview);
		// 启用支持javascript
				WebSettings settings = webView.getSettings();
				settings.setJavaScriptEnabled(true);
				// WebView加载web资源
				webView.loadUrl("file:///android_asset/goodetail.html");
				//判断webvew加载的状态
				webView.setWebChromeClient(new WebChromeClient() {
		            @Override
		            public void onProgressChanged(WebView view, int newProgress) {
		                // TODO Auto-generated method stub
		                if (newProgress == 100) {
		                    // 网页加载完成
		                } else {
		                    // 加载中

		                }

		            }
		        });
				// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
				webView.setWebViewClient(new WebViewClient() {
					@Override
					public boolean shouldOverrideUrlLoading(WebView view, String url) {
						// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
						view.loadUrl(url);
						return true;
					}
				});
			}
}

