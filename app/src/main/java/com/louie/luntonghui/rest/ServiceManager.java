package com.louie.luntonghui.rest;

import com.louie.luntonghui.model.result.AddtionAttentionResult;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.CheckCode;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.model.result.GoodsThinkSearchList;
import com.louie.luntonghui.model.result.MineAttentionResult;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.model.result.RetrivePasswordResult;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
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

        // 我的关注
        @GET("/mobile.php?act=collection_list&mode=test")
        Observable<MineAttentionResult> getMineAttentionGoodsList(@Query("user_id") String userId,@Query("ctype") String type);
        // 取消关注
        @GET("/mobile.php?act=delete_collection&mode=test")
        Observable<Result> cancelMineAttentionGoods(@Query("user_id") String userId,@Query("rec_id") String type);

        //添加关注
        @GET("/mobile.php?act=collect&mode=test")
        Observable<AddtionAttentionResult> addMineAttentionGoods(@Query("user_id") String userId,@Query("goods_id") String goodsId);

        @GET("/mobile.php?act=tofindthepwd&mode=test")
        Observable<RetrivePasswordResult> findTheUserPwd(@Query("username") String username);

        @GET("/mobile.php?act=changepwdmsg&mode=test")
        Observable<CheckCode> changepwdmsg(@Query("user_tel") String tel);

        @FormUrlEncoded
        @POST("/mobile.php?act=editpassword")
        Observable<Result> changePwd(@Field("user_id") String userId,@Field("new_pwd") String newPwd);

        @GET("/mobile.php?act=changepwd&mode=test")
        Observable<Result> changeNewPassword(@Query("user_id") String userId,@Query("old_pwd") String oldPwd,@Query("new_pwd") String newPwd);
    }
}