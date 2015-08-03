package com.louie.luntonghui.ui.register;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.RegisterSuccessEvent;
import com.louie.luntonghui.model.result.CheckCode;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.squareup.otto.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/7/2.
 */
public class RegisterStep2Activity extends BaseNormalActivity {

    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.verifitation_code)
    EditText verifitation_code;
    @InjectView(R.id.get_verifitation_code)
    Button getVerifitationCode;
    @InjectView(R.id.next_step)
    Button nextStep;
    public static final long COUNTDOWNIMERINTERVAL = 1000;
    public static final long COUNTDOWNTIMERTOTAL = 45 * COUNTDOWNIMERINTERVAL;
    @InjectView(R.id.tv_get_verifitation_code)
    TextView tvGetVerifitationCode;

    public String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step2);
        ButterKnife.inject(this);
        App.getBusInstance().register(this);
        //strCheckCode = DefaultShared.getString(RegisterStep1Activity.CHECK_CODE,"");
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            strCheckCode = bundle.getString(RegisterStep1Activity.CHECK_CODE);
            phoneNumber = bundle.getString(RegisterStep1Activity.PHONE_NUMBER);
        }


        init();
        mCountDownTime.start();
    }

    private void init() {
        toolbarTitle.setText(R.string.luntonghui_register);
        getVerifitationCode.setText(R.string.get_check_code);

        //phoneNumber = DefaultShared.getLong(RegisterStep1Activity.PHONE_NUMBER,1L);

        //tvGetVerifitationCode.setText("验证码验证");
        tvGetVerifitationCode.setText("已发送验证码到:" + phoneNumber);
    }

    private CountDownTimer mCountDownTime = new CountDownTimer(COUNTDOWNTIMERTOTAL, COUNTDOWNIMERINTERVAL) {

        @Override
        public void onTick(long millisUntilFinished) {
            long sencond = millisUntilFinished / COUNTDOWNIMERINTERVAL;
            getVerifitationCode.setEnabled(false);
            getVerifitationCode.setText("重新获取" + " " + String.valueOf(sencond));
        }

        @Override
        public void onFinish() {
            getVerifitationCode.setEnabled(true);
            getVerifitationCode.setText(R.string.get_check_code);
            tvGetVerifitationCode.setText("验证码验证");
        }
    };


    @OnClick(R.id.get_verifitation_code)
    public void getVerifitationCode(View view) {

            mCountDownTime.start();
            String url = String.format(ConstantURL.CHECKCODEURL, phoneNumber);
            executeRequest(new GsonRequest(url, CheckCode.class, responseListener(), errorListener()));
    }

    private String strCheckCode;
    private Response.Listener<CheckCode> responseListener() {
        return new Response.Listener<CheckCode>() {

            @Override
            public void onResponse(CheckCode checkCode) {
                strCheckCode = checkCode.rsgcheck;
                //verifitation_code.setText(checkCode.rsgcheck);
                tvGetVerifitationCode.setText("已发送验证码到:" + phoneNumber);
            }
        };
    }

    @OnClick(R.id.next_step)
    public void onNextStep(){
        if(verifitation_code.getText() ==null){
            ToastUtil.showShortToast(mContext,"请输入验证码");
            return;
        }
        String verifitationCode = verifitation_code.getText().toString();
        if (!verifitation_code.getText().toString().equals(strCheckCode)){
            ToastUtil.showShortToast(mContext, "请填写正确验证码");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(RegisterStep1Activity.PHONE_NUMBER,phoneNumber);
        IntentUtil.startActivity(RegisterStep2Activity.this, RegisterStep3Activity.class,bundle);
        finish();
    }


    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void registerSuccess(RegisterSuccessEvent event){
        finish();
    }
}

