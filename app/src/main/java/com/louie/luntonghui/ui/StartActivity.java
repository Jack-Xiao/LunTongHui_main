package com.louie.luntonghui.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.result.VersionUpdate;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.task.UpdateVersionTask;
import com.louie.luntonghui.ui.register.RegisterHome;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.ui.register.RegisterStep1Activity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyAlertDialogUtil;
import com.louie.luntonghui.view.MyCategoryListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

import static com.louie.luntonghui.ui.BaseNormalActivity.SUCCESSCODE;
import static com.louie.luntonghui.ui.BaseNormalActivity.SUCCESSCODE1;

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
        IntentUtil.startActivity(StartActivity.this, RegisterStep1Activity.class);
        //finish();
    }
}