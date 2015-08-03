package com.louie.luntonghui.view.widget.model;

import java.util.List;

/**
 * Created by Administrator on 2015/6/11.
 */
public class CityModel {
    public String name;
    public String[] citytModelList;

    public int parentId;

    public CityModel(){

    }

    public CityModel(String name, List<DistrictModel> districtModels){
        this.name = name;
        //this.citytModelList = districtModels;

    }
}
