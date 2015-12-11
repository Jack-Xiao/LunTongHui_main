package com.louie.luntonghui.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/6/24.
 */
@Table(name = "GoodsDetail")
public class GoodsDetail extends Model implements Parcelable {
    public static final String HASPROMOTION = "1";
    public static final String NONEPROMOTION = "0";

    @Column(name = "goods_id")
    public String goodsId;
    @Column(name = "goods_name")
    public String goodsName;
    @Column(name = "brand_name")
    public String brandName;
    @Column(name = "goods_brief")
    public String goodsBrief;
    @Column(name = "goods_img")
    public String goodsImg;

    @Column(name = "guige")
    public String guiGe;
    @Column(name = "goods_sn")
    public String goodsCode;
    @Column(name = "goods_number")
    public String goodsCount;
    @Column(name = "market_price")
    public String marketPrice;
    @Column(name = "shop_price")
    public String shopPrice;
    @Column(name = "goods_desc")
    public String goodsDesc;

    @Column(name = "has_promotion")
    public String hasPromotion;
    @Column(name = "promotion_name")
    public String promotionName;


    public String danwei;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goodsId);
        dest.writeString(this.goodsName);
        dest.writeString(this.brandName);
        dest.writeString(this.goodsBrief);
        dest.writeString(this.goodsImg);
        dest.writeString(this.guiGe);
        dest.writeString(this.goodsCode);
        dest.writeString(this.goodsCount);
        dest.writeString(this.marketPrice);
        dest.writeString(this.shopPrice);
        dest.writeString(this.goodsDesc);
        dest.writeString(this.hasPromotion);
        dest.writeString(this.promotionName);
    }

    public GoodsDetail() {
    }

    protected GoodsDetail(Parcel in) {
        this.goodsId = in.readString();
        this.goodsName = in.readString();
        this.brandName = in.readString();
        this.goodsBrief = in.readString();
        this.goodsImg = in.readString();
        this.guiGe = in.readString();
        this.goodsCode = in.readString();
        this.goodsCount = in.readString();
        this.marketPrice = in.readString();
        this.shopPrice = in.readString();
        this.goodsDesc = in.readString();
        this.hasPromotion = in.readString();
        this.promotionName = in.readString();
    }

    public static final Parcelable.Creator<GoodsDetail> CREATOR = new Parcelable.Creator<GoodsDetail>() {
        public GoodsDetail createFromParcel(Parcel source) {
            return new GoodsDetail(source);
        }

        public GoodsDetail[] newArray(int size) {
            return new GoodsDetail[size];
        }
    };
}
