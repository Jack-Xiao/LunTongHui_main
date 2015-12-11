package com.louie.luntonghui.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.event.WxChatPay;
import com.louie.luntonghui.ui.Home.WebTogetherGroupActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.IntentUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Jack on 15/11/13.
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final String TAG= "WXPayEntryActivity";
    private IWXAPI api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        App.getBusInstance().register(this);
        api = WXAPIFactory.createWXAPI(this, Config.WX_APPID);
        api.handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        App.getBusInstance().post(new WxChatPay(baseResp.errCode));
        IntentUtil.startActivity(WXPayEntryActivity.this, WebTogetherGroupActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }
}
