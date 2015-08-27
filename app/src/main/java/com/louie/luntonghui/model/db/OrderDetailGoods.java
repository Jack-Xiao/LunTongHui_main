package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/7/18.
 */
@Table(name = "order_detail_goods")
public class OrderDetailGoods extends Model {
    @Column(name = "image_url")
    public String imgUrl;

    @Column(name ="goods_id")
    public String goodsId;

    @Column(name = "goods_name")
    public String goodsName;

    @Column(name = "goods_price")
    public String goodsPrice;

    @Column(name = "goods_number")
    public String goodsNumber;



}
