package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/7/13.
 */
@Table(name = "hot_search")
public class HotSearchTable extends Model {
    @Column(name = "name")
    public String hotSearchChar;

}
