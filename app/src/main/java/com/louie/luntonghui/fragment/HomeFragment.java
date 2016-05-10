package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.HomeAdvertisementArrayAdapter;
import com.louie.luntonghui.adapter.HomeRecyclerViewAdapter;
import com.louie.luntonghui.data.AdvertisementInfo;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.DBHomeAdvertisement;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.DailySignIn;
import com.louie.luntonghui.model.result.HomeAdver;
import com.louie.luntonghui.model.result.HomeAdversion;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.Home.MipcaActivityCapture;
import com.louie.luntonghui.ui.Home.SecondKillActivity;
import com.louie.luntonghui.ui.Home.WebActivity;
import com.louie.luntonghui.ui.Home.WebTogetherGroupActivity;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.ui.mine.MineAttentionActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.ui.search.SearchActivity;
import com.louie.luntonghui.ui.web.AdvertisementWebActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.MyListView;
import com.louie.luntonghui.view.MyPtrClassicDefaultHeader;
import com.louie.luntonghui.view.MyScrollView;
import com.louie.luntonghui.view.NetworkImageHolderView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Louie on 2015/5/28.
 */
public class HomeFragment extends BaseFragment implements
        NetworkImageHolderView.OnSelectItemListener, MyScrollView.OnScrollListener {
    //@InjectView(R.id.iv_zoom)

    TextView tvRegion;
    TextView tvRegion1;

    //@InjectView(R.id.scroll_view)
    //PullToZoomScrollViewEx scrollView;
    MyScrollView scrollView;
    RelativeLayout mSearch;
    FrameLayout mSearchParent1;
    LinearLayout mSearchParent2;

    private List<AdvertisementInfo> imageIdList;
    private List<Integer> recommendPicList;
    private HomeRecyclerViewAdapter mAdapter;
    private int curPager = 1;
    public SearchListener mSearchListener;
    private Context mContext;
    private String[] activtyArea;
    private ProgressDialog mProgressDialog;
    public final String WEB_URL = "web_url";
    private DisplayMetrics localDisplayMetrics;
    private FrameLayout.LayoutParams mRelativeLayuotParams;
    private LinearLayout.LayoutParams localObject;
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    public static final int TURNNING_TIME = 3000;
    private ArrayList<Integer> localImages = new ArrayList<>();
    public static Map<Integer,String> webUrl;
    private ImageView[] pointViews;
    private int searchLayoutTop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        imageIdList = new ArrayList<>();
        imageIdList.add(new AdvertisementInfo("1", R.drawable.cu));
        imageIdList.add(new AdvertisementInfo("2", R.drawable.han));
        imageIdList.add(new AdvertisementInfo("3", R.drawable.hui));
        imageIdList.add(new AdvertisementInfo("4", R.drawable.bao));

        recommendPicList = new ArrayList<>();
        recommendPicList.add(R.drawable.cu);
        recommendPicList.add(R.drawable.han);
        recommendPicList.add(R.drawable.hui);
        recommendPicList.add(R.drawable.bao);

        mAdapter = new HomeRecyclerViewAdapter(getActivity());
        // mRecyclerView.setAdapter(mAdapter);

        activtyArea = getResources().getStringArray(R.array.activity_area_list);
        mProgressDialog = new ProgressDialog(mContext);
        webUrl = new HashMap<>();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private RelativeLayout mLinearNewGoods;
    private LinearLayout mLinearEverydayKill;
    private LinearLayout mLinearMineAttention;
    private LinearLayout mLinearQianDao;

    private RelativeLayout mLinearBrandStreet;
    private RelativeLayout mLinearSalePrice;

    private RelativeLayout mLinearHotGoods;
    private TextView mFactoryText;
    private LinearLayout mLinearGroup;

    private TextView tvGoodsRecommendTitle;
    private ImageView ivGoodsRecommendAdvTop;

    private ImageView ivGoodsRecommendAdvLeft;
    private ImageView ivGoodsRecommendAdvRight1;
    private ImageView ivGoodsRecommendAdvRight2;

    private LinearLayout.LayoutParams mLayoutIndictorParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
    , ViewGroup.LayoutParams.WRAP_CONTENT);
    private PtrFrameLayout ptrFrameLayout;
    private MyListView mListView;
    private HomeAdvertisementArrayAdapter arrayAdapter;

    private View.OnClickListener mOnClickSearchListener = v -> IntentUtil.startActivity(getActivity(),SearchActivity.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_home_new1, container, false);
        ButterKnife.inject(this, contentView);
        scrollView = (MyScrollView)contentView.findViewById(R.id.scroll_view);
        mSearch = (RelativeLayout)contentView.findViewById(R.id.toolbar_view1);
        convenientBanner = (ConvenientBanner)contentView.findViewById(R.id.banner);
        mSearchParent1 = (FrameLayout)contentView.findViewById(R.id.toolbar_parent1);
        mSearchParent2 = (LinearLayout)contentView.findViewById(R.id.toolbar_parent2);

        mSearchParent2.setVisibility(View.GONE);
        contentView.findViewById(R.id.fragment_ptr_home_ptr_frame).getViewTreeObserver().addOnGlobalLayoutListener(
                () -> onScroll(scrollView.getScrollY()));

        mSearchParent1.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            searchLayoutTop = mSearchParent1.getBottom();//获取searchLayout的顶部位置
        });

        ptrFrameLayout =(PtrFrameLayout)contentView.findViewById(R.id.fragment_ptr_home_ptr_frame);
        MyPtrClassicDefaultHeader header = new MyPtrClassicDefaultHeader(getActivity());
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initAdver();
                ptrFrameLayout.refreshComplete();
            }
        });

        scrollView.setOnScrollListener(this);
        tvRegion =(TextView)contentView.findViewById(R.id.region1);
        tvRegion1 = (TextView)contentView.findViewById(R.id.region);

        initContentView(contentView);

        initIntegral();

        initAdver();

        //testAnimCircleIndicator(); //广告页
        //initRecommend(); //今天剁手价
        //loadRecyclerView(); //产品列表
        initRegion();
        //initZoom();
        return contentView;
    }

    private View.OnClickListener mOnClickListener = v -> {
        //String catId = v.getTag().toString();
        String catId = v.getTag(R.string.position).toString();
        String url = ConstantURL.CATEGORYGOODS + catId;
        Bundle bundle = new Bundle();
        bundle.putString(GoodsDetailActivity.GOODSDETAILURL, url);
        bundle.putString(GoodsDetailActivity.GOODSDETAILID,catId);
        IntentUtil.startActivity(getActivity(),GoodsDetailActivity.class,bundle);
    };

    private void initContentView(View contentView1) {
        mLinearNewGoods = (RelativeLayout)contentView1.findViewById(R.id.new_goods);
        mLinearEverydayKill = (LinearLayout)contentView1.findViewById(R.id.everyday_kill);
        mLinearMineAttention = (LinearLayout)contentView1.findViewById(R.id.mine_attention);
        mLinearQianDao = (LinearLayout)contentView1.findViewById(R.id.qiandao);

        mLinearBrandStreet = (RelativeLayout)contentView1.findViewById(R.id.brand_street);
        mLinearSalePrice = (RelativeLayout)contentView1.findViewById(R.id.sale_price);
        mLinearHotGoods = (RelativeLayout)contentView1.findViewById(R.id.hot_goods);
        mLinearGroup = (LinearLayout)contentView1.findViewById(R.id.group);

        mFactoryText =(TextView)mLinearHotGoods.findViewById(R.id.factory_special_text);

        tvGoodsRecommendTitle = (TextView)contentView1.findViewById(R.id.goods_recommend_title);
        ivGoodsRecommendAdvTop = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_top);

        ivGoodsRecommendAdvLeft = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_left);
        ivGoodsRecommendAdvRight1 = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_right1);
        ivGoodsRecommendAdvRight2 = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_right2);

        /*tvGoodsRecommendAdvGoods1Title = (TextView)contentView1.findViewById(R.id.goods_recommend_adv_goods1_title);
        ivGoodsRecommendAdvGoods1Image = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_goods1_image);
        mvHomeGoodsPart1 = (MyGridView)contentView1.findViewById(R.id.home_goods_part1);

        tvGoodsRecommendAdvGoods2Title = (TextView)contentView1.findViewById(R.id.goods_recommend_adv_goods2_title);
        ivGoodsRecommendAdvGoods2Image = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_goods2_image);
        mvHomeGoodsPart2 = (MyGridView)contentView1.findViewById(R.id.home_goods_part2);

        tvGoodsRecommendAdvGoods3Title = (TextView)contentView1.findViewById(R.id.goods_recommend_adv_goods3_title);
        ivGoodsRecommendAdvGoods3Image = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_goods3_image);
        mvHomeGoodsPart3 = (MyGridView)contentView1.findViewById(R.id.home_goods_part3);*/

        mListView = (MyListView)contentView1.findViewById(R.id.list_view);

        mLinearEverydayKill.setOnClickListener(onClickEveryDayKill);
        mLinearMineAttention.setOnClickListener(onClickMineAttention);
        mLinearGroup.setOnClickListener(onClickGroupListener);
        mLinearHotGoods.setOnClickListener(onClickHotGoods);
        mLinearBrandStreet.setOnClickListener(onClickBrandStreet);
        mLinearSalePrice.setOnClickListener(onClickSpacialPrice);
    }

    private View.OnClickListener onClickEveryDayKill = v -> IntentUtil.startActivity(getActivity(), SecondKillActivity.class);

    private View.OnClickListener onClickSpacialPrice = v -> {
       /* String url = String.format(ConstantURL.SPECIAL_PRICE,userId);
        Bundle bundle = new Bundle();
        bundle.putString(WebActivity.WEB_URL,url);
        IntentUtil.startActivity(getActivity(),WebActivity.class,bundle);*/

        String url = String.format(ConstantURL.SPECIAL_PRICE,userId);
        Bundle bundle = new Bundle();
        bundle.putString(AdvertisementWebActivity.URL, url);
        bundle.putInt(AdvertisementWebActivity.MODEL,AdvertisementWebActivity.HAS_BAR_MODEL);
        IntentUtil.startActivity(getActivity(), AdvertisementWebActivity.class, bundle);
    };

    private View.OnClickListener onClickBrandStreet = v -> {
        String url = ConstantURL.BRAND_STREET;
        Bundle bundle = new Bundle();
        bundle.putString(WebActivity.WEB_URL,url);
        IntentUtil.startActivity(getActivity(),WebActivity.class,bundle);
    };

    private View.OnClickListener onClickMineAttention = v -> IntentUtil.startActivity(getActivity(),MineAttentionActivity.class);

    private View.OnClickListener onClickHotGoods = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mFastQueryListener.query(Config.FACTORY_ID);
        }
    };

    private View.OnClickListener onClickGroupListener = v -> IntentUtil.startActivity(getActivity(), WebTogetherGroupActivity.class);

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    private void initRegion() {
        String region = DefaultShared.getString(App.PROVINCE, App.DEFAULT_PROVINCE);
        if(TextUtils.isEmpty(region)) region = App.DEFAULT_PROVINCE;
        tvRegion.setText(region);
        tvRegion1.setText(region);
    }

    private void initIntegral() {
        boolean isSingIn = Config.isSignIn();
        if (isSingIn) {
            //qiandao.setBackgroundColor(getResources().getColor(R.color.useful_grey));
            signIn();
        } else {
            //qiandao.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private void initAdver() {
        String ctype = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
        String cityId = DefaultShared.getString(Config.CITY_ID, Config.DEFAULT_CITY_ID + "");

        String display = DefaultShared.getString(App.PROVINCEID,App.DEFAULT_PROVINCEID);
        String sourceDisplay = display;

        switch (display){
            case "6":
                display = "0";
                break;
            case "388":
                display = "1";
                break;
        }

        AppObservable.bindFragment(this,mApi.getHomeAdv(userId, cityId, display))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(advServer);

        AppObservable.bindFragment(this, mApi.getHomeAdvArray(sourceDisplay, userId))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(arrayServer);

        //Observable.zip()
    }

    Observer<HomeAdver> advServer = new Observer<HomeAdver>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(HomeAdver homeAdver) {
            new Delete()
                    .from(DBHomeAdvertisement.class)
                    .execute();
            DBHomeAdvertisement ad = new DBHomeAdvertisement();
            ad.adver = homeAdver.toString();
            ad.adverArray = "";
            ad.save();
            initRound(homeAdver);
        }
    };

    public synchronized void initRound(HomeAdver homeAdver){
        webUrl.clear();
        List<String> urls = new ArrayList<>();
        List<HomeAdver.ListallcatEntity> networkImages = new ArrayList<HomeAdver.ListallcatEntity>();
        if (homeAdver !=null && homeAdver.listallcat != null) {
            for (int i = 0; i < homeAdver.listallcat.size(); i++) {
                networkImages.add(homeAdver.listallcat.get(i));
                urls.add(homeAdver.listallcat.get(i).ad_code);
                webUrl.put(i, homeAdver.listallcat.get(i).ad_link);
            }
        }

        convenientBanner.setPages(() -> new NetworkImageHolderView(getActivity(),HomeFragment.this),urls);
        convenientBanner.startTurning(TURNNING_TIME);
        focusable();
    }

    Observer<HomeAdversion> arrayServer = new Observer<HomeAdversion>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showLongToast(mContext,"网络连接失败");
        }

        @Override
        public void onNext(HomeAdversion response) {
            tvGoodsRecommendTitle.setText(response.goods_recommend.title);

            Glide.with(mContext)
                    .load(response.goods_recommend.recommend_adv.img)
                    .crossFade()
                    .into(ivGoodsRecommendAdvTop);
            ivGoodsRecommendAdvTop.setTag(R.string.position,
                    response.goods_recommend.recommend_adv.cat_id);

            Glide.with(mContext).load(response.goods_recommend.recommend_adv_left.img)
                    .crossFade()
                    .into(ivGoodsRecommendAdvLeft);

            ivGoodsRecommendAdvLeft.setTag(R.string.position,
                    response.goods_recommend.recommend_adv_left.cat_id);

            Glide.with(mContext).load(response.goods_recommend.recommend_adv_right1.img)
                    .crossFade()
                    .into(ivGoodsRecommendAdvRight1);
            ivGoodsRecommendAdvRight1.setTag(R.string.position,response.goods_recommend.recommend_adv_right1.cat_id);

            Glide.with(mContext).load(response.goods_recommend.recommend_adv_right2.img)
                    .crossFade()
                    .into(ivGoodsRecommendAdvRight2);

            ivGoodsRecommendAdvRight2.setTag(R.string.position,response.goods_recommend.recommend_adv_right2.cat_id);

            ivGoodsRecommendAdvTop.setOnClickListener(mOnClickListener);
            ivGoodsRecommendAdvLeft.setOnClickListener(mOnClickListener);
            ivGoodsRecommendAdvRight1.setOnClickListener(mOnClickListener);
            ivGoodsRecommendAdvRight2.setOnClickListener(mOnClickListener);

            arrayAdapter = new HomeAdvertisementArrayAdapter(getActivity(),response.goods_adv_part);
            mListView.setAdapter(arrayAdapter);

            ptrFrameLayout.refreshComplete();
        }
    };

    private void focusable() {
        convenientBanner.setFocusable(true);
        convenientBanner.setFocusableInTouchMode(true);
        convenientBanner.requestFocus();
    }


    @Optional
    @OnClick({R.id.search,R.id.search_text,R.id.search_content,R.id.search_1,R.id.search_text_1,R.id.search_content_1})
    public void OnSearch() {
        //mSearchListener.beginSearch();
        /*getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,new GoodsDetailFragment())
                .commitAllowingStateLoss();*/
        IntentUtil.startActivity(getActivity(), SearchActivity.class);
    }

    @Optional
    @OnClick(R.id.ai_tangze)
    public void onTangZe() {
        onSearch(0);
    }

    @Optional
    @OnClick(R.id.ai_jiluer)
    public void onJiLuer() {
        onSearch(1);
    }

    @Optional
    @OnClick(R.id.ai_jiluer1)
    public void onJiLuer1() {
        onSearch(2);
    }

    @Optional
    @OnClick(R.id.ai_jiluer2)
    public void onJiLuer2() {
        onSearch(3);
    }

    @Optional
    @OnClick(R.id.ai_chongdianqi)
    public void onChongdianqi() {
        onSearch(4);
    }

    @Optional
    @OnClick(R.id.ai_luntai)
    public void onLunTai() {
        onSearch(5);
    }

    @Optional
    @OnClick(R.id.mine_attention)
    public void onAttention() {
        IntentUtil.startActivityWiehAlpha(getActivity(), MineAttentionActivity.class);
    }


    @Optional
    @OnClick(R.id.qiandao)
    public void onQiandao() {
        mProgressDialog.show();
        String userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        String url = String.format(ConstantURL.DAILY_SIGN_IN, userId, Config.SEND_SIGN_IN);
        RequestManager.addRequest(new GsonRequest(url, DailySignIn.class, singInRespose(),
                errorListener()), this);
    }

    private Response.Listener<DailySignIn> singInRespose() {
        return dailySignIn -> {
            mProgressDialog.dismiss();
            long currSignIn = System.currentTimeMillis();
            if (dailySignIn.code.equals(SUCCESSCODE)) {
                DefaultShared.putLong(Config.LAST_SING_IN_TIME, currSignIn);
                ToastUtil.showShortToast(mContext, dailySignIn.msg);
                //qiandao.setBackgroundColor(getResources().getColor(R.color.useful_grey));
                //qiandao.set;
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        new Update(User.class)
                                .set("integral = ?", dailySignIn.total)
                                .where("uid = ?", userId)
                                .execute();
                        return null;
                    }
                });

                signIn();
            } else {
                DefaultShared.putLong(Config.LAST_SING_IN_TIME, currSignIn);
                signIn();
                ToastUtil.showShortToast(mContext, dailySignIn.msg);
            }
        };
    }
    public void signIn(){
        /*qiandaoIcon.setImageResource(R.drawable.has_sign_in);
        qiandaoText.setText(R.string.has_signin);
        qiandaoText.setTextColor(getResources().getColor(R.color.order_font_choose));
        qiandao.setEnabled(false);*/
    }


    public void onSearch(int index) {
        Bundle bundle = new Bundle();
        bundle.putString(SearchActivity.SEARCH_CONTENT, activtyArea[index]);

        IntentUtil.startActivity(getActivity(), GoodsDetailActivity.class, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void selectItem(int index) {
        for(int i = 0; i <pointViews.length; i++){
            if(i == index ){
                pointViews[i].setImageResource(R.drawable.indicator_select);
            }else{
                pointViews[i].setImageResource(R.drawable.indictator_not_select);
            }
        }
    }

    @Override
    public void onScroll(int scrollY) {
        if(scrollY <= searchLayoutTop - mSearch.getHeight()){
            mSearchParent2.setVisibility(View.GONE);
        }else{
            mSearchParent2.setVisibility(View.VISIBLE);
        }
    }

    public interface SearchListener {
        public void beginSearch();
    }

    public interface ScrollListener{
        public void scroll(boolean hasFocuse);
    }

    private FastQueryListener mFastQueryListener;

    public interface FastQueryListener {
        public void query(String content);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mSearchListener = (SearchListener) activity;
            mFastQueryListener = (FastQueryListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SearchListener.");
        }
    }

    @Optional
    @OnClick(R.id.new_goods)
    public void newGoods() {
        Bundle bundle = new Bundle();
        bundle.putInt(GoodsDetailActivity.NEW_GOODS,GoodsDetailActivity.IS_NEW_GOODS);
        IntentUtil.startActivity(getActivity(), GoodsDetailActivity.class, bundle);
    }

    @Optional
    @OnClick(R.id.second_kill)
    public void onSecondKill() {
        IntentUtil.startActivity(getActivity(), SecondKillActivity.class);
    }

    @Optional
    @OnClick(R.id.qr_code1)
    public void onClickQrCode1(){
        qrCode();
    }

    @Optional
    @OnClick(R.id.qr_code2)
    public void onClickQrCode2(){
        qrCode();
    }

    public void qrCode(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(getActivity(), MipcaActivityCapture.class);
        getActivity().startActivity(intent);
        
        getActivity().overridePendingTransition(R.anim.activity_push_left_in, R.anim.activity_push_left_out);

    }
}