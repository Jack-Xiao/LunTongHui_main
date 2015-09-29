package com.louie.luntonghui.ui.register;

import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;

import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.event.LoginEvent;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.util.IntentUtil;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2015/6/2.
 */
public class RegisterHome extends BaseNormalActivity {
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getBusInstance().register(this);
        setContentView(R.layout.register_home);

        /*IntentFilter filter = new IntentFilter();
        filter.addAction(LOGINSUCCESS);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(receiver, filter);*/
        //mToolbar.setVisibility(View.GONE);

    }

    public void loginOnClick(View view){
        IntentUtil.startActivity(RegisterHome.this,RegisterLogin.class);
     }
    public void registerOnClick(View view){
        //IntentUtil.startActivity(RegisterHome.this, RegisterStep1Activity.class);
        IntentUtil.startActivity(RegisterHome.this, RegisterNewActivity.class);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_regist, menu);
        MenuItem location = menu.findItem(R.id.action_location);
        //location.setTitle(DefaultShared.getString("city","广州"));
        MenuItem cityName = menu.findItem(R.id.city_name);
        cityName.setTitle(DefaultShared.getString("city", "广州"));*/

        return true;
    }

    @Subscribe
    public void loginSuccess(LoginEvent event){
        this.finish();
    }


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
