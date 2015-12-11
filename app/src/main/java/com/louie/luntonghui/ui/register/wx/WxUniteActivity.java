package com.louie.luntonghui.ui.register.wx;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.result.WXUserInfo;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.wxapi.WXEntryActivity;
import com.squareup.picasso.Picasso;
import com.tencent.tauth.Tencent;

import org.apache.http.HttpStatus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jack on 15/11/5.
 */
public class WxUniteActivity extends BaseToolbarActivity {
    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.unit_user_image)
    CircleImageView unitUserImage;
    @InjectView(R.id.unit_welcome)
    TextView unitWelcome;
    @InjectView(R.id.unit_username)
    TextView unitUsername;
    @InjectView(R.id.quite_register)
    Button quiteRegister;
    @InjectView(R.id.immediate)
    Button immediate;
    private String openId;
    private String accessToken;
    public static final String TYPE = "unit_login_type";
    public static final String TYPE_WX = "wechat";
    public static final String TYPE_QQ ="qq";
    private String type;
    private Tencent mTencent;
    public static final String QQ_HEADER_LOGO_URL = "qq_header_logo_url";
    public static final String QQ_NICK_NAME = "qq_nick_name";
    private String qqNickname;
    private String qqHeaderLogoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        type = getIntent().getExtras().getString(TYPE);
        if(type.equals(TYPE_WX)) {
            openId = getIntent().getExtras().getString(WXEntryActivity.OPEN_ID);
            accessToken = getIntent().getExtras().getString(WXEntryActivity.ACCESS_TOKEN);
        }else{
            openId = getIntent().getExtras().getString(WXEntryActivity.OPEN_ID);
            qqNickname = getIntent().getExtras().getString(QQ_NICK_NAME);
            qqHeaderLogoUrl = getIntent().getExtras().getString(QQ_HEADER_LOGO_URL);
        }
        mTencent = Tencent.createInstance(Config.QQ_APPID, this.getApplicationContext());

        initNet();
    }

    private void initNet() {
        if(type.equals(TYPE_WX)) {
            String url = String.format(ConstantURL.WX_USER_INFO, accessToken, openId);
            executeRequest(new GsonRequest(url, WXUserInfo.class, getUserInfo(), errorListener()));
        }else{

            unitWelcome.setText("您好,QQ用户:");
            unitUsername.setText(qqNickname);
            Picasso.with(this)
                    .load(qqHeaderLogoUrl)
                    .into(unitUserImage);
        }
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
                    return;
                }

                switch (error.networkResponse.statusCode) {
                    case HttpStatus.SC_NO_CONTENT:
                }
                ToastUtil.showLongToast(WxUniteActivity.this, error.getMessage());
            }
        };
    }

    public Response.Listener<WXUserInfo> getUserInfo() {
        return new Response.Listener<WXUserInfo>() {
            @Override
            public void onResponse(WXUserInfo response) {

                 unitWelcome.setText("您好,微信用户:");

                unitUsername.setText(response.nickname);

                Picasso.with(WxUniteActivity.this)
                        .load(response.headimgurl)
                        .into(unitUserImage);
            }
        };
    }

    @OnClick(R.id.quite_register)
    public void onClickQuiteRegister() {
        Bundle bundle = new Bundle();
        bundle.putString(WXEntryActivity.OPEN_ID, openId);
        bundle.putString(TYPE,type);
        IntentUtil.startActivity(WxUniteActivity.this, FillInfoActivity.class, bundle);
        finish();
    }

    @OnClick(R.id.immediate)
    public void onClickImmediate() {
        Bundle bundle = new Bundle();
        bundle.putString(WXEntryActivity.OPEN_ID, openId);
        bundle.putString(TYPE,type);

        IntentUtil.startActivity(WxUniteActivity.this, WxLoginActivity.class, bundle);
        finish();
    }

    @Override
    protected int toolbarTitle() {
        return R.string.unit_login;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wx_unit_login;
    }
}