package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Administrator on 2015/7/8.
 */
public class ProduceOrder {

    public List<?> shipping_list;
    public TotalEntity total;
    public List<ConsigneeEntity> consignee;
    public List<Payment_listEntity> payment_list;
    public List<Cart_goodsEntity> cart_goods;

    /**
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
     */
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
        /**
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
         */
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
        /**
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
}