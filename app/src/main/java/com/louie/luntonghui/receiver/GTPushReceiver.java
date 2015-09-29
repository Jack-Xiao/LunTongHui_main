package com.louie.luntonghui.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.igexin.sdk.PushConsts;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.ui.Home.SecondKillActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.ui.category.GoodsDetailBuyActivity;
import com.louie.luntonghui.ui.extra.WebViewActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Jack on 15/9/2.
 */
public class GTPushReceiver extends BroadcastReceiver {
    private Map<String,String> valueMap;
    public static final String TYPE = "type";
    public static final String TYPE_TEXT = "0";
    public static final String TYPE_URL = "1";
    public static final String TYPE_SURFACE = "2";
    private String currentType;
    public static final String ACTION = "action";
    public static final String ACTION_SECOND_KILL = "0";
    public static final String ACTION_PRODUCE = "1";
    public static final String ACTION_CATE = "2";
    public static final String ACTION_NEW_GOODS = "3";
    public static final String CONTENT = "content";
    public static final String GOODS_ID = "goods_id";
    public static final String CAT_ID = "cat_id";
    public static final String TAG_LIST = "tag_list";
    private String serverTags;
    private String nativeTags;

    public static final String GOODS_URL = "goods_url";
    private NotificationManager nm;
    private PendingIntent pd;
    private Context mContext;
    public int DEFAULT_ID;

    public static final String TICKER_TEXT = "ticktext";
    public static final String TITLE = "title";
    private String tickerText;
    private String strContent;
    private String strTitle;
    private boolean curContains;
    private NotificationCompat.Builder mBuilder;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        mContext = context;
        nm =(NotificationManager)App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        switch (bundle.getInt(PushConsts.CMD_ACTION)){
            case PushConsts.GET_MSG_DATA:
                byte[] payload = bundle.getByteArray("payload");
                if(payload !=null){
                    mBuilder = new NotificationCompat.Builder(mContext);
                    DEFAULT_ID =UUID.randomUUID().hashCode();

                    String data = new String(payload);
                    valueMap = Config.getMapForJson(data);
                    serverTags = valueMap.get(TAG_LIST);

                    if(!TextUtils.isEmpty(serverTags)){
                        nativeTags = DefaultShared.getString(Config.GT_PUSH_TAGS, Config.DEFAULT_PUSH_TAGS);
                        List<String> nativeList = Arrays.asList(nativeTags.split(","));

                        String [] serList = serverTags.split("&&");
                        for(int i =0;i < serList.length;i++){
                            String [] currentArrayTag = serList[i].split("\\|\\|");
                            curContains = false;
                            for(int j=0;j<currentArrayTag.length;j++){
                                if(nativeList.contains(currentArrayTag[j])){
                                    curContains = true;
                                    break;
                                }
                            }
                            if(!curContains) return;
                        }
                    }

                    currentType = valueMap.get(TYPE);
                    tickerText = valueMap.get(TICKER_TEXT);
                    strContent = valueMap.get(CONTENT);
                    strTitle = valueMap.get(TITLE);

                    switch (currentType){
                        case TYPE_TEXT:
                            openApp();

                            break;
                        case TYPE_URL:
                            openUrl();

                            break;
                        case TYPE_SURFACE:
                            openSurface();

                            break;
                    }
                }
        }
    }

    private void openUrl() {
        String url = valueMap.get(ACTION);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(WebViewActivity.URL, url);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        intent.putExtra("url", url);
        intent.setClass(App.getContext(), WebViewActivity.class);

        openActivity(intent);
    }

    public void openActivity(Intent intent){
        pd = PendingIntent.getActivity(App.getContext(),0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentTitle(strTitle)
                .setContentIntent(pd)
                .setTicker(tickerText)
                .setContentText(strContent)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.push);

        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(0, notify);
    }

    private void openApp() {
        Intent intent = new Intent();
        intent.setClass(mContext,MainActivity.class);
        openActivity(intent);
    }

    private void openSurface() {
        String actionValue = valueMap.get(ACTION);

        Intent intent = new Intent();
        String goodsId;
        String catId;

        switch (actionValue){
            case ACTION_SECOND_KILL:
                intent.setClass(App.getContext(), SecondKillActivity.class);
                break;
            case ACTION_PRODUCE:    // 单独一个商品展示详细
                goodsId = valueMap.get(GOODS_ID);
                Bundle bundle = new Bundle();
                bundle.putString(GoodsDetailActivity.GOODSDETAILID, goodsId);
                intent.setClass(mContext, GoodsDetailBuyActivity.class);
                intent.putExtras(bundle);

                break;
            case ACTION_CATE:   // 商品分类展示
                catId = valueMap.get(CAT_ID);
                String url = ConstantURL.CATEGORYGOODS + catId;
                Bundle bundle1 = new Bundle();
                bundle1.putString(GoodsDetailActivity.GOODSDETAILID, catId);
                bundle1.putString(GoodsDetailActivity.GOODSDETAILURL, url);
                intent.setClass(mContext, GoodsDetailActivity.class);
                intent.putExtras(bundle1);

                break;

            case ACTION_NEW_GOODS:
                intent.setClass(mContext, GoodsDetailActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt(GoodsDetailActivity.NEW_GOODS,GoodsDetailActivity.IS_NEW_GOODS);
                intent.putExtras(bundle2);
                break;
        }
        openActivity(intent);
    }
}
