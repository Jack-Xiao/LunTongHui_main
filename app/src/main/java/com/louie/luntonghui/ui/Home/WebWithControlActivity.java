package com.louie.luntonghui.ui.Home;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;

/**
 * Created by Jack on 15/12/8.
 */
public class WebWithControlActivity extends AppCompatActivity {
    public static final String TITLE = "title";
    public static final String URL = "url";
    private String userId;
    private WebView browser;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview_control);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString(TITLE);
        url = bundle.getString(URL);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //给左上角图标的左边加上一个返回的图标
            actionBar.setTitle(title);
            //actionBar.setDisplayShowHomeEnable(true); //使左上角图标是否显示,显示应用程序图标，对应id 为 android.R.id.home
            //actionBar.setDisplayShowCustomEnabled(true); //使用自定义的普通View能在title栏显示,即setCustomView能起作用
        }
        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        browser =(WebView)findViewById(R.id.web_view);
        initWeb();
    }

    private void initWeb() {
        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);
        //browser.addJavascriptInterface(new BrandStreet(this), WEB_VIEW_OBJECT);

        browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //不使用缓存

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
                super.onProgressChanged(view, newProgress);
            }
        });

        browser.loadUrl(url);
    }
}
