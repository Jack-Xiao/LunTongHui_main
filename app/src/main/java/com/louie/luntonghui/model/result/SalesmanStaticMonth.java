package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 16/2/27.
 */
public class SalesmanStaticMonth {

    /**
     * list : [{"date":"02月01日","order_amount":0,"no_amount":0},{"date":"02月02日","order_amount":0,"no_amount":0},{"date":"02月03日","order_amount":0,"no_amount":0},{"date":"02月04日","order_amount":0,"no_amount":0},{"date":"02月05日","order_amount":0,"no_amount":0},{"date":"02月06日","order_amount":0,"no_amount":0},{"date":"02月07日","order_amount":0,"no_amount":0},{"date":"02月08日","order_amount":0,"no_amount":0},{"date":"02月09日","order_amount":0,"no_amount":0},{"date":"02月10日","order_amount":0,"no_amount":0},{"date":"02月11日","order_amount":0,"no_amount":0},{"date":"02月12日","order_amount":0,"no_amount":0},{"date":"02月13日","order_amount":0,"no_amount":0},{"date":"02月14日","order_amount":0,"no_amount":0},{"date":"02月15日","order_amount":0,"no_amount":0},{"date":"02月16日","order_amount":0,"no_amount":0},{"date":"02月17日","order_amount":0,"no_amount":0},{"date":"02月18日","order_amount":0,"no_amount":0},{"date":"02月19日","order_amount":0,"no_amount":0},{"date":"02月20日","order_amount":0,"no_amount":0},{"date":"02月21日","order_amount":0,"no_amount":0},{"date":"02月22日","order_amount":0,"no_amount":0},{"date":"02月23日","order_amount":0,"no_amount":0},{"date":"02月24日","order_amount":0,"no_amount":0},{"date":"02月25日","order_amount":0,"no_amount":0},{"date":"02月26日","order_amount":0,"no_amount":0},{"date":"02月27日","order_amount":0,"no_amount":0}]
     * total_order_amount : 0
     * total_no_amount : 0
     */

    /**
     * date : 02月01日
     * order_amount : 0
     * no_amount : 0
     */

    public List<ListEntity> list;

    public static class ListEntity {
        public String date;
        public String order_amount;
        public String no_amount;
        public String no_amount_a;
        public String no_amount_b;
    }
}
