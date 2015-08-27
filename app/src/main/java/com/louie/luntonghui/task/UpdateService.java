package com.louie.luntonghui.task;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Response;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.result.VersionUpdate;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.util.ClientUtil;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;

import java.io.File;

import static com.louie.luntonghui.ui.BaseNormalActivity.SUCCESSCODE1;

/**
 * Created by Jack on 15/8/10.
 */
public class UpdateService extends IntentService {
    public static final String UPDATE_SERVICE = "update_service";
    private int curVersionNumber;
    public static boolean ISUPDATEING = false;

    public UpdateService() {
        this("update service");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super(name);
    }

    public static synchronized boolean updateing() {
        return ISUPDATEING;
    }

    public static synchronized void beginUpdateing() {
        ISUPDATEING = true;
    }

    public static synchronized void finishUpdated() {
        ISUPDATEING = false;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /*
        if(!updateing()){
            beginUpdateing();
        }else{
            return;
        }*/

        curVersionNumber = Config.getCurrentVersion();
        String url = String.format(ConstantURL.CHECKVERSION, curVersionNumber);
        RequestManager.addRequest(new GsonRequest(url, VersionUpdate.class,
                updateApp(), null), this);

    }

    private Response.Listener<VersionUpdate> updateApp() {
        return new Response.Listener<VersionUpdate>() {
            @Override
            public void onResponse(VersionUpdate versionUpdate) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (versionUpdate.listallcat.rsgcode.equals(SUCCESSCODE1)) return;


                if (!versionUpdate.listallcat.rsgcode.equals(SUCCESSCODE1) &&
                        DefaultShared.getInt(UPDATE_SERVICE, Config.NOT_NEED_UPDATE) == Config.NEED_UPDATE) {
                    Log.d("update_service", "straight install.");

                    Intent apkIntent = new Intent(Intent.ACTION_VIEW);
                    apkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    File newAPKFile = new File(ClientUtil.getDownDir(), Config.newApkName);

                    Uri puri = Uri.fromFile(newAPKFile);
                    apkIntent.setDataAndType(puri, "application/vnd.android.package-archive");
                    startActivity(apkIntent);
                    return;
                }
                Log.d("update_service", "into update.");
                Log.d("update_service", updateing() + "  update");

                if (!updateing()) {
                    if (!versionUpdate.listallcat.rsgcode.equals(SUCCESSCODE1)) {
                        beginUpdateing();
                        String curUpdateUrl = versionUpdate.listallcat.url;

                        Log.d("update_service", "need update");

                        UpdateVersionTask task = new UpdateVersionTask(UpdateService.this, UpdateVersionTask.NOT_NEED_VIEW);
                        task.execute(curUpdateUrl);

                    /*MyAlertDialogUtil.getInstance()
                            .setMessage(versionUpdate.listallcat.remark)
                            .setCanceledOnTouchOutside(false)
                            .setNegativeContent(R.string.update_not)
                            .setPositiveContent(R.string.update_now);

                    MyAlertDialogUtil.getInstance().show(MainActivity.this, MainActivity.this);*/
                    }
                }
            }

        };
    }
}
