package com.louie.luntonghui.ui.register.retrivepassword;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.model.result.RetrivePasswordResult;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.checkcode.CheckView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Jack on 15/8/20.
 */
public class RetrivePassword1Activity extends BaseToolbarActivity {
    public static final String USERNAME = "user_name";
    public static final String PHONE_NUMBER = "phone_number";

    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.phone)
    EditText phone;
    @InjectView(R.id.input_check_code)
    EditText inputCheckCode;
    private String[] res = new String[4];


    @InjectView(R.id.check_code)
    CheckView checkCode;

    @InjectView(R.id.change_code)
    Button changeCode;

    @InjectView(R.id.next_step)
    Button nextStep;
    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this);
        stringBuilder = new StringBuilder();

        res = checkCode.getValidateAndSetImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected int toolbarTitle() {
        return R.string.retrive_password;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register_retrive1;
    }

    @OnClick(R.id.next_step)
    public void onClickNextStep() {
        String strPhone = phone.getText().toString();

        if (TextUtils.isEmpty(strPhone)){
            return;
        }

        if (strPhone.length() != 11) {
            ToastUtil.showShortToast(mContext, R.string.phone_number_error);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            stringBuilder.append(res[i].toString());
        }
        String strInputCode = inputCheckCode.getText().toString();
        String strCheckCode = stringBuilder.toString();
        if (!strCheckCode.equalsIgnoreCase(strInputCode)){
            ToastUtil.showShortToast(mContext,R.string.check_code_error);
            inputCheckCode.setText("");
            return;
        }

        AppObservable.bindActivity(this, mApi.findTheUserPwd(strPhone))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<RetrivePasswordResult> observer = new Observer<RetrivePasswordResult>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {

            ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
        }

        @Override
        public void onNext(RetrivePasswordResult result) {

            if (result.rsgcode.equals(Result.SUCCESS)) {
                //DefaultShared.putString(RegisterLogin.USERUID,result.uid);
                Bundle bundle = new Bundle();
                bundle.putString(USERNAME, result.name);
                bundle.putString(PHONE_NUMBER, result.phone);
                bundle.putString(RegisterLogin.USERUID,result.uid);
                IntentUtil.startActivity(RetrivePassword1Activity.this, RetrivePassword2Activity.class, bundle);
                finish();
            }else{
                ToastUtil.showShortToast(mContext,result.rsgmsg);
            }
        }
    };

    @OnClick(R.id.change_code)
    public void onClickChangeCode() {
        res = checkCode.getValidateAndSetImage();

    }
}
