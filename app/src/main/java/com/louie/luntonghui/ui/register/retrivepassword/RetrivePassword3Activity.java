package com.louie.luntonghui.ui.register.retrivepassword;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
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
public class RetrivePassword3Activity extends BaseToolbarActivity {

    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.reset_password)
    EditText resetPassword;
    @InjectView(R.id.reset_password_confirm)
    EditText resetPasswordConfirm;

    @InjectView(R.id.next_step)
    Button nextStep;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            uid = bundle.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        }
    }

    @Override
    protected int toolbarTitle() {
        return R.string.retrive_password;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register_retrive3;
    }

    @OnClick(R.id.next_step)
    public void onClickFinish(){
        String strPassword = resetPassword.getText().toString().trim();
        String strConfirmPassword = resetPasswordConfirm.getText().toString().trim();

        if(strPassword.length() < MIN_PASSWORD_LENGTH){
            ToastUtil.showShortToast(mContext,R.string.short_password_warning);
        }

        if(!strPassword.equals(strConfirmPassword)){
            ToastUtil.showShortToast(mContext,R.string.confirm_password_error);
            return;
        }

        AppObservable.bindActivity(RetrivePassword3Activity.this,mApi.changePwd(uid,strPassword))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<Result> observer = new Observer<Result>() {
        @Override
        public void onCompleted() {
            //finish();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
        }

        @Override
        public void onNext(Result result) {
            ToastUtil.showShortToast(mContext,result.rsgmsg);
            if(result.rsgcode.equals(Result.SUCCESS)){
               finish();
            }
        }
    };

}
