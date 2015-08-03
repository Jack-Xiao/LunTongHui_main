package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public class Test {


    /**
     * goods_list : [{"goods_name":"测试","goods_price":"200.00","is_real":"1","goods_id":"364","goods_sn":"ECS000364","goods_attr":"","rec_id":"81","extension_code":"","is_gift":"0","goods_number":"21","parent_id":"0","subtotal":"4200.00","goods_thumb":"http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg","market_price":"240.00","profit":"420.00"},{"goods_name":"测试2","goods_price":"100.00","is_real":"1","goods_id":"463","goods_sn":"ECS000463","goods_attr":"","rec_id":"80","extension_code":"","is_gift":"0","goods_number":"20","parent_id":"0","subtotal":"2000.00","goods_thumb":"http://120.25.224.250/images/Image.aspx2.jpg","market_price":"120.00","profit":"200.00"}]
     * payment_list : [{"format_pay_fee":"0.00","pay_config":"","pay_code":"alipay_wap","pay_name":"手机支付宝","pay_fee":"0","pay_id":"4","pay_desc":"","is_cod":"0"},{"format_pay_fee":"0.00","pay_config":"","pay_code":"alipay","pay_name":"支付宝","pay_fee":"0","pay_id":"1","pay_desc":"","is_cod":"0"}]
     * order : {"shipping_id":"0","country":"1","referer":"","pay_note":"","agent":"1","inv_content":"","bonus":"0.00","exist_real_goods":true,"discount":"0.00","formated_bonus":"0.00","how_oos":"","order_status":"未确认","best_time":"","formated_surplus":"0.00","province":"6","formated_shipping_fee":"0.00","order_amount":"6200.00","total_fee":"6200.00","formated_money_paid":"0.00","tel":"","money_paid":"0.00","sign_building":"","shipping_name":"","inv_payee":"","formated_pay_fee":"0.00","card_message":"","pack_fee":"0.00","extension_id":"0","pack_name":"","consignee":"测试订单","agency_id":"0","formated_add_time":"2015-06-25 15:47:57","insure_fee":"0.00","integral_money":"0.00","tax":"0.00","formated_insure_fee":"0.00","formated_card_fee":"0.00","card_id":"0","shipping_status":"未发货","pay_time":"","zipcode":"","shipping_fee":"0.00","user_id":"10","parent_id":"0","district":"693","formated_tax":"0.00","how_surplus_name":"","card_name":"","formated_total_fee":"6200.00","how_oos_name":"","pay_id":"0","order_id":"36","formated_goods_amount":"6200.00","formated_discount":"0.00","city":"76","postscript":"","from_ad":"0","is_separate":"0","card_fee":"0.00","pay_status":"未付款","extension_code":"","pay_online":"","formated_order_amount":"6200.00","how_surplus":"","integral":0,"formated_integral_money":"0.00","bonus_id":"0","shipping_time":"","inv_type":"","profit":"0.00","email":"","address":"测试订单测试订单测试订单","surplus":"0.00","pack_id":"0","goods_amount":"6200.00","invoice_no":"","display":"0","mobile":"13433333333","pay_name":"","confirm_time":"","formated_pack_fee":"0.00","gys_id":"0","pay_fee":"0.00","to_buyer":"","add_time":"1435218477","order_sn":"2015062577088","allow_update_address":1}
     */
    public List<Goods_listEntity> goods_list;
    public List<Payment_listEntity> payment_list;
    public OrderEntity order;

    public class Goods_listEntity {
        /**
         * goods_name : 测试
         * goods_price : 200.00
         * is_real : 1
         * goods_id : 364
         * goods_sn : ECS000364
         * goods_attr :
         * rec_id : 81
         * extension_code :
         * is_gift : 0
         * goods_number : 21
         * parent_id : 0
         * subtotal : 4200.00
         * goods_thumb : http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg
         * market_price : 240.00
         * profit : 420.00
         */
        public String goods_name;
        public String goods_price;
        public String is_real;
        public String goods_id;
        public String goods_sn;
        public String goods_attr;
        public String rec_id;
        public String extension_code;
        public String is_gift;
        public String goods_number;
        public String parent_id;
        public String subtotal;
        public String goods_thumb;
        public String market_price;
        public String profit;
    }

    public class Payment_listEntity {
        /**
         * format_pay_fee : 0.00
         * pay_config :
         * pay_code : alipay_wap
         * pay_name : 手机支付宝
         * pay_fee : 0
         * pay_id : 4
         * pay_desc :
         * is_cod : 0
         */
        public String format_pay_fee;
        public String pay_config;
        public String pay_code;
        public String pay_name;
        public String pay_fee;
        public String pay_id;
        public String pay_desc;
        public String is_cod;
    }

    public class OrderEntity {
        /**
         * shipping_id : 0
         * country : 1
         * referer :
         * pay_note :
         * agent : 1
         * inv_content :
         * bonus : 0.00
         * exist_real_goods : true
         * discount : 0.00
         * formated_bonus : 0.00
         * how_oos :
         * order_status : 未确认
         * best_time :
         * formated_surplus : 0.00
         * province : 6
         * formated_shipping_fee : 0.00
         * order_amount : 6200.00
         * total_fee : 6200.00
         * formated_money_paid : 0.00
         * tel :
         * money_paid : 0.00
         * sign_building :
         * shipping_name :
         * inv_payee :
         * formated_pay_fee : 0.00
         * card_message :
         * pack_fee : 0.00
         * extension_id : 0
         * pack_name :
         * consignee : 测试订单
         * agency_id : 0
         * formated_add_time : 2015-06-25 15:47:57
         * insure_fee : 0.00
         * integral_money : 0.00
         * tax : 0.00
         * formated_insure_fee : 0.00
         * formated_card_fee : 0.00
         * card_id : 0
         * shipping_status : 未发货
         * pay_time :
         * zipcode :
         * shipping_fee : 0.00
         * user_id : 10
         * parent_id : 0
         * district : 693
         * formated_tax : 0.00
         * how_surplus_name :
         * card_name :
         * formated_total_fee : 6200.00
         * how_oos_name :
         * pay_id : 0
         * order_id : 36
         * formated_goods_amount : 6200.00
         * formated_discount : 0.00
         * city : 76
         * postscript :
         * from_ad : 0
         * is_separate : 0
         * card_fee : 0.00
         * pay_status : 未付款
         * extension_code :
         * pay_online :
         * formated_order_amount : 6200.00
         * how_surplus :
         * integral : 0
         * formated_integral_money : 0.00
         * bonus_id : 0
         * shipping_time :
         * inv_type :
         * profit : 0.00
         * email :
         * address : 测试订单测试订单测试订单
         * surplus : 0.00
         * pack_id : 0
         * goods_amount : 6200.00
         * invoice_no :
         * display : 0
         * mobile : 13433333333
         * pay_name :
         * confirm_time :
         * formated_pack_fee : 0.00
         * gys_id : 0
         * pay_fee : 0.00
         * to_buyer :
         * add_time : 1435218477
         * order_sn : 2015062577088
         * allow_update_address : 1
         */
        public String shipping_id;
        public String country;
        public String referer;
        public String pay_note;
        public String agent;
        public String inv_content;
        public String bonus;
        public boolean exist_real_goods;
        public String discount;
        public String formated_bonus;
        public String how_oos;
        public String order_status;
        public String best_time;
        public String formated_surplus;
        public String province;
        public String formated_shipping_fee;
        public String order_amount;
        public String total_fee;
        public String formated_money_paid;
        public String tel;
        public String money_paid;
        public String sign_building;
        public String shipping_name;
        public String inv_payee;
        public String formated_pay_fee;
        public String card_message;
        public String pack_fee;
        public String extension_id;
        public String pack_name;
        public String consignee;
        public String agency_id;
        public String formated_add_time;
        public String insure_fee;
        public String integral_money;
        public String tax;
        public String formated_insure_fee;
        public String formated_card_fee;
        public String card_id;
        public String shipping_status;
        public String pay_time;
        public String zipcode;
        public String shipping_fee;
        public String user_id;
        public String parent_id;
        public String district;
        public String formated_tax;
        public String how_surplus_name;
        public String card_name;
        public String formated_total_fee;
        public String how_oos_name;
        public String pay_id;
        public String order_id;
        public String formated_goods_amount;
        public String formated_discount;
        public String city;
        public String postscript;
        public String from_ad;
        public String is_separate;
        public String card_fee;
        public String pay_status;
        public String extension_code;
        public String pay_online;
        public String formated_order_amount;
        public String how_surplus;
        public int integral;
        public String formated_integral_money;
        public String bonus_id;
        public String shipping_time;
        public String inv_type;
        public String profit;
        public String email;
        public String address;
        public String surplus;
        public String pack_id;
        public String goods_amount;
        public String invoice_no;
        public String display;
        public String mobile;
        public String pay_name;
        public String confirm_time;
        public String formated_pack_fee;
        public String gys_id;
        public String pay_fee;
        public String to_buyer;
        public String add_time;
        public String order_sn;
        public int allow_update_address;
    }
}