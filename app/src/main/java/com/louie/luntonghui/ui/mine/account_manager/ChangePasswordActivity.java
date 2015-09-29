package com.louie.luntonghui.ui.mine.account_manager;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Jack on 15/9/24.
 */
public class ChangePasswordActivity extends BaseToolbarActivity {
    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.old_password)
    EditText oldPassword;
    @InjectView(R.id.old_password_holder)
    TextInputLayout oldPasswordHolder;
    @InjectView(R.id.new_password)
    EditText newPassword;
    @InjectView(R.id.new_password_holder)
    TextInputLayout newPasswordHolder;
    @InjectView(R.id.confirm_new_password)
    EditText confirmNewPassword;
    @InjectView(R.id.confirm_new_password_holder)
    TextInputLayout confirmNewPasswordHolder;

    private String uId;

    private String strOldPassword;
    private String strNewPassword;
    @Override
    protected int toolbarTitle() {
        return R.string.change_password;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_mine_change_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);

        uId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
    }

    @OnClick(R.id.completed)
    public void onClickCompleted(){
        String strPassword = newPassword.getText().toString();

        if(strPassword.length()<6){
            newPasswordHolder.setErrorEnabled(true);
            newPasswordHolder.setError("请输入6位以上密码");
            return;
        }else{
            newPasswordHolder.setErrorEnabled(false);
        }

        String strPasswordVer = confirmNewPassword.getText().toString();

        if(!strPassword.equals(strPasswordVer)){
            confirmNewPasswordHolder.setErrorEnabled(true);
            confirmNewPasswordHolder.setError("两次密码输入不一致");
            return;
        }else{
            confirmNewPasswordHolder.setErrorEnabled(false);
        }
        strOldPassword = oldPassword.getText().toString();
        strNewPassword = newPassword.getText().toString();

        execChangePassword();
    }

    private void execChangePassword() {
        AppObservable.bindActivity(this, mApi.changeNewPassword(userId, strOldPassword,strNewPassword))

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<Result> observer = new Observer<Result>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
        }

        @Override
        public void onNext(Result result) {
            if(result.rsgcode.equals(Result.SUCCESS)){
                finish();
            }
            ToastUtil.showShortToast(mContext,result.rsgmsg);
        }
    };


}
