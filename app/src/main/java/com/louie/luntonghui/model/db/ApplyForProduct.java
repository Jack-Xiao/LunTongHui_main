package com.louie.luntonghui.model.db;

import java.util.List;

/**
 * Created by Jack on 16/5/3.
 */
public class ApplyForProduct {


    /**
     * goods_id : 2722
     * return_number : 4
     * return_reason : 其他
     */



    public String user_id;
    public String order_id;

    public List<Product> return_info;

    public class Product{
        public String goods_id;
        public String return_number;
        public String return_reason;
    }
}
