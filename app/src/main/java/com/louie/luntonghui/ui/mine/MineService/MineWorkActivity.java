package com.louie.luntonghui.ui.mine.MineService;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.mine.dispatch.DispatchTodayActivity;
import com.louie.luntonghui.ui.register.ProxyRegisterActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.IntentUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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

    @OnClick(R.id.mine_service_order)
    public void onClickServiceOrder(){

        IntentUtil.startActivity(MineWorkActivity.this,MineWorkCustomerOrderListActivity.class);
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

    @OnClick(R.id.mine_outstanding_statistics)
    public void onClickOutstandingStatistics(){
        MineOutstandingStatisticsActivity.start(mContext);
    }

    @OnClick(R.id.mine_outstanding_printer)
    public void onClickMineOrderQuery(){
        MineOutstandingPrinter.startActivity(mContext);
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

    @OnClick(R.id.share_key)
    public void onClickShare(){
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用  QQ
        oks.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.louie.luntonghui");


        // text是分享文本，所有平台都需要这个字段
        oks.setText("轮能惠，一款好用的在线购物商城!");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        String imgPath = Config.getSDPath();
        if(!TextUtils.isEmpty(imgPath)){
            oks.setImagePath(imgPath);//确保SDcard下面存在此张图片
        }

        //oks.setImageUrl();
        oks.setImageUrl("http://ww2.sinaimg.cn/large/7d499e44gw1ewccno0xtsj20sg0sgjuy.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.louie.luntonghui");


       /* // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");*/

        // 启动分享GUI
        oks.show(this);

    }

    @OnClick(R.id.mine_dispatch)
    public void onClickDispatch(){
        IntentUtil.startActivity(MineWorkActivity.this, DispatchTodayActivity.class);
    }
}
