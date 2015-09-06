package com.louie.luntonghui.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.louie.luntonghui.ui.mine.MineAdditionAddressActivity;
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
import butterknife.OnClick;
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
    @InjectView(R.id.terminal)
    RadioButton terminal;
    @InjectView(R.id.wholesale1)
    RadioButton wholesale1;

    @InjectView(R.id.address_detail)
    TextView addressDetail;

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
    public static final String PROXY_REGISTER = "proxy_register";
    public static final String CONSIGNEE = "consignee";
    public static final String TEL_PHONE = "tel_phone";
    public static final boolean IS_PROXY = true;
    public static final boolean NOT_PROXY = false;

    public static final String PROXY = "proxy";

    //public static final boolean IS_PROXY = true;
    //public static final boolean IS_PROXY = 15;
    public static final int PROXY_REQUEST_CODE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int REQUEST_FAIL = 2;

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

        if (strPassword.length() < 6) {
            ToastUtil.showShortToast(mContext, R.string.please_input_six_count);
            return;
        }

        mMatcher = mPattern.matcher(phoneNumber.getText().toString().trim());

        if (!mMatcher.find()) {
            ToastUtil.showLongToast(this, R.string.info_input_phone_number);
            return;
        }

        if(provinceId.equals(Default_Province)){
            ToastUtil.showShortToast(mContext, R.string.address_select);
            return;
        }

        try {
            strUsername = URLEncoder.encode(strUsername, "UTF-8");
            strphoneNumber = URLEncoder.encode(strphoneNumber, "UTF-8");
            strPassword = URLEncoder.encode(strPassword, "UTF-8");
            detailAddress = URLEncoder.encode(detailAddress,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String url;

        String type = "1";
        for (int i = 0; i < radList.size(); i++) {
            if (radList.get(i).isChecked()) {
                i = i + 1;
                type = i + "";
                break;
            }
        }


        String userType = RegisterLogin.USER_CLIENT;
        if(wholesale1.isChecked()){
            userType = RegisterLogin.USER_WHOLESALER;
        }

        //url = String.format(ConstantURL.PROXYREGISTER, strUsername, strPassword, strphoneNumber, mac, type, userId);
        url = String.format(ConstantURL.PROXYREGISTER, strUsername, strPassword, strphoneNumber, mac, type, userId,
                    userType,provinceId,cityId,streeId,detailAddress);
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
                    ToastUtil.showShortToast(mContext, R.string.proxy_register_success);
                    finish();
                }
            }
        };
    }

    private void reSetRadionState(int j) {
        for (int i = 0; i < radList.size(); i++) {
            if (i != j) {
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
    @OnClick(R.id.address_detail)
    public void onClickAddressSelect(){
        strUsername = username.getText().toString().trim();
        if(strUsername.length() == 0){
            ToastUtil.showShortToast(mContext,"请输入用户名");
            return;
        }

        //password = ConstantURL.INITPASSWORD;
        strPassword = password.getText().toString();
        strphoneNumber = phoneNumber.getText().toString().trim() + "";

        if (strPassword.length() < 6) {
            ToastUtil.showShortToast(mContext, R.string.please_input_six_count);
            return;
        }

        mMatcher = mPattern.matcher(phoneNumber.getText().toString().trim());

        if (!mMatcher.find()) {
            ToastUtil.showLongToast(this, R.string.info_input_phone_number);
            return;
        }


        Bundle bundle  = new Bundle();
        bundle.putBoolean(PROXY_REGISTER, IS_PROXY);
        bundle.putString(CONSIGNEE, strUsername);
        bundle.putString(TEL_PHONE,strphoneNumber);
        Intent intent = new Intent();
        intent.putExtra(PROXY, bundle);
        intent.setClass(ProxyRegisterActivity.this, MineAdditionAddressActivity.class);
        startActivityForResult(intent,PROXY_REQUEST_CODE);
    }

    private String provinceId = Default_Province;
    private String cityId;
    private String streeId;
    private String detailAddress;
    private String wholeAddressInfo;

    private String strProvince;
    private String strCity;
    private String strStree;

    public static final String Default_Province = "-1";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PROXY_REQUEST_CODE && resultCode == RESULT_OK){
            strProvince = data.getStringExtra(MineAdditionAddressActivity.PROVINCE);
            strCity = data.getStringExtra(MineAdditionAddressActivity.CITY);
            strStree = data.getStringExtra(MineAdditionAddressActivity.STREE);

            provinceId = data.getStringExtra(MineAdditionAddressActivity.PROVINCE_ID);
            cityId = data.getStringExtra(MineAdditionAddressActivity.CITY_ID);
            streeId = data.getStringExtra(MineAdditionAddressActivity.STREE_ID);
            detailAddress = data.getStringExtra(MineAdditionAddressActivity.DETAIL_ADDRESS);

            addressDetail.setText(strProvince +  strCity + strStree + " " + detailAddress);
        }
    }
}
