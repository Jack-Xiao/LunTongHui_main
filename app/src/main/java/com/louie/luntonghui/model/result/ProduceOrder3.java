package com.louie.luntonghui.model.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jack on 16/2/17.
 */
public class ProduceOrder3 extends Result implements Serializable{

    /**
     * real_goods_count : 5
     * gift_amount : 0
     * goods_price : 1732.3
     * market_price : 715.56
     * discount : null
     * pack_fee : 0
     * card_fee : 0
     * shipping_fee : 0
     * shipping_insure : 0
     * integral_money : 0
     * bonus : 0
     * surplus : 0
     * cod_fee : 0
     * pay_fee : 0
     * tax : 0
     * gysmoney : 13.3
     * payable : 1732.3
     * saving : -1016.74
     * save_rate : -142%
     * goods_price_formated : 1732.30
     * market_price_formated : 715.56
     * saving_formated : -1016.74
     * integral_control : 1
     * ratio : 可用的轮通币为150,可抵1.5元
     * availableintegral : 173230
     * relief : null
     * id : 0
     * restricts : null
     * g_id :
     * discounts :
     * difference :
     * act_type_ext : null
     * act_type :
     * goods_name :
     * min_amount :
     * discount_formated : 0.00
     * tax_formated : 0.00
     * pack_fee_formated : 0.00
     * card_fee_formated : 0.00
     * bonus_formated : 0.00
     * shipping_fee_formated : 0.00
     * shipping_insure_formated : 0.00
     * amount : 1732.3
     * formated_order_price : 1732.3
     * reducecost : 1719
     * surplus_formated : 0.00
     * integral : 0
     * integral_formated : 0.00
     * pay_fee_formated : 0.00
     * amount_formated : 1732.30
     * will_get_integral : 0
     * will_get_bonus : 0.00
     * formated_goods_price : 1732.30
     * formated_market_price : 715.56
     * formated_saving : -1016.74
     */

    public TotalEntity total;
    /**
     * address_id : 1681
     * address_name :
     * user_id : 1411
     * consignee : cfdd
     * email :
     * country : 1
     * province : 6
     * city : 76
     * district : 692
     * address : dbdbsb
     * zipcode :
     * tel :
     * mobile : 13455555555
     * sign_building :
     * best_time :
     */

    public List<ConsigneeEntity> consignee;
    /**
     * goods_thumb : http://120.24.209.210/images/201511/thumb_img/3062_thumb_G_1446859182947.jpg
     * danwei : 条
     * guige : 1*30
     * gysmoney : 2.00
     * rec_id : 116391
     * rid : 0
     * user_id : 1411
     * goods_id : 3062
     * goods_name : 22*2.125亿佳牌电动车外（特价）
     * goods_sn : ECS003060
     * goods_number : 50
     * market_price : 0.00
     * goods_price : 18.50
     * goods_attr_id :
     * goods_attr :
     * is_real : 1
     * extension_code :
     * parent_id : 0
     * is_gift : 0
     * is_shipping : 0
     * subtotal : 925.00
     * formated_market_price : 0.00
     * formated_goods_price : 18.50
     * formated_subtotal : 925.00
     * discounta : 0
     * discount : 0
     * discount_type : 0
     * discount_time : 0
     * discount_name : 0
     */

    public List<CartGoodsEntity> cart_goods;
    /**
     * consignee : [{"address_id":"1681","address_name":"","user_id":"1411","consignee":"cfdd","email":"","country":"1","province":"6","city":"76","district":"692","address":"dbdbsb","zipcode":"","tel":"","mobile":"13455555555","sign_building":"","best_time":""}]
     * cart_goods : [{"goods_thumb":"http://120.24.209.210/images/201511/thumb_img/3062_thumb_G_1446859182947.jpg","danwei":"条","guige":"1*30","gysmoney":"2.00","rec_id":"116391","rid":"0","user_id":"1411","goods_id":"3062","goods_name":"22*2.125亿佳牌电动车外（特价）","goods_sn":"ECS003060","goods_number":"50","market_price":"0.00","goods_price":"18.50","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"925.00","formated_market_price":"0.00","formated_goods_price":"18.50","formated_subtotal":"925.00","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.24.209.210/images/201511/thumb_img/57_thumb_G_1446861881138.jpg","danwei":"条","guige":"1*30","gysmoney":"2.00","rec_id":"116397","rid":"0","user_id":"1411","goods_id":"57","goods_name":"18*2.125 赛阳牌电动车外胎","goods_sn":"104001","goods_number":"6","market_price":"21.96","goods_price":"20.80","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"124.80","formated_market_price":"21.96","formated_goods_price":"20.80","formated_subtotal":"124.80","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.24.209.210/images/201512/thumb_img/58_thumb_G_1448990447164.jpg","danwei":"条","guige":"1*30","gysmoney":"3.15","rec_id":"116809","rid":"0","user_id":"1411","goods_id":"58","goods_name":"18*2.50赛阳牌捷纳电动车外胎（加厚）","goods_sn":"104010","goods_number":"10","market_price":"37.80","goods_price":"31.50","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"315.00","formated_market_price":"37.80","formated_goods_price":"31.50","formated_subtotal":"315.00","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.24.209.210/images/201511/thumb_img/59_thumb_G_1446686801720.jpg","danwei":"条","guige":"1*30","gysmoney":"2.45","rec_id":"116810","rid":"0","user_id":"1411","goods_id":"59","goods_name":"18*2.125赛阳牌捷纳神电动车外胎(加厚）","goods_sn":"104013","goods_number":"7","market_price":"29.40","goods_price":"24.50","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"171.50","formated_market_price":"29.40","formated_goods_price":"24.50","formated_subtotal":"171.50","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.24.209.210/images/201511/thumb_img/2985_thumb_G_1448819588157.jpg","danwei":"把","guige":"1*12","gysmoney":"3.70","rec_id":"116913","rid":"0","user_id":"1411","goods_id":"2985","goods_name":"德众牌8011型交通锁","goods_sn":"ECS002985","goods_number":"5","market_price":"0.00","goods_price":"39.20","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"196.00","formated_market_price":"0.00","formated_goods_price":"39.20","formated_subtotal":"196.00","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"}]
     * total : {"real_goods_count":5,"gift_amount":0,"goods_price":1732.3,"market_price":715.56,"discount":null,"pack_fee":0,"card_fee":0,"shipping_fee":0,"shipping_insure":0,"integral_money":0,"bonus":0,"surplus":0,"cod_fee":0,"pay_fee":0,"tax":0,"gysmoney":13.3,"payable":1732.3,"saving":-1016.74,"save_rate":"-142%","goods_price_formated":"1732.30","market_price_formated":"715.56","saving_formated":"-1016.74","integral_control":"1","ratio":"可用的轮通币为150,可抵1.5元","availableintegral":173230,"relief":null,"id":"0","restricts":null,"g_id":" ","discounts":" ","difference":" ","act_type_ext":null,"act_type":" ","goods_name":" ","min_amount":" ","discount_formated":"0.00","tax_formated":"0.00","pack_fee_formated":"0.00","card_fee_formated":"0.00","bonus_formated":"0.00","shipping_fee_formated":"0.00","shipping_insure_formated":"0.00","amount":1732.3,"formated_order_price":1732.3,"reducecost":1719,"surplus_formated":"0.00","integral":0,"integral_formated":"0.00","pay_fee_formated":"0.00","amount_formated":"1732.30","will_get_integral":0,"will_get_bonus":"0.00","formated_goods_price":"1732.30","formated_market_price":"715.56","formated_saving":"-1016.74"}
     * shipping_list : []
     * payment_list : [{"pay_id":"4","pay_code":"alipay_wap","pay_name":"手机支付宝","pay_desc":"手机支付宝\r\n\r\n","sel":0},{"pay_id":"2","pay_code":"cod","pay_name":"货到付款","pay_desc":"开通城市：×××\r\n货到付款区域：×××","sel":0},{"pay_id":"1","pay_code":"alipay","pay_name":"支付宝","pay_desc":"","sel":0}]
     */

    public List<?> shipping_list;
    /**
     * pay_id : 4
     * pay_code : alipay_wap
     * pay_name : 手机支付宝
     * pay_desc : 手机支付宝


     * sel : 0
     */

    public List<PaymentListEntity> payment_list;

    public static class TotalEntity {
        public int real_goods_count;
        public int gift_amount;
        public double goods_price;
        public double market_price;
        public Object discount;
        public int pack_fee;
        public int card_fee;
        public int shipping_fee;
        public int shipping_insure;
        public int integral_money;
        public int bonus;
        public int surplus;
        public int cod_fee;
        public int pay_fee;
        public int tax;
        public double gysmoney;
        public double payable;
        public double saving;
        public String save_rate;
        public String goods_price_formated;
        public String market_price_formated;
        public String saving_formated;
        public String integral_control;
        public String ratio;
        public int availableintegral;
        public Object relief;
        public String id;
        public Object restricts;
        public String g_id;
        public String discounts;
        public String difference;
        public Object act_type_ext;
        public String act_type;
        public String goods_name;
        public String min_amount;
        public String discount_formated;
        public String tax_formated;
        public String pack_fee_formated;
        public String card_fee_formated;
        public String bonus_formated;
        public String shipping_fee_formated;
        public String shipping_insure_formated;
        public double amount;
        public double formated_order_price;
        public int reducecost;
        public String surplus_formated;
        public int integral;
        public String integral_formated;
        public String pay_fee_formated;
        public String amount_formated;
        public int will_get_integral;
        public String will_get_bonus;
        public String formated_goods_price;
        public String formated_market_price;
        public String formated_saving;
    }

    public static class ConsigneeEntity {
        public String address_id;
        public String address_name;
        public String user_id;
        public String consignee;
        public String email;
        public String country;
        public String province;
        public String city;
        public String district;
        public String address;
        public String zipcode;
        public String tel;
        public String mobile;
        public String sign_building;
        public String best_time;
    }

    public static class CartGoodsEntity {
        public String goods_thumb;
        public String danwei;
        public String guige;
        public String gysmoney;
        public String rec_id;
        public String rid;
        public String user_id;
        public String goods_id;
        public String goods_name;
        public String goods_sn;
        public String goods_number;
        public String market_price;
        public String goods_price;
        public String goods_attr_id;
        public String goods_attr;
        public String is_real;
        public String extension_code;
        public String parent_id;
        public String is_gift;
        public String is_shipping;
        public String subtotal;
        public String formated_market_price;
        public String formated_goods_price;
        public String formated_subtotal;
        public String discounta;
        public String discount;
        public String discount_type;
        public String discount_time;
        public String discount_name;
    }

    public static class PaymentListEntity {
        public String pay_id;
        public String pay_code;
        public String pay_name;
        public String pay_desc;
        public int sel;
    }
}
