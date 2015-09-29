package com.louie.luntonghui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.adapter.HomeRecyclerViewAdapter;
import com.louie.luntonghui.data.AdvertisementInfo;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.model.db.User;
import com.louie.luntonghui.model.result.DailySignIn;
import com.louie.luntonghui.model.result.HomeAdver;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.Home.SecondKillActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

/**
 * Created by Louie on 2015/5/28.
 */
public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener{
    @InjectView(R.id.home_viewpager)
    InfiniteIndicatorLayout mAnimCircleIndicator;
    //@InjectView(R.id.home_viewpager)


    @InjectView(R.id.home_recommend)
    LinearLayout mRecommend;
    @InjectView(R.id.home_recyclerview)
    RecyclerView mRecyclerView;
    @InjectView(R.id.icon)
    ImageView icon;

    @InjectView(R.id.ai_tangze)
    LinearLayout aiTangze;
    @InjectView(R.id.ai_jiluer)
    LinearLayout aiJiluer;
    @InjectView(R.id.ai_jiluer1)
    LinearLayout aiJiluer1;
    @InjectView(R.id.ai_jiluer2)
    LinearLayout aiJiluer2;
    @InjectView(R.id.ai_chongdianqi)
    LinearLayout aiChongdianqi;
    @InjectView(R.id.ai_luntai)
    LinearLayout aiLuntai;
    @InjectView(R.id.mine_attention)
    LinearLayout mineAttention;

    @InjectView(R.id.qiandao)
    LinearLayout qiandao;
    @InjectView(R.id.activity_area)
    TextView activityArea;
    @InjectView(R.id.qiandao_icon)
    ImageView qiandaoIcon;
    @InjectView(R.id.qiandao_text)
    TextView qiandaoText;

    @InjectView(R.id.region)
    TextView tvRegion;

    private List<AdvertisementInfo> imageIdList;
    private List<Integer> recommendPicList;
    private HomeRecyclerViewAdapter mAdapter;
    private int curPager = 1;
    public SearchListener mSearchListener;
    private Context mContext;
    private String[] activtyArea;
    private ProgressDialog mProgressDialog;
    public final String WEB_URL = "web_url";

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

    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, contentView);

        initIntegral();

        initAdver();
        //initAdver();

        //testAnimCircleIndicator(); //广告页
        //initRecommend(); //今天剁手价
        //loadRecyclerView(); //产品列表
        initRegion();
        return contentView;
    }

    private void initRegion() {
        String region = DefaultShared.getString(App.PROVINCE,App.DEFAULT_PROVINCE);
        if(TextUtils.isEmpty(region)) region = App.DEFAULT_PROVINCE;
        tvRegion.setText(region);
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

        switch (display){
            case "6":
                display = "0";
                break;
            case "388":
                display = "1";
                break;
        }

        String url = String.format(ConstantURL.GOODS_HOME_ADVER, ctype, cityId,display);

        RequestManager.addRequest(new GsonRequest(url, HomeAdver.class, getHomeAdver(), errorListener()), this);

    }

    private Response.Listener<HomeAdver> getHomeAdver() {
        return new Response.Listener<HomeAdver>() {

            @Override
            public void onResponse(final HomeAdver homeAdver) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<HomeAdver.ListallcatEntity>>() {
                    @Override
                    protected List<HomeAdver.ListallcatEntity> doInBackground(Object... params) {
                        List<HomeAdver.ListallcatEntity> advList = new ArrayList<HomeAdver.ListallcatEntity>();
                        if (homeAdver !=null && homeAdver.listallcat != null) {
                            for (int i = 0; i < homeAdver.listallcat.size(); i++) {
                                advList.add(homeAdver.listallcat.get(i));
                            }
                        }
                        return advList;
                    }

                    @Override
                    protected void onPostExecute(List<HomeAdver.ListallcatEntity> list) {
                        try {
                            //mAnimCircleIndicator.removeAllSliders();
                            for (int i = 0; i < list.size(); i++) {
                                DefaultSliderView textSliderView = new DefaultSliderView(mContext);
                                textSliderView
                                        .image(list.get(i).ad_code)
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(HomeFragment.this);
                                        /*.showImageResForEmpty(R.drawable.default_image_in_no_source)
                                        .showImageResForError(R.drawable.default_image_in_no_source);*/
                                //textSliderView.getUrl()
                                Bundle bundle = textSliderView.getBundle();
                                bundle.putString(WEB_URL,list.get(i).ad_link);

                                mAnimCircleIndicator.addSlider(textSliderView);
                            }
                            mAnimCircleIndicator.setIndicatorPosition(InfiniteIndicatorLayout.
                                    IndicatorPosition.Center_Bottom);
                            mAnimCircleIndicator.startAutoScroll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
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
            int index = mRecommend.getChildCount();
            if (index > 1) {
                mRecommend.addView(mImageView, index - 1);
            } else {
                mRecommend.addView(mImageView);
            }
        }
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Bundle bundle = slider.getBundle();

        String url = bundle.getString(WEB_URL);
        bundle.putString(AdvertisementWebActivity.URL, url);
        IntentUtil.startActivity(getActivity(),AdvertisementWebActivity.class,bundle);
    }

    @OnClick({R.id.search,R.id.search_text})
    public void OnSearch() {
        //mSearchListener.beginSearch();
        /*getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,new GoodsDetailFragment())
                .commitAllowingStateLoss();*/
        IntentUtil.startActivity(getActivity(), SearchActivity.class);
    }


    @OnClick(R.id.ai_tangze)
    public void onTangZe() {
        onSearch(0);
    }

    @OnClick(R.id.ai_jiluer)
    public void onJiLuer() {
        onSearch(1);
    }

    @OnClick(R.id.ai_jiluer1)
    public void onJiLuer1() {
        onSearch(2);
    }

    @OnClick(R.id.ai_jiluer2)
    public void onJiLuer2() {
        onSearch(3);
    }

    @OnClick(R.id.ai_chongdianqi)
    public void onChongdianqi() {
        onSearch(4);
    }

    @OnClick(R.id.ai_luntai)
    public void onLunTai() {
        onSearch(5);
    }

    @OnClick(R.id.mine_attention)
    public void onAttention() {
        //ToastUtil.showShortToast(mContext, R.string.does_not_deploy);
        IntentUtil.startActivityWiehAlpha(getActivity(), MineAttentionActivity.class);
    }



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
        qiandaoIcon.setImageResource(R.drawable.has_sign_in);
        qiandaoText.setText(R.string.has_signin);
        qiandaoText.setTextColor(getResources().getColor(R.color.order_font_choose));
        qiandao.setEnabled(false);
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
        mAnimCircleIndicator.stopAutoScroll();
        ButterKnife.reset(this);
    }



    public interface SearchListener {
        public void beginSearch();
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

    @OnClick(R.id.new_goods)
    public void newGoods() {
        //ToastUtil.showShortToast(mContext, R.string.waiting_for);
        Bundle bundle = new Bundle();
        bundle.putInt(GoodsDetailActivity.NEW_GOODS,GoodsDetailActivity.IS_NEW_GOODS);
        IntentUtil.startActivity(getActivity(),GoodsDetailActivity.class,bundle);
    }

    @OnClick(R.id.second_kill)
    public void onSecondKill() {

        //ToastUtil.showShortToast(mContext, R.string.waiting_for);
        //ToastUtil.showShortToast(mContext, R.string.waiting_for);
        //mFastQueryListener.query("产品秒杀");

        IntentUtil.startActivity(getActivity(), SecondKillActivity.class);
    }
}