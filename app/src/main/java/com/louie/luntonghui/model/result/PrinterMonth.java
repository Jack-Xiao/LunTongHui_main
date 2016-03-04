package com.louie.luntonghui.model.result;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jack on 16/3/3.
 */
public class PrinterMonth {
    /**
     * new_user_amount : 0
     * new_user_order_amount : 24
     * order_amount : 28
     * order_user_amount : 11
     * total_amount0 : 5651.2
     * total_amount2 : 847.7
     * total_amount2_a : 802.2
     * total_amount2_b : 45.5
     * diff : 4803.5
     */
    @SerializedName("total")
    public TotalEntity total;
    /**
     * user_name : 亮
     * new_user_amount : 0
     * new_user_order_amount : 18
     * order_amount : 18
     * order_user_amount : 6
     * goods_amount0 : 2366.50
     * goods_amount2 : 217.40
     * goods_amount2_a : 171.90
     * goods_amount2_b : 45.50
     * amount_diff : 2149.1
     */

    public List<ListEntity> list;

    public static class TotalEntity {
        public double new_user_amount;
        public double new_user_order_amount;
        public double order_amount;
        public double order_user_amount;
        public double total_amount0;
        public double total_amount2;
        public double total_amount2_a;
        public double total_amount2_b;
        public double diff;
    }

    public static class ListEntity {
        public String user_name;
        public String new_user_amount;
        public String new_user_order_amount;
        public String order_amount;
        public double order_user_amount;
        public String goods_amount0;
        public String goods_amount2;
        public String goods_amount2_a;
        public String goods_amount2_b;
        public double amount_diff;
    }
/*
    *
     * new_user_amount : 0
     * new_user_order_amount : 24
     * order_amount : 28
     * order_user_amount : 11
     * total_amount0 : 5651.2
     * total_amount2 : 847.7
     * total_amount2_a : 802.2
     * total_amount2_b : 45.5
     * diff : 4803.5

    public TotalEntity total;
    *//**
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
     *//*

    public List<ListEntity> list;

    public static class TotalEntity {
        public double new_user_amount;
        public double new_user_order_amount;
        public double order_amount;
        public double order_user_amount;
        public double total_amount0;
        public double total_amount2;
        public double total_amount2_a;
        public double total_amount2_b;
        public double diff;
    }

    public static class ListEntity {
        public String user_name;
        public String new_user_amount;
        public String new_user_order_amount;
        public String order_amount;
        public double order_user_amount;
        public String goods_amount0;
        public String goods_amount2;
        public String goods_amount2_a;
        public double goods_amount2_b;
        public double amount_diff;
    }*/


}
