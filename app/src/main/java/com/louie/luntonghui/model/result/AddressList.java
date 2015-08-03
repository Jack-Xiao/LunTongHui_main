package com.louie.luntonghui.model.result;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2015/6/9.
 */
public class AddressList {

    /**
     * listallcat : [{"default":"1","consignee":"测试","address":"广东广州海珠区测试",
     *                  "address_id":"483","mobile":"13511147855","tel":"0","email":"0"}]
     */
    public List<ListallcatEntity> listallcat;
    /**
     * listallcat : [{"default":"1","consignee":"哥几个","address":"广东广州天河区哈哈见面v还好吧","province":"6","city":"76","district":"693","address_id":"522","mobile":"15989054686","tel":"0","email":"0"}]
     */
/*    public List<ListallcatEntity> listallcat;

    public class ListallcatEntity {
        *//**
         * default : 1
         * consignee : 测试
         * address : 广东广州海珠区测试
         * address_id : 483
         * mobile : 13511147855
         * tel : 0
         * email : 0
         *//*
        @SerializedName("default")
        public String defaultX;

        public String consignee;
        public String address;
        public String address_id;
        public String mobile;
        public String tel;
        public String email;

        //省市区 来自 raw/ecs_region1  的数据
        public int province;
        public int city;
        public int district;
    }*/


    public class ListallcatEntity {
        /**
         * default : 1
         * consignee : 哥几个
         * address : 广东广州天河区哈哈见面v还好吧
         * province : 6
         * city : 76
         * district : 693
         * address_id : 522
         * mobile : 15989054686
         * tel : 0
         * email : 0
         */
        @SerializedName("default")
        public String defaultX;
        public String consignee;
        public String address;
        public String province;
        public String city;
        public String district;
        public String address_id;
        public String mobile;
        public String tel;
        public String email;
    }
}
