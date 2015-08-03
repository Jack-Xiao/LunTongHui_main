package com.louie.luntonghui.rest;

import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.CheckCode;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.model.result.GoodsThinkSearchList;
import com.louie.luntonghui.model.result.Result;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.Observer;


/**
 * Created by Administrator on 2015/6/2.
 */
public class ServiceManager {


    public interface LunTongHuiApi {
        //@GET("act=bindingcode&user&tel={phoneNumber}&mode=test");
        //http://120.24.209.210/api/mobile.php?act=bindingcode&user_tel=13417185094&mode=test

        @GET("/mobile.php")
        Observer<CheckCode> getCheckCode(@Field("act") String act, @Field("user_tel") int phoneNumber
                , @Field("mode") String mode);

        CheckCode getCheckCode1(@Field("act") String act, @Field("user_tel") int phoneNumber
                , @Field("mode") String mode);
        //获取商品列表
        @GET("/mobile.php?act=listallcats")
        void getGoodsList(Callback<GoodsList> mCallback);

        @GET("/moblip.php?act=listcatgoods&mode=test&cat_id={id}")
        void getGoodsSelectList(@Field("cat_id") String id);

        //显示购物车
        @GET("/mobile.php?act=buylistcart&mode=test")
        void getCarList(@Query("user_id") String user_id,Callback<CarList> mCallback);
        //添加购物车
        @GET("/mobile.php?act=addbuycart&mode=test")
        Result addCarGoods(@QueryMap Map<String,String> options);
        //更新购物车内商品数量
        @GET("/mobile.php?act=edltbuycart&mode=test")
        Observable<Result> editCarGoods(@Query("rec_id") String recId,@Query("user_id") String userId,
                            @Query("new_number") String newNumber);

        //清空购物车
        @GET("mobile.php?act=emptycart&" +
                "mode=test")
        Observable<Result> clearCarList(@Query("user_id") String userId);


        @GET("/mobile.php?act=buylistcart&mode=test")
        CarList getCarList(@Query("user_id") String user_id);

        @GET("/mobile.php?act=findgoodsprompt&mode=test")
        GoodsThinkSearchList searchGoods(@Query("goodsname") String goodsName,@Query("ctype") String type);


    }
}