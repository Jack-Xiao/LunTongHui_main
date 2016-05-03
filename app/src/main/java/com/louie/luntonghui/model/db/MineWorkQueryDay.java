package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Jack on 16/2/27.
 */
@Table(name = "work_query_day")
public class MineWorkQueryDay extends Model {
    @Column(name = "user_name")
    public String user_name;

    @Column(name = "order_sn")
    public String order_sn;

    @Column(name = "goods_amount")
    public String goods_amount;

    @Column(name = "user_id")
    public String user_id;

    @Column(name = "order_id")
    public String order_id;
}