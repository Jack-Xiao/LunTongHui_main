package com.louie.luntonghui.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/11.
 */
public class XmlParserHandler extends DefaultHandler {
    public Map<String,List<String>>  provinceMap;
    public Map<String,List<String>>  cityMap;
    public Map<String,String> idNameList;
    public Map<String,String> nameIdList;


    public void startDocument(){

        idNameList = new HashMap<>();

        provinceMap = new HashMap<>();
        cityMap = new HashMap<>();
        nameIdList= new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //super.startElement(uri, localName, qName, attributes);
        if(qName.equals("RECORD")){
            String id = attributes.getValue("region_id");
            String name = attributes.getValue("region_name");
            idNameList.put(id,name);
            nameIdList.put(name,id);

            String ids="";
            String curName="";
            String parentId="";
            String parentName="";

            switch(Integer.parseInt(attributes.getValue("region_type"))){

                case 1:


                    provinceMap.put(attributes.getValue("region_name"),null);

                    break;
                case 2:

                    ids = attributes.getValue("region_id");
                    parentId = attributes.getValue("parent_id");
                    parentName = idNameList.get(parentId);
                    curName = attributes.getValue("region_name");

                    cityMap.put(curName,null);

                    if(provinceMap.containsKey(parentName)){
                        if(provinceMap.get(parentName) ==null)
                        provinceMap.put(parentName,new ArrayList<String>());
                    }

                    provinceMap.get(parentName).add(curName);
                     break;
                case 3:

                    ids = attributes.getValue("region_id");
                    curName = attributes.getValue("region_name");
                    parentId = attributes.getValue("parent_id");
                    parentName = idNameList.get(parentId);

                    if(cityMap.containsKey(parentName)){
                        if(cityMap.get(parentName) == null){
                            cityMap.put(parentName,new ArrayList<String>());
                        }
                        cityMap.get(parentName).add(curName);
                    }
                     break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }
}
