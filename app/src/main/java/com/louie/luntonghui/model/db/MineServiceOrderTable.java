package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Jack on 15/7/30.
 */
@Table(name = "mine_service_order_table")
public class MineServiceOrderTable extends Model {

    @Column(name = "allow_to_modify")
    public String allowToModify;

    @Column(name = "handler")
    public String handler;

    @Column(name = "user_name")
    public String userName;
    @Column(name = "user_id")
    public String userId;

    @Column(name = "mobile_phone")
    public String mobilePhone;

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

    @Column(name = "total_count")
    public String totalCount;
    //服务费
    @Column(name = "service_fee")
    public String serviceFee;
}
