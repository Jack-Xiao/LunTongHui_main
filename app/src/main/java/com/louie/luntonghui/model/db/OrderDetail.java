package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Administrator on 2015/7/18.
 */
@Table(name = "order_detail")
public class OrderDetail extends Model {

    @Column(name ="Goods")
    public OrderDetailGoods goodes;

    public List<OrderDetailGoods> goodes(){
        return getMany(OrderDetailGoods.class,"goodes");
    }

}