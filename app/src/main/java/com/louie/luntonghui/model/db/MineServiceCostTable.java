package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Jack on 15/8/6.
 */
@Table(name = "mine_service_cost")
public class MineServiceCostTable extends Model {
    @Column(name = "cur_date")
    public String curDate;
    @Column(name = "cur_profit")
    public String curProfit;
}
