package com.louie.luntonghui.model.result;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2015/7/10.
 */
public class OrderConfirm {
    /**
     * msg : [{"title":"长制胶(直纹)","amount":"数量：9999"}]
     * order : 2016031637337
     * rsgcode : 000
     * rsgmsg : 提交成功
     */

    public String order;
    public String rsgcode;
    public String rsgmsg;
    /**
     * title : 长制胶(直纹)
     * amount : 数量：9999
     */

    public List<FixGoods> msg;

    public static class FixGoods implements Parcelable {
        public String title;
        public String amount;

        protected FixGoods(Parcel in) {
            title = in.readString();
            amount = in.readString();
        }

        public static final Creator<FixGoods> CREATOR = new Creator<FixGoods>() {
            @Override
            public FixGoods createFromParcel(Parcel in) {
                return new FixGoods(in);
            }

            @Override
            public FixGoods[] newArray(int size) {
                return new FixGoods[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(amount);
        }
    }
}
