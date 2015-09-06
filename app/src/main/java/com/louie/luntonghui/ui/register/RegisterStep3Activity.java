package com.louie.luntonghui.ui.register;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
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
 * Created by Administrator on 2015/6/2.
 */
public class RegisterStep3Activity extends BaseNormalActivity {
    public static final String CHECKCODEACTION = "bindingcode";
    public static final long COUNTDOWNIMERINTERVAL = 1000;
    public static final long COUNTDOWNTIMERTOTAL = 45 * COUNTDOWNIMERINTERVAL;
    public static final String INIT_TYPE = "init_type";
    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.bicycle)
    RadioButton bicycle;
    @InjectView(R.id.fix_bicycle)
    RadioButton fixBicycle;
    @InjectView(R.id.electrombile)
    RadioButton electrombile;
    @InjectView(R.id.wholesale)
    RadioButton wholesale;

    private String phoneNumber;
    private String username;
    private String recommendCode;
    private String password;


    @InjectView(R.id.username)
    EditText mUsername;
    @InjectView(R.id.recommend_code)
    EditText mRecommendCode;
    @InjectView(R.id.password)
    EditText mPassword;
    private ProgressDialog mProgress;

    private String strCheckCode;
    private CountDownTimer mCountDownTime;
    private List<RadioButton> radList;
    private String mac;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_regist);
        ButterKnife.inject(this);

        mac = Config.getMacAddress(this);
        mPattern = Pattern.compile("^\\d{11}$");
        mProgress = new ProgressDialog(mContext);
        initView();
    }

    private void initView() {
        radList = new ArrayList<>();
        toolbarTitle.setText(R.string.setting_baseinfo);
        //phoneNumber = DefaultShared.getLong(RegisterStep1Activity.PHONE_NUMBER, 0) + "";
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            phoneNumber = bundle.getString(RegisterStep1Activity.PHONE_NUMBER);
        }


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

    private void reSetRadionState(int j) {
        for(int i =0;i<radList.size();i++){
            if(i != j){
                radList.get(i).setChecked(false);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        return true;
    }


    //注册
    public void onClickRegister(View view) {
        username = mUsername.getText().toString();

        //password = ConstantURL.INITPASSWORD;
        password = mPassword.getText().toString();
        recommendCode = mRecommendCode.getText().toString().trim() + "";

        try {
            username = URLEncoder.encode(username, "UTF-8");
            phoneNumber = URLEncoder.encode(phoneNumber, "UTF-8");
            password = URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String url;
        String args;
        /*if (recommendCode.equals("")) {
            url = String.format(ConstantURL.REGISTER, username, phoneNumber);
        } else {
            url = String.format(ConstantURL.REGISTERRECOMMEND, username, phoneNumber, recommendCode);
        }*/

        String type = "1";
        for(int i =0;i<radList.size();i++){
            if(radList.get(i).isChecked()){
                i = i + 1;
                type= i+"";
                break;
            }
        }

        //新的注册接口
        if (recommendCode.equals("")) {
            url = String.format(ConstantURL.NEWREGISTER, username,password, phoneNumber,mac,type);
        } else {
            url = String.format(ConstantURL.NEWREGISTERCOMMEND, username, password, phoneNumber, recommendCode,mac,type);
        }



        //url = url + "&" + "type=" + type;
        //ProgressUtil.show(mContext, "请稍等");

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
                    ToastUtil.showLongToast(RegisterStep3Activity.this, login.rsgmsg);

                } else {
                    TaskUtils.executeAsyncTask(new AsyncTask<Void, Void, Void>() {
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

                            App.getBusInstance().post(new LoginEvent());
                            RegisterStep3Activity.this.finish();
                            Bundle bundle = new Bundle();
                            bundle.putInt(INIT_TYPE, 2);
                            IntentUtil.startActivity(RegisterStep3Activity.this, MainActivity.class, bundle);
                        }
                    });
                }
            }
        };
    }

    private Pattern mPattern;
    private Matcher mMatcher;


    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
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
