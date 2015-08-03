package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/7/3.
 */
@Table(name = "Category")
public class Category extends Model {
    @Column(name = "c_id")
    public String cId;

    @Column(name = "name")
    public String cName;

    @Column(name = "url")
    public String cUrl;

    @Column(name = "img")
    public String cImage;


}
