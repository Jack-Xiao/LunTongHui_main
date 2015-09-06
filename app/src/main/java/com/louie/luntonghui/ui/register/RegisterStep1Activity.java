package com.louie.luntonghui.ui.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2015/7/1.
 */
public class RegisterStep1Activity extends BaseNormalActivity implements View.OnClickListener {
    public static final String PHONE_NUMBER = "phone_number";
    public static final String CHECK_CODE = "check_code";
    public static final String REGISTER_COUNT = "register_count";
    public static final String REGISTER_TIME = "register_time";
    public static final int INITTIME = 1;
    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.phone_number)
    EditText phoneNumber;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.next_step)
    Button nextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);
        ButterKnife.inject(this);
        App.getBusInstance().register(this);

        initView();

    }

    private void initRegisterTime() {

        if (Config.needClearRegisterTime()) {
            DefaultShared.putInt(REGISTER_COUNT, INITTIME);
        }
    }


    private void initView() {
        toolbarTitle.setText(R.string.luntonghui_register);
        checkbox.setChecked(true);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nextStep.setEnabled(isChecked);
            }
        });
    }

    private Pattern mPattern;
    private Matcher mMatcher;

    @OnClick(R.id.next_step)
    public void OnClick(View v) {

        mPattern = Pattern.compile("^\\d{11}$");
        mMatcher = mPattern.matcher(phoneNumber.getText().toString().trim());

        if (!mMatcher.find()) {
            ToastUtil.showLongToast(this, R.string.info_input_phone_number);
            return;
        }

        String url = String.format(ConstantURL.CHECKCODEURL, phoneNumber.getText().toString());
        //check the register time.
        initRegisterTime();
        int time = DefaultShared.getInt(REGISTER_COUNT, INITTIME);
        if (time >= 3) {
            //ToastUtil.showLongToast(this,"每天只能申请三次哦,");
            final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
            mMaterialDialog.setMessage(R.string.has_use_over_register_count)
                    .setCanceledOnTouchOutside(false);

            mMaterialDialog.show();
            mMaterialDialog.setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            })
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });
            return;
        } else {
            time += 1;
            DefaultShared.putInt(REGISTER_COUNT, time);
            DefaultShared.putLong(REGISTER_TIME, System.currentTimeMillis());
        }

        nextStep.setEnabled(false);
        ToastUtil.showLongToast(this, "已经发送请求，请耐心等待");

        executeRequest(new GsonRequest(url, CheckCode.class, responseListener(), errorListener()));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private String strCheckCode;

    private Response.Listener<CheckCode> responseListener() {
        return new Response.Listener<CheckCode>() {
            @Override
            public void onResponse(CheckCode checkCode) {
                //strCheckCode = checkCode.rsgcheck;
                //verifitation_code.setText(checkCode.rsgcheck);
                //
                nextStep.setEnabled(true);
                if (checkCode.rsgcode.equals(SUCCESSCODE)) {
                  /*  DefaultShared.putLong(PHONE_NUMBER, Long.parseLong(phoneNumber.getText().toString()));
                    DefaultShared.putString(CHECK_CODE, checkCode.rsgcheck);*/
                    Bundle bundle = new Bundle();
                    bundle.putString(PHONE_NUMBER, phoneNumber.getText().toString());
                    bundle.putString(CHECK_CODE, checkCode.rsgcheck);
                    IntentUtil.startActivity(RegisterStep1Activity.this, RegisterStep2Activity.class, bundle);
                    finish();
                } else {
                    ToastUtil.showShortToast(mContext, checkCode.rsgmsg);
                }
            }
        };
    }


    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }


    @Subscribe
    public void registerSuccess(RegisterSuccessEvent event) {
        finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
