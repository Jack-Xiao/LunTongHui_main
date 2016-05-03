package com.louie.luntonghui.model.result;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/7/17.
 */
public class OrderList {


    /**
     * mysalelist : [{"handler":2,"money":"0.00","order_amount":"200.00","allow_to_modify":1,"pay_name":"2","order_id":"113","add_time":"07-15 15:43:34","order_sn":"2015071583663"},{"handler":1,"money":"0.00","order_amount":"100.00","allow_to_modify":0,"pay_name":"","order_id":"101","add_time":"07-09 17:39:16","order_sn":"2015070966402"},{"handler":2,"money":"0.00","order_amount":"100.00","allow_to_modify":1,"pay_name":"","order_id":"100","add_time":"07-06 17:32:33","order_sn":"2015070693791"},{"handler":2,"money":"200.00","order_amount":"0.00","allow_to_modify":1,"pay_name":"","order_id":"99","add_time":"07-06 17:31:33","order_sn":"2015070686192"},{"handler":2,"money":"0.00","order_amount":"100.00","allow_to_modify":1,"pay_name":"","order_id":"98","add_time":"07-06 17:18:32","order_sn":"2015070696279"},{"handler":2,"money":"0.00","order_amount":"100.00","allow_to_modify":1,"pay_name":"","order_id":"97","add_time":"07-06 17:11:02","order_sn":"2015070605897"},{"handler":2,"money":"0.00","order_amount":"200.00","allow_to_modify":1,"pay_name":"","order_id":"96","add_time":"07-06 17:07:57","order_sn":"2015070639483"},{"handler":2,"money":"0.00","order_amount":"3400.00","allow_to_modify":1,"pay_name":"2","order_id":"70","add_time":"06-30 15:26:13","order_sn":"2015063080842"},{"handler":2,"money":"0.00","order_amount":"2600.00","allow_to_modify":1,"pay_name":"2","order_id":"64","add_time":"06-30 08:45:10","order_sn":"2015063098956"},{"handler":2,"money":"0.00","order_amount":"100.00","allow_to_modify":1,"pay_name":"2","order_id":"63","add_time":"06-30 08:37:39","order_sn":"2015063030813"},{"handler":2,"money":"0.00","order_amount":"11.00","allow_to_modify":1,"pay_name":"2","order_id":"62","add_time":"06-30 08:34:20","order_sn":"2015063052071"},{"handler":2,"money":"0.00","order_amount":"900.00","allow_to_modify":1,"pay_name":"2","order_id":"55","add_time":"06-29 16:55:10","order_sn":"2015062961618"},{"handler":2,"money":"0.00","order_amount":"12000.00","allow_to_modify":1,"pay_name":"2","order_id":"43","add_time":"06-26 17:51:06","order_sn":"2015062670365"},{"handler":2,"money":"0.00","order_amount":"2000.00","allow_to_modify":1,"pay_name":"2","order_id":"40","add_time":"06-26 17:26:12","order_sn":"2015062611884"},{"handler":2,"money":"0.00","order_amount":"4000.00","allow_to_modify":1,"pay_name":"2","order_id":"39","add_time":"06-26 16:19:06","order_sn":"2015062605902"},{"handler":2,"money":"0.00","order_amount":"2500.00","allow_to_modify":1,"pay_name":"2","order_id":"38","add_time":"06-26 16:03:43","order_sn":"2015062699284"},{"handler":1,"money":"0.00","order_amount":"6200.00","allow_to_modify":0,"pay_name":"","order_id":"36","add_time":"06-25 15:47:57","order_sn":"2015062577088"},{"handler":1,"money":"0.00","order_amount":"1000.00","allow_to_modify":0,"pay_name":"","order_id":"35","add_time":"06-23 20:19:38","order_sn":"2015062395395"},{"handler":1,"money":"0.00","order_amount":"5000.00","allow_to_modify":0,"pay_name":"","order_id":"34","add_time":"06-23 20:19:16","order_sn":"2015062377696"},{"handler":2,"money":"0.00","order_amount":"1000.00","allow_to_modify":1,"pay_name":"","order_id":"33","add_time":"06-23 19:12:00","order_sn":"2015062344367"},{"handler":2,"money":"0.00","order_amount":"200.00","allow_to_modify":1,"pay_name":"2","order_id":"32","add_time":"06-23 19:00:15","order_sn":"2015062361977"},{"handler":2,"money":"0.00","order_amount":"200.00","allow_to_modify":1,"pay_name":"2","order_id":"30","add_time":"06-23 18:22:05","order_sn":"2015062352839"},{"handler":2,"money":"0.00","order_amount":"200.00","allow_to_modify":1,"pay_name":"2","order_id":"29","add_time":"06-23 17:54:46","order_sn":"2015062351945"},{"handler":2,"money":"0.00","order_amount":"208.50","allow_to_modify":1,"pay_name":"2","order_id":"5","add_time":"06-18 15:58:56","order_sn":"2015061867913"},{"handler":2,"money":"0.00","order_amount":"393.85","allow_to_modify":1,"pay_name":"2","order_id":"2","add_time":"06-12 21:11:16","order_sn":"2015061293904"}]
     */
    public List<MysalelistEntity> mysalelist;

    public int total_count;

    public class MysalelistEntity implements Parcelable {
        /**
         * handler : 2
         * money : 0.00
         * order_amount : 200.00
         * allow_to_modify : 1
         * pay_name : 2
         * order_id : 113
         * add_time : 07-15 15:43:34
         * order_sn : 2015071583663
         */
        public String handler;
        public String money;
        public String order_amount;
        public String allow_to_modify;
        public String pay_name;
        public String order_id;
        public String add_time;
        public String order_sn;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.handler);
            dest.writeString(this.money);
            dest.writeString(this.order_amount);
            dest.writeString(this.allow_to_modify);
            dest.writeString(this.pay_name);
            dest.writeString(this.order_id);
            dest.writeString(this.add_time);
            dest.writeString(this.order_sn);
        }

        public MysalelistEntity() {
        }

        protected MysalelistEntity(Parcel in) {
            this.handler = in.readString();
            this.money = in.readString();
            this.order_amount = in.readString();
            this.allow_to_modify = in.readString();
            this.pay_name = in.readString();
            this.order_id = in.readString();
            this.add_time = in.readString();
            this.order_sn = in.readString();
        }

        public final Parcelable.Creator<MysalelistEntity> CREATOR = new Parcelable.Creator<MysalelistEntity>() {
            @Override
            public MysalelistEntity createFromParcel(Parcel source) {
                return new MysalelistEntity(source);
            }

            @Override
            public MysalelistEntity[] newArray(int size) {
                return new MysalelistEntity[size];
            }
        };
    }
}
