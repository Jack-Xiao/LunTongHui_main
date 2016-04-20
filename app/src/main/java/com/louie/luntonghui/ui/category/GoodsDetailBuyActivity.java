package com.louie.luntonghui.ui.category;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.ShowCarListEvent;
import com.louie.luntonghui.model.db.AttentionGoods;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.GoodsDetail;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.AddGoodsResult;
import com.louie.luntonghui.model.result.AddtionAttentionResult;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.DetailItem;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.BaseNormalActivity;
import com.louie.luntonghui.ui.ImageActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.register.RegisterLogin;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.TaskUtils;
import com.louie.luntonghui.util.ToastUtil;
import com.louie.luntonghui.view.BadgeView;
import com.louie.luntonghui.view.NetworkImageHolderViewProductDesc;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Observer;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import static com.louie.luntonghui.ui.category.GoodsDetailActivity.GOODSDETAILID;

/**
 * Created by Administrator on 2015/6/23.
 */
public class GoodsDetailBuyActivity extends BaseNormalActivity implements
        NetworkImageHolderViewProductDesc.OnSelectItemListener {
    public static final String DETAIL_ITEM = "detail_item";

    @InjectView(R.id.menu_list)
    ImageView menuList;

    @InjectView(R.id.progress)
    ProgressBar progress;

    /*@InjectView(R.id.product_viewpager)
    InfiniteIndicatorLayout productViewpager;*/
    @InjectView(R.id.product_viewpager)
    ConvenientBanner productViewpager;

    @InjectView(R.id.goods_name)
    TextView goodsName;

    @InjectView(R.id.goods_standard)
    TextView goodsStandard;

    @InjectView(R.id.goods_standard_value)
    TextView goodsStandardValue;

    @InjectView(R.id.marketp_price)
    TextView marketpPrice;

    @InjectView(R.id.shop_price)
    TextView shopPrice;

    @InjectView(R.id.goods_introduce)
    Button goodsIntroduce;

    @InjectView(R.id.content)
    EditText content;


    @InjectView(R.id.attention)
    ImageView attention;

    ImageView mCarList;

    @InjectView(R.id.into_car)
    TextView intoCar;
    @InjectView(R.id.toolbar_navigation)
    ImageView toolbarNavigation;
    @InjectView(R.id.toolbar_title)
    TextView toolbarTitle;
    @InjectView(R.id.sales_promotion_value)
    TextView salesPromotionValue;
    @InjectView(R.id.line_sales_promotion)
    LinearLayout lineSalesPromotion;

    @Optional
    @InjectView(R.id.minus)
    ImageButton minus;

    @Optional
    @InjectView(R.id.plus)
    ImageButton plus;

    @InjectView(R.id.car_list)
    ImageView carList;

    Toast toast;

    private String goodsId;

    private GoodsDetail mGoods;
    private List<DetailItem.ListallcatEntity.BoughtGoodsEntity> goodsBoughtList;
    //private ShowGoodsAdapter mAdapter;
    private ImageView[] indicator_imgs;
    private ImageView goodsPicture;
    private Context mContext;
    private String mGoodsId;
    private BadgeView badge;
    private ImageView mgAttention;
    private ImageView mgAttentionCancel;
    private BadgeView mBadgeView;
    private ShoppingCar mCar;
    private String[] imgList;
    public static final String sliderName = "extra";
    public static final String IMAGES = "images";
    public static final String INDEX = "index";
    //private List<String> attentionGoodsIds;
    private GoodsDetail currentGoodsDetail;
    private HashMap<String, String> attentionGoodsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_item_buy);
        ButterKnife.inject(this);
        App.getBusInstance().register(this);
        //+++ 20121214 测试内存泄露
        /*RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);*/

        mContext = this;
        mCarList = (ImageView) findViewById(R.id.car_list);
        mgAttention = new ImageView(mContext);
        mgAttention.setImageResource(R.drawable.product_attention_info);

        mgAttentionCancel = new ImageView(mContext);
        mgAttentionCancel.setImageResource(R.drawable.product_attention_cancel_info);
        attentionGoodsMap = new HashMap<>();

        String cityId = DefaultShared.getString(App.CITYID, App.DEFAULT_CITYID);
        Bundle bundle = getIntent().getExtras();
        String goodsId = bundle.getString(GOODSDETAILID);
        String userType = DefaultShared.getString(RegisterLogin.USER_TYPE, RegisterLogin.USER_DEFAULT);
        mGoodsId = goodsId;
        String url = String.format(ConstantURL.GOODS_DETAIL_ITEM, goodsId, cityId, userType, userId);

        initView();
        initBadgeView();

        //executeRequest(new GsonRequest(url, DetailItem.class, getGoodsItemDetail(), errorListener()));
        AppObservable.bindActivity(this, mApi.getProductDetail(goodsId, cityId, userType, userId))
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DetailItem, List<GoodsDetail>>() {
                    @Override
                    public List<GoodsDetail> call(DetailItem detailItem) {
                        List<GoodsDetail> goodses = new ArrayList<GoodsDetail>();
                        List<DetailItem.ListallcatEntity> listallcat = detailItem.listallcat;
                        if (listallcat != null && listallcat.size() > 0) {
                            for (int i = 0; i < listallcat.size(); i++) {
                                String goodsId = listallcat.get(i).goods_id;
                                new Delete()
                                        .from(GoodsDetail.class)
                                        .where("goods_id = ? ", goodsId);
                                GoodsDetail goods = new GoodsDetail();
                                goods.goodsId = listallcat.get(i).goods_id;
                                goods.goodsName = listallcat.get(i).goods_name;
                                goods.brandName = listallcat.get(i).brand_name;
                                goods.goodsBrief = listallcat.get(i).goods_brief;
                                goods.guiGe = listallcat.get(i).guige;
                                goods.goodsImg = listallcat.get(i).goods_img;
                                goods.goodsCode = listallcat.get(i).goods_sn;
                                goods.goodsCount = listallcat.get(i).goods_number;
                                goods.marketPrice = listallcat.get(i).market_price;
                                goods.shopPrice = listallcat.get(i).shop_price;
                                goods.goodsDesc = listallcat.get(i).goods_desc;
                                goods.hasPromotion = listallcat.get(i).discounta;
                                goods.promotionName = listallcat.get(i).discount_name;
                                goods.danwei = listallcat.get(i).danwei;

                                goods.save();
                                goods.inventory = listallcat.get(i).inventory;

                                goodses.add(goods);

                                goodsBoughtList = listallcat.get(i).bought_goods;
                            }
                            return goodses;
                        }
                        return null;
                    }})
                .subscribe(new Observer<List<GoodsDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showLongToast(mContext,"网络连接错误");
                    }

                    @Override
                    public void onNext(List<GoodsDetail> goodsDetails) {
                        if (goodsDetails != null) {
                            GoodsDetail goods = goodsDetails.get(0);
                            currentGoodsDetail = goods;

                            String goodsId = goods.goodsId;
                            mCar = new Select()
                                    .from(ShoppingCar.class)
                                    .where("goods_id=?", goodsId)
                                    .executeSingle();
                            if (mCar != null) {
                                content.setText(mCar.goodsNumber + "");
                            } else {
                                content.setText(1 + "");
                            }
                            mGoods = goods;
                            goodsName.setText(goods.goodsName);
                            goodsStandardValue.setText(goods.guiGe);
                            marketpPrice.setText("￥" + goods.marketPrice + "/" + goods.danwei);
                            marketpPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            shopPrice.setText("￥" + goods.shopPrice + "/" + goods.danwei);
                            if (goods.hasPromotion.equals(GoodsDetail.HASPROMOTION)) {
                                lineSalesPromotion.setVisibility(View.VISIBLE);
                                salesPromotionValue.setText(goods.promotionName);
                            } else {
                                lineSalesPromotion.setVisibility(View.GONE);
                            }

                            //showGoodsPicture();
                            initViewPager();
                            initInventory(goods);
                        }
                    }
                });

    }

    private void initInventory(GoodsDetail goods) {
        String inventory = goods.inventory;
        if(inventory.equals(Goods.NO_GOODS)){
            intoCar.setEnabled(false);
            intoCar.setText("缺货");
            intoCar.setBackgroundResource(R.color.category_grey);
            content.setEnabled(false);
        }else{
            intoCar.setEnabled(true);
            intoCar.setText("加入购物车");
            intoCar.setBackgroundResource(R.color.input_car);
            content.setEnabled(true);
        }
    }

    private void initBadgeView() {
        mBadgeView = new BadgeView(mContext, mCarList);
        List<ShoppingCar> list = new Select()
                .from(ShoppingCar.class)
                .execute();
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += Integer.parseInt(list.get(i).goodsNumber);
        }
        if (total > 0) {
            if (total > 99) {
                mBadgeView.setText("99+");
            } else {
                mBadgeView.setText(total + "");
            }
            mBadgeView.setTextSize(Config.BADGEVIEW_SIZE);
            mBadgeView.show();
        } else {
            mBadgeView.hide();
        }
    }

    private void initView() {
       /* BadgeView badgeView;
        badgeView = new BadgeView(GoodsDetailBuyActivity.this, mCarList);
        badgeView.setText( "1");
        badgeView.show();

        BadgeView badgeView1;
        badgeView1 = new BadgeView(GoodsDetailBuyActivity.this, attention);
        badgeView1.setText( "1");
        badgeView1.show();*/

        mCarList.setImageResource(R.drawable.product_cart_select);

        List<AttentionGoods> attGoodsList = new Select()
                .from(AttentionGoods.class)
                .execute();

        for (int i = 0; i < attGoodsList.size(); i++) {
            attentionGoodsMap.put(attGoodsList.get(i).goodsId, attGoodsList.get(i).recId);
        }

        adjustAttentionGoods();
        productViewpager = (ConvenientBanner) findViewById(R.id.product_viewpager);


        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               /* if (content.getText().toString().equals("")) {
                    content.setText("1");
                }*/
                content.setSelection(content.getText().length());
            }
        });
        content.setText(result + "");
    }

    private void adjustAttentionGoods() {
        if (!attentionGoodsMap.keySet().contains(mGoodsId)) {
            attention.setImageResource(R.drawable.attention_goods_none);
        } else {
            attention.setImageResource(R.drawable.attention_goods_has);
        }
    }

    @OnClick(R.id.goods_introduce)
    public void onIntroduce() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DETAIL_ITEM, mGoods);
        IntentUtil.startActivity(GoodsDetailBuyActivity.this, GoodsDetailBuyInfoActivity.class, bundle);
    }

    private Response.Listener<CarList> getCarList() {
        return new Response.Listener<CarList>() {
            @Override
            public void onResponse(CarList carList) {
               /* BadgeView badgeView;
                if (carList.goods_list.size() > 0) {
                    badgeView = new BadgeView(GoodsDetailBuyActivity.this, mCarList);
                    badgeView.setText(carList.total.real_goods_count + "");
                    badgeView.show();
                    mCarList.setImageResource(R.drawable.product_cart_select);
                } else {
                    badgeView = new BadgeView(GoodsDetailBuyActivity.this, mCarList);
                    badgeView.setText( "0");
                    badgeView.show();
                    mCarList.setImageResource(R.drawable.product_cart);
                }*/

              /*  badge = new BadgeView(GoodsDetailBuyActivity.this, mCarList);
                badge.setText(carList.goods_list.size() + "");
                badge.show();*/
            }
        };
    }

    private Response.Listener<DetailItem> getGoodsItemDetail() {
        return new Response.Listener<DetailItem>() {

            @Override
            public void onResponse(final DetailItem detailItem) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, List<GoodsDetail>>() {
                    @Override
                    protected void onPreExecute() {
                        progress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onPostExecute(List<GoodsDetail> goodsDetails) {
                        progress.setVisibility(View.GONE);

                        if (goodsDetails != null) {
                            GoodsDetail goods = goodsDetails.get(0);
                            currentGoodsDetail = goods;

                            String goodsId = goods.goodsId;
                           /* List<Goods> list = new Select()
                                    .from(Goods.class)
                                    .where("goods_id=?", goodsId)
                                    .execute();*/


                            mCar = new Select()
                                    .from(ShoppingCar.class)
                                    .where("goods_id=?", goodsId)
                                    .executeSingle();
                            if (mCar != null) {
                                content.setText(mCar.goodsNumber + "");
                            } else {
                                content.setText(1 + "");
                            }

                            mGoods = goods;
                            goodsName.setText(goods.goodsName);
                            goodsStandardValue.setText(goods.guiGe);
                            marketpPrice.setText("￥" + goods.marketPrice + "/" + goods.danwei);
                            marketpPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            shopPrice.setText("￥" + goods.shopPrice + "/" + goods.danwei);
                            if (goods.hasPromotion.equals(GoodsDetail.HASPROMOTION)) {
                                lineSalesPromotion.setVisibility(View.VISIBLE);
                                salesPromotionValue.setText(goods.promotionName);
                            } else {
                                lineSalesPromotion.setVisibility(View.GONE);
                            }

                            //showGoodsPicture();
                            initViewPager();
                        }
                    }

                    @Override
                    protected List<GoodsDetail> doInBackground(Object... params) {
                        List<GoodsDetail> goodses = new ArrayList<GoodsDetail>();
                        List<DetailItem.ListallcatEntity> listallcat = detailItem.listallcat;
                        if (listallcat != null && listallcat.size() > 0) {
                            for (int i = 0; i < listallcat.size(); i++) {
                                String goodsId = listallcat.get(i).goods_id;
                                new Delete()
                                        .from(GoodsDetail.class)
                                        .where("goods_id = ? ", goodsId);
                                GoodsDetail goods = new GoodsDetail();
                                goods.goodsId = listallcat.get(i).goods_id;
                                goods.goodsName = listallcat.get(i).goods_name;
                                goods.brandName = listallcat.get(i).brand_name;
                                goods.goodsBrief = listallcat.get(i).goods_brief;
                                goods.guiGe = listallcat.get(i).guige;
                                goods.goodsImg = listallcat.get(i).goods_img;
                                goods.goodsCode = listallcat.get(i).goods_sn;
                                goods.goodsCount = listallcat.get(i).goods_number;
                                goods.marketPrice = listallcat.get(i).market_price;
                                goods.shopPrice = listallcat.get(i).shop_price;
                                goods.goodsDesc = listallcat.get(i).goods_desc;
                                goods.hasPromotion = listallcat.get(i).discounta;
                                goods.promotionName = listallcat.get(i).discount_name;
                                goods.danwei = listallcat.get(i).danwei;
                                goods.save();
                                goods.inventory = listallcat.get(i).danwei;
                                goodses.add(goods);

                                goodsBoughtList = listallcat.get(i).bought_goods;
                            }
                            return goodses;
                        }
                        return null;
                    }
                });
            }
        };
    }

    public static Map<Integer, String> webUrl = new HashMap<>();

    private void initViewPager() {
        webUrl.clear();
        imgList = new String[goodsBoughtList.size()];
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < goodsBoughtList.size(); i++) {
            //urlMaps.put(i + "", goodsBoughtList.get(i).img_url);
            imgList[i] = goodsBoughtList.get(i).img_url;
            webUrl.put(i, goodsBoughtList.get(i).img_url);
            urls.add(goodsBoughtList.get(i).img_url);
            //urls.add(goodsBoughtList.get(i).thumb_url);
        }

        productViewpager.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderViewProductDesc(GoodsDetailBuyActivity.this, GoodsDetailBuyActivity.this);
            }
        }, urls);
        //productViewpager.add
        productViewpager.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }


    @Override
    protected void onPause() {
        super.onPause();
        //productViewpager.stopAutoScroll();
        MobclickAgent.onPause(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    private ArrayList<View> goodsImages;


    private int result = 1;

    @Optional
    @OnClick(R.id.plus)
    public void onPlusClick(View v) {
        try {
            result = Integer.parseInt(content.getText().toString());
        } catch (Exception e) {
            result = 1;
        }
        result++;
        notifyPriceChanged();
    }

    @Optional
    @OnClick(R.id.minus)
    public void onMinusClick(View v) {
        try {
            result = Integer.parseInt(content.getText().toString());
        } catch (Exception e) {
            result = 1;
        }
        if (result <= 1) {
            return;
        }
        result--;
        notifyPriceChanged();
    }

    private void notifyPriceChanged() {
        content.setText(result + "");
    }

    @OnClick(R.id.attention)
    public void onAttentation(View vi) {

        toast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER, 0, 0);
        if (attentionGoodsMap.keySet().contains(mGoodsId)) {
            toast.setView(mgAttentionCancel);
            //mApi.cancelMineAttentionGoods(userId,userType);
            AppObservable.bindActivity(this, mApi.cancelMineAttentionGoods(userId, attentionGoodsMap.get(mGoodsId)))
                    .map(new Func1<Result, Void>() {
                        @Override
                        public Void call(Result result) {
                            if (result.rsgcode.equals(SUCCESSCODE)) {
                                new Delete()
                                        .from(AttentionGoods.class)
                                        .where("goods_id = ?", mGoodsId)
                                        .execute();
                                attentionGoodsMap.remove(mGoodsId);

                            } else {
                                ToastUtil.showShortToast(mContext, result.rsgmsg);
                            }
                            return null;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);

        } else {
            AppObservable.bindActivity(this, mApi.addMineAttentionGoods(userId, mGoodsId))
                    .map(new Func1<AddtionAttentionResult, Void>() {
                        @Override
                        public Void call(AddtionAttentionResult result) {
                            if (result.rsgcode.equals(SUCCESSCODE)) {
                                AttentionGoods goods = new AttentionGoods();
                                goods.goodsId = mGoodsId;
                                goods.marketPrice = currentGoodsDetail.marketPrice;
                                goods.shopPrice = currentGoodsDetail.shopPrice;
                                goods.goodsName = currentGoodsDetail.goodsName;
                                goods.recId = result.rec_id;
                                goods.save();
                                attentionGoodsMap.put(mGoodsId, result.rec_id);
                            } else {
                                ToastUtil.showShortToast(mContext, result.rsgmsg);
                            }
                            return null;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
            toast.setView(mgAttention);
        }
    }


    Observer<Void> observer = new Observer<Void>() {
        @Override
        public void onCompleted() {
            toast.show();
        }

        @Override
        public void onError(Throwable e) {
            ToastUtil.showShortToast(mContext, R.string.network_connect_fail);
        }

        @Override
        public void onNext(Void aVoid) {
            Log.d("observer", "onNext");
            adjustAttentionGoods();
        }
    };

    @OnClick(R.id.into_car)
    public void OnClickIntoCar(View vi) {
        String userId = DefaultShared.getString(RegisterLogin.USERUID, "0");
        String goodsId = mGoodsId;

        String number = content.getText().toString();
        if (number.length() == 0) {
            ToastUtil.showShortToast(mContext, R.string.input_buy_count);
            return;
        }

        mCar = new Select()
                .from(ShoppingCar.class)
                .where("goods_id=?", goodsId)
                .executeSingle();

        if (mCar == null) {
            String url = String.format(ConstantURL.ADD_GOODS, userId, goodsId, number);
            executeRequest(new GsonRequest(url, AddGoodsResult.class, addGoods(goodsId, number), errorListener()));
        } else {

            String carId = mCar.carId;

            String url = String.format(ConstantURL.EDIT_GOODS, carId, userId, number);
            RequestManager.addRequest(new GsonRequest(url, Result.class,
                    editGoodsListener(carId, Integer.parseInt(number)), errorListener()), this);
        }
    }


    private Response.Listener<Result> editGoodsListener(final String carId, final int count) {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if (result.rsgcode.equals(ConstantURL.SUCCESSCODE)) {

                    TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Integer>() {
                        @Override
                        protected Integer doInBackground(Object... params) {
                            new Update(ShoppingCar.class)
                                    .set("goods_number = ?", count)
                                    .where("car_id=?", carId)
                                    .execute();

                            List<ShoppingCar> list =
                                    new Select()
                                            .from(ShoppingCar.class)
                                            .execute();
                            int total = 0;
                            for (int i = 0; i < list.size(); i++) {
                                total += Integer.parseInt(list.get(i).goodsNumber);
                            }
                            return total;
                        }

                        @Override
                        protected void onPostExecute(Integer index) {
                            ToastUtil.showShortToast(mContext, R.string.edit_cart_success);
                            mBadgeView.setText(index + "");
                            mBadgeView.show();
                        }
                    });

                } else {
                    ToastUtil.showShortToast(mContext, result.rsgmsg);
                }
            }
        };
    }

    private Response.Listener<AddGoodsResult> addGoods(final String goodsId, final String number) {
        return new Response.Listener<AddGoodsResult>() {
            @Override
            public void onResponse(final AddGoodsResult result) {
                if (result.rsgcode.equals(ConstantURL.SUCCESSCODE)) {
                    TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Integer>() {
                        @Override
                        protected Integer doInBackground(Object... params) {
                            ShoppingCar curCar = new ShoppingCar();
                            curCar.goodsId = goodsId;
                            curCar.goodsNumber = number;
                            curCar.carId = result.cat_id;
                            curCar.save();
                            List<ShoppingCar> list =
                                    new Select()
                                            .from(ShoppingCar.class)
                                            .execute();
                            int total = 0;
                            for (int i = 0; i < list.size(); i++) {
                                total += Integer.parseInt(list.get(i).goodsNumber);
                            }
                            return total;
                        }

                        @Override
                        protected void onPostExecute(Integer index) {
                            ToastUtil.showShortToast(mContext, R.string.input_cart);
                            mBadgeView.setText(index + "");
                            mBadgeView.show();
                        }
                    });
                } else {
                    ToastUtil.showShortToast(mContext, result.rsgmsg);
                }
            }
        };
    }

    @OnClick(R.id.car_list)
    public void onCarList() {
        Intent intent = new Intent(GoodsDetailBuyActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        App.getBusInstance().post(new ShowCarListEvent());
        finish();
    }

    @OnClick(R.id.tel_phone)
    public void onTelPhone() {
        try {
            String servicePhone = getResources().getString(R.string.service_phone);

            servicePhone = servicePhone.replace("-", "");
            Uri uri = Uri.parse("tel:" + servicePhone);
            Intent call = new Intent(Intent.ACTION_CALL, uri);
            startActivity(call);
        } catch (Exception e) {
            //存在双卡的问题 等待android 5.1 修复.
            //ToastUtil.showShortToast(mContext,);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void selectItem(int index) {
        Bundle bundle = new Bundle();
        bundle.putString(INDEX, index + "");

        bundle.putStringArray(IMAGES, imgList);

        IntentUtil.startActivity(GoodsDetailBuyActivity.this, ImageActivity.class, bundle);
    }
}
