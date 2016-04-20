package com.louie.luntonghui.ui.register.wx;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.activeandroid.query.Delete;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.LoginEvent;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.Login;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/11/5.
 */
public class FillInfoActivity extends BaseToolbarActivity {

    @InjectView(R.id.checkbox)
    CheckBox checkbox;

    @InjectView(R.id.complete)
    Button btnComplete;
    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.username)
    EditText username;
    @InjectView(R.id.phone)
    EditText phone;
    @InjectView(R.id.login)
    Button login;

    private String openId;
    private String mac;
    private Pattern mPattern;
    private Matcher mMatcher;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        mac = Config.getMacAddress(this);
        openId = getIntent().getExtras().getString(WXEntryActivity.OPEN_ID);
        type = getIntent().getExtras().getString(WxUniteActivity.TYPE);

        initView();

    }

    private void initView() {
        checkbox.setChecked(true);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnComplete.setEnabled(isChecked);
            }
        });
    }

    @Override
    protected int toolbarTitle() {
        return R.string.quit_register;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_fill_info;
    }

    @OnClick(R.id.complete)
    public void onClickComplete() {
        String strUsername = username.getText().toString().trim();
        String strPhone = phone.getText().toString().trim();

        mPattern = Pattern.compile("^\\d{11}$");
        mMatcher = mPattern.matcher(strPhone);

        if (!mMatcher.find()) {
            ToastUtil.showLongToast(this, R.string.info_input_phone_number);
            return;
        }

        try {
            strUsername = URLEncoder.encode(strUsername, "UTF-8");
            strPhone = URLEncoder.encode(strPhone, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ToastUtil.showLongToast(this, "用户名、密码输入有误");
            return;
        }

        String url = String.format(ConstantURL.WX_OPENID_REGISTER,openId,mac,strPhone,strUsername);
        if(type.equals(WxUniteActivity.TYPE_QQ)){
            url = url + "&type=" + type;
        }
        executeRequest(new GsonRequest(url,
                Login.class, registerListener(), errorListener()));
    }


    public Response.Listener<Login> registerListener(){
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
                    IntentUtil.startActivity(FillInfoActivity.this, MainActivity.class, bundle);
                    FillInfoActivity.this.finish();

                }else{
                    ToastUtil.showLongToast(FillInfoActivity.this,login.rsgmsg);
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
                ToastUtil.showLongToast(FillInfoActivity.this, error.getMessage());
            }
        };
    }

    @OnClick(R.id.login)
    public void onClickLogin(){
        Bundle bundle = new Bundle();
        bundle.putString(WXEntryActivity.OPEN_ID, openId);
        bundle.putString(WxUniteActivity.TYPE,type);

        IntentUtil.startActivity(FillInfoActivity.this,WxLoginActivity.class,bundle);
        finish();
    }
}
