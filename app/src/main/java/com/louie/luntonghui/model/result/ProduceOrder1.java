package com.louie.luntonghui.model.result;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 16/2/17.
 */
public class ProduceOrder1 implements Parcelable {

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

    public static class TotalEntity implements Parcelable {
        public int real_goods_count;
        public int gift_amount;
        public double goods_price;
        public double market_price;
        public String discount;
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
        public String relief;
        public String id;
        public String restricts;
        public String g_id;
        public String discounts;
        public String difference;
        public String act_type_ext;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.real_goods_count);
            dest.writeInt(this.gift_amount);
            dest.writeDouble(this.goods_price);
            dest.writeDouble(this.market_price);
            dest.writeString(this.discount);
            dest.writeInt(this.pack_fee);
            dest.writeInt(this.card_fee);
            dest.writeInt(this.shipping_fee);
            dest.writeInt(this.shipping_insure);
            dest.writeInt(this.integral_money);
            dest.writeInt(this.bonus);
            dest.writeInt(this.surplus);
            dest.writeInt(this.cod_fee);
            dest.writeInt(this.pay_fee);
            dest.writeInt(this.tax);
            dest.writeDouble(this.gysmoney);
            dest.writeDouble(this.payable);
            dest.writeDouble(this.saving);
            dest.writeString(this.save_rate);
            dest.writeString(this.goods_price_formated);
            dest.writeString(this.market_price_formated);
            dest.writeString(this.saving_formated);
            dest.writeString(this.integral_control);
            dest.writeString(this.ratio);
            dest.writeInt(this.availableintegral);
            dest.writeString(this.relief);
            dest.writeString(this.id);
            dest.writeString(this.restricts);
            dest.writeString(this.g_id);
            dest.writeString(this.discounts);
            dest.writeString(this.difference);
            dest.writeString(this.act_type_ext);
            dest.writeString(this.act_type);
            dest.writeString(this.goods_name);
            dest.writeString(this.min_amount);
            dest.writeString(this.discount_formated);
            dest.writeString(this.tax_formated);
            dest.writeString(this.pack_fee_formated);
            dest.writeString(this.card_fee_formated);
            dest.writeString(this.bonus_formated);
            dest.writeString(this.shipping_fee_formated);
            dest.writeString(this.shipping_insure_formated);
            dest.writeDouble(this.amount);
            dest.writeDouble(this.formated_order_price);
            dest.writeInt(this.reducecost);
            dest.writeString(this.surplus_formated);
            dest.writeInt(this.integral);
            dest.writeString(this.integral_formated);
            dest.writeString(this.pay_fee_formated);
            dest.writeString(this.amount_formated);
            dest.writeInt(this.will_get_integral);
            dest.writeString(this.will_get_bonus);
            dest.writeString(this.formated_goods_price);
            dest.writeString(this.formated_market_price);
            dest.writeString(this.formated_saving);
        }

        public TotalEntity() {
        }

        protected TotalEntity(Parcel in) {
            this.real_goods_count = in.readInt();
            this.gift_amount = in.readInt();
            this.goods_price = in.readDouble();
            this.market_price = in.readDouble();
            this.discount = in.readParcelable(Object.class.getClassLoader());
            this.pack_fee = in.readInt();
            this.card_fee = in.readInt();
            this.shipping_fee = in.readInt();
            this.shipping_insure = in.readInt();
            this.integral_money = in.readInt();
            this.bonus = in.readInt();
            this.surplus = in.readInt();
            this.cod_fee = in.readInt();
            this.pay_fee = in.readInt();
            this.tax = in.readInt();
            this.gysmoney = in.readDouble();
            this.payable = in.readDouble();
            this.saving = in.readDouble();
            this.save_rate = in.readString();
            this.goods_price_formated = in.readString();
            this.market_price_formated = in.readString();
            this.saving_formated = in.readString();
            this.integral_control = in.readString();
            this.ratio = in.readString();
            this.availableintegral = in.readInt();
            this.relief = in.readParcelable(Object.class.getClassLoader());
            this.id = in.readString();
            this.restricts = in.readParcelable(Object.class.getClassLoader());
            this.g_id = in.readString();
            this.discounts = in.readString();
            this.difference = in.readString();
            this.act_type_ext = in.readParcelable(Object.class.getClassLoader());
            this.act_type = in.readString();
            this.goods_name = in.readString();
            this.min_amount = in.readString();
            this.discount_formated = in.readString();
            this.tax_formated = in.readString();
            this.pack_fee_formated = in.readString();
            this.card_fee_formated = in.readString();
            this.bonus_formated = in.readString();
            this.shipping_fee_formated = in.readString();
            this.shipping_insure_formated = in.readString();
            this.amount = in.readDouble();
            this.formated_order_price = in.readDouble();
            this.reducecost = in.readInt();
            this.surplus_formated = in.readString();
            this.integral = in.readInt();
            this.integral_formated = in.readString();
            this.pay_fee_formated = in.readString();
            this.amount_formated = in.readString();
            this.will_get_integral = in.readInt();
            this.will_get_bonus = in.readString();
            this.formated_goods_price = in.readString();
            this.formated_market_price = in.readString();
            this.formated_saving = in.readString();
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

    @Override
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
    }

    public ProduceOrder1() {
    }

    protected ProduceOrder1(Parcel in) {
        this.total = in.readParcelable(TotalEntity.class.getClassLoader());
        this.consignee = new ArrayList<ConsigneeEntity>();
        in.readList(this.consignee, List.class.getClassLoader());
        this.cart_goods = new ArrayList<CartGoodsEntity>();
        in.readList(this.cart_goods, List.class.getClassLoader());
        this.shipping_list = new ArrayList<>();
        in.readList(this.shipping_list, List.class.getClassLoader());
        this.payment_list = new ArrayList<PaymentListEntity>();
        in.readList(this.payment_list, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProduceOrder1> CREATOR = new Parcelable.Creator<ProduceOrder1>() {
        public ProduceOrder1 createFromParcel(Parcel source) {
            return new ProduceOrder1(source);
        }

        public ProduceOrder1[] newArray(int size) {
            return new ProduceOrder1[size];
        }
    };
}
