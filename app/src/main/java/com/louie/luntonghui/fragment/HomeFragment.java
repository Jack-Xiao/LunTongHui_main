package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.HomeGoodsAdvertisementAdapter;
import com.louie.luntonghui.adapter.HomeRecyclerViewAdapter;
import com.louie.luntonghui.data.AdvertisementInfo;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.DailySignIn;
import com.louie.luntonghui.model.result.HomeAdver;
import com.louie.luntonghui.model.result.HomeAdversion;
import com.louie.luntonghui.net.RequestManager;
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
import com.louie.luntonghui.view.MyGridView;
import com.louie.luntonghui.view.MyPtrClassicDefaultHeader;
import com.louie.luntonghui.view.MyScrollView;
import com.louie.luntonghui.view.NetworkImageHolderView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Louie on 2015/5/28.
 */
public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener
        ,NetworkImageHolderView.OnSelectItemListener, MyScrollView.OnScrollListener {
    //@InjectView(R.id.iv_zoom)
    InfiniteIndicatorLayout mAnimCircleIndicator;

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
        imageIdList = new ArrayList<AdvertisementInfo>();
        imageIdList.add(new AdvertisementInfo("1", R.drawable.cu));
        imageIdList.add(new AdvertisementInfo("2", R.drawable.han));
        imageIdList.add(new AdvertisementInfo("3", R.drawable.hui));
        imageIdList.add(new AdvertisementInfo("4", R.drawable.bao));

        recommendPicList = new ArrayList<Integer>();
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

    private TextView tvGoodsRecommendAdvGoods1Title;
    private ImageView ivGoodsRecommendAdvGoods1Image;
    private MyGridView mvHomeGoodsPart1;

    private TextView tvGoodsRecommendAdvGoods2Title;
    private ImageView ivGoodsRecommendAdvGoods2Image;
    private MyGridView mvHomeGoodsPart2;
    private HomeGoodsAdvertisementAdapter mGoodsAdvApater1;
    private HomeGoodsAdvertisementAdapter mGoodsAdvApater2;

    private TextView tvGoodsRecommendAdvGoods3Title;
    private ImageView ivGoodsRecommendAdvGoods3Image;
    private MyGridView mvHomeGoodsPart3;
    private HomeGoodsAdvertisementAdapter mGoodsAdvApater3;
    private LinearLayout mLinearLayoutIndicator;
    private LinearLayout.LayoutParams mLayoutIndictorParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
    , ViewGroup.LayoutParams.WRAP_CONTENT);
    private PtrFrameLayout ptrFrameLayout;

    private View.OnClickListener mOnClickSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentUtil.startActivity(getActivity(),SearchActivity.class);
        }
    };

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
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        onScroll(scrollView.getScrollY());
                    }
                });

        mSearchParent1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                searchLayoutTop = mSearchParent1.getBottom();//获取searchLayout的顶部位置
            }
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

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String catId = v.getTag().toString();
            String url = ConstantURL.CATEGORYGOODS + catId;
            Bundle bundle = new Bundle();
            bundle.putString(GoodsDetailActivity.GOODSDETAILURL, url);
            bundle.putString(GoodsDetailActivity.GOODSDETAILID,catId);
            IntentUtil.startActivity(getActivity(),GoodsDetailActivity.class,bundle);
        }
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

        tvGoodsRecommendAdvGoods1Title = (TextView)contentView1.findViewById(R.id.goods_recommend_adv_goods1_title);
        ivGoodsRecommendAdvGoods1Image = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_goods1_image);
        mvHomeGoodsPart1 = (MyGridView)contentView1.findViewById(R.id.home_goods_part1);

        tvGoodsRecommendAdvGoods2Title = (TextView)contentView1.findViewById(R.id.goods_recommend_adv_goods2_title);
        ivGoodsRecommendAdvGoods2Image = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_goods2_image);
        mvHomeGoodsPart2 = (MyGridView)contentView1.findViewById(R.id.home_goods_part2);

        tvGoodsRecommendAdvGoods3Title = (TextView)contentView1.findViewById(R.id.goods_recommend_adv_goods3_title);
        ivGoodsRecommendAdvGoods3Image = (ImageView)contentView1.findViewById(R.id.goods_recommend_adv_goods3_image);
        mvHomeGoodsPart3 = (MyGridView)contentView1.findViewById(R.id.home_goods_part3);


        mLinearEverydayKill.setOnClickListener(onClickEveryDayKill);
        mLinearMineAttention.setOnClickListener(onClickMineAttention);
        mLinearGroup.setOnClickListener(onClickGroupListener);
        mLinearHotGoods.setOnClickListener(onClickHotGoods);
        mLinearBrandStreet.setOnClickListener(onClickBrandStreet);
        mLinearSalePrice.setOnClickListener(onClickSpacialPrice);

    }

    private View.OnClickListener onClickEveryDayKill = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentUtil.startActivity(getActivity(), SecondKillActivity.class);
        }
    };

    private View.OnClickListener onClickSpacialPrice = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = String.format(ConstantURL.SPECIAL_PRICE,userId);
            Bundle bundle = new Bundle();
            bundle.putString(WebActivity.WEB_URL,url);
            IntentUtil.startActivity(getActivity(),WebActivity.class,bundle);
        }
    };

    private View.OnClickListener onClickBrandStreet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = ConstantURL.BRAND_STREET;
            Bundle bundle = new Bundle();
            bundle.putString(WebActivity.WEB_URL,url);
            IntentUtil.startActivity(getActivity(),WebActivity.class,bundle);
        }
    };

    private View.OnClickListener onClickMineAttention = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentUtil.startActivity(getActivity(),MineAttentionActivity.class);
        }
    };

    private View.OnClickListener onClickHotGoods = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mFastQueryListener.query(Config.FACTORY_ID);
        }
    };

    private View.OnClickListener onClickGroupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              IntentUtil.startActivity(getActivity(), WebTogetherGroupActivity.class);
        }
    };

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

        String url = String.format(ConstantURL.GOODS_HOME_ADVER, userId, cityId,display);


        RequestManager.addRequest(new GsonRequest(url, HomeAdver.class, getHomeAdver(), errorListener()), this);


        String url1 =String.format(ConstantURL.HOME_ADV_ARRAY,sourceDisplay,userId);

        RequestManager.addRequest(new GsonRequest(url1, HomeAdversion.class,
                getHomeAdverGoods(), errorListener()),this);
    }

    private Response.Listener<HomeAdversion> getHomeAdverGoods(){
        return new Response.Listener<HomeAdversion>() {
            @Override
            public void onResponse(HomeAdversion response) {

                tvGoodsRecommendTitle.setText(response.goods_recommend.title);
                /*Picasso.with(mContext).load(response.goods_recommend.recommend_adv.img)
                        *//*.placeholder(R.drawable.default_image_in_no_source)
                    .error(R.drawable.default_image_in_no_source)*//*
                        .into(ivGoodsRecommendAdvTop);*/

                Picasso.with(mContext)
                        .load(response.goods_recommend.recommend_adv.img)
                        .into(ivGoodsRecommendAdvTop);

                ivGoodsRecommendAdvTop.setTag(response.goods_recommend.recommend_adv.cat_id);

                Picasso.with(mContext).load(response.goods_recommend.recommend_adv_left.img)
                        /*.placeholder(R.drawable.default_image_in_no_source)
                        .error(R.drawable.default_image_in_no_source)*/
                        .into(ivGoodsRecommendAdvLeft);

                ivGoodsRecommendAdvLeft.setTag(response.goods_recommend.recommend_adv_left.cat_id);

                Picasso.with(mContext).load(response.goods_recommend.recommend_adv_right1.img)
                        /*.placeholder(R.drawable.default_image_in_no_source)
                        .error(R.drawable.default_image_in_no_source)*/
                        .into(ivGoodsRecommendAdvRight1);
                ivGoodsRecommendAdvRight1.setTag(response.goods_recommend.recommend_adv_right1.cat_id);


                Picasso.with(mContext).load(response.goods_recommend.recommend_adv_right2.img)
                        /*.placeholder(R.drawable.default_image_in_no_source)
                        .error(R.drawable.default_image_in_no_source)*/
                        .into(ivGoodsRecommendAdvRight2);
                ivGoodsRecommendAdvRight2.setTag(response.goods_recommend.recommend_adv_right2.cat_id);


                tvGoodsRecommendAdvGoods1Title.setText(response.goods_adv_part.get(0).title);
                Picasso.with(mContext).load(response.goods_adv_part.get(0).goods_part_adv.img)
                        /*.placeholder(R.drawable.default_image_in_no_source)
                        .error(R.drawable.default_image_in_no_source)*/
                        .into(ivGoodsRecommendAdvGoods1Image);
                ivGoodsRecommendAdvGoods1Image.setTag(response.goods_adv_part.get(0).goods_part_adv.cat_id);


                tvGoodsRecommendAdvGoods2Title.setText(response.goods_adv_part.get(1).title);
                Picasso.with(mContext).load(response.goods_adv_part.get(1).goods_part_adv.img)
                       /* .placeholder(R.drawable.default_image_in_no_source)
                        .error(R.drawable.default_image_in_no_source)*/
                        .into(ivGoodsRecommendAdvGoods2Image);

                ivGoodsRecommendAdvGoods2Image.setTag(response.goods_adv_part.get(1).goods_part_adv.cat_id);


                tvGoodsRecommendAdvGoods3Title.setText(response.goods_adv_part.get(2).title);
                Picasso.with(mContext).load(response.goods_adv_part.get(2).goods_part_adv.img)
                        /*.placeholder(R.drawable.default_image_in_no_source)
                        .error(R.drawable.default_image_in_no_source)*/
                        .into(ivGoodsRecommendAdvGoods3Image);
                ivGoodsRecommendAdvGoods3Image.setTag(response.goods_adv_part.get(2).goods_part_adv.cat_id);

                mGoodsAdvApater1 = new HomeGoodsAdvertisementAdapter(getActivity(),response.goods_adv_part.get(0).goods_part_adv_array);
                mGoodsAdvApater2 = new HomeGoodsAdvertisementAdapter(getActivity(),response.goods_adv_part.get(1).goods_part_adv_array);
                mGoodsAdvApater3 = new HomeGoodsAdvertisementAdapter(getActivity(),response.goods_adv_part.get(2).goods_part_adv_array);

                ivGoodsRecommendAdvTop.setOnClickListener(mOnClickListener);
                ivGoodsRecommendAdvLeft.setOnClickListener(mOnClickListener);
                ivGoodsRecommendAdvRight1.setOnClickListener(mOnClickListener);
                ivGoodsRecommendAdvRight2.setOnClickListener(mOnClickListener);

                ivGoodsRecommendAdvGoods1Image.setOnClickListener(mOnClickListener);
                ivGoodsRecommendAdvGoods2Image.setOnClickListener(mOnClickListener);
                ivGoodsRecommendAdvGoods3Image.setOnClickListener(mOnClickListener);


                mvHomeGoodsPart1.setAdapter(mGoodsAdvApater1);
                mvHomeGoodsPart2.setAdapter(mGoodsAdvApater2);
                mvHomeGoodsPart3.setAdapter(mGoodsAdvApater3);



                ptrFrameLayout.refreshComplete();

                /*mGoodsAdvApater1.addData(response.goods_adv_part.get(0).goods_part_adv_array);
                mGoodsAdvApater2.addData(response.goods_adv_part.get(1).goods_part_adv_array);
                mGoodsAdvApater3.addData(response.goods_adv_part.get(2).goods_part_adv_array);*/
            }
        };
    }
    private void focusable(){
        convenientBanner.setFocusable(true);
        convenientBanner.setFocusableInTouchMode(true);
        convenientBanner.requestFocus();
    }

    private Response.Listener<HomeAdver> getHomeAdver() {
        return new Response.Listener<HomeAdver>() {
            @Override
            public void onResponse(final HomeAdver homeAdver) {

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

                convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView(getActivity(),HomeFragment.this);
                    }
                },urls);
                convenientBanner.startTurning(TURNNING_TIME);
                focusable();
            }
        };
    }

    private void initRecommend() {
        for (Integer ids : recommendPicList) {
            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mLayoutParams.setMargins(0, 10, 15, 10);
            ImageView mImageView = new ImageView(getActivity());
            mImageView.setLayoutParams(mLayoutParams);
            mImageView.setImageResource(ids);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setTag(ids);
            /*int index = mRecommend.getChildCount();
            if (index > 1) {
                mRecommend.addView(mImageView, index - 1);
            } else {
                mRecommend.addView(mImageView);
            }*/
        }
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Bundle bundle = slider.getBundle();

        String url = bundle.getString(WEB_URL);

        if(userId.equals(RegisterLogin.DEFAULT_USER_ID)){
            IntentUtil.startActivityFromMain(getActivity(), RegisterLogin.class);
            return;
        }

        if(TextUtils.isEmpty(url)){
            ToastUtil.showShortToast(mContext,"功能正在赶制中...");
            return;
        }

        bundle.putString(AdvertisementWebActivity.URL, url);
        IntentUtil.startActivity(getActivity(),AdvertisementWebActivity.class,bundle);
    }

    @Optional
    @OnClick({R.id.search,R.id.search_text})
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
        //ToastUtil.showShortToast(mContext, R.string.does_not_deploy);
        IntentUtil.startActivityWiehAlpha(getActivity(), MineAttentionActivity.class);
    }


    @Optional
    @OnClick(R.id.qiandao)
    public void onQiandao() {
        //ToastUtil.showShortToast(getActivity(),);
        mProgressDialog.show();
        String userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);
        String url = String.format(ConstantURL.DAILY_SIGN_IN, userId, Config.SEND_SIGN_IN);
        RequestManager.addRequest(new GsonRequest(url, DailySignIn.class, singInRespose(),
                errorListener()), this);
    }

    private Response.Listener<DailySignIn> singInRespose() {
        return new Response.Listener<DailySignIn>() {

            @Override
            public void onResponse(final DailySignIn dailySignIn) {
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
        bundle.putString(SearchActivity.SEARCH_CONTENT,
                activtyArea[index]);
        IntentUtil.startActivity(getActivity(), GoodsDetailActivity.class, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mAnimCircleIndicator !=null)mAnimCircleIndicator.stopAutoScroll();
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
        //ToastUtil.showShortToast(mContext, R.string.waiting_for);
        Bundle bundle = new Bundle();
        bundle.putInt(GoodsDetailActivity.NEW_GOODS,GoodsDetailActivity.IS_NEW_GOODS);
        IntentUtil.startActivity(getActivity(),GoodsDetailActivity.class,bundle);
    }

    @Optional
    @OnClick(R.id.second_kill)
    public void onSecondKill() {

        //ToastUtil.showShortToast(mContext, R.string.waiting_for);
        //ToastUtil.showShortToast(mContext, R.string.waiting_for);
        //mFastQueryListener.query("产品秒杀");

        IntentUtil.startActivity(getActivity(), SecondKillActivity.class);
    }
}