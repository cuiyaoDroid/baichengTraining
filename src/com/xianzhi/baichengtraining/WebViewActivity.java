package com.xianzhi.baichengtraining;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
//import android.webkit.ClientCertRequestHandler;
//import android.webkit.WebViewClientClassicExt;

public class WebViewActivity extends BaseTitleBarActivity {


	private WebView webView;

	private class MyWebViewClient extends WebViewClient {

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			System.out.println("shouldOverrideUrlLoading");
			return true;
		}

		public void onPageFinished(WebView view, String url) {
			System.out.println("onPageFinished");
		}

		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			System.out.println("onReceivedError");
		}

		@Override
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {
			// TODO Auto-generated method stub
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
		}

		@Override
		public void onReceivedSslError(final WebView view,
				SslErrorHandler handler, SslError error) {
			Log.i("WebViewActivity", "onReceivedSslError");
			handler.proceed();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@SuppressLint("SetJavaScriptEnabled")
	protected void initContentView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_web);
		webView = (WebView) this.findViewById(R.id.wv_oauth);
		webView.getSettings().setJavaScriptEnabled(true);
		MyWebViewClient myWebView = new MyWebViewClient();
		webView.setWebViewClient(myWebView);

		webView.setDownloadListener(new DownloadListener() {
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				// TODO Auto-generated method stub
			}
		});

		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.setVisibility(View.VISIBLE);
		webView.loadUrl("http://115.28.4.107:8280/html/education_threenew.html");
	}

	@Override
	protected void initContentData() {
		// TODO Auto-generated method stub
		
	}

}