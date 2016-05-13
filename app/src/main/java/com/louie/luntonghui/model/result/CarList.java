package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Administrator on 2015/6/25.
 */
public class CarList {


    /**
     * total : {"saving":"1240.00","real_goods_count":41,"goods_price":"6200.00","goods_amount":6200,"market_price":"7440.00","save_rate":"17%","virtual_goods_count":0}
     * goods_list : [{"img":{"small":"http://120.25.224.250/images/Image.aspx2.jpg","thumb":"http://120.25.224.250/images/Image.aspx2.jpg","url":"http://120.25.224.250/images/Image.aspx2.jpg"},"gysmoney":"10.00","goods_price":"100.00","discounta":"0","is_real":"1","is_shipping":"0","discount":"0","goods_attr":"","pid":"463","rid":"0","extension_code":"","discount_name":"0","can_handsel":"0","profit":"200.00","discount_time":"0","goods_name":"测试2","original_img":"http://120.25.224.250/images/Image.aspx2.jpg","goods_id":"463","goods_sn":"ECS000463","rec_id":"95","goods_attr_id":"","is_gift":"0","goods_number":"20","rec_type":"0","parent_id":"0","subtotal":"2000.00","market_price":"120.00","goods_img":"http://120.25.224.250/images/Image.aspx2.jpg"},{"img":{"small":"http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg","thumb":"http://120.25.224.250/images/201503/goods_img/364_G_1425995810795.jpg","url":"http://120.25.224.250/images/201503/source_img/364_G_1425995810453.png"},"gysmoney":"20.00","goods_price":"200.00","discounta":"0","is_real":"1","is_shipping":"0","discount":"0","goods_attr":"","pid":"364","rid":"0","extension_code":"","discount_name":"0","can_handsel":"0","profit":"420.00","discount_time":"0","goods_name":"测试","original_img":"http://120.25.224.250/images/201503/source_img/364_G_1425995810453.png","goods_id":"364","goods_sn":"ECS000364","rec_id":"96","goods_attr_id":"","is_gift":"0","goods_number":"21","rec_type":"0","parent_id":"0","subtotal":"4200.00","market_price":"240.00","goods_img":"http://120.25.224.250/images/201503/goods_img/364_G_1425995810795.jpg"}]
     */
    public TotalEntity total;
    public List<Goods_listEntity> goods_list;

    public String msg;

    public class TotalEntity {
        /**
         * saving : 1240.00
         * real_goods_count : 41
         * goods_price : 6200.00
         * goods_amount : 6200
         * market_price : 7440.00
         * save_rate : 17%
         * virtual_goods_count : 0
         */
        public String saving;
        public int real_goods_count;
        public String goods_price;
        public double goods_amount;
        public String market_price;
        public String save_rate;
        public int virtual_goods_count;
    }

    public class Goods_listEntity {
        /**
         * img : {"small":"http://120.25.224.250/images/Image.aspx2.jpg","thumb":"http://120.25.224.250/images/Image.aspx2.jpg","url":"http://120.25.224.250/images/Image.aspx2.jpg"}
         * gysmoney : 10.00
         * goods_price : 100.00
         * discounta : 0
         * is_real : 1
         * is_shipping : 0
         * discount : 0
         * goods_attr :
         * pid : 463
         * rid : 0    //
         * extension_code :
         * discount_name : 0
         * can_handsel : 0
         * profit : 200.00
         * discount_time : 0
         * goods_name : 测试2
         * original_img : http://120.25.224.250/images/Image.aspx2.jpg
         * goods_id : 463
         * goods_sn : ECS000463
         * rec_id : 95
         * goods_attr_id :
         * is_gift : 0
         * goods_number : 20
         * rec_type : 0
         * parent_id : 0
         * subtotal : 2000.00
         * market_price : 120.00
         * goods_img : http://120.25.224.250/images/Image.aspx2.jpg
         */
        public ImgEntity img;
        public String gysmoney;
        public String goods_price;
        public String discounta;
        public String is_real;
        public String is_shipping;
        public String discount;
        public String goods_attr;
        public String pid;
        public String rid;
        public String extension_code;
        public String discount_name;
        public String can_handsel;
        public String profit;
        public String discount_time;
        public String goods_name;
        public String original_img;
        public String goods_id;
        public String goods_sn;
        public String rec_id;
        public String goods_attr_id;
        public String is_gift;
        public String goods_number;
        public String rec_type;
        public String parent_id;
        public String subtotal;
        public String market_price;
        public String goods_img;
        public String guige;
        public String danwei;
        public String discount_type;

        public class ImgEntity {
            /**
             * small : http://120.25.224.250/images/Image.aspx2.jpg
             * thumb : http://120.25.224.250/images/Image.aspx2.jpg
             * url : http://120.25.224.250/images/Image.aspx2.jpg
             */
            public String small;
            public String thumb;
            public String url;
        }
    }
}
