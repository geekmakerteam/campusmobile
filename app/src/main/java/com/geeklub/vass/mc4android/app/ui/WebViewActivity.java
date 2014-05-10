package com.geeklub.vass.mc4android.app.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.utils.KFSLog;
import com.geeklub.vass.mc4android.app.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @author Administrator
 *
 */
public class WebViewActivity extends Activity{

	private WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
		webview = (WebView) findViewById(R.id.webview);
		//设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		//加载需要显示的网页

		Log.e("-------webURL-------", getIntent().getStringExtra("urladdress"));
		Log.e("-------webURL-------",getIntent().getStringExtra("urladdress"));
		Log.e("-------webURL-------",getIntent().getStringExtra("urladdress"));
		Log.e("-------webURL-------",getIntent().getStringExtra("urladdress"));



		webview.loadUrl(getIntent().getStringExtra("urladdress"));
		//设置Web视图
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				setTitle("页面加载中 ... " + progress + "%");
				setProgress(progress * 100);
				if (progress == 100) {
					setTitle(R.string.app_name);
				}
			}
		});

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//设置点击网页里面的链接还是在当前的webview里跳转
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onReceivedSslError(WebView view,
			                               SslErrorHandler handler, android.net.http.SslError error) {
				//设置webview处理https请求
				handler.proceed();
			}
			public void onReceivedError(WebView view,
			                            int errorCode, String description, String failingUrl) {
				//加载页面报错时的处理
				Toast.makeText(WebViewActivity.this,
						"Oh sorry! " + description, Toast.LENGTH_SHORT).show();
			}
		});

	}
/*

	//Web视图
	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
*/


	@Override
	//设置回退
	//覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			//webview.goBack(); //goBack()表示返回WebView的上一页面
			finish();
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		if ( webview.canGoBack()) {
			//webview.goBack(); //goBack()表示返回WebView的上一页面
		    finish();
		    return ;
		}
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}



























