package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/6/30.
 */
@Table(name = "shopping_car")
public class ShoppingCar extends Model {
    public static final String NOTGIVEAWAY = "0";

    @Column(name = "car_id")
    public String carId;

    @Column(name = "isChecked")
    public String isChecked;

    @Column(name = "goods_image")
    public String goodsImage;

    @Column(name = "goods_name")
    public String goodsName;

    @Column(name = "goods_market_price")
    public String goodsMarketPrice;

    @Column(name = "goods_shop_price")
    public String goodsShopPrice;

    @Column(name = "goods_id")
    public String goodsId;

    @Column(name="goods_number")
    public String goodsNumber;

    @Column(name="guige")
    public String guige;

    @Column(name = "unit")
    public String unit;

    //不为0，则为某个商品的赠品
    @Column(name = "rid")
    public String rId;

    @Column(name = "discount_type")
    public String discountType;
    //折扣价  /10;
    @Column(name = "discount")
    public String discount;
}