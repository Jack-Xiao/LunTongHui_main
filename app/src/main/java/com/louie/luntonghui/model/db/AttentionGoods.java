package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.louie.luntonghui.util.Config;

/**
 * Created by Jack on 15/8/12.
 */
@Table(name = "AttentionGoods")
public class AttentionGoods extends Model{
    public static final String GOODS_IS_BUY = "1";
    public static final String GOODS_IS_NOT_BUY = "0";

    public static final String GOODS_ATTENTION = "1";
    public static final String GOODS_ATTENTION_CANCEL = "0";

    public static final String HAS_SET_ALARM_CLOCK = "1";
    public static final String NOT_SET_ALARM_CLOCK = "0";

    @Column(name = "goods_id")
    public String goodsId;
    @Column(name = "goods_name")
    public String goodsName;
    @Column(name = "goods_img")
    public String goodsImg;
    @Column(name = "goods_sn")
    public String goodsSN;
    @Column(name = "goods_number")
    public String goodsNumber;
    @Column(name = "market_price")
    public String marketPrice;
    @Column(name = "shop_price")
    public String shopPrice;
    @Column(name = "gys_money")
    public String gysMoney;
    @Column(name = "promote_price")
    public String promotePrice;
    @Column(name = "goods_brief")
    public String goodsBrief;
    @Column(name = "goods_desc")
    public String goodsDesc;
    @Column(name = "sort_order")
    public String sortOrder;
    @Column(name = "is_best")
    public String isBest;
    @Column(name = "is_new")
    public String isNew;
    @Column(name = "is_hot")
    public String isHot;
    @Column(name = "display")
    public String display;
    @Column(name = "give_integral")
    public String giveIntegral;
    @Column(name = "integral")
    public String integral;
    @Column(name = "is_promote")
    public String isPromote;
    @Column(name = "discounta")
    public String discounta;
    @Column(name = "discount")
    public String discount;
    @Column(name = "discount_time")
    public String discountTime;
    @Column(name = "discount_name")
    public String discountName;

    @Column(name = "isChecked")
    public String isChecked;

    @Column(name = "goods_url")
    public String goodsUrl;

    @Column(name = "goods_parent_id")
    public String goodsParentId;

    @Column(name = "goods_attention")
    public String goodsAttention;

    @Column(name = "guige")
    public String guige;

    @Column(name = "unit")
    public String unit;

    @Column(name = "is_second_kill")
    public String is_second_kill;

    public String canRushGoods = Config.CAN_SET_ALARM_CLOCK;

    @Column(name = "setAlarmClock")
    public String setAlarmClock;
    //关注id
    @Column(name = "rec_id")
    public String recId;
    //优惠活动
    @Column(name = "discount_type")
    public String discountType;
}
