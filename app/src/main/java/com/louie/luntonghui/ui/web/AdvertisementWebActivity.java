package com.louie.luntonghui.ui.web;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.louie.luntonghui.App;
import com.louie.luntonghui.R;
import com.louie.luntonghui.data.GsonRequest;
import com.louie.luntonghui.event.ShowCarListEvent;
import com.louie.luntonghui.model.db.Goods;
import com.louie.luntonghui.model.db.ShoppingCar;
import com.louie.luntonghui.model.result.AddGoodsResult;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.net.RequestManager;
import com.louie.luntonghui.ui.BaseToolbarActivity;
import com.louie.luntonghui.ui.MainActivity;
import com.louie.luntonghui.ui.category.GoodsDetailActivity;
import com.louie.luntonghui.ui.category.GoodsDetailBuyActivity;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.ConstantURL;
import com.louie.luntonghui.util.IntentUtil;
import com.louie.luntonghui.util.ToastUtil;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Jack on 15/9/23.
 */
public class AdvertisementWebActivity extends BaseToolbarActivity{
    public static final String URL = "url";
    public static final String MODEL = " model";
    private String url;
    private WebView browser;
    private ProgressBar progressBar;
    public static final int MAX = 100;
    public static final String BUYTO_NATIVE_CART = "buy_to_native_cart";
    public static final int WEB_CHOOSE_DEFAULT_SURFACE = -1;
    public static final String WEB_CHOOSE_SURFACE = "web_choose_surface";
    public ChooseActivityInterface mListener;
    public static final int DEFAULT_MODEL = 0;
    public static final int HAS_BAR_MODEL = 1;
    private int model;


    public interface ChooseActivityInterface{
        public void chooseSurface(int sequence);
    }

    @Override
    protected int toolbarTitle() {
        return R.string.activity_detail;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        browser = (WebView)findViewById(R.id.web_view);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setMax(MAX);
        //mAppBar.setVisibility(View.GONE);

        App.getBusInstance().register(this);

        Bundle bundle = getIntent().getExtras();
        url = bundle.getString(URL);
        model = bundle.getInt(MODEL,DEFAULT_MODEL);

        if(model == HAS_BAR_MODEL){
            mAppBar.setVisibility(View.GONE);
        }

        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);
        browser.addJavascriptInterface(new BuyGoodsFromWebView(this), BUYTO_NATIVE_CART);

        //browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //缓存
        browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //不使用缓存

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        browser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        browser.loadUrl(url);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(browser.canGoBack()){
                browser.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



    public void choose(int seq) {
        Intent intent = new Intent(AdvertisementWebActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        App.getBusInstance().post(new ShowCarListEvent());
    }


    @Override
    protected void onDestroy() {
        App.getBusInstance().unregister(this);
        super.onDestroy();
    }


    final public class BuyGoodsFromWebView {
        private int cureentCount = 1;
        private String goodsName;
        private String goodsShopPrice;
        private String goodsUnit;
        private String goodsGuiGe;

        private String mGoodsId;


        private AdvertisementWebActivity mContext;

        public BuyGoodsFromWebView(AdvertisementWebActivity context){
            this.mContext = context;
            //init_cart();
        }

        @JavascriptInterface
        public void buy(String goodsName, String goodsPrice, String goodsUnit,
                        String goodsGuiGe, String goodsId) {

            ShoppingCar car = new Select()
                    .from(ShoppingCar.class)
                    .where("goods_id = ?", goodsId)
                    .executeSingle();

            if (car != null) {
            /*cureentCount = Integer.parseInt(car.goodsNumber);
            goodsName = car.goodsName;
            goodsShopPrice = car.goodsShopPrice;
            goodsUnit = car.unit;
            goodsGuiGe = car.guige;*/

                cureentCount = Integer.parseInt(car.goodsNumber);
            } else {
                cureentCount = 1;
            }

            mGoodsId = goodsId;

            adjustGoods(goodsName, goodsPrice, goodsUnit, goodsGuiGe, cureentCount + "");

        }

        @JavascriptInterface
        public void home() {
            callMainActivity(Config.MAIN_HOME_SEQUENCE);
        }

        @JavascriptInterface
        public void person() {
            callMainActivity(Config.MAIN_PERSON_SEQUENCE);
        }

        @JavascriptInterface
        public void cart() {
            callMainActivity(Config.MAIN_CART_SEQUENCE);
        }

        private void callMainActivity(int sequence) {
            Intent intent;

            switch (sequence){
                case Config.MAIN_CART_SEQUENCE:
                    intent = new Intent(AdvertisementWebActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(WEB_CHOOSE_SURFACE,Config.MAIN_CART_SEQUENCE);
                    startActivity(intent);
                    break;

                case Config.MAIN_HOME_SEQUENCE:
                    intent = new Intent(AdvertisementWebActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    break;

                case Config.MAIN_PERSON_SEQUENCE:
                    intent = new Intent(AdvertisementWebActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(WEB_CHOOSE_SURFACE, Config.MAIN_PERSON_SEQUENCE);
                    startActivity(intent);
                    break;
            }
        }

        @JavascriptInterface
        public void group(){
            AdvertisementWebActivity.this.finish();
        }


        @JavascriptInterface
        public void goods_detail(String goodsId) {
            Bundle bundle = new Bundle();
            bundle.putString(GoodsDetailActivity.GOODSDETAILID, goodsId);
            IntentUtil.startActivity(mContext, GoodsDetailBuyActivity.class, bundle);
        }

        @JavascriptInterface
        public void current_cart_total_number(){

            List<ShoppingCar> list = new Select()
                    .from(ShoppingCar.class)
                    .execute();

            int total = 0;
            for(ShoppingCar cart :list){

                total += Integer.parseInt(cart.goodsNumber);
            }

            final String strTotal;
            if(total != 0){
               if(total > 99){
                   strTotal = "99+";
               }else{
                   strTotal = total +"";
               }
                browser.post(new Runnable() {
                    @Override
                    public void run() {
                        browser.loadUrl("javascript:current_cart_total_number('" + strTotal + "')");
                    }
                });
            }else{
                browser.post(new Runnable() {
                    @Override
                    public void run() {
                        browser.loadUrl("javascript:current_cart_total_number('" + 0 + "')");
                    }
                });
            }
        }

        @JavascriptInterface
        public void init_cart(){
            current_cart_total_number();
        }


        public void adjustGoods(String goodsName, String goodsPrice, String strUnit, String strGuige,
                                String content) {
            //AlertDialogUtil.getInstance().show(mContext,CarFragmentAdapter.this, );

            final String goodsId = mGoodsId;

            View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_adjust_goods_number, null);
            ImageButton btnMinus = (ImageButton) contentView.findViewById(R.id.minus);
            ImageButton btnPlus = (ImageButton) contentView.findViewById(R.id.plus);
            TextView tvGoodsName = (TextView) contentView.findViewById(R.id.goods_name);
            TextView tvShopPrice = (TextView) contentView.findViewById(R.id.shop_price);
            TextView guige = (TextView) contentView.findViewById(R.id.guige);

            Button btnConfirm = (Button) contentView.findViewById(R.id.confirm);
            Button btnCacnel = (Button) contentView.findViewById(R.id.cancel);


            tvGoodsName.setText(goodsName);
            tvShopPrice.setText(goodsPrice + "/" + strUnit);

            guige.setText("规格:" + strGuige);


            final EditText mContent = (EditText) contentView.findViewById(R.id.content);
            final int[] curResult = new int[1];

            mContent.setText(content + "");
            mContent.setSelection(mContent.length());


            final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);

            mMaterialDialog.setView(contentView)
                    .setCanceledOnTouchOutside(true);

            mMaterialDialog.show();

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        curResult[0] = Integer.parseInt(mContent.getText().toString());
                        //mListener.reset(curResult[0]);
                        //
                    } catch (Exception e) {
                        ToastUtil.showShortToast(App.getContext(), R.string.please_input_number);
                        return;
                    }
                    //notifyNumberChanged(holder, curResult[0], position, holder.checked.isChecked());
                    notifyNumberChanged(goodsId,curResult[0]);

                    mMaterialDialog.dismiss();
                }
            });

            btnCacnel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            });


            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int result = 1;
                    try {
                        result = Integer.parseInt(mContent.getText().toString());
                    } catch (Exception e) {
                        result = 1;
                    }
                    result++;
                    //notifyPriceChanged(holder, result);
                    mContent.setText(result + "");
                    //notifyNumberChanged(holder, result, position, holder.checked.isChecked());
                }
            });


            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int result = 1;
                    try {
                        result = Integer.parseInt(mContent.getText().toString());
                    } catch (Exception e) {
                        result = 1;
                    }


                    if (result > 1) {
                        result--;
                        ///notifyPriceChanged(holder, result);
                        mContent.setText(result + "");
                        //notifyNumberChanged(holder, result, position, holder.checked.isChecked());
                    } else {
                        return;
                    }
                }
            });
        }

        private void notifyNumberChanged(String goodsId,int count) {
            ShoppingCar car;

            car = new Select()
                    .from(ShoppingCar.class)
                    .where("goods_id = ?",goodsId)
                    .executeSingle();

            if(car == null){
                String url = String.format(ConstantURL.ADD_GOODS,userId,goodsId,count);
                RequestManager.addRequest(new GsonRequest(url, AddGoodsResult.class,
                        addGoodsListener(goodsId, count), errorListener()), this);
            }else{
                String carId = car.carId;

                String url = String.format(ConstantURL.EDIT_GOODS,carId,userId,count);
                RequestManager.addRequest(new GsonRequest(url,Result.class,
                        editGoodsListener(carId,count),errorListener()),this);
            }
        }

        private Response.Listener<AddGoodsResult> addGoodsListener(final String goodsId,final int count) {
            return new Response.Listener<AddGoodsResult>() {
                @Override
                public void onResponse(AddGoodsResult result) {
                    if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                        ToastUtil.showShortToast(mContext, R.string.input_cart);
                        new Update(Goods.class)
                                .set("isChecked = ?",Goods.GOODS_IS_BUY)
                                .where("goods_id=?",goodsId)
                                .execute();

                        ShoppingCar car = new ShoppingCar();
                        car.goodsNumber = count + "";
                        car.goodsId = goodsId;
                        car.carId = result.cat_id;
                        car.save();

                        current_cart_total_number();
                    }else{
                        ToastUtil.showShortToast(mContext,result.rsgmsg);
                    }
                }
            };
        }

        private Response.Listener<Result> editGoodsListener(final String carId,final int count) {
            return new Response.Listener<Result>() {
                @Override
                public void onResponse(Result result) {
                    if(result.rsgcode.equals(ConstantURL.SUCCESSCODE)){
                        ToastUtil.showShortToast(mContext, R.string.edit_cart_success);
                        new Update(ShoppingCar.class)
                                .set("goods_number = ?", count)
                                .where("car_id=?", carId)
                                .execute();
                        //mListener.referenceBadge();

                        current_cart_total_number();
                    }else{
                        ToastUtil.showShortToast(mContext,result.rsgmsg);
                    }
                }
            };
        }

        private Response.ErrorListener errorListener() {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ToastUtil.showLongToast(mContext,volleyError.getMessage());
                }
            };
        }
    }
}
