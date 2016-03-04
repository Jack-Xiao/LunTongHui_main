package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 16/2/27.
 */
public class SalesmanStaticYear {
    /**
     * date : 2016年01月
     * order_amount : 4599.9
     * no_amount : 818.5
     */

    public List<ListEntity> list;

    public static class ListEntity {
        public String date;
        public double order_amount;
        public double no_amount;
    }
}
