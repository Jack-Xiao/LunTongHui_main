package com.louie.luntonghui.ui.register.wx;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.LoginEvent;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.Login;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.ui.register.RegisterPasswordHomeActivity;
import com.louie.luntonghui.ui.register.RegisterStep3Activity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.wxapi.WXEntryActivity;

import org.apache.http.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/11/5.
 */
public class WxLoginActivity extends BaseToolbarActivity {

    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.username)
    EditText username;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.login)
    Button btnLogin;
    @InjectView(R.id.retrive_password)
    TextView retrivePassword;

    private String openId;
    private String mac;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        if(getIntent().getExtras().getString(WXEntryActivity.OPEN_ID) == null){
            ToastUtil.showLongToast(mContext,"获取微信信息失败，请重试");
            return;
        }
        openId = getIntent().getExtras().getString(WXEntryActivity.OPEN_ID);

        type = getIntent().getExtras().getString(WxUniteActivity.TYPE);
        mac = Config.getMacAddress(this);

    }

    @Override
    protected int toolbarTitle() {
        return R.string.login_bind;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wx_login_bind;
    }

    @OnClick(R.id.login)
    public void onClickLogin(){
        String strUsername = username.getText().toString().trim();
        String strPassword = password.getText().toString().trim();

        try {
            strUsername = URLEncoder.encode(strUsername, "UTF-8");
            strPassword = URLEncoder.encode(strPassword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ToastUtil.showLongToast(this,"用户名、密码输入有误");
            return;
        }

        String url = ConstantURL.WX_OPENID_BIND_ACCOUNT;
        Map<String,String > param = new HashMap<>();
        param.put("openid",openId);
        param.put("mac",mac);
        param.put("account",strUsername);
        param.put("password", strPassword);
        if(type.equals(WxUniteActivity.TYPE_QQ)){
            param.put("type",type);
        }

        RequestManager.addRequest(new GsonRequest(
                Request.Method.POST,url, Login.class,param,bindLogin(),errorListener()),this);
    }

    @OnClick(R.id.retrive_password)
    public void onClickRetrivePassword(){
        IntentUtil.startActivity(WxLoginActivity.this, RegisterPasswordHomeActivity.class);
        finish();
    }

    public Response.Listener<Login> bindLogin() {
        return new Response.Listener<Login>() {
            @Override
            public void onResponse(Login login) {
                if (login.rsgcode.equals(SUCCESS)) {
                    User user;

                    String phoneNumber = login.mobile_phone;
                    new Delete()
                            .from(User.class)
                            .where("mobile_phone = ?", phoneNumber).execute();

                    User curuser = new User();
                    curuser.uid = login.userid;
                    curuser.username = login.user_name;
                    curuser.email = login.email;
                    curuser.isSupplier = login.gysa;
                    curuser.superiorSupplier = login.gys;
                    curuser.superiorSupplierInviteCode = login.yqm;
                    curuser.integral = login.jif;
                    curuser.mobilePhone = login.mobile_phone;
                    curuser.rankName = login.rank_name;
                    curuser.verification = login.verification;
                    curuser.wechatBd = login.wxch_bd;
                    curuser.regTime = login.reg_time;
                    curuser.place = login.display;
                    curuser.type = login.type;
                    curuser.save();

                    DefaultShared.putInt(RegisterLogin.LOGIN_IN, RegisterLogin.HASLOGIN);
                    DefaultShared.putString(RegisterLogin.USER_TYPE, login.type);
                    DefaultShared.putString(RegisterLogin.USERUID, login.userid);
                    DefaultShared.putLong(Config.LAST_SING_IN_TIME, Config.CLEAR_SIGN_IN);
                    DefaultShared.putString(User.IS_EMPLOYEE, login.personnel);
                    App.getBusInstance().post(new LoginEvent());
                    Bundle bundle = new Bundle();
                    bundle.putInt(RegisterStep3Activity.INIT_TYPE, 2);
                    IntentUtil.startActivity(WxLoginActivity.this, MainActivity.class, bundle);
                    WxLoginActivity.this.finish();

                }else{
                    ToastUtil.showLongToast(WxLoginActivity.this,login.rsgmsg);
                }
            }
        };
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse == null){
                    ToastUtil.showShortToast(mContext,R.string.network_connect_fail);
                    return;
                }
                switch (error.networkResponse.statusCode){
                    case HttpStatus.SC_NO_CONTENT:
                }

                ToastUtil.showLongToast(WxLoginActivity.this, error.getMessage());
            }
        };
    }

}
