package com.louie.luntonghui.data;

/**
 * Created by Jack on 2015/5/29.
 * 广告类
 */
public class AdvertisementInfo {
    private String data;
    private String url;
    private Integer drawableRes;

    public AdvertisementInfo(String data,String url){
        this.data = data;
        this.url = url;
    }
    public AdvertisementInfo(String data,Integer drawableRes){
        this.data = data;
        this.drawableRes = drawableRes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(Integer drawableRes) {
        this.drawableRes = drawableRes;
    }

}
