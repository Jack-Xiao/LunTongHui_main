package com.louie.luntonghui.ui.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.result.Login;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jack on 15/8/8.
 */
public class ProxyRegisterActivity extends BaseNormalActivity {

    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.phone_number)
    EditText phoneNumber;
    @InjectView(R.id.username)
    EditText username;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.bicycle)
    RadioButton bicycle;
    @InjectView(R.id.fix_bicycle)
    RadioButton fixBicycle;
    @InjectView(R.id.electrombile)
    RadioButton electrombile;
    @InjectView(R.id.wholesale)
    RadioButton wholesale;

    private String strphoneNumber;
    private String strUsername;
    private String recommendCode;
    private String strPassword;
    private ProgressDialog mProgress;

    private String strCheckCode;
    private CountDownTimer mCountDownTime;
    private List<RadioButton> radList;
    private String mac;

    private Pattern mPattern;
    private Matcher mMatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_regist_proxy);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
        mac = Config.getMacAddress(this);
        mPattern = Pattern.compile("^\\d{11}$");
        mProgress = new ProgressDialog(mContext);

        initView();
    }

    private void initView() {
        radList = new ArrayList<>();
        toolbarTitle.setText(R.string.setting_baseinfo);

        radList.add(bicycle);
        radList.add(fixBicycle);
        radList.add(electrombile);
        radList.add(wholesale);

        bicycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) reSetRadionState(0);
            }
        });
        fixBicycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) reSetRadionState(1);
            }
        });
        electrombile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) reSetRadionState(2);
            }
        });
        wholesale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) reSetRadionState(3);
            }
        });
    }

    public void onClickRegister(View view) {

        strUsername = username.getText().toString();

        //password = ConstantURL.INITPASSWORD;
        strPassword = password.getText().toString();
        strphoneNumber = phoneNumber.getText().toString().trim() + "";

        if(strPassword.length()<6){
            ToastUtil.showShortToast(mContext,R.string.please_input_six_count);
            return;
        }

        mMatcher = mPattern.matcher(phoneNumber.getText().toString().trim());

        if (!mMatcher.find()) {
            ToastUtil.showLongToast(this, R.string.info_input_phone_number);
            return;
        }

        try {
            strUsername = URLEncoder.encode(strUsername, "UTF-8");
            strphoneNumber = URLEncoder.encode(strphoneNumber, "UTF-8");
            strPassword = URLEncoder.encode(strPassword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String url;

        String type = "1";
        for(int i =0;i<radList.size();i++){
            if(radList.get(i).isChecked()){
                i = i + 1;
                type= i+"";
                break;
            }
        }

        url = String.format(ConstantURL.PROXYREGISTER, strUsername,strPassword, strphoneNumber,mac,type,userId);
        mProgress.show();
        executeRequest(new GsonRequest(url,
                Login.class, registerListener(), errorListener()));
    }

    private Response.Listener<Login> registerListener() {
        return new Response.Listener<Login>() {
            @Override
            public void onResponse(final Login login) {
                mProgress.dismiss();
                if (!login.rsgcode.equals(SUCCESSCODE)) {
                    ToastUtil.showLongToast(ProxyRegisterActivity.this, login.rsgmsg);
                } else {
                    ToastUtil.showShortToast(mContext,R.string.proxy_register_success);
                    finish();
                }
            }
        };
    }

    private void reSetRadionState(int j) {
        for(int i =0;i<radList.size();i++){
            if(i != j){
                radList.get(i).setChecked(false);
            }
        }
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
