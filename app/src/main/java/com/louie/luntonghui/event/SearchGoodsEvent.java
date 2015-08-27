package com.louie.luntonghui.event;

/**
 * Created by Administrator on 2015/7/14.
 */
public class SearchGoodsEvent {
    private String mSearchContent;

    public void SearchGoodsEvent(String content){
        mSearchContent = content;
    }
    public String toString(){
        return mSearchContent.toString();
    }
}
