package com.louie.luntonghui.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.CategoryGoAroundEvent;
import com.louie.luntonghui.event.ExitAppEvent;
import com.louie.luntonghui.event.IntoCartEvent;
import com.louie.luntonghui.event.LoginEvent;
import com.louie.luntonghui.event.OrderConfirmEvent;
import com.louie.luntonghui.event.RefreshCarListCountEvent;
import com.louie.luntonghui.event.ShowCarListEvent;
import com.louie.luntonghui.fragment.CarFragment;
import com.louie.luntonghui.fragment.CategoryFragment;
import com.louie.luntonghui.fragment.GoodsDetailFragment;
import com.louie.luntonghui.fragment.HomeFragment;
import com.louie.luntonghui.fragment.MineFragment1;
import com.louie.luntonghui.fragment.OrderFragment;
import com.louie.luntonghui.fragment.OrderFragment.ComeBackListener;
import com.louie.luntonghui.model.db.AttentionGoods;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.CurrentBrandGoodsList;
import com.louie.luntonghui.model.result.DailySignIn;
import com.louie.luntonghui.model.result.MineAttentionResult;
import com.louie.luntonghui.model.result.VersionUpdate;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.task.UpdateVersionTask;
import com.louie.luntonghui.ui.register.RegisterHome;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.ui.register.RegisterStep3Activity;
import com.louie.luntonghui.ui.web.AdvertisementWebActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.FragmentFactory;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.BadgeView;
import com.louie.luntonghui.view.MyAlertDialogUtil;
import com.louie.luntonghui.view.NavigationItem;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

import static android.support.v7.widget.Toolbar.OnClickListener;
import static com.louie.luntonghui.ui.BaseNormalActivity.SUCCESSCODE1;
import static com.louie.luntonghui.ui.register.RegisterLogin.HASLOGIN;
import static com.louie.luntonghui.ui.register.RegisterLogin.LOGIN_IN;
import static com.louie.luntonghui.ui.register.RegisterLogin.NOTLOGIN;

public class MainActivity extends BaseActivity implements OnClickListener, HomeFragment.SearchListener,
        GoodsDetailFragment.SearchGoodsListener, MyAlertDialogUtil.AlertDialogListener,
        HomeFragment.FastQueryListener, MineFragment1.OrderTypeListener, ComeBackListener,
        CarFragment.OnReferenCartListener, CategoryFragment.UpdateListener{

    public static final String BDLOCATIONCORR = "gcj02";
    public static final int BDLOCATIONSPAN = 5000;

    /*@InjectView(R.id.toolbar)
    Toolbar mToolbar;*/
    private Fragment fragment;
    @InjectView(R.id.navigation_home)
    NavigationItem mHome;
    @InjectView(R.id.navigation_category)
    NavigationItem mCategory;
    @InjectView(R.id.navigation_mine)
    NavigationItem mMine;
    @InjectView(R.id.navigation_car)
    NavigationItem mCar;
    @InjectView(R.id.navigation_form)
    NavigationItem mForm;
    private List<NavigationItem> mTabIndicators;
    public static final String INITCHECKED = "-1";

/*    @InjectView(R.id.viewpager)
    MyViewPager mViewPager;*/

    private FragmentManager fragmentManager;
    private LocationClient mLocationClient;
    private LocationClientOption.LocationMode mLocationMode = LocationClientOption.LocationMode.Battery_Saving;
    private String strLocation;
    private App.MyLocationListener myLocationListener;
    private int tabIndex;
    private List<Fragment> listFragment;
    private List<Fragment> fragmentes;
    public int initTab = 0;
    private String userId;
    private String userType;


    private int[] normalImage = {
            R.drawable.navigation_homebutton_normal,
            R.drawable.navigation_typebutton_normal,
            R.drawable.navigation_mystorebutton_normal,
            R.drawable.navigation_cartbutton_normal,
            R.drawable.navigation_datebutton_normal};
    private int[] pressImage = {
            R.drawable.navigation_homebutton_press,
            R.drawable.navigation_typebutton_press,
            R.drawable.navigation_mystorebutton_press,
            R.drawable.navigation_cartbutton_press,
            R.drawable.navigation_datebutton_press};

    private String[] tabName = {"首页", "分类", "我的", "购物车", "订单"};
    private int curVersionNumber;
    private int lastIndex = -1;
    public static final int CARINDEX = 3;
    private BadgeView mBadgeView;


    private List<Fragment> list;
    @Optional
    /*@InjectView(R.id.content)
    View mContent;*/
    private LocalBroadcastManager mLocalBroadcastManager;
    public String curUpdateUrl;
    private Context mContext;
    private SimpleDateFormat dateFormat;
    private String provinceId;
    public String strTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mContext = this;

        userId = DefaultShared.getString(RegisterLogin.USERUID, App.DEFAULT_USER_ID);
        userType = DefaultShared.getString(RegisterLogin.USER_TYPE,RegisterLogin.USER_DEFAULT);
        //设置别名
        if(!userId.equals(App.DEFAULT_USER_ID)){
            provinceId = DefaultShared.getString(App.PROVINCEID,App.DEFAULT_PROVINCEID);
            if(provinceId.equals(App.DEFAULT_PROVINCEID)){
                provinceId = "0";
            }else if(provinceId.equals(App.DEFAULT_LIAO_LING_ID)){
                provinceId = "1";
            }

            //JPushInterface.setAliasAndTags(mContext, userId, tags);

            PushManager.getInstance().initialize(this.getApplicationContext());

            String dProvinceId = "d" + provinceId;
            Tag tag = new Tag();
            tag.setName(dProvinceId);

            String uType = "u" + userType;
            Tag tag1 = new Tag();
            tag1.setName(uType);

            strTags = dProvinceId + "," + uType;
            DefaultShared.putString(Config.GT_PUSH_TAGS, strTags);

            Tag [] tagss= new Tag[]{tag,tag1};
            PushManager.getInstance().bindAlias(App.getContext(), "a" +userId);
            PushManager.getInstance().setTag(App.getContext(), tagss);

            PushManager.getInstance().turnOnPush(App.getContext());
            String clientId = PushManager.getInstance().getClientid(App.getContext());
            String test = "a" + clientId;

        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initTab = bundle.getInt(RegisterStep3Activity.INIT_TYPE);
        }

       /* // App Logo
        toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        toolbar.setTitle("App Title");
        // Sub Title
        toolbar.setSubtitle("Sub title");*/

      /*  *//*mToolbar.setLogo(R.drawable.icon);*//*
        setSupportActionBar(mToolbar);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View view = View.inflate(this, R.layout.navigation_toolbar, null);
        getSupportActionBar().setCustomView(view, params);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        //mToolbar.setLogo(R.drawable.actionbar_logo);
        //mToolbar.setLogo(R.drawable.logo_white);
        mToolbar.setNavigationIcon(R.drawable.logo_white);*/

        mTabIndicators = new ArrayList<NavigationItem>();

        fragmentManager = getSupportFragmentManager();
        initView();
/*        fragment = new HomeFragment();
        break;
        case 1:
        fragment = new CategoryFragment();
        break;
        case 2:
        fragment = new MineFragment();
        break;
        case 3:
        fragment = new CarFragment();
        break;
        case 4:
        fragment = new FormFragment();
        break;*/
        /*HomeFragment.class,
                CategoryFragment.class,
                MineFragment.class,
                CarFragment.class,
                FormFragment.class*/
      /*  Fragment[] fragments=new Fragment[]{CategoryFragment.class
               };*/
        listFragment = new ArrayList<>();
        listFragment.add(new HomeFragment());
        listFragment.add(new CategoryFragment());
        listFragment.add(new MineFragment1());
        listFragment.add(new CarFragment());
        listFragment.add(new OrderFragment());


        //checkVersion();

        initNavigation();
        initConfig();
        onTabChange(initTab);
        getGPSInfo();
        register();

        initMineAttention();

        initBadge();

        initShopCar();
        initDateFormat();
    }

    private void initMineAttention() {
        if (userId.equals(App.DEFAULT_USER_ID)) return;
        String url = String.format(ConstantURL.MINEATTENTION,userId,userType);
        RequestManager.addRequest(
                new GsonRequest(url,MineAttentionResult.class,
                        mineAttentionRequest(),errorListener()),this);
    }

    private Response.Listener<MineAttentionResult> mineAttentionRequest() {
        return new Response.Listener<MineAttentionResult>() {
            @Override
            public void onResponse(final MineAttentionResult list) {
                        new Delete()
                                .from(AttentionGoods.class)
                                .execute();
                        for (int i = 0; i < list.listallcat.size(); i++) {
                            MineAttentionResult.ListallcatEntity entity = list.listallcat.get(i);
                            AttentionGoods goods1 = new AttentionGoods();
                            goods1.goodsId = entity.goods_id;
                            goods1.goodsName = entity.goods_name;
                            goods1.goodsImg = entity.goods_img;
                            goods1.goodsSN = entity.goods_sn;
                            goods1.goodsNumber = entity.goods_number;
                            goods1.marketPrice = entity.market_price;
                            goods1.shopPrice = entity.shop_price;
                            goods1.gysMoney = entity.gys_money;
                            goods1.promotePrice = entity.promote_price;
                            goods1.goodsBrief = entity.goods_brief;
                            goods1.goodsDesc = entity.goods_desc;
                            goods1.sortOrder = entity.sort_order;
                            goods1.isBest = entity.is_best;
                            goods1.isNew = entity.is_new;
                            goods1.isHot = entity.is_hot;
                            goods1.display = entity.display;
                            goods1.giveIntegral = entity.give_integral;
                            goods1.integral = entity.integral;
                            goods1.isPromote = entity.is_promote;
                            goods1.discounta = entity.discounta;
                            goods1.discount = entity.discount;
                            goods1.discountTime = entity.discount_time;
                            goods1.discountName = entity.discount_name;
                            goods1.guige = entity.guige;
                            goods1.unit = entity.danwei;
                            goods1.recId = entity.rec_id;
                            goods1.save();
                        }
            }
        };
    }


    public void initConfig(){
        //init category tab selecte item.
        //DefaultShared.putInt(Config.LAST_SELECT_CATEGORY_ITEM, Config.INIT_LAST_SELECT_CATEGORY_ITEM);
    }

    private void initDateFormat() {
        dateFormat = new SimpleDateFormat("yyyyMMdd");
    }

    private void initBadge() {
        NavigationItem item = mTabIndicators.get(CARINDEX);
        ImageView image = item.getIcon();
        mBadgeView = new BadgeView(mContext, image);
        mBadgeView.setTextSize(Config.BADGEVIEW_SIZE);
    }

    private void initShopCar() {
        if (userId.equals(App.DEFAULT_USER_ID)) return;
        String getCarList = String.format(ConstantURL.GET_CAR_LIST, userId);
        RequestManager.addRequest(new GsonRequest(
                getCarList, CarList.class, getCarList(), errorListener()), this);
    }

    public Response.Listener<CarList> getCarList() {
        return new Response.Listener<CarList>() {

            @Override
            public void onResponse(final CarList carList) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Integer>() {
                    @Override
                    protected Integer doInBackground(Object... params) {
                        new Delete()
                                .from(ShoppingCar.class)
                                .execute();
                        int total = 0;
                        List<ShoppingCar> data = new ArrayList<ShoppingCar>();
                        if (carList != null && carList.goods_list != null) {
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
                                    total += Integer.parseInt(carList.goods_list.get(i).goods_number);
                                    car.save();
                                    data.add(car);
                                }
                        }
                        return total;
                    }

                    @Override
                    protected void onPostExecute(Integer count) {
                        if (count > 0) {
                            updateCarListShowCount(count);
                        }
                    }
                });
            }
        };
    }

    private void checkVersion() {
       /* Intent mServiceIntent = new Intent(MainActivity.this, UpdateService.class);
        startService(mServiceIntent);*/
        curVersionNumber = Config.getCurrentVersion();
        String url = String.format(ConstantURL.CHECKVERSION, curVersionNumber);
        RequestManager.addRequest(new GsonRequest(url, VersionUpdate.class,
                updateApp(), errorListener()), this);
    }

    protected com.android.volley.Response.ErrorListener errorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.showLongToast(MainActivity.this, error.getMessage());
            }
        };
    }

    private Response.Listener<VersionUpdate> updateApp() {
        return new Response.Listener<VersionUpdate>() {
            @Override
            public void onResponse(final VersionUpdate versionUpdate) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {

                        return null;
                    }
                    @Override
                    protected void onPostExecute(Object o) {
                        if (!versionUpdate.listallcat.rsgcode.equals(SUCCESSCODE1)) {
                            curUpdateUrl = versionUpdate.listallcat.url;

                            MyAlertDialogUtil.getInstance()
                                    .setMessage(versionUpdate.listallcat.remark)
                                    .setCanceledOnTouchOutside(false)
                                    .setNegativeContent(R.string.update_not)
                                    .setPositiveContent(R.string.update_now);

                            MyAlertDialogUtil.getInstance().show(MainActivity.this, MainActivity.this);
                        }
                    }
                });

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCarListCount(null);
        MobclickAgent.onPageStart("SplashScreen");
        MobclickAgent.onResume(this);

        String lastSECONDKILLDate = DefaultShared.getString(Config.SECOND_KILL, Config.DEFAULT_SECOND_KILL);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        int chooseIndex= getIntent().getIntExtra(AdvertisementWebActivity.WEB_CHOOSE_SURFACE,AdvertisementWebActivity.WEB_CHOOSE_DEFAULT_SURFACE);
        if(chooseIndex != AdvertisementWebActivity.WEB_CHOOSE_DEFAULT_SURFACE){
            onTabChange(chooseIndex);
        }

        /*String str = dateFormat.format(curDate);
        if (!lastSECONDKILLDate.equals(str)) {
            DefaultShared.putString(Config.SECOND_KILL, str);
            String userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
            String url = String.format(ConstantURL.SECOND_KILL_GOODS, userType);
            RequestManager.addRequest(new GsonRequest(url, CurrentBrandGoodsList.class, getSecondKillGoods(), errorListener()), mContext);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SplashScreen");
        MobclickAgent.onPause(this);
    }

    private Response.Listener<CurrentBrandGoodsList> getSecondKillGoods() {
        return new Response.Listener<CurrentBrandGoodsList>() {
            @Override
            public void onResponse(final CurrentBrandGoodsList currentBrandGoodsList) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        if (currentBrandGoodsList == null || currentBrandGoodsList.listallcat == null)
                            return null;
                        try {
                            new Delete()
                                    .from(Goods.class)
                                    .where("is_second_kill = ?", Config.SECONDVKILLGOODS)
                                    .execute();
                            Log.d("", "...........");

                            for (int i = 0; i < currentBrandGoodsList.listallcat.size(); i++) {
                                CurrentBrandGoodsList.ListallcatEntity entity = currentBrandGoodsList.listallcat.get(i);
                                Goods goods1 = new Goods();
                                goods1.goodsId = entity.goods_id;
                                goods1.goodsName = entity.goods_name;
                                goods1.goodsImg = entity.goods_img;
                                goods1.goodsSN = entity.goods_sn;
                                goods1.goodsNumber = entity.goods_number;
                                goods1.marketPrice = entity.market_price;
                                goods1.shopPrice = entity.shop_price;
                                goods1.gysMoney = entity.gys_money;
                                goods1.promotePrice = entity.promote_price;
                                goods1.goodsBrief = entity.goods_brief;
                                goods1.goodsDesc = entity.goods_desc;
                                goods1.sortOrder = entity.sort_order;
                                goods1.isBest = entity.is_best;
                                goods1.isNew = entity.is_new;
                                goods1.isHot = entity.is_hot;
                                goods1.display = entity.display;
                                goods1.giveIntegral = entity.give_integral;
                                goods1.integral = entity.integral;
                                goods1.isPromote = entity.is_promote;
                                goods1.discounta = entity.discounta;
                                goods1.discount = entity.discount;
                                goods1.discountTime = entity.discount_time;
                                goods1.discountName = entity.discount_name;
                                goods1.guige = entity.guige;
                                goods1.unit = entity.danwei;
                                goods1.is_second_kill = Config.SECONDVKILLGOODS;
                                goods1.save();
                                //data.add(goods1);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("error ", e.getMessage());
                        } finally {

                        }
                        return null;
                    }
                });
            }
        };
    }

    private void register() {

        App.getBusInstance().register(this);
       /* IntentFilter filter = new IntentFilter();
        filter.addAction(LOGINSUCCESS);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(receiver, filter);*/
    }

    private void getGPSInfo() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(mLocationMode);
        option.setCoorType(BDLOCATIONCORR);
        option.setScanSpan(BDLOCATIONSPAN);
        option.setIsNeedAddress(true);

        mLocationClient = ((App) getApplication()).mLocationClient;
        myLocationListener = ((App) getApplication()).mMyLocationListener;
        mLocationClient.setLocOption(option);

    }

    @Override
    protected void onStop() {
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(myLocationListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        mLocationClient.start();
        mLocationClient.registerLocationListener(myLocationListener);
        super.onStart();
    }

    private void initView() {

        list = new ArrayList<Fragment>();
        Fragment fragment = new HomeFragment();
        list.add(fragment);
        fragment = new CategoryFragment();
        list.add(fragment);
        fragment = new MineFragment1();
        list.add(fragment);
        fragment = new CarFragment();
        list.add(fragment);
        fragment = new OrderFragment();
        list.add(fragment);
    }

    private void initNavigation() {
        mHome.setOnClickListener(this);
        mCategory.setOnClickListener(this);
        mMine.setOnClickListener(this);
        mCar.setOnClickListener(this);
        mForm.setOnClickListener(this);

        mTabIndicators.add(mHome);
        mTabIndicators.add(mCategory);
        mTabIndicators.add(mMine);
        mTabIndicators.add(mCar);
        mTabIndicators.add(mForm);

        /*FragmentPagerAdapter adapter = new FragmentPagerAdapter(fragmentManager) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return list.size();
            }

            @Override
            public Fragment getItem(int position) {

                return list.get(position);
            }
        };
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                onTabChange(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {


            }
        });*/
    }

    public void onTabChange(int index) {
        if (index == lastIndex) return;
        for (int i = 0; i < mTabIndicators.size(); i++) {
            if (index == i) {
                //mTabIndicators.get(i).setBackgroundColor(Color.parseColor("#0078FF"));
                mTabIndicators.get(i).setTextAndColor(tabName[i], getResources().getColor(R.color.red));
                mTabIndicators.get(i).setIcon(pressImage[i]);
            } else {
                //mTabIndicators.get(i).setBackgroundColor(Color.parseColor("#00FFFFFF"));
                mTabIndicators.get(i).setTextAndColor(tabName[i], Color.parseColor("#666666"));
                mTabIndicators.get(i).setIcon(normalImage[i]);
            }
        }
        Fragment fragment;
        /*if(index == 0 || index ==1){
            fragment = listFragment.get(index);
        }else{
            fragment = FragmentFactory.getNavigationFragment(index);
        }*/
        fragment = FragmentFactory.getNavigationFragment(index);
        //fragment = listFragment.get(index);
        if(index == 1){
            Bundle bundle = new Bundle();
            //for..test.
            fragment.setArguments(bundle);

        }

        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commitAllowingStateLoss(); //yun commit类似

        lastIndex = index;
        // mViewPager.setCurrentItem(index,false);

       /* if (index == 5) {
            mTabIndicators.get(2).setTextAndColor(tabName[2], getResources().getColor(R.color.red));
            mTabIndicators.get(2).setIcon(pressImage[2]);
        }

        //mViewPager.setCurrentItem(index, false);
        //Fragment fragment = FragmentFactory.getNavigationFragment(index);
        Fragment fragment = listFragment.get(index);
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commitAllowingStateLoss(); //yun commit类似



        /*Fragment fragment = listFragment.get(index);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragmentes = getSupportFragmentManager().getFragments();
        if(fragmentes == null){
            fragmentes = new ArrayList<>();
            //fragmentes.add(listFragment.get(0));
        }
        if(!fragmentes.contains(fragment)){
            transaction.add(R.id.content,fragment);
        }

        transaction.addToBackStack(index +"");
        transaction.hide(listFragment.get(tabIndex));
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
        tabIndex = index;*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        fragment = new Fragment();
        int id = v.getId();
        switch (id) {
            case R.id.navigation_home:

                onTabChange(0);
                break;
            case R.id.navigation_category:

                onTabChange(1);
                break;
            case R.id.navigation_mine:
                if (DefaultShared.getInt(LOGIN_IN, NOTLOGIN) == NOTLOGIN) {
                    IntentUtil.startActivity(MainActivity.this, RegisterHome.class);
                } else {
                    onTabChange(2);
                }
                break;
            case R.id.navigation_car:
                if (DefaultShared.getInt(LOGIN_IN, NOTLOGIN) == NOTLOGIN) {
                    IntentUtil.startActivity(MainActivity.this, RegisterHome.class);
                } else {
                    onTabChange(3);
                }
                break;
            case R.id.navigation_form:
                onTabChange(4);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onLoginSucess(LoginEvent event) {
        DefaultShared.putInt(LOGIN_IN, HASLOGIN);
        onTabChange(2);
    }


    @Override
        protected void onDestroy() {
        App.getBusInstance().unregister(this);
        RequestManager.cancelAll(this);
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        exitApp();
    }

    @Subscribe
    public void onOrderConfirmSuccess(OrderConfirmEvent event) {
        //我的
        //onTabChange(4);
        int index = 4;
        for (int i = 0; i < mTabIndicators.size(); i++) {
            if (index == i) {
                //mTabIndicators.get(i).setBackgroundColor(Color.parseColor("#0078FF"));
                mTabIndicators.get(i).setTextAndColor(tabName[i], getResources().getColor(R.color.red));
                mTabIndicators.get(i).setIcon(pressImage[i]);
            } else {
                //mTabIndicators.get(i).setBackgroundColor(Color.parseColor("#00FFFFFF"));
                mTabIndicators.get(i).setTextAndColor(tabName[i], Color.parseColor("#666666"));
                mTabIndicators.get(i).setIcon(normalImage[i]);
            }
        }
        //mViewPager.setCurrentItem(index, false);
        Fragment fragment = FragmentFactory.getNavigationFragment(index);
        //Fragment fragment = listFragment.get(index);
       /* Bundle bundle = new Bundle();
        bundle.putInt(OrderFragment.ISREFERENCE, OrderFragment.NEEDREFERECE);
*/
        if (fragment.isAdded()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(OrderFragment.ISREFERENCE, OrderFragment.NEEDREFERECE);
        /*Fragment fragment = listFragment.get(index);
        fragment.setArguments(bundle);
        mViewPager.setCurrentItem(index, false);*/

        fragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commitAllowingStateLoss(); //yun commit类似
        lastIndex = index;

        String getIntegralUrl = String.format(ConstantURL.DAILY_SIGN_IN, userId, Config.SEND_QUERY_INTEGRAL);
        RequestManager.addRequest(new GsonRequest(getIntegralUrl, DailySignIn.class,
                getIntegralRequest(), errorListener()), this);
    }

    private Response.Listener<DailySignIn> getIntegralRequest() {
        return new Response.Listener<DailySignIn>() {
            @Override
            public void onResponse(final DailySignIn dailySignIn) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        new Update(User.class)
                                .set("integral = ?", dailySignIn.total)
                                .execute();
                        return null;
                    }
                });

            }
        };
    }

    @Subscribe
    public void onGoAround(CategoryGoAroundEvent event) {
        onTabChange(1);
    }

    @Override
    public void beginSearch() {
        //进入搜索查寻
        //onTabChange(5);
       /* Fragment fragment = FragmentFactory.getNavigationFragment(index);
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commitAllowingStateLoss();*/

    }

    @Override
    public void search(String goodsName) {
        Log.d("goodsname", goodsName);
    }

    @Override
    public void goBackHome() {
        onTabChange(0);
    }

    @Subscribe
    public void refreshCarListCount(RefreshCarListCountEvent event) {
        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Integer>() {
            @Override
            protected Integer doInBackground(Object... params) {
                int tempCount = 0;
                List<ShoppingCar> list = new Select()
                        .from(ShoppingCar.class)
                        .execute();
                for (int i = 0; i < list.size(); i++) {
                    tempCount += Integer.parseInt(list.get(i).goodsNumber);
                }
                return tempCount;
            }

            @Override
            protected void onPostExecute(Integer count) {
                updateCarListShowCount(count);
            }
        });
    }

    void updateCarListShowCount(int index) {
        if (index == 0) {
            mBadgeView.hide();
        } else {
            if(index >99){
                mBadgeView.setText("99+");
            }else{
                mBadgeView.setText(index + "");
            }
            mBadgeView.show();
        }
    }

    @Subscribe
    public void onShowCarList(ShowCarListEvent event) {
        onTabChange(3);
    }

    @Override
    public void confirmUpdate() {
        UpdateVersionTask task = new UpdateVersionTask(MainActivity.this,UpdateVersionTask.NEED_VIEW);
        task.execute(curUpdateUrl);
        //checkVersion();*/
    }

    @Subscribe
    public void onIntoCart(IntoCartEvent event) {
        onTabChange(3);
    }

    @Override
    public void query(String content) {
        int index = 1;
        argFragment(index, content);
    }


    @Override
    public void selectType(int type) {
        int index = 4;
        argFragment(index, type + "");
    }

    public void argFragment(int index, String argument) {

        for (int i = 0; i < mTabIndicators.size(); i++) {
            if (index == i) {
                //mTabIndicators.get(i).setBackgroundColor(Color.parseColor("#0078FF"));
                mTabIndicators.get(i).setTextAndColor(tabName[i], getResources().getColor(R.color.red));
                mTabIndicators.get(i).setIcon(pressImage[i]);
            } else {
                //mTabIndicators.get(i).setBackgroundColor(Color.parseColor("#00FFFFFF"));
                mTabIndicators.get(i).setTextAndColor(tabName[i], Color.parseColor("#666666"));
                mTabIndicators.get(i).setIcon(normalImage[i]);
            }
        }
        Bundle bundle = new Bundle();
        switch (index) {
            case 1:
                bundle.putString(Config.FASTQUERYARG, argument);
                break;
            case 4:
                bundle.putInt(OrderFragment.TYPE, Integer.parseInt(argument));
                break;
        }

        //Fragment fragment = listFragment.get(index);


        Fragment fragment = FragmentFactory.getNavigationFragment(index);
        fragment.setArguments(bundle);
        //mViewPager.setCurrentItem(index, false);
        //

        //Fragment fragment = listFragment.get(index);
        //fragment.setArguments(bundle);
        //mViewPager.setCurrentItem(index,false);

        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commitAllowingStateLoss(); //yun commit类似
        lastIndex = index;

    }

    @Subscribe
    public void onExit(ExitAppEvent event) {
        finish();
    }

    @Override
    public void back() {
        onTabChange(2);
    }

    @Override
    public void referenceCart(int count) {
        updateCarListShowCount(count);
    }

    @Override
    public void updateVersion() {
        checkVersion();
    }


    // 客户端退出
    private long startexit = 0;
    private int exitAppTimes = 0;

    public void exitApp() {
        if (System.currentTimeMillis() - startexit > 2000) {
            exitAppTimes = 0;
        }
        ++exitAppTimes;
        if (exitAppTimes == 1) {
            startexit = System.currentTimeMillis();
            ToastUtil.showShortToast(MainActivity.this, R.string.enter_exit);
        } else if (exitAppTimes == 2) {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

}