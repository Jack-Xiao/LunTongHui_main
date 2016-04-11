package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Jack on 16/2/27.
 */
@Table(name = "work_query_month")
public class MineWorkQueryMonth extends Model{
    @Column(name = "date")
    public String date;

    //订单总额
    @Column(name = "order_amount")
    public String order_amount;

    //
    @Column(name = "no_amount")
    public String no_amount;


    /*@Column(name = "total_order_amount")
    public String total_order_amount;

    @Column(name = "total_no_amount")
    public String total_no_amount;*/

    //电池
    @Column(name = "no_amount_a")
    public String no_amount_a;

    //整车
    @Column(name= "no_amount_b")
    public String no_amount_b;

}
