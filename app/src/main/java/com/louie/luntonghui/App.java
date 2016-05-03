package com.louie.luntonghui;

import android.app.Application;
import android.app.Service;
import android.os.AsyncTask;
import android.os.Vibrator;

import com.activeandroid.ActiveAndroid;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.facebook.stetho.Stetho;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.net.OkHttpUtils;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.util.DataCleanManager;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.util.XmlParserHandler;
import com.squareup.otto.Bus;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/*import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;*/

/**
 * Created by Louie on 2015/5/28.
 */
public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

    public static final String CITY = "city";
    public static final String PROVINCE = "province";
    public static final String CITYID = "city_id";
    public static final String DEFAULT_CITYID = "76";//广州
    public static final String DEFAULT_PROVINCEID = "6"; //广东
    public static final String PROVINCEID = "province_id";
    public static final String USERUID = "user_uid";
    public static final String DEFAULT_USER_ID = "-1";
    public static final String DEFAULT_CITY = "广州";
    public static final String DEFAULT_PROVINCE = "广东";
    public static final String DEFAULT_LIAO_LING_ID = "1";


    public static final String GOODS_TOP_PARENT_ID = "-1";

    private static App application;
    private static Bus bus = new Bus();
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public Vibrator mVibrator;
    public String strLocation;
    public String cityName = "";
    public String provinceName = "";

    public Map<String, String> idNList;
    public Map<String, String> nameidList;
    public Map<String, List<String>> proNameMap;
    public Map<String, List<String>> cityNameMap;

    public static List<GoodsList.Goods_listEntity> mGoods_list;
    private CarList mCarList;
    public List<String> goodsInCaridsList;
    private int curVersionNumber;
    private String curUpdateUrl;
    private String userId;
    public static final String INITCHECKED = "-1";


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ActiveAndroid.initialize(this);
        //refWatcher = LeakCanary.install(this);

        initCache();
        parserXml();
        initImageConnect();

        mLocationClient = new LocationClient(this.getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);

        mMyLocationListener = new MyLocationListener();
        //mLocationClient.registerLocationListener(mMyLocationListener);
        //mGeofenceClient = new GeofenceClient(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

        initDebug();
    }

    private void initCache() {
        DataCleanManager.cleanInternalCache(this);
    }

    private void initImageConnect() {
        Glide.get(this).register(GlideUrl.class,InputStream.class,
                new OkHttpUrlLoader.Factory(OkHttpUtils.getInstance(this)));
    }
/*
    private static RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context){
        return refWatcher;
    }*/

    private void initDebug() {
        if(BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
    }

    protected com.android.volley.Response.ErrorListener errorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.showLongToast(application, "网络连接失败");
            }
        };
    }


    private void parserXml() {
        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                try {
                    InputStream input = getResources().openRawResource(R.raw.ecs_region1); //读取raw文件
                    SAXParserFactory spf = SAXParserFactory.newInstance();
                    SAXParser parser = spf.newSAXParser();
                    XmlParserHandler handle = new XmlParserHandler();
                    parser.parse(input, handle);
                    input.close();
                    proNameMap = handle.provinceMap;
                    cityNameMap = handle.cityMap;
                    idNList = handle.idNameList;
                    nameidList = handle.nameIdList;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                cityName = location.getCity().replace("市", "");
                provinceName = location.getProvince().replace("省", "");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                if(location.getCity() == null){
                    cityName = DEFAULT_CITY;
                }else{
                    cityName = location.getCity();
                }

                if(location.getProvince() == null){
                    provinceName = DEFAULT_PROVINCE;
                }else{
                    provinceName = location.getProvince();
                }
                cityName = cityName.replace("市", "");
                provinceName = provinceName.replace("省", "");
            }

            DefaultShared.putString(CITY, cityName);
            DefaultShared.putString(PROVINCE, provinceName);
            if (nameidList != null && nameidList.containsKey(cityName)) {
                DefaultShared.putString(CITYID, nameidList.get(cityName));
            }
            if (nameidList != null && nameidList.containsKey(provinceName)) {
                DefaultShared.putString(PROVINCEID, nameidList.get(provinceName));
            }
        }
    }

    public static App getContext() {
        return application;
    }

    public static Bus getBusInstance() {
        return bus;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        RequestManager.cancelAll();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
