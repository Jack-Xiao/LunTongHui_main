package com.louie.luntonghui.ui.extra;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.BaseToolbarActivity;

/**
 * Created by Jack on 15/9/2.
 * setWebClient：主要处理解析，渲染网页等浏览器做的事情
   setWebChromeClient：辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
   WebViewClient就是帮助WebView处理各种通知、请求事件的。
 *
 *
 */
public class WebViewActivity extends BaseToolbarActivity {
    public static final String URL = "url";
    private String url;
    private WebView browser;
    private ProgressBar progressBar;
    public static final String HTTP_PREFIX = "http://";
    public static final int MAX = 100;
    @Override
    protected int toolbarTitle() {
        return R.string.activity_detail;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        browser = (WebView)findViewById(R.id.web_view);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setMax(MAX);

        Bundle bundle = getIntent().getExtras();
        url = bundle.getString(URL);

        if(!url.startsWith("http://")){
            url = HTTP_PREFIX + url;
        }

        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);


        browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //缓存
        //browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //不使用缓存

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        browser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE==progressBar.getVisibility()){
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        browser.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(browser.canGoBack()){
                browser.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
