package com.louie.luntonghui.ui.category;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.ShowCarListEvent;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.GoodsDetail;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.AddGoodsResult;
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
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

import static com.louie.luntonghui.ui.category.GoodsDetailActivity.GOODSDETAILID;

/**
 * Created by Administrator on 2015/6/23.
 */
public class GoodsDetailBuyActivity extends BaseNormalActivity implements BaseSliderView.OnSliderClickListener {
    public static final String DETAIL_ITEM = "detail_item";

    @InjectView(R.id.menu_list)
    ImageView menuList;

    @InjectView(R.id.progress)
    ProgressBar progress;

    @InjectView(R.id.product_viewpager)
    InfiniteIndicatorLayout productViewpager;

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


    private String goodsId;

    private GoodsDetail mGoods;
    private List<DetailItem.ListallcatEntity.Bought_goodsEntity> goodsBoughtList;
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
    public static final String sliderName ="extra";
    public static final String IMAGES = "images";
    public static final String INDEX = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_item_buy);
        ButterKnife.inject(this);
        App.getBusInstance().register(this);

        mContext = this;

        mCarList = (ImageView)findViewById(R.id.car_list);
        mgAttention = new ImageView(mContext);
        mgAttention.setImageResource(R.drawable.product_attention_info);

        mgAttentionCancel = new ImageView(mContext);
        mgAttentionCancel.setImageResource(R.drawable.product_attention_cancel_info);


        String cityId = DefaultShared.getString(App.CITYID, App.DEFAULT_CITYID);
        Bundle bundle = getIntent().getExtras();
        String goodsId = bundle.getString(GOODSDETAILID);
        String userType = DefaultShared.getString(RegisterLogin.USER_TYPE,RegisterLogin.USER_DEFAULT);
        mGoodsId = goodsId;
        String url = String.format(ConstantURL.GOODS_DETAIL_ITEM, goodsId, cityId,userType);

        initView();
        initBadgeView();
        executeRequest(new GsonRequest(url, DetailItem.class, getGoodsItemDetail(), errorListener()));
        String userId = DefaultShared.getString(RegisterLogin.USERUID, RegisterLogin.DEFAULT_USER_ID);

       /* url = String.format(ConstantURL.GET_CAR_LIST, userId);
        executeRequest(new GsonRequest(url, CarList.class, getCarList(), errorListener()));*/
        initCart();
    }

    private void initCart() {


    }

    private void initBadgeView() {
        mBadgeView = new BadgeView(mContext,mCarList);
        List<ShoppingCar> list = new Select()
                .from(ShoppingCar.class)
                .execute();
        int total = 0;
        for(int i =0;i<list.size();i++){
            total += Integer.parseInt(list.get(i).goodsNumber);
        }
        if(total >0){
            mBadgeView.setText(total +"");
            mBadgeView.setTextSize(Config.BADGEVIEW_SIZE);
            mBadgeView.show();
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

        int size = new Select()
                .from(Goods.class)
                .where("goods_id= ? and goods_attention=?", mGoodsId, Goods.GOODS_ATTENTION)
                .execute()
                .size();
        if(size == 0){
            attention.setImageResource(R.drawable.product_cancel_attention);
        }else{
            attention.setImageResource(R.drawable.product_attention);
        }

        productViewpager = (InfiniteIndicatorLayout) findViewById(R.id.product_viewpager);


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

                            String goodsId = goods.goodsId;
                            List<Goods> list = new Select()
                                    .from(Goods.class)
                                    .where("goods_id=?", goodsId)
                                    .execute();

                            if(list!=null ) {
                                if (list.size()>0 && list.get(0).goodsAttention != null && list.get(0).goodsAttention.equals(Goods.GOODS_ATTENTION)) {
                                    attention.setImageResource(R.drawable.product_attention);
                                } else {
                                    attention.setImageResource(R.drawable.product_cancel_attention);
                                }
                            }
                            mCar = new Select()
                                    .from(ShoppingCar.class)
                                    .where("goods_id=?",goodsId)
                                    .executeSingle();
                            if(mCar !=null){
                                content.setText(mCar.goodsNumber + "");
                            }else{
                                content.setText( 1 +"");
                            }

                            mGoods = goods;
                            goodsName.setText(goods.goodsName);
                            goodsStandardValue.setText(goods.guiGe);
                            marketpPrice.setText("￥" + goods.marketPrice + "/个");
                            marketpPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            shopPrice.setText("￥" + goods.shopPrice +"/个");
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

                                goods.save();
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

    private void initViewPager() {
        HashMap<String, String> urlMaps = new HashMap<>();
        imgList = new String[goodsBoughtList.size()];
        for (int i = 0; i < goodsBoughtList.size(); i++) {
            urlMaps.put(i + "", goodsBoughtList.get(i).img_url);
            imgList[i] = goodsBoughtList.get(i).img_url;
        }

        for (String name : urlMaps.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(urlMaps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString(sliderName, name);
            productViewpager.addSlider(textSliderView);
        }
        productViewpager.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
    }


    @Override
    protected void onPause() {
        super.onPause();
        productViewpager.stopAutoScroll();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        productViewpager.startAutoScroll();
        MobclickAgent.onResume(this);
    }

/*
    private void showGoodsPicture() {
        initIndicator();
        goodsPicture = new ImageView(this);
        mAdapter = new ShowGoodsAdapter(goodsBoughtList);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ShowGoodsListener());
    }*/


    private ArrayList<View> goodsImages;

  /*  class ShowGoodsAdapter extends PagerAdapter {
        private List<DetailItem.ListallcatEntity.Bought_goodsEntity> mList;

        public ShowGoodsAdapter(List<DetailItem.ListallcatEntity.Bought_goodsEntity> goodsBoughtList) {
            mList = goodsBoughtList;
        }

        @Override
        public int getCount() {
            return goodsBoughtList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = (ImageView) goodsImages.get(position);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(mContext)
                    .load(mList.get(position).img_url)
                    .into(imageView);
            container.removeView(goodsImages.get(position));
            container.addView(goodsImages.get(position));
            return goodsImages.get(position);
        }
    }

    class ShowGoodsListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < indicator_imgs.length; i++) {
                indicator_imgs[i].setImageResource(R.drawable.page);
            }
            indicator_imgs[position].setImageResource(R.drawable.page_now);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }*/

    private int result = 1;

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
        int size = new Select()
                .from(Goods.class)
                .where("goods_id= ? and goods_attention=?", mGoodsId, Goods.GOODS_ATTENTION)
                .execute()
                .size();
        Toast toast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (size == 1) {
            toast.setView(mgAttentionCancel);
            new Update(Goods.class)
                    .set("goods_attention=?", Goods.GOODS_ATTENTION_CANCEL)
                    .where("goods_id=?", mGoodsId)
                    .execute();
            attention.setImageResource(R.drawable.product_cancel_attention);
        } else {
            toast.setView(mgAttention);
            new Update(Goods.class)
                    .set("goods_attention=?", Goods.GOODS_ATTENTION)
                    .where("goods_id=?", mGoodsId)
                    .execute();
            attention.setImageResource(R.drawable.product_attention);
        }
        toast.show();
    }

    @OnClick(R.id.into_car)
    public void OnClickIntoCar(View vi) {
        String userId = DefaultShared.getString(RegisterLogin.USERUID, "0");
        String goodsId = mGoodsId;

        String number = content.getText().toString();
        if(number.length() == 0){
            ToastUtil.showShortToast(mContext,R.string.input_buy_count);
            return;
        }

        mCar = new Select()
                .from(ShoppingCar.class)
                .where("goods_id=?",goodsId)
                .executeSingle();

        if(mCar == null){
            String url = String.format(ConstantURL.ADD_GOODS, userId, goodsId, number);
            executeRequest(new GsonRequest(url, AddGoodsResult.class, addGoods(goodsId, number), errorListener()));
        }else{

            String carId = mCar.carId;

            String url = String.format(ConstantURL.EDIT_GOODS,carId,userId,number);
            RequestManager.addRequest(new GsonRequest(url, Result.class,
                    editGoodsListener(carId, Integer.parseInt(number)), errorListener()), this);
        }
    }


    private Response.Listener<Result> editGoodsListener(final String carId,final int count) {
        return new Response.Listener<Result>() {
            @Override
            public void onResponse(Result result) {
                if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){


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

                }else{
                    ToastUtil.showShortToast(mContext,result.rsgmsg);
                }
            }
        };
    }

    private Response.Listener<AddGoodsResult> addGoods(final String goodsId,final String number) {
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

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String index = slider.getBundle().getString(sliderName);
        //executeRequest();
        Bundle bundle = new Bundle();
        bundle.putString(INDEX, index);

        bundle.putStringArray(IMAGES, imgList);
        //IntentUtil.startActivity(GoodsDetailBuyActivity.this, SpaceImageDetailActivity.class,bundle);
        IntentUtil.startActivity(GoodsDetailBuyActivity.this, ImageActivity.class,bundle);

    }

    @OnClick(R.id.car_list)
    public void onCarList(){
        Intent intent= new Intent(GoodsDetailBuyActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        App.getBusInstance().post(new ShowCarListEvent());
        finish();
    }
}
