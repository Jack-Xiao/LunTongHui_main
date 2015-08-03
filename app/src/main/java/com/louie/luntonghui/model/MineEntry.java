package com.louie.luntonghui.model;

/**
 * Created by Administrator on 2015/6/4.
 */
public class MineEntry {

    //1.有名称  2.中间断线  3.断背景
    public int category;

    public String name;

    public int imageId;

    public MineEntry(int category){
        this.category = category;
    }
    public MineEntry(int imageId,String name,int category){
        this.name = name;
        this.imageId = imageId;
        this.category = category;
    }
}
