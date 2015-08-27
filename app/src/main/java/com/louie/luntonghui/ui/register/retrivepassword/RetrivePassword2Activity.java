package com.louie.luntonghui.ui.register.retrivepassword;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.CheckCode;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Jack on 15/8/20.
 */
public class RetrivePassword2Activity extends BaseToolbarActivity {

    public static final long COUNTDOWNIMERINTERVAL = 1000;
    public static final long COUNTDOWNTIMERTOTAL = 45 * COUNTDOWNIMERINTERVAL;
    public String uid;

    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @InjectView(R.id.tv_get_verifitation_code)
    TextView tvGetVerifitationCode;
    @InjectView(R.id.verifitation_code)
    EditText verifitationCode;
    @InjectView(R.id.get_verifitation_code)
    Button getVerifitationCode;
    @InjectView(R.id.next_step)
    Button nextStep;

    private String strUsername;
    private String phone;
    private String checkCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        initBundle();
        loadData();
    }

    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        strUsername = bundle.getString(RetrivePassword1Activity.USERNAME);
        phone = bundle.getString(RetrivePassword1Activity.PHONE_NUMBER);
        uid = bundle.getString(RegisterLogin.USERUID,RegisterLogin.DEFAULT_USER_ID);
    }

    private void loadData() {
         AppObservable.bindActivity(RetrivePassword2Activity.this,mApi.changepwdmsg(phone))
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(observer);
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
    public void onClickGetVerifitationCode(){
        loadData();
    }


    Observer<CheckCode> observer = new Observer<CheckCode>() {
        @Override
        public void onCompleted() {
            //finish();
            mCountDownTime.start();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
        }

        @Override
        public void onNext(CheckCode result) {
            if(result.rsgcode.equals(Result.SUCCESS)){
                checkCode = result.rsgcheck;
                tvGetVerifitationCode.setText("已发送验证码到: " + phone);

            }else{
                ToastUtil.showShortToast(mContext,result.rsgmsg);
            }
        }
    };

    @Override
    protected int toolbarTitle() {
        return R.string.retrive_password;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register_retrive2;
    }

    @OnClick(R.id.next_step)
    public void onClickNextStep(){
        String code  = verifitationCode.getText().toString();
        if(!code.equals(checkCode)){
            ToastUtil.showShortToast(mContext,R.string.input_true_check_code);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(RegisterLogin.USERUID,uid);

        IntentUtil.startActivity(RetrivePassword2Activity.this,RetrivePassword3Activity.class,bundle);
        finish();
    }
}
