package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 15/8/13.
 */
public class MineAttentionResult {


    /**
     * listallcat : [{"give_integral":"200.00","danwei":"个","discounta":"1","discount":100,"is_best":"0","is_promote":"0","is_hot":"0","integral":"0","discount_name":"08月11号-08月14号:买满10/个打5折","sort_order":"100","discount_time":"打折的时间为2015-08-11到2015-08-14，赶快来抢吧！","goods_name":"测试","shop_price":"200.00","is_new":"0","display":"0","goods_id":"364","goods_sn":"ECS000364","promote_price":"0.00","goods_desc":"","rec_id":"118","goods_brief":"","goods_number":"1000","gys_money":"20.00","market_price":"240.00","guige":"1*20","goods_img":"http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg"},{"give_integral":"100","danwei":"件","discounta":"1","discount":6,"is_best":"0","is_promote":"0","is_hot":"0","integral":"0","discount_name":"08月11号-08月14号:买满10/件打5折","sort_order":"100","discount_time":"打折的时间为2015-08-11到2015-08-14，赶快来抢吧！","goods_name":"夏测试","shop_price":"12.00","is_new":"0","display":"0","goods_id":"283","goods_sn":"12345678999","promote_price":"0.00","goods_desc":"","rec_id":"117","goods_brief":"","goods_number":"6004","gys_money":"10.00","market_price":"14.39","guige":"1*50","goods_img":"http://120.25.224.250/images/201412/thumb_img/283_thumb_G_1419239917815.jpg"}]
     */
    public List<ListallcatEntity> listallcat;

    public static class ListallcatEntity {
        /**
         * give_integral : 200.00
         * danwei : 个
         * discounta : 1
         * discount : 100
         * is_best : 0
         * is_promote : 0
         * is_hot : 0
         * integral : 0
         * discount_name : 08月11号-08月14号:买满10/个打5折
         * sort_order : 100
         * discount_time : 打折的时间为2015-08-11到2015-08-14，赶快来抢吧！
         * goods_name : 测试
         * shop_price : 200.00
         * is_new : 0
         * display : 0
         * goods_id : 364
         * goods_sn : ECS000364
         * promote_price : 0.00
         * goods_desc :
         * rec_id : 118
         * goods_brief :
         * goods_number : 1000
         * gys_money : 20.00
         * market_price : 240.00
         * guige : 1*20
         * goods_img : http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg
         *
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
        public String rec_id;
        public String goods_brief;
        public String goods_number;
        public String gys_money;
        public String market_price;
        public String guige;
        public String goods_img;
        public String discount_type;

        public String inventory;

    }
}
