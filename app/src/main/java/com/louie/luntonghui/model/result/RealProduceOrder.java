package com.louie.luntonghui.model.result;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jack on 16/3/16.
 */
public class RealProduceOrder implements Serializable{
    /**
     * real_goods_count : 2
     * gift_amount : 0
     * goods_price : 179998.2
     * market_price : 215886.73
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
     * gysmoney : 0
     * payable : 179998.2
     * saving : 35888.53
     * save_rate : 17%
     * goods_price_formated : 179998.20
     * market_price_formated : 215886.73
     * saving_formated : 35888.53
     * integral_control : 0
     * ratio : 可用的轮通币为1100,可抵11元
     * availableintegral : 17999820
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
     * amount : 179998.2
     * formated_order_price : 179998.2
     * surplus_formated : 0.00
     * integral : 0
     * integral_formated : 0.00
     * pay_fee_formated : 0.00
     * amount_formated : 179998.20
     * will_get_integral : 179998
     * will_get_bonus : 0.00
     * formated_goods_price : 179998.20
     * formated_market_price : 215886.73
     * formated_saving : 35888.53
     */

    public TotalEntity total;
    /**
     * title : 26x175赛阳牌神龟王内胎（ 加长咀）
     * amount : 数量：9999
     */

    public List<MsgEntity> msg;
    /**
     * address_id : 403
     * address_name :
     * user_id : 140
     * consignee : sdafadsfa
     * email :
     * country : 1
     * province : 6
     * city : 76
     * district : 692
     * address : asdfasdfsadfasd
     * zipcode :
     * tel :
     * mobile : 12345678
     * sign_building :
     * best_time :
     */

    public List<ConsigneeEntity> consignee;
    /**
     * goods_thumb : http://120.25.224.250/images/201503/thumb_img/444_thumb_G_1426669890060.jpg
     * danwei : 条
     * guige : 1*50
     * gysmoney : 0.00
     * rec_id : 3004
     * rid : 0
     * user_id : 140
     * goods_id : 444
     * goods_name : 26x21/2赛阳牌神龟王内胎（ 英咀）
     * goods_sn : 202031
     * goods_number : 11111
     * market_price : 11.52
     * goods_price : 9.60
     * goods_attr_id :
     * goods_attr :
     * is_real : 1
     * extension_code :
     * parent_id : 0
     * is_gift : 0
     * is_shipping : 0
     * subtotal : 106665.60
     * formated_market_price : 11.52
     * formated_goods_price : 9.60
     * formated_subtotal : 106665.60
     * discounta : 0
     * discount : 0
     * discount_type : 0
     * discount_time : 0
     * discount_name : 0
     */

    public List<CartGoodsEntity> cart_goods;
    /**
     * msg : [{"title":"26x175赛阳牌神龟王内胎（ 加长咀）","amount":"数量：9999"},{"title":"26x21/2赛阳牌神龟王内胎（ 英咀）","amount":"数量：9999"}]
     * consignee : [{"address_id":"403","address_name":"","user_id":"140","consignee":"sdafadsfa","email":"","country":"1","province":"6","city":"76","district":"692","address":"asdfasdfsadfasd","zipcode":"","tel":"","mobile":"12345678","sign_building":"","best_time":""}]
     * cart_goods : [{"goods_thumb":"http://120.25.224.250/images/201503/thumb_img/444_thumb_G_1426669890060.jpg","danwei":"条","guige":"1*50","gysmoney":"0.00","rec_id":"3004","rid":"0","user_id":"140","goods_id":"444","goods_name":"26x21/2赛阳牌神龟王内胎（ 英咀）","goods_sn":"202031","goods_number":"11111","market_price":"11.52","goods_price":"9.60","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"106665.60","formated_market_price":"11.52","formated_goods_price":"9.60","formated_subtotal":"106665.60","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.25.224.250/images/201503/thumb_img/443_thumb_G_1426649306260.jpg","danwei":"条","guige":"1*50","gysmoney":"0.00","rec_id":"3006","rid":"0","user_id":"140","goods_id":"443","goods_name":"26x175赛阳牌神龟王内胎（ 加长咀）","goods_sn":"202033","goods_number":"11111","market_price":"7.91","goods_price":"6.60","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"73332.60","formated_market_price":"7.91","formated_goods_price":"6.60","formated_subtotal":"73332.60","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"}]
     * total : {"real_goods_count":2,"gift_amount":0,"goods_price":179998.2,"market_price":215886.73,"discount":null,"pack_fee":0,"card_fee":0,"shipping_fee":0,"shipping_insure":0,"integral_money":0,"bonus":0,"surplus":0,"cod_fee":0,"pay_fee":0,"tax":0,"gysmoney":0,"payable":179998.2,"saving":35888.53,"save_rate":"17%","goods_price_formated":"179998.20","market_price_formated":"215886.73","saving_formated":"35888.53","integral_control":"0","ratio":"可用的轮通币为1100,可抵11元","availableintegral":17999820,"relief":null,"id":"0","restricts":null,"g_id":" ","discounts":" ","difference":" ","act_type_ext":null,"act_type":" ","goods_name":" ","min_amount":" ","discount_formated":"0.00","tax_formated":"0.00","pack_fee_formated":"0.00","card_fee_formated":"0.00","bonus_formated":"0.00","shipping_fee_formated":"0.00","shipping_insure_formated":"0.00","amount":179998.2,"formated_order_price":179998.2,"surplus_formated":"0.00","integral":0,"integral_formated":"0.00","pay_fee_formated":"0.00","amount_formated":"179998.20","will_get_integral":179998,"will_get_bonus":"0.00","formated_goods_price":"179998.20","formated_market_price":"215886.73","formated_saving":"35888.53"}
     * shipping_list : []
     * payment_list : [{"pay_id":"4","pay_code":"alipay_wap","pay_name":"手机支付宝","pay_desc":"手机支付宝\r\n\r\n","sel":1},{"pay_id":"2","pay_code":"cod","pay_name":"货到付款","pay_desc":"开通城市：×××\r\n货到付款区域：×××","sel":1},{"pay_id":"1","pay_code":"alipay","pay_name":"支付宝","pay_desc":"支付宝网站(www.alipay.com) 是国内先进的网上支付平台。<br/>支付宝收款接口：在线即可开通，<font color=\"red\"><b>零预付，免年费<\/b><\/font>，单笔阶梯费率，无流量限制。<br/><a href=\"http://cloud.ecshop.com/payment_apply.php?mod=alipay\" target=\"_blank\"><font color=\"red\">立即在线申请<\/font><\/a>","sel":0}]
     */

    public List<?> shipping_list;
    /**
     * pay_id : 4
     * pay_code : alipay_wap
     * pay_name : 手机支付宝
     * pay_desc : 手机支付宝


     * sel : 1
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
        public int gysmoney;
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

    public static class MsgEntity {
        public String title;
        public String amount;
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

    public static class CartGoodsEntity implements Serializable{
        @SerializedName("goods_thumb")
        public String goods_thumb;
        @SerializedName("danwei")
        public String danwei;
        @SerializedName("guige")
        public String guige;
        @SerializedName("gysmoney")
        public String gysmoney;
        @SerializedName("rec_id")
        public String rec_id;
        @SerializedName("rid")
        public String rid;
        @SerializedName("user_id")
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
