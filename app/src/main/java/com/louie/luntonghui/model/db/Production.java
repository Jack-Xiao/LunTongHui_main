package com.louie.luntonghui.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/6/16.
 */
@Table(name = "Production")
public class Production extends Model implements Parcelable {
    @Column(name ="name")
    public String name;
    @Column(name =  "pid")
    public String id;
    @Column(name = "img")
    public String img;
    @Column(name = "url")
    public String url;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.img);
        dest.writeString(this.url);
    }

    public Production() {
    }

    protected Production(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.img = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Production> CREATOR = new Parcelable.Creator<Production>() {
        public Production createFromParcel(Parcel source) {
            return new Production(source);
        }

        public Production[] newArray(int size) {
            return new Production[size];
        }
    };
}
