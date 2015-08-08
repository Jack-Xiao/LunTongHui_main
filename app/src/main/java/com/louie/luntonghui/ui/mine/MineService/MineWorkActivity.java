package com.louie.luntonghui.ui.mine.MineService;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.register.ProxyRegisterActivity;
import com.louie.luntonghui.util.IntentUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/7/30.
 */
public class MineWorkActivity extends BaseNormalActivity {
    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;

    @InjectView(R.id.mine_proxy_register)
    RelativeLayout mineProxyRegister;
    @InjectView(R.id.mine_customer_people_list)
    RelativeLayout mineCustomerPeopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_work);
        ButterKnife.inject(this);

        toolbarTitle.setText("客户管理");
        initView();

    }

    @OnClick(R.id.mine_proxy_register)
    public void onClickProxyRegister(){
        IntentUtil.startActivity(MineWorkActivity.this, ProxyRegisterActivity.class);
    }

    @OnClick(R.id.mine_customer_people_list)
    public void onClickCustomerPeopleList(){
        Bundle bundle = new Bundle();
        bundle.putString(User.IS_EMPLOYEE,User.ISEMPLOYEE);
        IntentUtil.startActivity(MineWorkActivity.this,MineCustomerPeopleListActivity.class,bundle);
    }

    private void initView() {
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
