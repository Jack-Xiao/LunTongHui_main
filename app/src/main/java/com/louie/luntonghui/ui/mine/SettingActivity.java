package com.louie.luntonghui.ui.mine;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.android.volley.Response;
import com.igexin.sdk.PushManager;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.ExitAppEvent;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.VersionUpdate;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.task.UpdateVersionTask;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.Home.WebActivity;
import com.louie.luntonghui.ui.Home.WebWithControlActivity;
import com.louie.luntonghui.ui.register.RegisterHome;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DataCleanManager;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyAlertDialogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2015/7/16.
 */
public class SettingActivity extends BaseNormalActivity implements MyAlertDialogUtil.AlertDialogListener,
        BaseAlertDialogUtil.BaseAlertDialogListener{

    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.check_version)
    TextView checkVersion;
    @InjectView(R.id.current_version_name)
    TextView currentVersionName;

    @InjectView(R.id.cache_value)
    TextView cacheValue;

    private Context mContext;
    private int curVersionNumber;
    private String curUpdateUrl;
    private String curVersionName;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        toolbarTitle.setText(R.string.mine_setting);
        mContext = this;
        curVersionNumber = Config.getCurrentVersion();
        curVersionName = Config.getCurrentName();
        currentVersionName.setText("v" + curVersionName);
        App.getBusInstance().register(this);
        mProgressDialog = new ProgressDialog(mContext);

        initCacheValue();
    }

    private void initCacheValue() {
        String cacheSize ;
        try {
            cacheSize =DataCleanManager.getCacheSize(SettingActivity.this);
        } catch (Exception e) {
            cacheSize = "4008 K";
            e.printStackTrace();
        }
        cacheValue.setText(cacheSize);
    }

    @OnClick(R.id.check_version)
    public void checkVersion() {
        mProgressDialog.show();
        curVersionNumber = Config.getCurrentVersion();
        String url = String.format(ConstantURL.CHECKVERSION, curVersionNumber);
        RequestManager.addRequest(new GsonRequest(url, VersionUpdate.class,
                updateApp(), errorListener()), this);
    }

    private Response.Listener<VersionUpdate> updateApp() {
        return new Response.Listener<VersionUpdate>() {
            @Override
            public void onResponse(VersionUpdate versionUpdate) {
                mProgressDialog.dismiss();

                String serviceVersion = versionUpdate.listallcat.version;
                Log.d("service version", serviceVersion + "....");
                int version = Integer.parseInt(serviceVersion);

                if (versionUpdate.listallcat.rsgcode.equals(SUCCESSCODE) ||
                        versionUpdate.listallcat.rsgcode.equals(SUCCESSCODE1)) {
                    ToastUtil.showLongToast(mContext, versionUpdate.listallcat.remark);
                } else {
                    curUpdateUrl = versionUpdate.listallcat.url;
                    MyAlertDialogUtil instance = MyAlertDialogUtil.getInstance()
                            .setMessage(versionUpdate.listallcat.remark)
                            .setCanceledOnTouchOutside(false)
                            .setNegativeContent(R.string.update_not)
                            .setPositiveContent(R.string.update_now);

                    instance.show(mContext, SettingActivity.this);
                }
            }
        };
    }

    @Override
    public void confirmUpdate() {
        UpdateVersionTask task = new UpdateVersionTask(mContext,UpdateVersionTask.NEED_VIEW);
        task.execute(curUpdateUrl);
    }

    @OnClick(R.id.btn_exit)
    public void onExit(){
        BaseAlertDialogUtil.getInstance()
                .setMessage(R.string.sure_exit)
                .setCanceledOnTouchOutside(true)
                .setNegativeContent(R.string.cancel)
                .setPositiveContent(R.string.confirm);

        BaseAlertDialogUtil.getInstance().show(mContext, SettingActivity.this);
    }

    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void confirm() {

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


        //IntentUtil.startActivity(SettingActivity.this, RegisterLogin.class);
        IntentUtil.startActivity(SettingActivity.this, RegisterHome.class);
        finish();
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

    @OnClick(R.id.clean_cache)
    public void onClickCleanCache(){
        mProgressDialog.show();
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();

        DataCleanManager.cleanInternalCache(this);
        //cacheValue.setText("0 K");
        initCacheValue();
        mProgressDialog.dismiss();
    }

    @OnClick(R.id.about_us)
    public void onClickAboutUs(){
        Bundle bundle = new Bundle();
        bundle.putString(WebWithControlActivity.TITLE,"关于我们");

        String url = ConstantURL.ABOUT_US;
        bundle.putString(WebActivity.WEB_URL,url);
        IntentUtil.startActivity(SettingActivity.this, WebActivity.class, bundle);
    }
}