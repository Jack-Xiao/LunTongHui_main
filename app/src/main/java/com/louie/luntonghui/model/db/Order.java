package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/7/17.
 */
@Table(name = "my_order")
public class Order extends Model{
    public static final String ORDERID = "order_id";
    public static final String QUERY_TYPE = "query_type";
    public static final int DEFAULT_QUERY_TYPE = 0;
    public static final int SERVICE_TYPE = 1;
    public static final String USER_ID = "user_id";

    @Column(name = "allow_to_modify")
    public String allowToModify;
    @Column(name = "type")
    public String type;
    @Column(name = "money")
    public String money;
    @Column(name = "pay_name")
    public String payName;

    @Column(name = "order_id")
    public String orderId;
    @Column(name = "order_sn")
    public String orderSn;

    @Column(name = "order_amount")
    public String orderAmount;

    @Column(name = "add_time")
    public String addTime;


}
