package com.louie.luntonghui.model.result;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/8.
 */
public class ProduceOrder2 extends Result implements Serializable {
    public static final String CAN_USE_INTEGRAL = "1";
    public static final String CAN_NOT_INTEGRAL = "0";
    private static final long serialVersionUID = 1L; // 定义序列化ID

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

    public ArrayList<ConsigneeEntity> consignee;
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

    public ArrayList<CartGoodsEntity> cart_goods;
    /**
     * consignee : [{"address_id":"1681","address_name":"","user_id":"1411","consignee":"cfdd","email":"","country":"1","province":"6","city":"76","district":"692","address":"dbdbsb","zipcode":"","tel":"","mobile":"13455555555","sign_building":"","best_time":""}]
     * cart_goods : [{"goods_thumb":"http://120.24.209.210/images/201511/thumb_img/3062_thumb_G_1446859182947.jpg","danwei":"条","guige":"1*30","gysmoney":"2.00","rec_id":"116391","rid":"0","user_id":"1411","goods_id":"3062","goods_name":"22*2.125亿佳牌电动车外（特价）","goods_sn":"ECS003060","goods_number":"50","market_price":"0.00","goods_price":"18.50","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"925.00","formated_market_price":"0.00","formated_goods_price":"18.50","formated_subtotal":"925.00","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.24.209.210/images/201511/thumb_img/57_thumb_G_1446861881138.jpg","danwei":"条","guige":"1*30","gysmoney":"2.00","rec_id":"116397","rid":"0","user_id":"1411","goods_id":"57","goods_name":"18*2.125 赛阳牌电动车外胎","goods_sn":"104001","goods_number":"6","market_price":"21.96","goods_price":"20.80","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"124.80","formated_market_price":"21.96","formated_goods_price":"20.80","formated_subtotal":"124.80","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.24.209.210/images/201512/thumb_img/58_thumb_G_1448990447164.jpg","danwei":"条","guige":"1*30","gysmoney":"3.15","rec_id":"116809","rid":"0","user_id":"1411","goods_id":"58","goods_name":"18*2.50赛阳牌捷纳电动车外胎（加厚）","goods_sn":"104010","goods_number":"10","market_price":"37.80","goods_price":"31.50","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"315.00","formated_market_price":"37.80","formated_goods_price":"31.50","formated_subtotal":"315.00","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.24.209.210/images/201511/thumb_img/59_thumb_G_1446686801720.jpg","danwei":"条","guige":"1*30","gysmoney":"2.45","rec_id":"116810","rid":"0","user_id":"1411","goods_id":"59","goods_name":"18*2.125赛阳牌捷纳神电动车外胎(加厚）","goods_sn":"104013","goods_number":"7","market_price":"29.40","goods_price":"24.50","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"171.50","formated_market_price":"29.40","formated_goods_price":"24.50","formated_subtotal":"171.50","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"},{"goods_thumb":"http://120.24.209.210/images/201511/thumb_img/2985_thumb_G_1448819588157.jpg","danwei":"把","guige":"1*12","gysmoney":"3.70","rec_id":"116913","rid":"0","user_id":"1411","goods_id":"2985","goods_name":"德众牌8011型交通锁","goods_sn":"ECS002985","goods_number":"5","market_price":"0.00","goods_price":"39.20","goods_attr_id":"","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"196.00","formated_market_price":"0.00","formated_goods_price":"39.20","formated_subtotal":"196.00","discounta":"0","discount":"0","discount_type":"0","discount_time":"0","discount_name":"0"}]
     * total : {"real_goods_count":5,"gift_amount":0,"goods_price":1732.3,"market_price":715.56,"discount":null,"pack_fee":0,"card_fee":0,"shipping_fee":0,"shipping_insure":0,"integral_money":0,"bonus":0,"surplus":0,"cod_fee":0,"pay_fee":0,"tax":0,"gysmoney":13.3,"payable":1732.3,"saving":-1016.74,"save_rate":"-142%","goods_price_formated":"1732.30","market_price_formated":"715.56","saving_formated":"-1016.74","integral_control":"1","ratio":"可用的轮通币为150,可抵1.5元","availableintegral":173230,"relief":null,"id":"0","restricts":null,"g_id":" ","discounts":" ","difference":" ","act_type_ext":null,"act_type":" ","goods_name":" ","min_amount":" ","discount_formated":"0.00","tax_formated":"0.00","pack_fee_formated":"0.00","card_fee_formated":"0.00","bonus_formated":"0.00","shipping_fee_formated":"0.00","shipping_insure_formated":"0.00","amount":1732.3,"formated_order_price":1732.3,"reducecost":1719,"surplus_formated":"0.00","integral":0,"integral_formated":"0.00","pay_fee_formated":"0.00","amount_formated":"1732.30","will_get_integral":0,"will_get_bonus":"0.00","formated_goods_price":"1732.30","formated_market_price":"715.56","formated_saving":"-1016.74"}
     * shipping_list : []
     * payment_list : [{"pay_id":"4","pay_code":"alipay_wap","pay_name":"手机支付宝","pay_desc":"手机支付宝\r\n\r\n","sel":0},{"pay_id":"2","pay_code":"cod","pay_name":"货到付款","pay_desc":"开通城市：×××\r\n货到付款区域：×××","sel":0},{"pay_id":"1","pay_code":"alipay","pay_name":"支付宝","pay_desc":"","sel":0}]
     */

    public ArrayList<?> shipping_list;
    /**
     * pay_id : 4
     * pay_code : alipay_wap
     * pay_name : 手机支付宝
     * pay_desc : 手机支付宝


     * sel : 0
     */

    public ArrayList<PaymentListEntity> payment_list;

    public static class TotalEntity implements Parcelable {
        public String real_goods_count;
        public String gift_amount;
        public double goods_price;
        public String market_price;
        public String discount;
        public String pack_fee;
        public String card_fee;
        public String shipping_fee;
        public String shipping_insure;
        public String integral_money;
        public String bonus;
        public String surplus;
        public String cod_fee;
        public String pay_fee;
        public String tax;
        public String gysmoney;
        public String payable;
        public String saving;
        public String save_rate;
        public String goods_price_formated;
        public String market_price_formated;
        public String saving_formated;

        public String ratio;
        public String availableintegral;
        public String relief;
        public String id;
        public String restricts;
        public String g_id;

        public String difference;
        public String act_type_ext;

        public String goods_name;
        public String min_amount;
        public String discount_formated;
        public String tax_formated;
        public String pack_fee_formated;
        public String card_fee_formated;
        public String bonus_formated;
        public String shipping_fee_formated;
        public String shipping_insure_formated;
        public String amount;
        public String formated_order_price;
        public String reducecost;
        public String surplus_formated;
        public String integral;
        public String integral_formated;
        public String pay_fee_formated;
        public String amount_formated;
        public String will_get_integral;
        public String will_get_bonus;
        public String formated_goods_price;
        public String formated_market_price;
        public String formated_saving;



        public String act_type; // 1 = 减   2 = 赠   3 减和赠
        public String details;
        public String gift;
        public String prompt;
        public String discounts;

        public String integral_control; // 限制使用积分，1 = 不显示。 0 = 显示

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.real_goods_count);
            dest.writeString(this.gift_amount);
            dest.writeDouble(this.goods_price);
            dest.writeString(this.market_price);
            dest.writeString(this.discount);
            dest.writeString(this.pack_fee);
            dest.writeString(this.card_fee);
            dest.writeString(this.shipping_fee);
            dest.writeString(this.shipping_insure);
            dest.writeString(this.integral_money);
            dest.writeString(this.bonus);
            dest.writeString(this.surplus);
            dest.writeString(this.cod_fee);
            dest.writeString(this.pay_fee);
            dest.writeString(this.tax);
            dest.writeString(this.gysmoney);
            dest.writeString(this.payable);
            dest.writeString(this.saving);
            dest.writeString(this.save_rate);
            dest.writeString(this.goods_price_formated);
            dest.writeString(this.market_price_formated);
            dest.writeString(this.saving_formated);
            dest.writeString(this.ratio);
            dest.writeString(this.availableintegral);
            dest.writeString(this.relief);
            dest.writeString(this.id);
            dest.writeString(this.restricts);
            dest.writeString(this.g_id);
            dest.writeString(this.difference);
            dest.writeString(this.act_type_ext);
            dest.writeString(this.goods_name);
            dest.writeString(this.min_amount);
            dest.writeString(this.discount_formated);
            dest.writeString(this.tax_formated);
            dest.writeString(this.pack_fee_formated);
            dest.writeString(this.card_fee_formated);
            dest.writeString(this.bonus_formated);
            dest.writeString(this.shipping_fee_formated);
            dest.writeString(this.shipping_insure_formated);
            dest.writeString(this.amount);
            dest.writeString(this.formated_order_price);
            dest.writeString(this.reducecost);
            dest.writeString(this.surplus_formated);
            dest.writeString(this.integral);
            dest.writeString(this.integral_formated);
            dest.writeString(this.pay_fee_formated);
            dest.writeString(this.amount_formated);
            dest.writeString(this.will_get_integral);
            dest.writeString(this.will_get_bonus);
            dest.writeString(this.formated_goods_price);
            dest.writeString(this.formated_market_price);
            dest.writeString(this.formated_saving);
            dest.writeString(this.act_type);
            dest.writeString(this.details);
            dest.writeString(this.gift);
            dest.writeString(this.prompt);
            dest.writeString(this.discounts);
            dest.writeString(this.integral_control);
        }

        public TotalEntity() {
        }

        protected TotalEntity(Parcel in) {
            this.real_goods_count = in.readString();
            this.gift_amount = in.readString();
            this.goods_price = in.readDouble();
            this.market_price = in.readString();
            this.discount = in.readString();
            this.pack_fee = in.readString();
            this.card_fee = in.readString();
            this.shipping_fee = in.readString();
            this.shipping_insure = in.readString();
            this.integral_money = in.readString();
            this.bonus = in.readString();
            this.surplus = in.readString();
            this.cod_fee = in.readString();
            this.pay_fee = in.readString();
            this.tax = in.readString();
            this.gysmoney = in.readString();
            this.payable = in.readString();
            this.saving = in.readString();
            this.save_rate = in.readString();
            this.goods_price_formated = in.readString();
            this.market_price_formated = in.readString();
            this.saving_formated = in.readString();
            this.ratio = in.readString();
            this.availableintegral = in.readString();
            this.relief = in.readString();
            this.id = in.readString();
            this.restricts = in.readString();
            this.g_id = in.readString();
            this.difference = in.readString();
            this.act_type_ext = in.readString();
            this.goods_name = in.readString();
            this.min_amount = in.readString();
            this.discount_formated = in.readString();
            this.tax_formated = in.readString();
            this.pack_fee_formated = in.readString();
            this.card_fee_formated = in.readString();
            this.bonus_formated = in.readString();
            this.shipping_fee_formated = in.readString();
            this.shipping_insure_formated = in.readString();
            this.amount = in.readString();
            this.formated_order_price = in.readString();
            this.reducecost = in.readString();
            this.surplus_formated = in.readString();
            this.integral = in.readString();
            this.integral_formated = in.readString();
            this.pay_fee_formated = in.readString();
            this.amount_formated = in.readString();
            this.will_get_integral = in.readString();
            this.will_get_bonus = in.readString();
            this.formated_goods_price = in.readString();
            this.formated_market_price = in.readString();
            this.formated_saving = in.readString();
            this.act_type = in.readString();
            this.details = in.readString();
            this.gift = in.readString();
            this.prompt = in.readString();
            this.discounts = in.readString();
            this.integral_control = in.readString();
        }

        public static final Creator<TotalEntity> CREATOR = new Creator<TotalEntity>() {
            public TotalEntity createFromParcel(Parcel source) {
                return new TotalEntity(source);
            }

            public TotalEntity[] newArray(int size) {
                return new TotalEntity[size];
            }
        };
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



        public ConsigneeEntity() {
        }

        protected ConsigneeEntity(Parcel in) {
            this.address_id = in.readString();
            this.address_name = in.readString();
            this.user_id = in.readString();
            this.consignee = in.readString();
            this.email = in.readString();
            this.country = in.readString();
            this.province = in.readString();
            this.city = in.readString();
            this.district = in.readString();
            this.address = in.readString();
            this.zipcode = in.readString();
            this.tel = in.readString();
            this.mobile = in.readString();
            this.sign_building = in.readString();
            this.best_time = in.readString();
        }

        /*public static final Creator<ConsigneeEntity> CREATOR = new Creator<ConsigneeEntity>() {
            public ConsigneeEntity createFromParcel(Parcel source) {
                return new ConsigneeEntity(source);
            }

            public ConsigneeEntity[] newArray(int size) {
                return new ConsigneeEntity[size];
            }
        };*/
    }

    public static class CartGoodsEntity{
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

        public String orderid;


        public CartGoodsEntity() {
        }

        protected CartGoodsEntity(Parcel in) {
            this.gysmoney = in.readString();
            this.danwei = in.readString();
            this.goods_price = in.readString();
            this.discounta = in.readString();
            this.is_real = in.readString();
            this.is_shipping = in.readString();
            this.discount = in.readString();
            this.goods_attr = in.readString();
            this.rid = in.readString();
            this.extension_code = in.readString();
            this.discount_name = in.readString();
            this.discount_time = in.readString();
            this.goods_name = in.readString();
            this.goods_id = in.readString();
            this.goods_sn = in.readString();
            this.rec_id = in.readString();
            this.goods_attr_id = in.readString();
            this.discount_type = in.readString();
            this.formated_market_price = in.readString();
            this.formated_goods_price = in.readString();
            this.is_gift = in.readString();
            this.goods_number = in.readString();
            this.user_id = in.readString();
            this.formated_subtotal = in.readString();
            this.parent_id = in.readString();
            this.subtotal = in.readString();
            this.goods_thumb = in.readString();
            this.market_price = in.readString();
            this.guige = in.readString();
            this.orderid = in.readString();
        }

        /*public static final Creator<CartGoodsEntity> CREATOR = new Creator<CartGoodsEntity>() {
            public CartGoodsEntity createFromParcel(Parcel source) {
                return new CartGoodsEntity(source);
            }

            public CartGoodsEntity[] newArray(int size) {
                return new CartGoodsEntity[size];
            }
        };*/
    }

    public static class PaymentListEntity{
        public String pay_id;
        public String pay_code;
        public String pay_name;
        public String pay_desc;
        public int sel;


        public PaymentListEntity() {
        }

        protected PaymentListEntity(Parcel in) {
            this.pay_id = in.readString();
            this.pay_code = in.readString();
            this.pay_name = in.readString();
            this.pay_desc = in.readString();
            this.sel = in.readInt();
        }

       /* public static final Creator<PaymentListEntity> CREATOR = new Creator<PaymentListEntity>() {
            public PaymentListEntity createFromParcel(Parcel source) {
                return new PaymentListEntity(source);
            }

            public PaymentListEntity[] newArray(int size) {
                return new PaymentListEntity[size];
            }
        };*/
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.total, flags);
        dest.writeList(this.consignee);
        dest.writeList(this.cart_goods);
        dest.writeList(this.shipping_list);
        dest.writeList(this.payment_list);
    }*/

    public ProduceOrder2() {
    }

    protected ProduceOrder2(Parcel in) {
        this.total = in.readParcelable(TotalEntity.class.getClassLoader());
        this.consignee = new ArrayList<ConsigneeEntity>();
        in.readList(this.consignee, ArrayList.class.getClassLoader());

        this.cart_goods = new ArrayList<CartGoodsEntity>();
        in.readList(this.cart_goods, ArrayList.class.getClassLoader());
        this.shipping_list = new ArrayList<>();
        in.readList(this.shipping_list, List.class.getClassLoader());
        this.payment_list = new ArrayList<PaymentListEntity>();
        in.readList(this.payment_list, ArrayList.class.getClassLoader());
    }

    /*public static final Creator<ProduceOrder2> CREATOR = new Creator<ProduceOrder2>() {
        public ProduceOrder2 createFromParcel(Parcel source) {
            return new ProduceOrder2(source);
        }

        public ProduceOrder2[] newArray(int size) {
            return new ProduceOrder2[size];
        }
    };*/
}
/*
public class ProduceOrder extends Result {
    public static final String CAN_USE_INTEGRAL = "1";
    public static final String CAN_NOT_INTEGRAL = "0";
    */
/**
     * shipping_list : []
     * total : {"bonus_formated":"0.00","gysmoney":20,"shipping_insure":0,"goods_price":242,"bonus":0,"discount":121,"goods_price_formated":"242.00","market_price_formated":"498.06","surplus_formated":"0.00","card_fee":0,"tax_formated":"0.00","payable":242,"integral":0,"amount_formated":"121.00","save_rate":"51%","gift_amount":0,"cod_fee":0,"pack_fee":0,"amount":121,"discount_formated":"121.00","surplus":0,"will_get_integral":242,"will_get_bonus":"0.00","integral_money":0,"tax":0,"integral_formated":"0.00","shipping_insure_formated":"0.00","pay_fee_formated":"0.00","formated_market_price":"498.06","card_fee_formated":"0.00","formated_goods_price":"242.00","saving_formated":"256.06","shipping_fee":0,"saving":256.06,"formated_saving":"256.06","pack_fee_formated":"0.00","real_goods_count":2,"shipping_fee_formated":"0.00","availableintegral":24200,"market_price":498.06,"pay_fee":0,"ratio":"可用的轮通币为7457,可抵74.57元"}
     * consignee : [{"country":"1","consignee":"哭","address":"家具特价","city":"76","address_id":"224","mobile":"13417185094","address_name":"","zipcode":"","best_time":"","province":"6","user_id":"58","district":"693","tel":"","sign_building":"","email":""}]
     * payment_list : [{"pay_code":"alipay_wap","pay_name":"手机支付宝","sel":0,"pay_id":"4","pay_desc":""},{"pay_code":"cod","pay_name":"货到付款","sel":0,"pay_id":"2","pay_desc":""},{"pay_code":"alipay","pay_name":"支付宝","sel":0,"pay_id":"1","pay_desc":""}]
     * cart_goods : [{"gysmoney":"0.00","danwei":"","goods_price":"22.00","discounta":"1","is_real":"1","is_shipping":"0","discount":"5","goods_attr":"","rid":"0","extension_code":"","discount_name":"","discount_time":"打折的时间为2015-08-11到2015-08-15，赶快来抢吧！","goods_name":"18*2.125 赛阳牌神龟王电动车外胎","goods_id":"57","goods_sn":"ECS000057","rec_id":"2000","goods_attr_id":"","discount_type":"3","formated_market_price":"23.46","formated_goods_price":"22.00","is_gift":"0","goods_number":"11","user_id":"58","formated_subtotal":"242.00","parent_id":"0","subtotal":"242.00","goods_thumb":"","market_price":"23.46"},{"gysmoney":"20.00","danwei":"个","goods_price":"0.00","discounta":"1","is_real":"1","is_shipping":"0","discount":"","goods_attr":"","rid":"2000","extension_code":"","discount_name":"","discount_time":"打折的时间为2015-08-11到2015-08-15，赶快来抢吧！","goods_name":"测试","goods_id":"364","goods_sn":"ECS000364","rec_id":"2001","goods_attr_id":"","discount_type":"1","formated_market_price":"240.00","formated_goods_price":"0.00","is_gift":"7","goods_number":"1","user_id":"58","formated_subtotal":"0.00","parent_id":"0","subtotal":"0.00","goods_thumb":"","market_price":"240.00"}]
     *//*

    public List<?> shipping_list;
    public TotalEntity total;
    public List<ConsigneeEntity> consignee;
    public List<PaymentListEntity> payment_list;
    public List<CartGoodsEntity> cart_goods;



    public static class TotalEntity {
        */
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
         *//*

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

        public String act_type; // 1 = 减   2 = 赠   3 减和赠
        public String details;
        public String gift;
        public String prompt;
        public String discounts;

        public String integral_control; // 限制使用积分，1 = 不显示。 0 = 显示


    }

    public static class ConsigneeEntity{
        */
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


        public ConsigneeEntity() {
        }

        protected ConsigneeEntity(Parcel in) {
            this.country = in.readString();
            this.consignee = in.readString();
            this.address = in.readString();
            this.city = in.readString();
            this.address_id = in.readString();
            this.mobile = in.readString();
            this.address_name = in.readString();
            this.zipcode = in.readString();
            this.best_time = in.readString();
            this.province = in.readString();
            this.user_id = in.readString();
            this.district = in.readString();
            this.tel = in.readString();
            this.sign_building = in.readString();
            this.email = in.readString();
        }


    }

    public static class PaymentListEntity{
        */
/**
         * pay_code : alipay_wap
         * pay_name : 手机支付宝
         * sel : 0
         * pay_id : 4
         * pay_desc :
         *//*

        public String pay_code;
        public String pay_name;
        public String sel;
        public String pay_id;
        public String pay_desc;

    }

    public static class CartGoodsEntity {
        */
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
         *//*

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

        public String orderid;


    } */
/**




  */
/*  public List<?> shipping_list;
    public TotalEntity total;
    public List<ConsigneeEntity> consignee;
    public List<Payment_listEntity> payment_list;
    public List<Cart_goodsEntity> cart_goods;

    *//*
*/
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
     *//*
*/
/*
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
        *//*
*/
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
     *//*
*/
/*
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
        *//*
*/
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
     *//*
*/
/*
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
    }*//*




    public ProduceOrder() {
    }
}*/
