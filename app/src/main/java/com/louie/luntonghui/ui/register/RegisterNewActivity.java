package com.louie.luntonghui.ui.register;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.Login;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/9/19.
 */
public class RegisterNewActivity extends BaseNormalActivity {
    public static final String INIT_TYPE = "init_type";

    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.phone_number)
    EditText phoneNumber;
    @InjectView(R.id.user_name)
    EditText userName;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.password_ver)
    EditText passwordVer;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.next_step)
    Button nextStep;
    @InjectView(R.id.phone_number_holder)
    TextInputLayout phoneNumberHolder;
    @InjectView(R.id.user_name_holder)
    TextInputLayout userNameHolder;
    @InjectView(R.id.password_holder)
    TextInputLayout passwordHolder;
    @InjectView(R.id.password_ver_holder)
    TextInputLayout passwordVerHolder;

    private Pattern mPattern;
    private Matcher mMatcher;
    private String mac;

    private ProgressDialog mProgress;
    private String strPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);

        ButterKnife.inject(this);
        mac = Config.getMacAddress(this);
        mPattern = Pattern.compile("^\\d{11}$");
        mProgress = new ProgressDialog(mContext);

        initView();

    }

    public void initView() {
        toolbarTitle.setText(R.string.luntonghui_register);
        checkbox.setChecked(true);

        phoneNumber.addTextChangedListener(textWatcher);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nextStep.setEnabled(isChecked);
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count == 1){
                int length = s.toString().length();
                if(length == 3 || length == 8){
                    phoneNumber.setText(s + " ");
                    phoneNumber.setSelection(phoneNumber.getText().toString().length());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @OnClick(R.id.next_step)
    public void onCompleteClick() {
        mPattern = Pattern.compile("^\\d{11}$");
        mMatcher = mPattern.matcher(phoneNumber.getText().toString().replace(" ","").trim());

        if (!mMatcher.find()) {
            //ToastUtil.showLongToast(this, R.string.info_input_phone_number);
            phoneNumberHolder.setErrorEnabled(true);
            phoneNumberHolder.setError("请输入11位有效电话号码");
            return;
        }else{
            phoneNumberHolder.setErrorEnabled(false);
        }

        String strPassword = password.getText().toString();

        if(strPassword.length()<6){
            passwordHolder.setErrorEnabled(true);
            passwordHolder.setError("请输入6位以上密码");
            return;
        }else{
            passwordHolder.setErrorEnabled(false);
        }

        String strPasswordVer = passwordVer.getText().toString();

        if(!strPassword.equals(strPasswordVer)){
            passwordVerHolder.setErrorEnabled(true);
            passwordVerHolder.setError("两次密码输入不一致");
            return;
        }else{
            passwordVerHolder.setErrorEnabled(false);
        }

        String strUsername = userName.getText().toString();
        strPhoneNumber = phoneNumber.getText().toString().replace(" ","").trim();

        String type = "1";

        try {
            strUsername = URLEncoder.encode(strUsername, "UTF-8");
            strPhoneNumber = URLEncoder.encode(strPhoneNumber,"UTF-8");
            strPassword = URLEncoder.encode(strPassword,"UTF-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(ConstantURL.NEWREGISTER, strUsername,strPassword, strPhoneNumber,mac,type);


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
                    ToastUtil.showLongToast(RegisterNewActivity.this, login.rsgmsg);

                } else {
                    TaskUtils.executeAsyncTask(new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
//                          User user = User.load(User.class,)
                            User user;
                            //if(phoneNumber.)
                            mMatcher = mPattern.matcher(strPhoneNumber);
                            if (mMatcher.find()) {
                                user = new Select()
                                        .from(User.class)
                                        .where("mobile_phone = ?", strPhoneNumber)
                                        .executeSingle();
                            } else {
                                user = new Select()
                                        .from(User.class)
                                        .where("username = ?", strPhoneNumber)
                                        .executeSingle();
                            }

                            if (user != null) {
                                new Update(User.class)
                                        .set("uid=?," +
                                                        "username=?," +
                                                        "email=?," +
                                                        "is_supplier=?," +
                                                        "superior_supplier=?," +
                                                        "superior_supplier_invite_code=?," +
                                                        "integral=?," +
                                                        "mobile_phone=?," +
                                                        "rank_name=?," +
                                                        "verification=?," +
                                                        "wechat_bd=?," +
                                                        "reg_time=?," +
                                                        "place=?",
                                                login.userid,
                                                login.user_name,
                                                login.email,
                                                login.gysa,
                                                login.gys,
                                                login.yqm,
                                                login.jif,
                                                login.mobile_phone,
                                                login.rank_name,
                                                login.verification,
                                                login.wxch_bd,
                                                login.reg_time,
                                                login.display
                                        )
                                        .where("mobile_phone=?", strPhoneNumber)
                                        .execute();
                            } else {
                                User curuser = new User();
                                curuser.uid = login.userid;
                                curuser.username = login.user_name;
                                curuser.email = login.email;
                                curuser.isSupplier = login.yqm;
                                curuser.superiorSupplier = login.gys;
                                //curuser.superiorSupplierInviteCode = //login.gysa;
                                curuser.superiorSupplierInviteCode = login.yqm;
                                curuser.integral = login.jif;
                                curuser.mobilePhone = login.mobile_phone;
                                curuser.rankName = login.rank_name;
                                curuser.verification = login.verification;
                                curuser.wechatBd = login.wxch_bd;
                                curuser.regTime = login.reg_time;
                                curuser.place = login.display;
                                curuser.type = login.type;
                                curuser.save();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {

                            ToastUtil.showShortToast(mContext, "注册成功");
                            DefaultShared.putInt(RegisterLogin.LOGIN_IN, RegisterLogin.HASLOGIN);
                            DefaultShared.putString(RegisterLogin.USER_TYPE, login.type);
                            DefaultShared.putString(RegisterLogin.USERUID, login.userid);
                            DefaultShared.putString(User.IS_EMPLOYEE, login.personnel);

                            RegisterNewActivity.this.finish();
                            Bundle bundle = new Bundle();
                            bundle.putInt(INIT_TYPE, 2);
                            IntentUtil.startActivity(RegisterNewActivity.this, MainActivity.class, bundle);
                        }
                    });
                }
            }
        };
    }

    @OnClick(R.id.login)
    public void onLoginClick(){
        IntentUtil.startActivity(RegisterNewActivity.this, RegisterLogin.class);
        finish();

    }
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