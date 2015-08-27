package com.louie.luntonghui.view.widget.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/11.
 */
public class ProvinceModel {
    public String name;
    public Map<String,String[]> cityModelList;

    public ProvinceModel(){
        super();
    }
    public ProvinceModel(String name,List<CityModel> cityModels){
        super();
        this.name = name;
        //this.cityModelList = cityModels;
    }
}
