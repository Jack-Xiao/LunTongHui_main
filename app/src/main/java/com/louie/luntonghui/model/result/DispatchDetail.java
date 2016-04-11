package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 16/4/8.
 */
public class DispatchDetail {
    /**
     * order : {"order_id":"1675","order_sn":"2016032917775","user_id":"265","gys_id":"0","order_status":"1","uncheck_label":"1","shipping_status":"0","pay_status":"0","consignee":"叶02","country":"1","province":"6","city":"76","district":"692","address":"一林路52号后台","zipcode":"","tel":"","mobile":"13563258964","email":"","best_time":"","sign_building":"","postscript":"","shipping_id":"0","shipping_name":"","pay_id":"2","pay_name":"货到付款","how_oos":"","how_surplus":"","pack_name":"","card_name":"","card_message":"","inv_payee":"","inv_content":"","goods_amount":"64.00","shipping_fee":"0.00","insure_fee":"0.00","pay_fee":"0.00","pack_fee":"0.00","card_fee":"0.00","money_paid":"0.00","surplus":"0.00","integral":"0.00","integral_money":0,"bonus":"0.00","order_amount":"64.00","from_ad":"0","referer":"","add_time":"1459220145","confirm_time":"确认于 2016-03-31 15:41:13","pay_time":"","shipping_time":"","pack_id":"0","card_id":"0","bonus_id":"0","invoice_no":"","extension_code":"","extension_id":"0","to_buyer":"","pay_note":"","agency_id":"0","inv_type":"","tax":"0.00","is_separate":"0","parent_id":"0","discount":"0.00","relief":"0.00","discounts":"","discounts_id":"0","agent":"1","profit":"0.00","display":"0","genorder":"0","special":"0","group_id":"0","money_unpaid":"0.00","suspicious":"0","total_fee":"64.00","act_type":"","details":"","gift":"","formated_goods_amount":"64.00","formated_discount":"0.00","formated_tax":"0.00","formated_shipping_fee":"0.00","formated_insure_fee":"0.00","formated_pay_fee":"0.00","formated_pack_fee":"0.00","formated_card_fee":"0.00","formated_total_fee":"64.00","formated_money_paid":"0.00","formated_bonus":"0.00","formated_integral_money":"0.00","formated_surplus":"0.00","formated_order_amount":"64.00","formated_add_time":"2016-03-29 10:55:45","will_get_integral":"64.00","allow_update_address":0,"exist_real_goods":true,"user_name":"","pay_desc":"开通城市：×××\r\n货到付款区域：×××","pay_online":"","how_oos_name":"","how_surplus_name":"","handler":2}
     * goods_list : [{"goods_thumb":"http://120.25.224.250/images/201505/thumb_img/1701_thumb_G_1431483500580.jpg","rec_id":"5302","guige":"1*30","danwei":"条","goods_id":"1701","goods_name":"厚成牌700C中孔实心胎【红色】","goods_sn":"121026","profit":"0.00","market_price":"36.00","goods_number":"2","discount_new":"0","premium_id":"0","gift":"0","goods_price":"32.00","goods_attr":"","is_real":"1","parent_id":"0","is_gift":"0","rid":"0","subtotal":"64.00","extension_code":"","relief":"0.00","discount":"0","discount_type":"0"}]
     */

    public List<DataEntity> data;

    public static class DataEntity {
        /**
         * order_id : 1675
         * order_sn : 2016032917775
         * user_id : 265
         * gys_id : 0
         * order_status : 1
         * uncheck_label : 1
         * shipping_status : 0
         * pay_status : 0
         * consignee : 叶02
         * country : 1
         * province : 6
         * city : 76
         * district : 692
         * address : 一林路52号后台
         * zipcode :
         * tel :
         * mobile : 13563258964
         * email :
         * best_time :
         * sign_building :
         * postscript :
         * shipping_id : 0
         * shipping_name :
         * pay_id : 2
         * pay_name : 货到付款
         * how_oos :
         * how_surplus :
         * pack_name :
         * card_name :
         * card_message :
         * inv_payee :
         * inv_content :
         * goods_amount : 64.00
         * shipping_fee : 0.00
         * insure_fee : 0.00
         * pay_fee : 0.00
         * pack_fee : 0.00
         * card_fee : 0.00
         * money_paid : 0.00
         * surplus : 0.00
         * integral : 0.00
         * integral_money : 0
         * bonus : 0.00
         * order_amount : 64.00
         * from_ad : 0
         * referer :
         * add_time : 1459220145
         * confirm_time : 确认于 2016-03-31 15:41:13
         * pay_time :
         * shipping_time :
         * pack_id : 0
         * card_id : 0
         * bonus_id : 0
         * invoice_no :
         * extension_code :
         * extension_id : 0
         * to_buyer :
         * pay_note :
         * agency_id : 0
         * inv_type :
         * tax : 0.00
         * is_separate : 0
         * parent_id : 0
         * discount : 0.00
         * relief : 0.00
         * discounts :
         * discounts_id : 0
         * agent : 1
         * profit : 0.00
         * display : 0
         * genorder : 0
         * special : 0
         * group_id : 0
         * money_unpaid : 0.00
         * suspicious : 0
         * total_fee : 64.00
         * act_type :
         * details :
         * gift :
         * formated_goods_amount : 64.00
         * formated_discount : 0.00
         * formated_tax : 0.00
         * formated_shipping_fee : 0.00
         * formated_insure_fee : 0.00
         * formated_pay_fee : 0.00
         * formated_pack_fee : 0.00
         * formated_card_fee : 0.00
         * formated_total_fee : 64.00
         * formated_money_paid : 0.00
         * formated_bonus : 0.00
         * formated_integral_money : 0.00
         * formated_surplus : 0.00
         * formated_order_amount : 64.00
         * formated_add_time : 2016-03-29 10:55:45
         * will_get_integral : 64.00
         * allow_update_address : 0
         * exist_real_goods : true
         * user_name :
         * pay_desc : 开通城市：×××
         货到付款区域：×××
         * pay_online :
         * how_oos_name :
         * how_surplus_name :
         * handler : 2
         */

        public OrderEntity order;
        /**
         * goods_thumb : http://120.25.224.250/images/201505/thumb_img/1701_thumb_G_1431483500580.jpg
         * rec_id : 5302
         * guige : 1*30
         * danwei : 条
         * goods_id : 1701
         * goods_name : 厚成牌700C中孔实心胎【红色】
         * goods_sn : 121026
         * profit : 0.00
         * market_price : 36.00
         * goods_number : 2
         * discount_new : 0
         * premium_id : 0
         * gift : 0
         * goods_price : 32.00
         * goods_attr :
         * is_real : 1
         * parent_id : 0
         * is_gift : 0
         * rid : 0
         * subtotal : 64.00
         * extension_code :
         * relief : 0.00
         * discount : 0
         * discount_type : 0
         */

        public List<GoodsListEntity> goods_list;

        public static class OrderEntity {
            public String order_id;
            public String order_sn;
            public String user_id;
            public String gys_id;
            public String order_status;
            public String uncheck_label;
            public String shipping_status;
            public String pay_status;
            public String consignee;
            public String country;
            public String province;
            public String city;
            public String district;
            public String address;
            public String zipcode;
            public String tel;
            public String mobile;
            public String email;
            public String best_time;
            public String sign_building;
            public String postscript;
            public String shipping_id;
            public String shipping_name;
            public String pay_id;
            public String pay_name;
            public String how_oos;
            public String how_surplus;
            public String pack_name;
            public String card_name;
            public String card_message;
            public String inv_payee;
            public String inv_content;
            public String goods_amount;
            public String shipping_fee;
            public String insure_fee;
            public String pay_fee;
            public String pack_fee;
            public String card_fee;
            public String money_paid;
            public String surplus;
            public String integral;
            public String integral_money;
            public String bonus;
            public String order_amount;
            public String from_ad;
            public String referer;
            public String add_time;
            public String confirm_time;
            public String pay_time;
            public String shipping_time;
            public String pack_id;
            public String card_id;
            public String bonus_id;
            public String invoice_no;
            public String extension_code;
            public String extension_id;
            public String to_buyer;
            public String pay_note;
            public String agency_id;
            public String inv_type;
            public String tax;
            public String is_separate;
            public String parent_id;
            public String discount;
            public String relief;
            public String discounts;
            public String discounts_id;
            public String agent;
            public String profit;
            public String display;
            public String genorder;
            public String special;
            public String group_id;
            public String money_unpaid;
            public String suspicious;
            public String total_fee;
            public String act_type;
            public String details;
            public String gift;
            public String formated_goods_amount;
            public String formated_discount;
            public String formated_tax;
            public String formated_shipping_fee;
            public String formated_insure_fee;
            public String formated_pay_fee;
            public String formated_pack_fee;
            public String formated_card_fee;
            public String formated_total_fee;
            public String formated_money_paid;
            public String formated_bonus;
            public String formated_integral_money;
            public String formated_surplus;
            public String formated_order_amount;
            public String formated_add_time;
            public String will_get_integral;
            public String allow_update_address;
            public String exist_real_goods;
            public String user_name;
            public String pay_desc;
            public String pay_online;
            public String how_oos_name;
            public String how_surplus_name;
            public String handler;
        }

        public static class GoodsListEntity {
            public String goods_thumb;
            public String rec_id;
            public String guige;
            public String danwei;
            public String goods_id;
            public String goods_name;
            public String goods_sn;
            public String profit;
            public String market_price;
            public String goods_number;
            public String discount_new;
            public String premium_id;
            public String gift;
            public String goods_price;
            public String goods_attr;
            public String is_real;
            public String parent_id;
            public String is_gift;
            public String rid;
            public String subtotal;
            public String extension_code;
            public String relief;
            public String discount;
            public String discount_type;
        }
    }
}
