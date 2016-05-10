package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 16/5/3.
 */
public class ReturnProductDetail{


    /**
     * rsgcode : 000
     * rsgmsg : 请求成功
     * data : {"order":{"order_id":"1790","return_id":"509","return_status":"1","order_amount":"36.0000","order_sn":"RN-2016-05-0002","add_time":"2016-05-05 10:37:15","user_id":"277","consignee":"叶12","mobile":"13562854125","address":"广东 广州 海珠区 东风路大众车行"},"goods_list":[{"goods_name":"测试0405-5","goods_number":"5","goods_price":"12.00","danwei":"","goods_id":"3044","goods_thumb":"http://120.25.224.250/images/Image.aspx2.jpg","profit":"0.00","market_price":"0.00","return_number":"3.00","return_reason":"质量问题"}]}
     */

    public String rsgcode;
    public String rsgmsg;
    /**
     * order : {"order_id":"1790","return_id":"509","return_status":"1","order_amount":"36.0000","order_sn":"RN-2016-05-0002","add_time":"2016-05-05 10:37:15","user_id":"277","consignee":"叶12","mobile":"13562854125","address":"广东 广州 海珠区 东风路大众车行"}
     * goods_list : [{"goods_name":"测试0405-5","goods_number":"5","goods_price":"12.00","danwei":"","goods_id":"3044","goods_thumb":"http://120.25.224.250/images/Image.aspx2.jpg","profit":"0.00","market_price":"0.00","return_number":"3.00","return_reason":"质量问题"}]
     */

    public DataEntity data;

    public static class DataEntity {
        /**
         * order_id : 1790
         * return_id : 509
         * return_status : 1
         * order_amount : 36.0000
         * order_sn : RN-2016-05-0002
         * add_time : 2016-05-05 10:37:15
         * user_id : 277
         * consignee : 叶12
         * mobile : 13562854125
         * address : 广东 广州 海珠区 东风路大众车行
         */

        public OrderEntity order;
        /**
         * goods_name : 测试0405-5
         * goods_number : 5
         * goods_price : 12.00
         * danwei :
         * goods_id : 3044
         * goods_thumb : http://120.25.224.250/images/Image.aspx2.jpg
         * profit : 0.00
         * market_price : 0.00
         * return_number : 3.00
         * return_reason : 质量问题
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
            //public String profit;
            public String market_price;
            public String return_number;
            public String return_reason;
        }
    }
}
