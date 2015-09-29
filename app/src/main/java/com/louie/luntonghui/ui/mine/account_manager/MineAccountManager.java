package com.louie.luntonghui.ui.mine.account_manager;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.activeandroid.query.Delete;
import com.igexin.sdk.PushManager;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.event.ExitAppEvent;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.register.RegisterHome;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/9/24.
 */
public class MineAccountManager extends BaseToolbarActivity {
    @InjectView(R.id.app_toolbar)
    Toolbar appToolbar;
    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.change_password)
    RelativeLayout changePassword;
    @InjectView(R.id.exit_login)
    Button exitLogin;
    private String strPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strPhoneNumber = bundle.getString(MineCouponActivity.PHONE_NUMBER);
        }
    }

    @Override
    protected int toolbarTitle() {
        return R.string.account_manager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_mine_account_manager;
    }

    @OnClick(R.id.change_password)
    public void onClickChangePassword(){
        IntentUtil.startActivity(MineAccountManager.this,ChangePasswordActivity.class);
        finish();
    }

    @OnClick(R.id.exit_login)
    public void onClickExitLogin(){

        new Delete()
                .from(User.class)
                .execute();
        new Delete()
                .from(ShoppingCar.class)
                .execute();

        DefaultShared.putString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        DefaultShared.putString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
        DefaultShared.putLong(Config.LAST_SING_IN_TIME, Config.DEFAULT_SING_IN_TIME);
        DefaultShared.putString(Config.GT_PUSH_TAGS,Config.DEFAULT_PUSH_TAGS);

        App.getBusInstance().post(new ExitAppEvent());
        PushManager.getInstance().unBindAlias(App.getContext(), "a" + userId, true);

        IntentUtil.startActivity(MineAccountManager.this, RegisterHome.class);
        finish();
    }
}