package com.louie.luntonghui.ui.register;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.LoginEvent;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.Login;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.register.retrivepassword.RetrivePassword1Activity;
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
 * Created by Administrator on 2015/6/2.
 */
public class RegisterLogin extends BaseNormalActivity {
    public static final int NOTLOGIN = 0;
    public static final int HASLOGIN = 1;
    public static final String LOGIN_IN = "login";
    public static final int SAVE_FINISH = 1;
    public static final String LOGINSUCCESS = "login_success";
    public static final String USERUID = "user_uid";
    public static final String USER_TYPE = "user_type";

    public static final String USER_CLIENT = "0";    //终端店
    public static final String USER_CONSUMER = "1"; //消费者
    public static final String USER_WHOLESALER = "2";  //批发商
    public static final String USER_SERVICE = "3";    //服务商
    public static final String USER_DEFAULT = USER_CONSUMER; //默认消费者

    public static final String DEFAULT_USER_ID = "-1";

    EditText mPhoneNumber;
    EditText mPassword;
    public String strPhoneNumber;
    @InjectView(R.id.toolbar_cancel)
    TextView toolbarCancel;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;

    @InjectView(R.id.third_account)
    TextView thirdAccount;
    @InjectView(R.id.login_third_center)
    TextView loginThirdCenter;

    private Pattern mPattern;
    private Matcher mMatcher;
    public static final String PHONE_NAME = "phone";
    public static final String PASSWORD = "password";
    ProgressDialog mProgressDialog;
    private String mac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login);
        ButterKnife.inject(this);
        strPhoneNumber = getIntent().getStringExtra("phoneNumber");
        mPassword = (EditText) findViewById(R.id.password);
        mPhoneNumber = (EditText) findViewById(R.id.username);
        //mPhoneNumber.setText(strPhoneNumber);

        mPattern = Pattern.compile("^\\d{11}$");
        mProgressDialog = new ProgressDialog(this);
        mac = Config.getMacAddress(mContext);

        if(getIntent().getExtras()!=null && getIntent().getExtras().getString(PHONE_NAME)!=null){
            String phoneNumber = getIntent().getExtras().getString(PHONE_NAME);
            String password = getIntent().getExtras().getString(PASSWORD);
            if (phoneNumber.equals("") && password.equals("")) {
                String url = String.format(ConstantURL.LOGIN, phoneNumber, password);
                executeRequest(new GsonRequest(url,
                        Login.class, loginListener(phoneNumber), errorListener()));
            }
        }
    }

    public void onClickLogin(View view) {
        String phoneNumber = mPhoneNumber.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        try {
            phoneNumber = URLEncoder.encode(phoneNumber, "UTF-8");
            password = URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(ConstantURL.LOGIN, phoneNumber, password,mac);

        mProgressDialog.show();
        executeRequest(new GsonRequest(url,
                Login.class, loginListener(phoneNumber), errorListener()));
    }

    public Response.Listener<Login> loginListener(final String phoneNumber) {
        return new Response.Listener<Login>() {
            @Override
            public void onResponse(final Login login) {
                mProgressDialog.dismiss();
                if (!login.rsgcode.equals(SUCCESSCODE)) {
                    ToastUtil.showLongToast(RegisterLogin.this, login.rsgmsg);
                } else {
                    TaskUtils.executeAsyncTask(new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected void onPreExecute() {

                        }

                        @Override
                        protected Void doInBackground(Void... params) {
//                            User user = User.load(User.class,)
                            User user;
                            //if(phoneNumber.)
                            mMatcher = mPattern.matcher(phoneNumber);
                            if (mMatcher.find()) {
                                user = new Select()
                                        .from(User.class)
                                        .where("mobile_phone = ?", phoneNumber)
                                        .executeSingle();
                            } else {
                                user = new Select()
                                        .from(User.class)
                                        .where("username = ?", phoneNumber)
                                        .executeSingle();
                            }



                         /*   new Delete()
                                    .from(User.class)
                                    .where("mobile_phone = ?",phoneNumber).execute();*/

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
                                        .where("mobile_phone=?", phoneNumber)
                                        .execute();
                            } else {
                                User curuser = new User();
                                curuser.uid = login.userid;
                                curuser.username = login.user_name;
                                curuser.email = login.email;
                                curuser.isSupplier = login.gysa;
                                curuser.superiorSupplier = login.gys;
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
                            DefaultShared.putInt(LOGIN_IN, HASLOGIN);
                            DefaultShared.putString(USER_TYPE, login.type);
                            DefaultShared.putString(USERUID, login.userid);
                            DefaultShared.putLong(Config.LAST_SING_IN_TIME, Config.CLEAR_SIGN_IN);
                            DefaultShared.putString(User.IS_EMPLOYEE,login.personnel);
                            App.getBusInstance().post(new LoginEvent());
                            Bundle bundle = new Bundle();
                            bundle.putInt(RegisterStep3Activity.INIT_TYPE, 2);
                            try {


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            IntentUtil.startActivity(RegisterLogin.this, MainActivity.class,bundle);
                            RegisterLogin.this.finish();
                        }
                    });
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        App.getBusInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        App.getBusInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    @OnClick(R.id.phone_quite_register)
    public void OnQuiteRegister() {
        IntentUtil.startActivityFromMain(RegisterLogin.this, RegisterStep1Activity.class);
        finish();
    }

    @OnClick(R.id.retrive_password)
    public void onClickRetrivePassword(){
        IntentUtil.startActivity(RegisterLogin.this, RetrivePassword1Activity.class);
    }

    public void onClickClearUsername(){
        mPhoneNumber.setText("");
    }
    public void onClickClearPassword(){
        mPassword.setText("");
    }
}
