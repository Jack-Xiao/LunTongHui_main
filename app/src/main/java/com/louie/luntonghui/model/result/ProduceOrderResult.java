package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 15/8/14.
 */
public class ProduceOrderResult {


    /**
     * goods_list : [{"gift":"0","goods_name":"18*2.50赛阳牌神龟王电动车外胎","discount_new":"5","danwei":"条","goods_price":"26.00","is_real":"1","goods_id":"1999","goods_sn":"104024","discount":"5","premium_id":"0","goods_attr":"","rec_id":"1570","rid":"0","discount_type":"1","extension_code":"","is_gift":"0","goods_number":"10","parent_id":"0","subtotal":"260.00","goods_thumb":"","market_price":"30.60","guige":"1*30","profit":"25.50"},{"gift":"0","goods_name":"测试","discount_new":"0","danwei":"个","goods_price":"200.00","is_real":"1","goods_id":"364","goods_sn":"ECS000364","discount":"0","premium_id":"1570","goods_attr":"","rec_id":"1569","rid":"1570","discount_type":"0","extension_code":"","is_gift":"7","goods_number":"1","parent_id":"0","subtotal":"200.00","goods_thumb":"","market_price":"240.00","guige":"1*20","profit":"0.00"}]
     * rsgmsg : 保存成功
     * rsgcode : 000
     * order : {"shipping_id":"0","country":"1","referer":"","pay_note":"","handler":1,"agent":"1","inv_content":"","bonus":"0.00","exist_real_goods":true,"discount":"130.00","genorder":"0","formated_bonus":"0.00","how_oos":"","order_status":"0","best_time":"","formated_surplus":"0.00","province":"6","formated_shipping_fee":"0.00","order_amount":"330.00","total_fee":"330.00","formated_money_paid":"0.00","tel":"","money_paid":"0.00","sign_building":"","shipping_name":"","inv_payee":"","formated_pay_fee":"0.00","card_message":"","pack_fee":"0.00","extension_id":"0","pack_name":"","consignee":"哭","will_get_integral":"260.00","agency_id":"0","formated_add_time":"2015-08-13 00:14:57","insure_fee":"0.00","integral_money":0,"tax":"0.00","formated_insure_fee":"0.00","formated_card_fee":"0.00","card_id":"0","shipping_status":"0","pay_time":"","zipcode":"","shipping_fee":"0.00","user_id":"58","parent_id":"0","district":"693","formated_tax":"0.00","how_surplus_name":"","card_name":"","formated_total_fee":"330.00","how_oos_name":"","pay_id":"2","order_id":"653","formated_goods_amount":"460.00","formated_discount":"130.00","city":"76","postscript":"","user_name":"0","from_ad":"0","is_separate":"0","card_fee":"0.00","pay_status":"0","extension_code":"","pay_online":"","formated_order_amount":"330.00","how_surplus":"","integral":"0.00","formated_integral_money":"0.00","bonus_id":"0","shipping_time":"","inv_type":"","profit":"0.00","email":"","log_id":"1503","address":"家具特价","surplus":"0.00","pack_id":"0","goods_amount":"460.00","invoice_no":"","display":"0","mobile":"13417185094","pay_name":"货到付款","pay_desc":"","confirm_time":"","formated_pack_fee":"0.00","gys_id":"0","pay_fee":"0.00","to_buyer":"","add_time":"1439367297","order_sn":"2015081245223","allow_update_address":1}
     */
    public List<GoodsListEntity> goods_list;
    public String rsgmsg;
    public String rsgcode;
    public OrderEntity order;

    public static class GoodsListEntity {
        /**
         * gift : 0
         * goods_name : 18*2.50赛阳牌神龟王电动车外胎
         * discount_new : 5
         * danwei : 条
         * goods_price : 26.00
         * is_real : 1
         * goods_id : 1999
         * goods_sn : 104024
         * discount : 5
         * premium_id : 0
         * goods_attr :
         * rec_id : 1570
         * rid : 0
         * discount_type : 1
         * extension_code :
         * is_gift : 0
         * goods_number : 10
         * parent_id : 0
         * subtotal : 260.00
         * goods_thumb :
         * market_price : 30.60
         * guige : 1*30
         * profit : 25.50
         */
        public String gift;
        public String goods_name;
        public String discount_new;
        public String danwei;
        public String goods_price;
        public String is_real;
        public String goods_id;
        public String goods_sn;
        public String discount;
        public String premium_id;
        public String goods_attr;
        public String rec_id;
        public String rid;
        public String discount_type;
        public String extension_code;
        public String is_gift;
        public String goods_number;
        public String parent_id;
        public String subtotal;
        public String goods_thumb;
        public String market_price;
        public String guige;
        public String profit;
    }

    public static class OrderEntity {
        /**
         * shipping_id : 0
         * country : 1
         * referer :
         * pay_note :
         * handler : 1
         * agent : 1
         * inv_content :
         * bonus : 0.00
         * exist_real_goods : true
         * discount : 130.00
         * genorder : 0
         * formated_bonus : 0.00
         * how_oos :
         * order_status : 0
         * best_time :
         * formated_surplus : 0.00
         * province : 6
         * formated_shipping_fee : 0.00
         * order_amount : 330.00
         * total_fee : 330.00
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
         * consignee : 哭
         * will_get_integral : 260.00
         * agency_id : 0
         * formated_add_time : 2015-08-13 00:14:57
         * insure_fee : 0.00
         * integral_money : 0
         * tax : 0.00
         * formated_insure_fee : 0.00
         * formated_card_fee : 0.00
         * card_id : 0
         * shipping_status : 0
         * pay_time :
         * zipcode :
         * shipping_fee : 0.00
         * user_id : 58
         * parent_id : 0
         * district : 693
         * formated_tax : 0.00
         * how_surplus_name :
         * card_name :
         * formated_total_fee : 330.00
         * how_oos_name :
         * pay_id : 2
         * order_id : 653
         * formated_goods_amount : 460.00
         * formated_discount : 130.00
         * city : 76
         * postscript :
         * user_name : 0
         * from_ad : 0
         * is_separate : 0
         * card_fee : 0.00
         * pay_status : 0
         * extension_code :
         * pay_online :
         * formated_order_amount : 330.00
         * how_surplus :
         * integral : 0.00
         * formated_integral_money : 0.00
         * bonus_id : 0
         * shipping_time :
         * inv_type :
         * profit : 0.00
         * email :
         * log_id : 1503
         * address : 家具特价
         * surplus : 0.00
         * pack_id : 0
         * goods_amount : 460.00
         * invoice_no :
         * display : 0
         * mobile : 13417185094
         * pay_name : 货到付款
         * pay_desc :
         * confirm_time :
         * formated_pack_fee : 0.00
         * gys_id : 0
         * pay_fee : 0.00
         * to_buyer :
         * add_time : 1439367297
         * order_sn : 2015081245223
         * allow_update_address : 1
         */
        public String shipping_id;
        public String country;
        public String referer;
        public String pay_note;
        public String handler;
        public String agent;
        public String inv_content;
        public String bonus;
        public String exist_real_goods;
        public String discount;
        public String genorder;
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
        public String will_get_integral;
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
        public String user_name;
        public String from_ad;
        public String is_separate;
        public String card_fee;
        public String pay_status;
        public String extension_code;
        public String pay_online;
        public String formated_order_amount;
        public String how_surplus;
        public String integral;
        public String formated_integral_money;
        public String bonus_id;
        public String shipping_time;
        public String inv_type;
        public String profit;
        public String email;
        public String log_id;
        public String address;
        public String surplus;
        public String pack_id;
        public String goods_amount;
        public String invoice_no;
        public String display;
        public String mobile;
        public String pay_name;
        public String pay_desc;
        public String confirm_time;
        public String formated_pack_fee;
        public String gys_id;
        public String pay_fee;
        public String to_buyer;
        public String add_time;
        public String order_sn;
        public String allow_update_address;
    }
}