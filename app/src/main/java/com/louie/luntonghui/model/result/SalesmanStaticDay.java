package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 16/2/27.
 */
public class SalesmanStaticDay {


    /**
     * user_name : 15989054681
     * order_sn : 2016012274952
     * goods_amount : 600.00
     */

    public List<ListEntity> list;

    public static class ListEntity {
        public String user_name;
        public String order_sn;
        public String goods_amount;
        public String user_id;
        public String order_id;
    }
}
