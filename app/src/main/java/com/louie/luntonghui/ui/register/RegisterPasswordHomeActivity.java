package com.louie.luntonghui.ui.register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.register.retrivepassword.RetrivePassword1Activity;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.IntentUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/9/21.
 */
public class RegisterPasswordHomeActivity extends BaseToolbarActivity
                implements BaseAlertDialogUtil.BaseAlertDialogListener {
    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.retrive_by_note)
    LinearLayout retriveByNote;
    @InjectView(R.id.retrive_by_call)
    LinearLayout retriveByCall;

    private String servicePhone;
    @Override
    protected int toolbarTitle() {
        return R.string.retrive_password;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.retrive_password_home_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this);
        servicePhone = getResources().getString(R.string.service_phone);
    }

    @OnClick(R.id.retrive_by_call)
    public void onRetriveByCall(){
        BaseAlertDialogUtil.getInstance()
                .setMessage("拔打客服电话: " + servicePhone)
                .setCanceledOnTouchOutside(true)
                .setNegativeContent(R.string.cancel)
                .setPositiveContent(R.string.confirm);

        BaseAlertDialogUtil.getInstance().show(mContext, RegisterPasswordHomeActivity.this);
    }

    @OnClick(R.id.retrive_by_note)
    public void onRetiveByNote(){
        IntentUtil.startActivity(RegisterPasswordHomeActivity.this,RetrivePassword1Activity.class);
        finish();
    }

    @Override
    public void confirm() {
        try {
            servicePhone = servicePhone.replace("-", "");
            Uri uri = Uri.parse("tel:" + servicePhone);
            Intent call = new Intent(Intent.ACTION_CALL, uri);
            startActivity(call);
        } catch (Exception e) {
            //存在双卡的问题 等待android 5.1 修复.
            //ToastUtil.showShortToast(mContext,);
        }
    }
}
