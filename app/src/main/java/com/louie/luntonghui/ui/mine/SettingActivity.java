package com.louie.luntonghui.ui.mine;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.android.volley.Response;
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
import com.louie.luntonghui.ui.register.RegisterHome;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.BaseAlertDialogUtil;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyAlertDialogUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
        currentVersionName.setText("v"+curVersionName);
        App.getBusInstance().register(this);
        mProgressDialog = new ProgressDialog(mContext);
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

        App.getBusInstance().post(new ExitAppEvent());
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
}