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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.MyFragmentPagerAdapter;
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
import com.louie.luntonghui.fragment.NewOrderFragment;
import com.louie.luntonghui.fragment.OrderFragment.ComeBackListener;
import com.louie.luntonghui.model.db.AttentionGoods;
import com.louie.luntonghui.model.db.HotSearchTable;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.DailySignIn;
import com.louie.luntonghui.model.result.HotSearch;
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
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.BadgeView;
import com.louie.luntonghui.view.CustomViewPager;
import com.louie.luntonghui.view.MyAlertDialogUtil;
import com.louie.luntonghui.view.NavigationItem;
import com.squareup.otto.Subscribe;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.Toolbar.OnClickListener;
import static com.louie.luntonghui.ui.BaseNormalActivity.SUCCESSCODE1;
import static com.louie.luntonghui.ui.register.RegisterLogin.HASLOGIN;
import static com.louie.luntonghui.ui.register.RegisterLogin.LOGIN_IN;
import static com.louie.luntonghui.ui.register.RegisterLogin.NOTLOGIN;

public class MainActivity extends BaseActivity implements OnClickListener, HomeFragment.SearchListener,
        GoodsDetailFragment.SearchGoodsListener, MyAlertDialogUtil.AlertDialogListener,
        HomeFragment.FastQueryListener, MineFragment1.OrderTypeListener, ComeBackListener,
        CarFragment.OnReferenCartListener, CategoryFragment.UpdateListener, HomeFragment.ScrollListener {

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

    @InjectView(R.id.login_prompt)
    RelativeLayout mLoginPrompt;

    @InjectView(R.id.btn_login)
    Button btnLogin;

    @InjectView(R.id.remove_login)
    ImageView removeLogin;

    private List<NavigationItem> mTabIndicators;
    public static final String INITCHECKED = "-1";

    @InjectView(R.id.viewpager)
    CustomViewPager mViewPager;

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

    //private List<Fragment> list;
    //@Optional
    /*@InjectView(R.id.content)
    View mContent;*/
    private LocalBroadcastManager mLocalBroadcastManager;
    public String curUpdateUrl;
    private Context mContext;
    private SimpleDateFormat dateFormat;
    private String provinceId;
    public String strTags;
    private MyFragmentPagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mContext = this;
        /*RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);*/

        UmengUpdateAgent.update(this);
        FeedbackAgent agent = new FeedbackAgent(mContext);
        agent.sync();

        userId = DefaultShared.getString(RegisterLogin.USERUID, App.DEFAULT_USER_ID);
        userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
        //设置别名

        mLoginPrompt.setAlpha(0.9f);
        if (!userId.equals(App.DEFAULT_USER_ID)) {
            provinceId = DefaultShared.getString(App.PROVINCEID, App.DEFAULT_PROVINCEID);
            if (provinceId.equals(App.DEFAULT_PROVINCEID)) {
                provinceId = "0";
            } else if (provinceId.equals(App.DEFAULT_LIAO_LING_ID)) {
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

            Tag[] tagss = new Tag[]{tag, tag1};
            PushManager.getInstance().bindAlias(App.getContext(), "a" + userId);
            PushManager.getInstance().setTag(App.getContext(), tagss);

            PushManager.getInstance().turnOnPush(App.getContext());
            String clientId = PushManager.getInstance().getClientid(App.getContext());
            String test = "a" + clientId;
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initTab = bundle.getInt(RegisterStep3Activity.INIT_TYPE);
        }

        mTabIndicators = new ArrayList<>();

        fragmentManager = getSupportFragmentManager();
        initView();


        initSearch();

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


    private void initSearch() {
        AppObservable.bindActivity(this, mApi.getHotProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()))
                .subscribe(new Observer<HotSearch>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HotSearch hotSearch) {
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
                });
    }

    @OnClick(R.id.btn_login)
    public void onCLickLogin() {
        IntentUtil.startActivity(MainActivity.this, RegisterLogin.class);
    }

    @OnClick(R.id.remove_login)
    public void onClickRemoveLogin() {
        mLoginPrompt.setVisibility(View.GONE);
    }

    private Fragment mFragmentContent;

    private void initMineAttention() {
        if (userId.equals(App.DEFAULT_USER_ID)) return;
        AppObservable.bindActivity(this, mApi.getMineAttentionGoodsList(userId, userType))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<MineAttentionResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MineAttentionResult list) {
                        List<AttentionGoods> data = new ArrayList<>();
                        new Delete().
                                from(AttentionGoods.class)
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
                            goods1.discount = entity.discount;
                            goods1.discountType = entity.discount_type;
                            goods1.save();
                            data.add(goods1);
                        }
                    }
                });
    }


    public void initConfig() {
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

        AppObservable.bindActivity(this, mApi.getCarList(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCarListServer);
    }

    Observer<CarList> getCarListServer = new Observer<CarList>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(final CarList carList) {
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

    private void checkVersion() {
        curVersionNumber = Config.getCurrentVersion();
        String url = String.format(ConstantURL.CHECKVERSION, curVersionNumber);
        RequestManager.addRequest(new GsonRequest(url, VersionUpdate.class,
                updateApp(), errorListener()), this);
    }

    protected com.android.volley.Response.ErrorListener errorListener() {
        return error -> ToastUtil.showLongToast(MainActivity.this, "网络连接失败");
    }

    private Response.Listener<VersionUpdate> updateApp() {
        return versionUpdate -> TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
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

    @Override
    protected void onResume() {
        super.onResume();
        refreshCarListCount(null);
        MobclickAgent.onPageStart("SplashScreen");
        MobclickAgent.onResume(this);
        userId = DefaultShared.getString(RegisterLogin.USERUID, App.DEFAULT_USER_ID);

        if (!userId.equals(App.DEFAULT_USER_ID)) {
            mLoginPrompt.setVisibility(View.GONE);
        } else {
            mLoginPrompt.setVisibility(View.VISIBLE);
        }

        String lastSECONDKILLDate = DefaultShared.getString(Config.SECOND_KILL, Config.DEFAULT_SECOND_KILL);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        int chooseIndex = getIntent().getIntExtra(AdvertisementWebActivity.WEB_CHOOSE_SURFACE, AdvertisementWebActivity.WEB_CHOOSE_DEFAULT_SURFACE);
        if (chooseIndex != AdvertisementWebActivity.WEB_CHOOSE_DEFAULT_SURFACE) {
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
        Fragment homeFragment = new HomeFragment();
        Fragment categoryFragment = new CategoryFragment();
        Fragment mineFragment1 = new MineFragment1();
        Fragment carFragment = new CarFragment();
        Fragment orderFragment = new NewOrderFragment();

        Fragment[] fragments = new Fragment[]{
                homeFragment,categoryFragment,mineFragment1,carFragment,orderFragment};

        boolean [] fragmentsUpdateFlag =new boolean []{false, false, false, false,false};

        mPageAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager())
                .setFragment(fragments)
                .setFragmentUpdateFlag(fragmentsUpdateFlag);

        mViewPager.setAdapter(mPageAdapter);

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
    }

    public void setViewPagerItem(int index){
        mViewPager.setCurrentItem(index,false);
        lastIndex = index;
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

        setViewPagerItem(index);
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
        TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                try {
                    Glide.get(MainActivity.this).clearDiskCache();
                    Glide.get(MainActivity.this).clearMemory();
                } catch (Exception e) {
                }

                return null;
            }
        });
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

        setViewPagerItem(index);

        String getIntegralUrl = String.format(ConstantURL.DAILY_SIGN_IN, userId, Config.SEND_QUERY_INTEGRAL);
        RequestManager.addRequest(new GsonRequest(getIntegralUrl, DailySignIn.class,
                getIntegralRequest(), errorListener()), this);
    }

    private Response.Listener<DailySignIn> getIntegralRequest() {
        return dailySignIn -> TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                new Update(User.class)
                        .set("integral = ?", dailySignIn.total)
                        .execute();
                return null;
            }
        });
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
            if (index > 99) {
                mBadgeView.setText("99+");
            } else {
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
        UpdateVersionTask task = new UpdateVersionTask(MainActivity.this, UpdateVersionTask.NEED_VIEW);
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
        sendMessageToOrderFrame(type);
    }
    public void sendMessageToOrderFrame(int index){
        NewOrderFragment fragment = null;
        List<Fragment> list = MainActivity.this.getSupportFragmentManager().getFragments();
        for(Fragment f1 : list){
            if(f1 instanceof NewOrderFragment){
                fragment = (NewOrderFragment)f1;
                fragment.setCurrentItem(index);
            }
        }
        //fragment.getChildFragmentManager().getFragments();
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

        setViewPagerItem(index);
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
            System.exit(0);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        scroll(hasFocus);
    }


    @Override
    public void scroll(boolean hasFocuse) {
    }
}