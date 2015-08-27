package com.louie.luntonghui.ui.category;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louie.luntonghui.R;
import com.louie.luntonghui.model.db.GoodsDetail;
import com.louie.luntonghui.ui.SecondLevelBaseActivity;
import com.louie.luntonghui.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.louie.luntonghui.ui.category.GoodsDetailBuyActivity.DETAIL_ITEM;

/**
 * Created by Administrator on 2015/6/24.
 */
public class GoodsDetailBuyInfoActivity extends SecondLevelBaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    LinearLayout linearGoodsName;
    LinearLayout linearGoodsExplain;
    LinearLayout linearGoodsCode;
    LinearLayout linearCountExplain;
    LinearLayout linearGoodsSize;
    LinearLayout linearShopPrice;

    TextView goodsNameValue;
    TextView goodsExplainValue;
    TextView goodsCodeValue;
    TextView countExplainValue;
    TextView goodsSizeValue;
    TextView shopPriceValue;

    private GoodsDetail mGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        mToolbar.setTitle(R.string.goods_detail);

        mGoods = getIntent().getExtras().getParcelable(DETAIL_ITEM);

        if (mGoods == null) {
            ToastUtil.showShortToast(this, R.string.no_goods_info);
            finish();
        }
        initView();


    }

    private void initView() {
        linearGoodsName = (LinearLayout) findViewById(R.id.goods_name);
        linearGoodsExplain = (LinearLayout) findViewById(R.id.simple_explain);
        linearGoodsCode = (LinearLayout) findViewById(R.id.goods_code);
        linearCountExplain = (LinearLayout) findViewById(R.id.count_explain);
        linearGoodsSize = (LinearLayout) findViewById(R.id.goods_size);
        linearShopPrice = (LinearLayout) findViewById(R.id.shop_price);

        TextView tv = (TextView) linearGoodsName.findViewById(R.id.name);
        tv.setText(R.string.goods_name);
        tv.getPaint().setFakeBoldText(true);
        TextView goodsExplain = (TextView) linearGoodsExplain.findViewById(R.id.name);
        goodsExplain.setText(R.string.goods_explain);
        goodsExplain.getPaint().setFakeBoldText(true);

        TextView goodsCode = (TextView) linearGoodsCode.findViewById(R.id.name);
        goodsCode.setText(R.string.goods_code);

        TextView countExplain = (TextView) linearCountExplain.findViewById(R.id.name);
        countExplain.setText(R.string.count_explain);

        TextView goodsSize = (TextView) linearGoodsSize.findViewById(R.id.name);
        goodsSize.setText(R.string.goods_size);

        TextView shopPrice = (TextView) linearShopPrice.findViewById(R.id.name);
        shopPrice.setText(R.string.shop_price);

        goodsNameValue = (TextView) linearGoodsName.findViewById(R.id.value);
        goodsExplainValue = (TextView) linearGoodsExplain.findViewById(R.id.value);
        goodsCodeValue = (TextView) linearGoodsCode.findViewById(R.id.value);
        countExplainValue = (TextView) linearCountExplain.findViewById(R.id.value);
        goodsSizeValue = (TextView) linearGoodsSize.findViewById(R.id.value);
        shopPriceValue = (TextView) linearShopPrice.findViewById(R.id.value);
        shopPriceValue.setTextColor(getResources().getColor(R.color.red));

        try {
            goodsNameValue.setText(mGoods.goodsName);
            goodsExplainValue.setText(mGoods.goodsBrief);
            goodsCodeValue.setText(mGoods.goodsCode);
            countExplainValue.setText(mGoods.goodsCount);
            //goodsSizeValue.setText();    尺寸  暂时待定
            shopPriceValue.setText("￥" + mGoods.shopPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int setToolTitle() {
        return R.string.goods_detail;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_goods_detail_item_buy_info;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
