package com.louie.luntonghui;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.facebook.stetho.Stetho;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.HotSearchTable;
import com.louie.luntonghui.model.db.Order;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.model.result.HotSearch;
import com.louie.luntonghui.model.result.OrderList;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.util.XmlParserHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.otto.Bus;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    private static final String TWITTER_KEY = "casoxiao@gmail.com";
    private static final String TWITTER_SECRET = "zz3321";

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
    public GeofenceClient mGeofenceClient;
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

        parserXml();
        initDB();
        initOrderList();

        initImageLoader(getApplicationContext());

        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        //mLocationClient.registerLocationListener(mMyLocationListener);
        mGeofenceClient = new GeofenceClient(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);


        initDebug();
    }

    private void initDebug() {
        if(BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
    }


    private void initOrderList() {
        userId = DefaultShared.getString(RegisterLogin.USERUID, DEFAULT_USER_ID);
        if (userId.equals("") || userId.equals(DEFAULT_USER_ID)) return;

        String url = String.format(ConstantURL.GET_WHOLE_ORDER, userId);
        RequestManager.addRequest(new GsonRequest(url, OrderList.class, getWholeOrderList(), errorListener()), this);
    }

    private Response.Listener<OrderList> getWholeOrderList() {
        return new Response.Listener<OrderList>() {
            @Override
            public void onResponse(final OrderList orderList) {
                        List<Order> data = new ArrayList<Order>();
                        if (orderList != null && orderList.mysalelist != null) {
                                new Delete()
                                        .from(Order.class)
                                        .execute();
                                for (int i = 0; i < orderList.mysalelist.size(); i++) {
                                    Order order = new Order();
                                    order.allowToModify = orderList.mysalelist.get(i).allow_to_modify;
                                    order.type = orderList.mysalelist.get(i).handler;
                                    order.money = orderList.mysalelist.get(i).money;
                                    order.payName = orderList.mysalelist.get(i).pay_name;
                                    order.orderId = orderList.mysalelist.get(i).order_id;
                                    order.orderSn = orderList.mysalelist.get(i).order_sn;
                                    order.orderAmount = orderList.mysalelist.get(i).order_amount;
                                    order.addTime = orderList.mysalelist.get(i).add_time;
                                    order.save();
                                    data.add(order);
                                }
                        }
            }
        };
    }


    public void initDB() {
        //String userId = DefaultShared.getString(USERUID, DEFAULT_USER_ID);
        /* String cityId = DefaultShared.getString(CITYID, DEFAULT_CITYID);
        String userType = DefaultShared.getString(RegisterLogin.USER_TYPE,RegisterLogin.USER_DEFAULT);
        String url = String.format(ConstantURL.GOODS_LIST, cityId,userType);
        String tempArg = "";
        if (DefaultShared.getString(USER_TYPE, USER_DEFAULT).equals(USER_WHOLESALER)) {
            tempArg = "";
        } else {
            tempArg = "&is_show=01";
        }
        url = url + tempArg;

        goodsInCaridsList = new ArrayList<>();*/


/*
        if (!userId.equals(DEFAULT_USER_ID)) {
            //RequestManager.addRequest
            String getCarList = String.format(ConstantURL.GET_CAR_LIST, userId);
            RequestManager.addRequest(new GsonRequest(
                    getCarList, CarList.class, getCarList(), errorListener()), this);

        }*/

        RequestManager.addRequest(new GsonRequest(ConstantURL.HOT_SEARCH, HotSearch.class,
                getHotSearchList(), errorListener()), this);
/*
        RequestManager.addRequest(new GsonRequest(url, GoodsList.class,
                getGoodsList(), errorListener()), this);*/

    }

    public Response.Listener<CarList> getCarList() {
        return new Response.Listener<CarList>() {

            @Override
            public void onResponse(final CarList carList) {
                mCarList = carList;
                for (int i = 0; i < carList.goods_list.size(); i++) {
                    goodsInCaridsList.add(carList.goods_list.get(i).goods_id);
                }
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        new Delete()
                                .from(ShoppingCar.class)
                                .execute();

                        List<ShoppingCar> data = new ArrayList<ShoppingCar>();
                        if (carList != null && carList.goods_list != null) {
                            try {
                                ActiveAndroid.beginTransaction();
                                for (int i = 0; i < carList.goods_list.size(); i++) {
                                    ShoppingCar car = new ShoppingCar();
                                    car.carId = carList.goods_list.get(i).rec_id;
                                    car.isChecked = INITCHECKED;
                                    car.goodsShopPrice = carList.goods_list.get(i).goods_price;
                                    car.goodsImage = carList.goods_list.get(i).goods_img;
                                    car.goodsNumber = carList.goods_list.get(i).goods_number;
                                    car.goodsId = carList.goods_list.get(i).goods_id;
                                    car.goodsName = carList.goods_list.get(i).goods_name;
                                    car.guige = carList.goods_list.get(i).guige;
                                    car.unit = carList.goods_list.get(i).danwei;
                                    car.save();
                                    data.add(car);
                                }
                                ActiveAndroid.setTransactionSuccessful();
                            } finally {
                                ActiveAndroid.endTransaction();
                            }
                        }
                        return data;
                    }

                    ;
                });
            }
        };
    }

    public Response.Listener<HotSearch> getHotSearchList() {
        return new Response.Listener<HotSearch>() {
            @Override
            public void onResponse(final HotSearch hotSearch) {
                        if (hotSearch != null && hotSearch.listallcat != null) {
                            new Delete()
                                    .from(HotSearchTable.class)
                                    .execute();
                                for (int i = 0; i < hotSearch.listallcat.size(); i++) {
                                    HotSearchTable table = new HotSearchTable();
                                    table.hotSearchChar = hotSearch.listallcat.get(i).name;
                                    table.save();
                                }
                        }
            }
        };
    }

    private com.android.volley.Response.Listener<GoodsList> getGoodsList() {
        return new com.android.volley.Response.Listener<GoodsList>() {

            @Override
            public void onResponse(final GoodsList goodsList) {

                mGoods_list = goodsList.goods_list;
            }
        };
    }


    protected com.android.volley.Response.ErrorListener errorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //ToastUtil.showLongToast(App.this, error.getMessage());
                ToastUtil.showLongToast(application, R.string.network_connect_fail);
            }
        };
    }


    private void parserXml() {
        //读取 asset 文件
        /*AssetManager aset = getAssets();
        aset.open("ecs_region1.xml");*/

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
            StringBuffer sb = new StringBuffer(256);
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\ndirection : ");
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());

                cityName = location.getCity().replace("市", "");
                provinceName = location.getProvince().replace("省", "");
                location.getProvince();
                //sb.append(location.getDirection() + "");  可能为null
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                cityName = location.getCity().replace("市", "");
                provinceName = location.getProvince().replace("省", "");
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators() + "");
            }
            Log.d("current city", cityName + "");
            Log.d("current city", provinceName + "");
            DefaultShared.putString(CITY, cityName);
            DefaultShared.putString(PROVINCE, provinceName);
            if (nameidList != null && nameidList.containsKey(cityName)) {
                DefaultShared.putString(CITYID, nameidList.get(cityName));
            }
            if (nameidList != null && nameidList.containsKey(provinceName)) {
                DefaultShared.putString(PROVINCEID, nameidList.get(provinceName));
            }
            //mLocationClient.registerLocationListener(mMyLocationListener);
            //mLocationClient.unRegisterLocationListener(mMyLocationListener);
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


    // 初始化ImageLoader
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)).discCacheSize(10 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
