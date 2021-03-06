package com.louie.luntonghui.rest;

import com.louie.luntonghui.model.db.ApplyForProduct;
import com.louie.luntonghui.model.result.AddtionAttentionResult;
import com.louie.luntonghui.model.result.CarList;
import com.louie.luntonghui.model.result.CheckCode;
import com.louie.luntonghui.model.result.DetailItem;
import com.louie.luntonghui.model.result.DispatchDetail;
import com.louie.luntonghui.model.result.DispatchToday;
import com.louie.luntonghui.model.result.GoodsList;
import com.louie.luntonghui.model.result.GoodsThinkSearchList;
import com.louie.luntonghui.model.result.HomeAdver;
import com.louie.luntonghui.model.result.HomeAdversion;
import com.louie.luntonghui.model.result.HotSearch;
import com.louie.luntonghui.model.result.MineAttentionResult;
import com.louie.luntonghui.model.result.OrderConfirm;
import com.louie.luntonghui.model.result.OrderList;
import com.louie.luntonghui.model.result.PrinterDay;
import com.louie.luntonghui.model.result.PrinterMonth;
import com.louie.luntonghui.model.result.ProduceOrder;
import com.louie.luntonghui.model.result.Result;
import com.louie.luntonghui.model.result.ResultObject;
import com.louie.luntonghui.model.result.RetrivePasswordResult;
import com.louie.luntonghui.model.result.ReturnProductDetail;
import com.louie.luntonghui.model.result.SalesmanStaticDay;
import com.louie.luntonghui.model.result.SalesmanStaticMonth;
import com.louie.luntonghui.model.result.SalesmanStaticYear;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
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
        void getCarList(@Query("user_id") String user_id, Callback<CarList> mCallback);

        //添加购物车
        @GET("/mobile.php?act=addbuycart&mode=test")
        Result addCarGoods(@QueryMap Map<String, String> options);

        //更新购物车内商品数量
        @GET("/mobile.php?act=edltbuycart&mode=test")
        Observable<Result> editCarGoods(@Query("rec_id") String recId, @Query("user_id") String userId,
                                        @Query("new_number") String newNumber);

        //清空购物车
        @GET("mobile.php?act=emptycart&" +
                "mode=test")
        Observable<Result> clearCarList(@Query("user_id") String userId);


        //获取购物车列表
        @GET("/mobile.php?act=buylistcart&mode=test")
        Observable<CarList> getCarList(@Query("user_id") String user_id);

        @GET("/mobile.php?act=findgoodsprompt&mode=test")
        GoodsThinkSearchList searchGoods(@Query("goodsname") String goodsName, @Query("ctype") String type);

        // 我的关注
        @GET("/mobile.php?act=collection_list&mode=test")
        Observable<MineAttentionResult> getMineAttentionGoodsList(@Query("user_id") String userId, @Query("ctype") String type);

        // 取消关注
        @GET("/mobile.php?act=delete_collection&mode=test")
        Observable<Result> cancelMineAttentionGoods(@Query("user_id") String userId, @Query("rec_id") String type);

        //添加关注
        @GET("/mobile.php?act=collect&mode=test")
        Observable<AddtionAttentionResult> addMineAttentionGoods(@Query("user_id") String userId, @Query("goods_id") String goodsId);

        @GET("/mobile.php?act=tofindthepwd&mode=test")
        Observable<RetrivePasswordResult> findTheUserPwd(@Query("username") String username);

        @GET("/mobile.php?act=changepwdmsg&mode=test")
        Observable<CheckCode> changepwdmsg(@Query("user_tel") String tel);

        @FormUrlEncoded
        @POST("/mobile.php?act=editpassword")
        Observable<Result> changePwd(@Field("user_id") String userId, @Field("new_pwd") String newPwd);

        @GET("/mobile.php?act=changepwd&mode=test")
        Observable<Result> changeNewPassword(@Query("user_id") String userId, @Query("old_pwd") String oldPwd, @Query("new_pwd") String newPwd);


        @GET("/mobile.php?act=carouselnew&mode=test")
        Observable<HomeAdver> getHomeAdv(@Query("user_id") String userId,
                                         @Query("city") String city,
                                         @Query("display") String display);

        @GET("/mobile.php?act=new_index&mode=test")
        Observable<HomeAdversion> getHomeAdvArray(@Query("display") String display,
                                                  @Query("user_id") String userId);

        @FormUrlEncoded
        @POST("/mobile.php?act=leave_message")
        Observable<Result> sendFeedback(@Field("user_id") String userId,
                                        @Field("content") String message,
                                        @Field("channel") String channel);

        @GET("/mobile.php?act=listallcats")
        Observable<GoodsList> getProductCategory(@Query("city") String city,
                                                 @Query("ctype") String cType,
                                                 @Query("version") String version,
                                                 @Query("user_id") String userId,
                                                 @Query("display") String display);

        //获取订单
        @GET("/mobile.php?act=mysalelist&mode=test")
        Observable<OrderList> getOrderList(@Query("user_id") String userId);

        //获取商品热搜
        @GET("/mobile.php?act=hotsearch")
        Observable<HotSearch> getHotProduct();

        //获取商品详情
        @GET("/mobile.php?act=goodsdesc&mode=test")
        Observable<DetailItem> getProductDetail(@Query("goods_id") String goodsId,@Query("city") String city,
                                                @Query("ctype")String cType,@Query("user_id") String userId);

        //生成订单
        @GET("/mobile.php?act=generateorders&mode=test")
        Observable<ProduceOrder> getProduceOrder(@Query("user_id") String userId,
                                                 @Query("new_number") String newNumber);

        //查询某天所有订单
        @GET("/mobile.php?act=get_salesman_order")
        Observable<SalesmanStaticDay> getSalesDay(@Query("salesman_id") String salesmanId,
                                                  @Query("date") String date);
        //查询某月所有订单
        @GET("/mobile.php?act=get_salesman_static_by_day")
        Observable<SalesmanStaticMonth> getSaleMonth(@Query("salesman_id") String salesmanId,
                                                     @Query("select_month") String date);
        //查询当年
        @GET("/mobile.php?act=get_salesman_static_by_month")
        Observable<SalesmanStaticYear> getSaleYear(@Query("salesman_id") String salesmanId);

        //查询日期排行榜
        @GET("/mobile.php?act=salesman_ranking_by_day")
        Observable<PrinterDay> getPrinterDay(@Query("select_day") String day);
        //查询月排行榜
        @GET("/mobile.php?act=salesman_ranking_by_month")
        Observable<PrinterMonth> getPrinterMonth(@Query("select_month") String month);
        //今天的订单
        @GET("/mobile.php?act=get_dman_order")
        Observable<DispatchToday> getTodayDispatchDate(@Query("user_id") String userId);

        //历史订单
        @GET("/mobile.php?act=get_dman_order")
        Observable<DispatchToday> getHistoryDispatchDate(@Query("user_id") String userId,
                                                         @Query("start_time") String startTime,
                                                         @Query("end_time") String endTime,
                                                         @Query("page") int page,
                                                         @Query("page_size") int pageSize,
                                                         @QueryMap Map<String, Integer> status);

        //订单详细 example: http://120.25.224.250/api/mobile.php?act=view_delivery_detail&user_id=267&order_id=342
        @GET("/mobile.php?act=view_delivery_detail")
        Observable<DispatchDetail> getDispatchDetail(@Query("user_id") String userId,
                                             @Query("order_id") String orderId);
        //确认收款 http://120.25.224.250/api/mobile.php?act=confirm_delivery&user_id=267&order_id=287
        @GET("/mobile.php?act=confirm_recieve")
        Observable<Result> confirmDelivery(@Query("user_id") String userId,
                                           @Query("order_id") String orderId,
                                           @Query("r_money") String rMoney,
                                           @Query("r_type") String tType,
                                           @QueryMap Map<String,String> maps);
        //确认收货
        @GET("/mobile.php?act=orderconfirmation")
        Observable<OrderConfirm> submitOrder(@Query("user_id") String userId,
                                            @Query("address_id") int addressId,
                                            @Query("pay_id") String payId,
                                            @Query("postscript") String postScript,
                                            @Query("integral") String integral,
                                            @Query("display") String display);


        //获取订单
        @GET("/mobile.php?act=mysalelist&mode=test")
        Observable<OrderList> getOrderList(@Query("user_id") String userId,
                                       @Query("page") int page,
                                       @Query("page_size") int pageSize,
                                       @Query("handler") String handler);
        //申请退货单 提交json数据
        @POST("/mobile.php?act=application_for_return")
        Observable<ResultObject> applyForProduct(@Body ApplyForProduct product);



        //退货单详细
        @GET("/mobile.php?act=get_return_order_info")
        Observable<ReturnProductDetail> getReturnProductDetail(@Query("order_id") String orderId,
                                                               @Query("user_id") String userId);


        //取消退货
        //http://120.25.224.250/api/mobile.php?act=cancel_return&user_id=265&order_id=2030&return_id=482
        @GET("/mobile.php?act=cancel_return")
        Observable<ResultObject> cancelReturnProduct(@Query("return_id") String returnId,
                                                     @Query("order_id") String orderId,
                                                     @Query("user_id") String userId);


    }

}