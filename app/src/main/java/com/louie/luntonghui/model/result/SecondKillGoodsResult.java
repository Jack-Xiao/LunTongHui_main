package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 15/7/31.
 */
public class SecondKillGoodsResult {


    /**
     * listallcat : [{"give_integral":"22.00","danwei":"","discounta":"0","discount":"0","is_best":"1","is_promote":"0","is_hot":"0","integral":"0","discount_name":"0","sort_order":"100","discount_time":"0","goods_name":"18*2.125 赛阳牌神龟王电动车外胎","shop_price":"1.00","is_new":"0","display":"0","goods_id":"57","goods_sn":"ECS000057","promote_price":"0.00","goods_desc":"","goods_brief":"","goods_number":"7795","gys_money":"0.00","market_price":"23.46","guige":"","goods_img":"http://120.25.224.250/images/Image.aspx2.jpg"},{"give_integral":"100","danwei":"","discounta":"0","discount":"0","is_best":"1","is_promote":"0","is_hot":"1","integral":"50","discount_name":"0","sort_order":"100","discount_time":"0","goods_name":"14x175赛阳牌神龟王内胎（美咀）","shop_price":"1.00","is_new":"1","display":"0","goods_id":"38","goods_sn":"ECS000038","promote_price":"0.00","goods_desc":"","goods_brief":"诠释低碳，缔造尚品丁基尼内胎","goods_number":"1018","gys_money":"10.00","market_price":"5.52","guige":"1*50","goods_img":"http://120.25.224.250/images/201501/thumb_img/38_thumb_G_1421912353519.jpg"},{"give_integral":"200.00","danwei":"","discounta":"0","discount":"0","is_best":"0","is_promote":"0","is_hot":"0","integral":"0","discount_name":"0","sort_order":"100","discount_time":"0","goods_name":"测试","shop_price":"1.00","is_new":"0","display":"0","goods_id":"364","goods_sn":"ECS000364","promote_price":"0.00","goods_desc":"爱上大声地asd","goods_brief":"","goods_number":"1000","gys_money":"20.00","market_price":"240.00","guige":"1*20","goods_img":"http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg"}]
     * spiketime : [{"start_time":"2015-07-31 09:00","end_time":"2015-07-31 10:00"}]
     */
    public List<ListallcatEntity> listallcat;
    public List<SpiketimeEntity> spiketime;

    public static class ListallcatEntity {
        /**
         * give_integral : 22.00
         * danwei :
         * discounta : 0
         * discount : 0
         * is_best : 1
         * is_promote : 0
         * is_hot : 0
         * integral : 0
         * discount_name : 0
         * sort_order : 100
         * discount_time : 0
         * goods_name : 18*2.125 赛阳牌神龟王电动车外胎
         * shop_price : 1.00
         * is_new : 0
         * display : 0
         * goods_id : 57
         * goods_sn : ECS000057
         * promote_price : 0.00
         * goods_desc :
         * goods_brief :
         * goods_number : 7795
         * gys_money : 0.00
         * market_price : 23.46
         * guige :
         * goods_img : http://120.25.224.250/images/Image.aspx2.jpg
         */
        public String give_integral;
        public String danwei;
        public String discounta;
        public String discount;
        public String is_best;
        public String is_promote;
        public String is_hot;
        public String integral;
        public String discount_name;
        public String sort_order;
        public String discount_time;
        public String goods_name;
        public String shop_price;
        public String is_new;
        public String display;
        public String goods_id;
        public String goods_sn;
        public String promote_price;
        public String goods_desc;
        public String goods_brief;
        public String goods_number;
        public String gys_money;
        public String market_price;
        public String guige;
        public String goods_img;
    }

    public static class SpiketimeEntity {
        /**
         * start_time : 2015-07-31 09:00
         * end_time : 2015-07-31 10:00
         */
        public String start_time;
        public String end_time;
    }
}
