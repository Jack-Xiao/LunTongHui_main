package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Jack on 16/2/27.
 */
@Table(name = "work_query_year")
public class MineWorkQueryYear extends Model {
    @Column(name = "date")
    public String date;

    @Column(name = "order_amount")
    public String order_amount;

    @Column(name = "no_amount")
    public String no_amount;

    @Column(name = "no_amount_a")
    public String no_amount_a;

    @Column(name = "no_amount_b")
    public String no_amount_b;
}
