package com.louie.luntonghui.ui.mine.account_manager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/9/24.
 */
public class MineCouponActivity extends BaseToolbarActivity {

    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.user_name)
    TextView userName;
    @InjectView(R.id.phone_number)
    TextView phoneNumber;
    @InjectView(R.id.mine_type)
    TextView mineType;
    @InjectView(R.id.address_place)
    TextView addressPlace;
    @InjectView(R.id.account_manager)
    RelativeLayout accountManager;

    private String uId;
    private String address;
    private String type;
    private String strPhoneNumber;
    public static final String PHONE_NUMBER ="phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        uId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        address = DefaultShared.getString(App.PROVINCE, App.DEFAULT_PROVINCE);
        if(TextUtils.isEmpty(address)) address = App.DEFAULT_PROVINCE;

        String[] customType = getResources().getStringArray(R.array.custom_type);
        userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.DEFAULT_USER_ID);

        type = customType[Integer.parseInt(userType)];

        initUserInfo();
    }

    private void initUserInfo() {
        TaskUtils.executeAsyncTask(new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... params) {

                User user = new Select()
                        .from(User.class)
                        .where("uid = ?", uId)
                        .executeSingle();
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user == null) return;
                strPhoneNumber = user.mobilePhone;
                userName.setText(user.username);
                phoneNumber.setText(strPhoneNumber);
                mineType.setText(type);
                addressPlace.setText(address);
            }
        });
    }

    @Override
    protected int toolbarTitle() {
        return R.string.mine_coupon;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_mine_coupon;
    }

    @OnClick(R.id.account_manager)
    public void onClickAccountManager(){
        Bundle bundle = new Bundle();
        bundle.putString(PHONE_NUMBER,strPhoneNumber);
        IntentUtil.startActivity(MineCouponActivity.this, MineAccountManager.class, bundle);
    }
}
