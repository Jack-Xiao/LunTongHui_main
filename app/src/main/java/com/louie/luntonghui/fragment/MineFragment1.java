package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.DailySignIn;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.Home.WebActivity;
import com.louie.luntonghui.ui.mine.FeedbackActivity;
import com.louie.luntonghui.ui.mine.MineAttentionActivity;
import com.louie.luntonghui.ui.mine.MineReceiverAddressActivity;
import com.louie.luntonghui.ui.mine.MineService.MineServiceProviderActivity;
import com.louie.luntonghui.ui.mine.MineService.MineWorkActivity;
import com.louie.luntonghui.ui.mine.SettingActivity;
import com.louie.luntonghui.ui.mine.account_manager.MineCouponActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Administrator on 2015/7/7.
 */
public class MineFragment1 extends BaseFragment {
    @InjectView(R.id.toolbar_set)
    ImageView toolbarSet;

    @InjectView(R.id.toolbar_list)
    ImageView toolbarList;
    /*    @InjectView(R.id.user_image)
        CircleImageView userImage;*/
    @InjectView(R.id.username)
    TextView username;

    @Optional
    @InjectView(R.id.phone_number)
    TextView phoneNumber;

    @InjectView(R.id.custom_type)
    TextView customType;

    @InjectView(R.id.luntong_money)
    TextView luntongMoney;

    @Optional
    @InjectView(R.id.status_name)
    TextView statusName;
    @InjectView(R.id.status_value)
    TextView statusValue;

    @InjectView(R.id.mine_check_order)
    TextView mineCheckOrder;
    @InjectView(R.id.mine_wait_confirm)
    TextView mineWaitConfirm;
    @InjectView(R.id.mine_wait_deliver)
    TextView mineWaitDeliver;
    @InjectView(R.id.mine_has_deliver)
    TextView mineHasDeliver;
    @InjectView(R.id.mine_has_cancel)
    TextView mineHasCancel;
    @InjectView(R.id.mine_whole_order)
    TextView mineWholeOrder;

    @InjectView(R.id.mine_attention)
    RelativeLayout mineAttention;

    @InjectView(R.id.mine_address_manager)
    RelativeLayout mineAddressManager;
    @InjectView(R.id.mine_service_info)
    RelativeLayout mineServiceInfo;

    @InjectView(R.id.signin_songlungtobi)
    TextView signinSonglungtobi;

    @Optional
    @InjectView(R.id.lin_sign_in)
    FrameLayout linSignIn;

    @InjectView(R.id.service_phone_number)
    TextView servicePhoneNumber;

    @InjectView(R.id.mine_work)
    RelativeLayout mineWork;

    private Context mContext;

    @InjectView(R.id.mine_user_main_image)
    RelativeLayout mineUserMainImage;

    @InjectView(R.id.mine_new_service)
    RelativeLayout mRelativeMineNewService;

    private String mUid;
    private String mCustomType;
    private ProgressDialog mProgressDialog;
    public String userType;
    private String employeeType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] customType = getResources().getStringArray(R.array.custom_type);

        mUid = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.DEFAULT_USER_ID);
        if (!userType.equals(RegisterLogin.DEFAULT_USER_ID)){
            if (userType.equals("")) {
                userType = RegisterLogin.USER_DEFAULT;
            }
            int iType = Integer.parseInt(userType);
            mCustomType = customType[iType];
        }
        employeeType = DefaultShared.getString(User.IS_EMPLOYEE, User.NOTEMPLOYEE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_mine_new, null);

        ButterKnife.inject(this, contentView);
        mContext = getActivity();
        mProgressDialog = new ProgressDialog(mContext);
        initIntegral();

        queryData();
        if (userType.equals(RegisterLogin.USER_SERVICE)) {
            toolbarList.setVisibility(View.VISIBLE);
            mRelativeMineNewService.setVisibility(View.VISIBLE);
        } else {
            mRelativeMineNewService.setVisibility(View.GONE);
            toolbarList.setVisibility(View.GONE);
        }

        if(employeeType.equals(User.ISEMPLOYEE)){
            mineWork.setVisibility(View.VISIBLE);
        }else{
            mineWork.setVisibility(View.GONE);
        }

        return contentView;
    }

    private void initIntegral() {
        boolean isSingIn = Config.isSignIn();
        if (isSingIn) {
            signIn();
        }
        //signInSendMoney.setEnabled(!isSingIn);
    }

    @OnClick(R.id.mine_feedback)
    public void onClickFeedback(){
        FeedbackAgent agent = new FeedbackAgent(mContext);
        UserInfo info = agent.getUserInfo();
        if(info == null) info = new UserInfo();
        Map<String,String> contact = info.getContact();
        if(contact == null)
            contact = new HashMap<>();

        //String contact_info = con
        //agent.startFeedbackActivity();
        //getActivity().startActivity();
        IntentUtil.startActivity(getActivity(), FeedbackActivity.class);
    }

    private void queryData() {
        TaskUtils.executeAsyncTask(new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... params) {

                User user = new Select()
                        .from(User.class)
                        .where("uid = ?", mUid)
                        .executeSingle();
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user == null) return;
                if (username == null) return;
                username.setText(user.username);
                String orgMobilePhone = user.mobilePhone;
                String codeMobilePhone = orgMobilePhone.substring(0, 3) + "****" + orgMobilePhone.substring(7, orgMobilePhone.length());
                //phoneNumber.setText("手机号:" + codeMobilePhone);
                customType.setText(mCustomType);
                luntongMoney.setText(user.integral);
                //statusValue.setText(user.integral);
                statusValue.setText(0 + "");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.mine_attention)
    public void onAttentionClick() {
        //if (!loginUser()) return;
        IntentUtil.startActivityWiehAlpha(getActivity(), MineAttentionActivity.class);
    }

    @OnClick(R.id.mine_address_manager)
    public void onAddressManager() {
        if (!loginUser()) return;
        IntentUtil.startActivity(getActivity(), MineReceiverAddressActivity.class);
    }

    @OnClick(R.id.mine_service_info)
    public void onServiceInfo() {
        if (!loginUser()) return;
    }

    private boolean loginUser() {
        if (mUid.equals(RegisterLogin.DEFAULT_USER_ID)) {
            IntentUtil.startActivityFromMain(getActivity(), RegisterLogin.class);
            return false;
        }
        return true;
    }

    @OnClick(R.id.toolbar_set)
    public void setting() {
        IntentUtil.startActivity(getActivity(), SettingActivity.class);
    }

    /*@OnClick(R.id.signin_value)
    public void onSignIn() {
        //ToastUtil.showShortToast(getActivity(),);
        boolean isSingIn = Config.isSignIn();
        if (isSingIn) return;
        mProgressDialog.show();
        String userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        String url = String.format(ConstantURL.DAILY_SIGN_IN, userId, Config.SEND_SIGN_IN);
        RequestManager.addRequest(new GsonRequest(url, DailySignIn.class, singInRespose(),
                errorListener()), this);
    }*/

    @OnClick(R.id.signin_songlungtobi)
    public void onSignIn(){
        boolean isSingIn = Config.isSignIn();
        if (isSingIn) return;
        mProgressDialog.show();
        String userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        String url = String.format(ConstantURL.DAILY_SIGN_IN, userId, Config.SEND_SIGN_IN);
        RequestManager.addRequest(new GsonRequest(url, DailySignIn.class, singInRespose(),
                errorListener()), this);
    }

    public void signIn() {
        //signinValue.setBackgroundResource(R.drawable.not_sign_in);
        signinSonglungtobi.setText("已签到");

        //signinSonglungtobi.setVisibility(View.GONE);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                   // ViewGroup.LayoutParams.MATCH_PARENT,0,1);
        //layoutParams.setMargins(0,30,0,0);
        //mineUserMainImage.setLayoutParams(layoutParams);
        //signinSonglungtobi.setText(R.string.has_signin);
        //signinSonglungtobi.setBackgroundResource(R.drawable.has_signin_background);
    }


    @OnClick(R.id.mine_check_order)
    public void OnWholeOrderClick() {
        mOrderListener.selectType(0);
    }

    @OnClick(R.id.mine_wait_confirm)
    public void OnWaitOrderClick() {
        mOrderListener.selectType(1);
    }

    @OnClick(R.id.mine_wait_deliver)
    public void OnWaitDeliver() {
        mOrderListener.selectType(2);
    }

    @OnClick(R.id.mine_has_deliver)
    public void onHasDeliver() {
        mOrderListener.selectType(3);
    }

    @OnClick(R.id.mine_has_cancel)
    public void onHasCancel() {
        mOrderListener.selectType(4);
    }

    @OnClick(R.id.mine_whole_order)
    public void OnWholeOrder() {
        mOrderListener.selectType(0);
    }

    public interface OrderTypeListener {
        public void selectType(int type);
    }

    public OrderTypeListener mOrderListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOrderListener = (OrderTypeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SearchListener.");
        }
    }

    @OnClick(R.id.mine_service_info)
    public void onServicePhone() {
        try {
            String servicePhone = servicePhoneNumber.getText().toString();
            servicePhone = servicePhone.replace("-", "");
            Uri uri = Uri.parse("tel:" + servicePhone);
            Intent call = new Intent(Intent.ACTION_CALL, uri);
            startActivity(call);
        } catch (Exception e) {
            //存在双卡的问题 等待android 5.1 修复.
            //ToastUtil.showShortToast(mContext,);
        }
    }

    @OnClick({R.id.toolbar_list,R.id.mine_new_service})
    public void onClickMineCustomerList() {
        IntentUtil.startActivity(getActivity(), MineServiceProviderActivity.class);
    }

    private Response.Listener<DailySignIn> singInRespose() {
        return new Response.Listener<DailySignIn>() {

            @Override
            public void onResponse(final DailySignIn dailySignIn) {
                mProgressDialog.dismiss();
                long currSignIn = System.currentTimeMillis();
                if (dailySignIn.code.equals(SUCCESSCODE)) {
                    DefaultShared.putLong(Config.LAST_SING_IN_TIME, currSignIn);
                    ToastUtil.showShortToast(mContext, dailySignIn.msg);
                    //qiandao.setBackgroundColor(getResources().getColor(R.color.useful_grey));
                    //qiandao.set;
                    TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                        @Override
                        protected Object doInBackground(Object... params) {
                            new Update(User.class)
                                    .set("integral = ?", dailySignIn.total)
                                    .where("uid = ?", userId)
                                    .execute();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            luntongMoney.setText(dailySignIn.total);
                            //statusValue.setText(dailySignIn.total);
                            statusValue.setText(0+"");
                        }
                    });

                    signIn();
                } else {
                    DefaultShared.putLong(Config.LAST_SING_IN_TIME, currSignIn);
                    signIn();
                    ToastUtil.showShortToast(mContext, dailySignIn.msg);
                }
            }
        };
    }

    @OnClick(R.id.mine_work)
    public void onClickMineClient() {
        IntentUtil.startActivity(getActivity(), MineWorkActivity.class);
    }

    @OnClick(R.id.user_image)
    public void onClickUserImage(){
        IntentUtil.startActivity(getActivity(), MineCouponActivity.class);
    }

    @OnClick(R.id.mine_group)
    public void onClickGroup(){
        String url = String.format(ConstantURL.MINE_TUAN,userId);
        Bundle bundle = new Bundle();
        bundle.putString(WebActivity.WEB_URL,url);
        IntentUtil.startActivity(getActivity(),WebActivity.class,bundle);
    }
}
