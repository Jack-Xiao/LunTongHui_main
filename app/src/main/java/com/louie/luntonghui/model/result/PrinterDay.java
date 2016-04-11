package com.louie.luntonghui.model.result;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jack on 16/3/3.
 */
public class PrinterDay {


    /**
     * new_user_amount : 0
     * new_user_order_amount : 2
     * order_amount : 3
     * order_user_amount : 3
     * total_amount0 : 404.4
     * total_amount2 : 404.4
     * total_amount2_a : 397.9
     * total_amount2_b : 6.5
     * diff : 0
     */
    @SerializedName("total")
    public TotalEntity total;
    /**
     * user_name : fiony
     * new_user_amount : 0
     * new_user_order_amount : 0
     * order_amount : 1
     * order_user_amount : 1
     * goods_amount0 : 377.20
     * goods_amount2 : 377.20
     * goods_amount2_a : 377.20
     * goods_amount2_b : 0
     * amount_diff : 0
     */

    public List<ListEntity> list;

    public static class TotalEntity {
        public String new_user_amount;
        public String new_user_order_amount;
        public String order_amount;
        public String order_user_amount;
        public String total_amount0;
        public String total_amount2;
        public String total_amount2_a;
        public String total_amount2_b;
        public String diff;
    }

    public static class ListEntity {
        public String user_name;
        public String new_user_amount;
        public String new_user_order_amount;
        public String order_amount;
        public String order_user_amount;
        public String goods_amount0;
        public String goods_amount2;
        public String goods_amount2_a;
        public String goods_amount2_b;
        public String amount_diff;
    }
}
