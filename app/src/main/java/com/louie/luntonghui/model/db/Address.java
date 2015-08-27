package com.louie.luntonghui.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.TableInfo;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/6/9.
 */
@Table(name = "Address")
public class Address extends Model implements Parcelable {
    @Column(name = "uid")
    public String uid;
    //服务器address id
    @Column(name = "address_id")
    public String addressId;

    //联系人
    @Column(name = "consignee")
    public String consignee;

    //联系方式
    @Column(name ="mobile")
    public String phone;

    //默认选择  1 默认
    @Column(name = "default_select")
    public String defaultSelect;

    @Column(name = "address")
    public String address;

    @Column(name = "province")
    public String province;
    @Column(name =  "city")
    public String city;
    @Column(name = "district")
    public String district;


    public Address() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.addressId);
        dest.writeString(this.consignee);
        dest.writeString(this.phone);
        dest.writeString(this.defaultSelect);
        dest.writeString(this.address);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);

    }

    private Address(Parcel in) {
        this.uid = in.readString();
        this.addressId = in.readString();
        this.consignee = in.readString();
        this.phone = in.readString();
        this.defaultSelect = in.readString();
        this.address = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
