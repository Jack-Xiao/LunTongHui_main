package com.louie.luntonghui.ui.Home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.ui.search.SearchActivity;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;

/**
 * Created by Jack on 15/11/19.
 */
public class WebActivity extends AppCompatActivity {
    private String url;
    public static String WEB_URL = "web_url";
    private String userId;
    private WebView browser;
    private static final String WEB_VIEW_OBJECT = "android_object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_togethergroup);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString(WEB_URL);
        }
        userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        browser = (WebView) findViewById(R.id.web_view);

        initWeb();
    }

    private void initWeb() {
        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);
        browser.addJavascriptInterface(new BrandStreet(this), WEB_VIEW_OBJECT);

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

    final public class BrandStreet {
        private Context mContext;

        public BrandStreet(WebActivity mActivity) {
            this.mContext = mActivity;
        }

        @JavascriptInterface
        public void jump(String searchContent) {
            Bundle bundle = new Bundle();
            bundle.putString(SearchActivity.SEARCH_CONTENT, searchContent);
            IntentUtil.startActivity(WebActivity.this, GoodsDetailActivity.class, bundle);
        }

        @JavascriptInterface
        public void group() {
            WebActivity.this.finish();
        }

        @JavascriptInterface
        public void tel() {
            try {
                String servicePhone = getResources().getString(R.string.service_phone);

                servicePhone = servicePhone.replace("-", "");
                Uri uri = Uri.parse("tel:" + servicePhone);
                Intent call = new Intent(Intent.ACTION_CALL, uri);
                startActivity(call);
            } catch (Exception e) {
                //存在双卡的问题 等待android 5.1 修复.
                //ToastUtil.showShortToast(mContext,);
            }
        }
    }
}