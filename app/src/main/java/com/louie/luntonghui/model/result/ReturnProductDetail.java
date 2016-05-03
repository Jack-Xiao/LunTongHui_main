package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 16/5/3.
 */
public class ReturnProductDetail extends Result {
    /**
     * order : {"order_id":"2030","return_id":"482","return_status":"1","order_amount":"100.4000","order_sn":"RN-2016-04-0003","add_time":"2016-04-26 07:38:25","user_id":"265","consignee":"叶02","mobile":"13563896542","address":"广东 广州 从化市 东纵路38号"}
     * goods_list : [{"goods_name":"26x175赛阳牌神龟王内胎（ 加长咀）","goods_number":"4","goods_price":"6.60","danwei":"条","goods_id":"443","goods_thumb":"http://120.25.224.250/images/201503/thumb_img/443_thumb_G_1426649306260.jpg","profit":"0.00","market_price":"7.91","return_number":"1.00","return_reason":"你妹"},{"goods_name":"12*234撒大声地我","goods_number":"4","goods_price":"23.00","danwei":"","goods_id":"2973","goods_thumb":"http://120.25.224.250/images/Image.aspx2.jpg","profit":"0.00","market_price":"23.00","return_number":"4.00","return_reason":"产品质量问题"},{"goods_name":"电动制胶(罗纹)","goods_number":"5","goods_price":"0.45","danwei":"付","goods_id":"2722","goods_thumb":"http://120.25.224.250/images/Image.aspx2.jpg","profit":"0.00","market_price":"0.50","return_number":"4.00","return_reason":"其他"}]
     */

    public DataEntity data;

    public static class DataEntity {
        /**
         * order_id : 2030
         * return_id : 482
         * return_status : 1
         * order_amount : 100.4000
         * order_sn : RN-2016-04-0003
         * add_time : 2016-04-26 07:38:25
         * user_id : 265
         * consignee : 叶02
         * mobile : 13563896542
         * address : 广东 广州 从化市 东纵路38号
         */

        public OrderEntity order;
        /**
         * goods_name : 26x175赛阳牌神龟王内胎（ 加长咀）
         * goods_number : 4
         * goods_price : 6.60
         * danwei : 条
         * goods_id : 443
         * goods_thumb : http://120.25.224.250/images/201503/thumb_img/443_thumb_G_1426649306260.jpg
         * profit : 0.00
         * market_price : 7.91
         * return_number : 1.00
         * return_reason : 你妹
         */

        public List<GoodsListEntity> goods_list;

        public static class OrderEntity {
            public String order_id;
            public String return_id;
            public String return_status;
            public String order_amount;
            public String order_sn;
            public String add_time;
            public String user_id;
            public String consignee;
            public String mobile;
            public String address;
        }

        public static class GoodsListEntity {
            public String goods_name;
            public String goods_number;
            public String goods_price;
            public String danwei;
            public String goods_id;
            public String goods_thumb;
            public String profit;
            public String market_price;
            public String return_number;
            public String return_reason;
        }
    }
}
