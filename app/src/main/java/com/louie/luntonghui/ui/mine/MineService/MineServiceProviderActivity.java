package com.louie.luntonghui.ui.mine.MineService;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.ui.BaseActivity;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Jack on 15/7/30.
 */
public class MineServiceProviderActivity extends BaseNormalActivity {

    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.recommend_code)
    TextView recommendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_service_provider);
        ButterKnife.inject(this);

        toolbarTitle.setText(R.string.mine_is_service_provider);
        initView();

    }

    private void initView() {
        User user = new Select()
                .from(User.class)
                .where("uid = ?",userId)
                .executeSingle();
        if(user !=null){
            String code = user.superiorSupplierInviteCode;
            recommendCode.setText(code);
        }
    }

    @OnClick(R.id.mine_customer_people_list)
    public void onClickPeopleList(){
        IntentUtil.startActivity(MineServiceProviderActivity.this,MineCustomerPeopleListActivity.class);
    }
    @OnClick(R.id.mine_customer_order_list)
    public void onClickOrderList(){
        IntentUtil.startActivity(MineServiceProviderActivity.this,MineCustomerOrderListActivity.class);
    }
    @OnClick(R.id.mine_service_cost_query)
    public void onClickCostQuery(){
        ToastUtil.showShortToast(mContext,"暂未开放");
    }
}
