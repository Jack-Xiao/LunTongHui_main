package com.louie.luntonghui.model.result;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 16/4/1.
 */
public class DispatchToday implements Parcelable {


    /**
     * rsgcode : 000
     * rsgmsg :
     * list : [{"order_id":"285","delivery_man_name":"叶04","salesman":"亮","transfer_station":"中俄","ts_id":"2","delivery_man_id":"267","user_name":"时间的话从尴尬不说过","deliver_sn":"IO-2016-03-0011","total_amount":"106.00","alloc_status":"1","d_status":"1","r_status":"1","d_remark":"","wh_date":"2016-03-10","alloc_time":"2016-03-18 10:20:51","address":["dog ghg"],"consignee":["rffdd"],"mobile":["15627726742"]},{"order_id":"287","delivery_man_name":"叶04","salesman":"fiony","transfer_station":"中2","ts_id":"1","delivery_man_id":"267","user_name":"roo","deliver_sn":"IO-2016-03-0012","total_amount":"147.00","alloc_status":"1","d_status":"1","r_status":"0","d_remark":"","wh_date":"2016-03-10","alloc_time":"2016-03-18 10:20:51","address":["dog ghg"],"consignee":["rffdd"],"mobile":["15627726742"]},{"order_id":"289","delivery_man_name":"叶04","salesman":"fiony","transfer_station":"中2","ts_id":"1","delivery_man_id":"267","user_name":"roo","deliver_sn":"IO-2016-03-0013","total_amount":"2700.00","alloc_status":"1","d_status":"0","r_status":"0","d_remark":"","wh_date":"2016-03-11","alloc_time":"2016-03-18 10:20:51","address":["dog ghg"],"consignee":["rffdd"],"mobile":["15627726742"]},{"order_id":"293","delivery_man_name":"叶04","salesman":"fiony","transfer_station":"中2","ts_id":"1","delivery_man_id":"267","user_name":"roo","deliver_sn":"IO-2016-03-0014","total_amount":"200.00","alloc_status":"1","d_status":"0","r_status":"0","d_remark":"","wh_date":"2016-03-16","alloc_time":"2016-03-18 10:20:51","address":[""],"consignee":["于文"],"mobile":["1452368597"]},{"order_id":"300","delivery_man_name":"叶04","salesman":"叶04","transfer_station":"法国","ts_id":"3","delivery_man_id":"267","user_name":"叶","deliver_sn":"IO-2016-03-0015","total_amount":"255.00","alloc_status":"1","d_status":"0","r_status":"0","d_remark":"","wh_date":"2016-03-16","alloc_time":"2016-03-18 10:20:51","address":["友谊路"],"consignee":["小美"],"mobile":["13563256789"]},{"order_id":"342","delivery_man_name":"叶04","salesman":"叶04","transfer_station":"中俄","ts_id":"2","delivery_man_id":"267","user_name":"叶02","deliver_sn":"IO-2016-03-0044","total_amount":"117.00","alloc_status":"1","d_status":"0","r_status":"0","d_remark":"","wh_date":"2016-03-31","alloc_time":"2016-03-31 15:42:59","address":["一林路52号后台","东纵路38号","村标准5号","阿是测试长撒"],"consignee":["叶02","叶02","从人的","测试"],"mobile":["13563258964","13563896542","13586489546","13417185094"]}]
     */

    public String rsgcode;
    public String rsgmsg;
    /**
     * order_id : 285
     * delivery_man_name : 叶04
     * salesman : 亮
     * transfer_station : 中俄
     * ts_id : 2
     * delivery_man_id : 267
     * user_name : 时间的话从尴尬不说过
     * deliver_sn : IO-2016-03-0011
     * total_amount : 106.00
     * alloc_status : 1
     * d_status : 1
     * r_status : 1
     * d_remark :
     * wh_date : 2016-03-10
     * alloc_time : 2016-03-18 10:20:51
     * address : ["dog ghg"]
     * consignee : ["rffdd"]
     * mobile : ["15627726742"]
     */

    public List<ListEntity> list;

    public static class ListEntity implements Parcelable {
        public String order_id;
        public String delivery_man_name;
        public String salesman;
        public String transfer_station;
        public String ts_id;
        public String delivery_man_id;
        public String user_name;
        public String deliver_sn;
        public String total_amount;
        public String alloc_status;
        public String d_status;
        public String r_status;
        public String d_remark;
        public String wh_date;
        public String alloc_time;
        public List<String> address;
        public List<String> consignee;
        public List<String> mobile;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.order_id);
            dest.writeString(this.delivery_man_name);
            dest.writeString(this.salesman);
            dest.writeString(this.transfer_station);
            dest.writeString(this.ts_id);
            dest.writeString(this.delivery_man_id);
            dest.writeString(this.user_name);
            dest.writeString(this.deliver_sn);
            dest.writeString(this.total_amount);
            dest.writeString(this.alloc_status);
            dest.writeString(this.d_status);
            dest.writeString(this.r_status);
            dest.writeString(this.d_remark);
            dest.writeString(this.wh_date);
            dest.writeString(this.alloc_time);
            dest.writeStringList(this.address);
            dest.writeStringList(this.consignee);
            dest.writeStringList(this.mobile);
        }

        public ListEntity() {
        }

        protected ListEntity(Parcel in) {
            this.order_id = in.readString();
            this.delivery_man_name = in.readString();
            this.salesman = in.readString();
            this.transfer_station = in.readString();
            this.ts_id = in.readString();
            this.delivery_man_id = in.readString();
            this.user_name = in.readString();
            this.deliver_sn = in.readString();
            this.total_amount = in.readString();
            this.alloc_status = in.readString();
            this.d_status = in.readString();
            this.r_status = in.readString();
            this.d_remark = in.readString();
            this.wh_date = in.readString();
            this.alloc_time = in.readString();
            this.address = in.createStringArrayList();
            this.consignee = in.createStringArrayList();
            this.mobile = in.createStringArrayList();
        }

        public static final Creator<ListEntity> CREATOR = new Creator<ListEntity>() {
            @Override
            public ListEntity createFromParcel(Parcel source) {
                return new ListEntity(source);
            }

            @Override
            public ListEntity[] newArray(int size) {
                return new ListEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rsgcode);
        dest.writeString(this.rsgmsg);
        dest.writeList(this.list);
    }

    public DispatchToday() {
    }

    protected DispatchToday(Parcel in) {
        this.rsgcode = in.readString();
        this.rsgmsg = in.readString();
        this.list = new ArrayList<ListEntity>();
        in.readList(this.list, ListEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<DispatchToday> CREATOR = new Parcelable.Creator<DispatchToday>() {
        @Override
        public DispatchToday createFromParcel(Parcel source) {
            return new DispatchToday(source);
        }

        @Override
        public DispatchToday[] newArray(int size) {
            return new DispatchToday[size];
        }
    };
}
