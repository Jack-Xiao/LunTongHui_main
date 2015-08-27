package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Administrator on 2015/7/8.
 */
public class ProduceOrder {
    /**
     * shipping_list : []
     * total : {"bonus_formated":"0.00","gysmoney":20,"shipping_insure":0,"goods_price":242,"bonus":0,"discount":121,"goods_price_formated":"242.00","market_price_formated":"498.06","surplus_formated":"0.00","card_fee":0,"tax_formated":"0.00","payable":242,"integral":0,"amount_formated":"121.00","save_rate":"51%","gift_amount":0,"cod_fee":0,"pack_fee":0,"amount":121,"discount_formated":"121.00","surplus":0,"will_get_integral":242,"will_get_bonus":"0.00","integral_money":0,"tax":0,"integral_formated":"0.00","shipping_insure_formated":"0.00","pay_fee_formated":"0.00","formated_market_price":"498.06","card_fee_formated":"0.00","formated_goods_price":"242.00","saving_formated":"256.06","shipping_fee":0,"saving":256.06,"formated_saving":"256.06","pack_fee_formated":"0.00","real_goods_count":2,"shipping_fee_formated":"0.00","availableintegral":24200,"market_price":498.06,"pay_fee":0,"ratio":"可用的轮通币为7457,可抵74.57元"}
     * consignee : [{"country":"1","consignee":"哭","address":"家具特价","city":"76","address_id":"224","mobile":"13417185094","address_name":"","zipcode":"","best_time":"","province":"6","user_id":"58","district":"693","tel":"","sign_building":"","email":""}]
     * payment_list : [{"pay_code":"alipay_wap","pay_name":"手机支付宝","sel":0,"pay_id":"4","pay_desc":""},{"pay_code":"cod","pay_name":"货到付款","sel":0,"pay_id":"2","pay_desc":""},{"pay_code":"alipay","pay_name":"支付宝","sel":0,"pay_id":"1","pay_desc":""}]
     * cart_goods : [{"gysmoney":"0.00","danwei":"","goods_price":"22.00","discounta":"1","is_real":"1","is_shipping":"0","discount":"5","goods_attr":"","rid":"0","extension_code":"","discount_name":"","discount_time":"打折的时间为2015-08-11到2015-08-15，赶快来抢吧！","goods_name":"18*2.125 赛阳牌神龟王电动车外胎","goods_id":"57","goods_sn":"ECS000057","rec_id":"2000","goods_attr_id":"","discount_type":"3","formated_market_price":"23.46","formated_goods_price":"22.00","is_gift":"0","goods_number":"11","user_id":"58","formated_subtotal":"242.00","parent_id":"0","subtotal":"242.00","goods_thumb":"","market_price":"23.46"},{"gysmoney":"20.00","danwei":"个","goods_price":"0.00","discounta":"1","is_real":"1","is_shipping":"0","discount":"","goods_attr":"","rid":"2000","extension_code":"","discount_name":"","discount_time":"打折的时间为2015-08-11到2015-08-15，赶快来抢吧！","goods_name":"测试","goods_id":"364","goods_sn":"ECS000364","rec_id":"2001","goods_attr_id":"","discount_type":"1","formated_market_price":"240.00","formated_goods_price":"0.00","is_gift":"7","goods_number":"1","user_id":"58","formated_subtotal":"0.00","parent_id":"0","subtotal":"0.00","goods_thumb":"","market_price":"240.00"}]
     */
    public List<?> shipping_list;
    public TotalEntity total;
    public List<ConsigneeEntity> consignee;
    public List<PaymentListEntity> payment_list;
    public List<CartGoodsEntity> cart_goods;

    public static class TotalEntity {
        /**
         * bonus_formated : 0.00
         * gysmoney : 20
         * shipping_insure : 0
         * goods_price : 242
         * bonus : 0
         * discount : 121
         * goods_price_formated : 242.00
         * market_price_formated : 498.06
         * surplus_formated : 0.00
         * card_fee : 0
         * tax_formated : 0.00
         * payable : 242
         * integral : 0
         * amount_formated : 121.00
         * save_rate : 51%
         * gift_amount : 0
         * cod_fee : 0
         * pack_fee : 0
         * amount : 121
         * discount_formated : 121.00
         * surplus : 0
         * will_get_integral : 242
         * will_get_bonus : 0.00
         * integral_money : 0
         * tax : 0
         * integral_formated : 0.00
         * shipping_insure_formated : 0.00
         * pay_fee_formated : 0.00
         * formated_market_price : 498.06
         * card_fee_formated : 0.00
         * formated_goods_price : 242.00
         * saving_formated : 256.06
         * shipping_fee : 0
         * saving : 256.06
         * formated_saving : 256.06
         * pack_fee_formated : 0.00
         * real_goods_count : 2
         * shipping_fee_formated : 0.00
         * availableintegral : 24200
         * market_price : 498.06
         * pay_fee : 0
         * ratio : 可用的轮通币为7457,可抵74.57元
         */
        public String bonus_formated;
        public String gysmoney;
        public String shipping_insure;
        public double goods_price;
        public String bonus;
        public String discount;
        public String goods_price_formated;
        public String market_price_formated;
        public String surplus_formated;
        public String card_fee;
        public String tax_formated;
        public String payable;
        public String integral;
        public String amount_formated;
        public String save_rate;
        public String gift_amount;
        public String cod_fee;
        public String pack_fee;
        public String amount;
        public String discount_formated;
        public String surplus;
        public String will_get_integral;
        public String will_get_bonus;
        public String integral_money;
        public String tax;
        public String integral_formated;
        public String shipping_insure_formated;
        public String pay_fee_formated;
        public String formated_market_price;
        public String card_fee_formated;
        public String formated_goods_price;
        public String saving_formated;
        public String shipping_fee;
        public String saving;
        public String formated_saving;
        public String pack_fee_formated;
        public String real_goods_count;
        public String shipping_fee_formated;
        public String availableintegral;
        public String market_price;
        public String pay_fee;
        public String ratio;
    }

    public static class ConsigneeEntity {
        /**
         * country : 1
         * consignee : 哭
         * address : 家具特价
         * city : 76
         * address_id : 224
         * mobile : 13417185094
         * address_name :
         * zipcode :
         * best_time :
         * province : 6
         * user_id : 58
         * district : 693
         * tel :
         * sign_building :
         * email :
         */
        public String country;
        public String consignee;
        public String address;
        public String city;
        public String address_id;
        public String mobile;
        public String address_name;
        public String zipcode;
        public String best_time;
        public String province;
        public String user_id;
        public String district;
        public String tel;
        public String sign_building;
        public String email;
    }

    public static class PaymentListEntity {
        /**
         * pay_code : alipay_wap
         * pay_name : 手机支付宝
         * sel : 0
         * pay_id : 4
         * pay_desc :
         */
        public String pay_code;
        public String pay_name;
        public String sel;
        public String pay_id;
        public String pay_desc;
    }

    public class CartGoodsEntity {
        /**
         * gysmoney : 0.00
         * danwei :
         * goods_price : 22.00
         * discounta : 1
         * is_real : 1
         * is_shipping : 0
         * discount : 5
         * goods_attr :
         * rid : 0
         * extension_code :
         * discount_name :
         * discount_time : 打折的时间为2015-08-11到2015-08-15，赶快来抢吧！
         * goods_name : 18*2.125 赛阳牌神龟王电动车外胎
         * goods_id : 57
         * goods_sn : ECS000057
         * rec_id : 2000
         * goods_attr_id :
         * discount_type : 3
         * formated_market_price : 23.46
         * formated_goods_price : 22.00
         * is_gift : 0
         * goods_number : 11
         * user_id : 58
         * formated_subtotal : 242.00
         * parent_id : 0
         * subtotal : 242.00
         * goods_thumb :
         * market_price : 23.46
         */
        public String gysmoney;
        public String danwei;
        public String goods_price;
        public String discounta;
        public String is_real;
        public String is_shipping;
        public String discount;
        public String goods_attr;
        public String rid;
        public String extension_code;
        public String discount_name;
        public String discount_time;
        public String goods_name;
        public String goods_id;
        public String goods_sn;
        public String rec_id;
        public String goods_attr_id;
        public String discount_type;
        public String formated_market_price;
        public String formated_goods_price;
        public String is_gift;
        public String goods_number;
        public String user_id;
        public String formated_subtotal;
        public String parent_id;
        public String subtotal;
        public String goods_thumb;
        public String market_price;
        public String guige;
    } /**




  /*  public List<?> shipping_list;
    public TotalEntity total;
    public List<ConsigneeEntity> consignee;
    public List<Payment_listEntity> payment_list;
    public List<Cart_goodsEntity> cart_goods;

    *//**
     * goods_name : 18*2.125 ������������綯����̥
     * gysmoney : 0.00
     * goods_price : 19.55
     * is_real : 1
     * is_shipping : 0
     * goods_id : 57
     * goods_sn : ECS000057
     * goods_attr :
     * rec_id : 163
     * goods_attr_id :
     * formated_market_price : 23.46
     * formated_goods_price : 19.55
     * extension_code :
     * is_gift : 0
     * goods_number : 1
     * user_id : 152
     * formated_subtotal : 19.55
     * parent_id : 0
     * subtotal : 19.55
     * goods_thumb : http://120.25.224.250/images/Image.aspx2.jpg
     * market_price : 23.46
     *//*
    public class Cart_goodsEntity {
        public String goods_name;
        public String gysmoney;
        public String goods_price;
        public String is_real;
        public String is_shipping;
        public String goods_id;
        public String goods_sn;
        public String goods_attr;
        public String rec_id;
        public String goods_attr_id;
        public String formated_market_price;
        public String formated_goods_price;
        public String extension_code;
        public String is_gift;
        public String goods_number;
        public String user_id;
        public String formated_subtotal;
        public String parent_id;
        public String subtotal;
        public String goods_thumb;
        public String market_price;
    }

    public class Payment_listEntity {

    }

    public class TotalEntity {
        *//**
     * bonus_formated : 0.00
     * shipping_insure : 0
     * goods_price : 19.55
     * bonus : 0
     * discount : null
     * goods_price_formated : 19.55
     * market_price_formated : 23.46
     * surplus_formated : 0.00
     * card_fee : 0
     * tax_formated : 0.00
     * payable : 19.55
     * integral : 0
     * amount_formated : 19.55
     * save_rate : 17%
     * gift_amount : 0
     * cod_fee : 0
     * pack_fee : 0
     * amount : 19.55
     * discount_formated : 0.00
     * surplus : 0
     * will_get_integral : 77128
     * will_get_bonus : 0.00
     * integral_money : 0
     * tax : 0
     * integral_formated : 0.00
     * shipping_insure_formated : 0.00
     * pay_fee_formated : 0.00
     * formated_market_price : 23.46
     * card_fee_formated : 0.00
     * formated_goods_price : 19.55
     * saving_formated : 3.91
     * shipping_fee : 0
     * saving : 3.91
     * formated_saving : 3.91
     * pack_fee_formated : 0.00
     * real_goods_count : 1
     * shipping_fee_formated : 0.00
     * availableintegral : 1955
     * market_price : 23.46
     * pay_fee : 0
     * ratio : ���õ���ͨ��Ϊ100,�ɵ�1Ԫ
     *//*
        public String bonus_formated;
        public int shipping_insure;
        public double goods_price;
        public int bonus;
        public String discount;
        public String goods_price_formated;
        public String market_price_formated;
        public String surplus_formated;
        public int card_fee;
        public String tax_formated;
        public double payable;
        public int integral;
        public String amount_formated;
        public String save_rate;
        public int gift_amount;
        public int cod_fee;
        public int pack_fee;
        public double amount;
        public String discount_formated;
        public int surplus;
        public int will_get_integral;
        public String will_get_bonus;
        public int integral_money;
        public int tax;
        public String integral_formated;
        public String shipping_insure_formated;
        public String pay_fee_formated;
        public String formated_market_price;
        public String card_fee_formated;
        public String formated_goods_price;
        public String saving_formated;
        public int shipping_fee;
        public double saving;
        public String formated_saving;
        public String pack_fee_formated;
        public int real_goods_count;
        public String shipping_fee_formated;
        public int availableintegral;
        public double market_price;
        public int pay_fee;
        public String ratio;
    }

    public class ConsigneeEntity {
        *//**
     * country : 1
     * consignee : ը����
     * address : �ڶ�191��
     * city : 76
     * address_id : 153
     * mobile : 15989054686
     * address_name :
     * zipcode :
     * best_time :
     * province : 6
     * user_id : 152
     * district : 966
     * tel :
     * sign_building :
     * email :
     *//*
        public String country;
        public String consignee;
        public String address;
        public String city;
        public String address_id;
        public String mobile;
        public String address_name;
        public String zipcode;
        public String best_time;
        public String province;
        public String user_id;
        public String district;
        public String tel;
        public String sign_building;
        public String email;
    }*/
}