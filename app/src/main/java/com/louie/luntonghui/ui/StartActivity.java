package com.louie.luntonghui.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.ui.register.RegisterNewActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/7/1.
 */
public class StartActivity extends BaseNormalActivity{
    @InjectView(R.id.login)
    Button login;
    @InjectView(R.id.register)
    Button register;
    private Handler handler = new Handler();
    public String DEFAULT_USERID = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.inject(this);

         mContext = this;

        MobclickAgent.updateOnlineConfig(mContext);

        String devInfo = Config.getDeviceInfo(mContext);

        String userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.USER_DEFAULT);

        if(userId.equals(DEFAULT_USERID)){
            IntentUtil.startActivityToMainActivity(StartActivity.this, RegisterLogin.class);

        }else{
            IntentUtil.startActivityToMainActivity(StartActivity.this, MainActivity.class);
            finish();
        }
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },100);*/
    }

    @OnClick(R.id.login)
    public void login(){
        login.setTextColor(getResources().getColor(R.color.useful_grey));
        login.setBackgroundResource(R.drawable.start_login);
        IntentUtil.startActivity(StartActivity.this, RegisterLogin.class);
        //finish();
    }

    @OnClick(R.id.register)
    public void register(){
        register.setTextColor(getResources().getColor(R.color.useful_grey));
        register.setBackgroundResource(R.drawable.start_login);
        //IntentUtil.startActivity(StartActivity.this, RegisterStep1Activity.class);
        IntentUtil.startActivity(StartActivity.this, RegisterNewActivity.class);
        //finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        //JPushInterface.onResume(this);

    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
       // JPushInterface.onPause(this);

    }

}