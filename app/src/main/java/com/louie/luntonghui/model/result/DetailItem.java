package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
public class DetailItem {
    /**
     * goods_id : 2921
     * cat_id : 160
     * goods_name : 095型永康型彩色胶兰（黄）
     * brand_name : 河北
     * goods_brief :
     * guige : 1*30
     * danwei : 个
     * goods_img : http://120.25.201.114/images/201509/thumb_img/2921_thumb_G_1442951213197.jpg
     * goods_sn : ECS002921
     * goods_number : 65535
     * market_price : 6.80
     * shop_price : 6.10
     * gys_money : 0.80
     * promote_price : 0.00
     * goods_desc :
     * sort_order : 100
     * is_best : 0
     * is_new : 0
     * is_hot : 0
     * display : 0
     * give_integral : 0
     * integral : 0
     * extension_code :
     * is_promote : 0
     * properties : []
     * specification : []
     * pictures : [{"goods_id":"2917","goods_name":"095型永康型彩色胶兰（梅红）","short_name":"095型永康型彩色胶兰（梅红）","goods_thumb":"http://120.25.201.114/images/201509/thumb_img/2917_thumb_G_1442270368815.jpg","goods_img":"http://120.25.201.114/images/201509/goods_img/2917_G_1442270368385.jpg","shop_price":"6.10","url":"http://120.25.201.114/goods.php?id=2917","promote_price":0},{"goods_id":"2920","goods_name":"095型永康型彩色胶兰（蓝）","short_name":"095型永康型彩色胶兰（蓝）","goods_thumb":"http://120.25.201.114/images/201509/thumb_img/2920_thumb_G_1442953184229.jpg","goods_img":"http://120.25.201.114/images/201509/goods_img/2920_G_1442953184559.jpg","shop_price":"6.10","url":"http://120.25.201.114/goods.php?id=2920","promote_price":0},{"goods_id":"2919","goods_name":"095型永康型彩色胶兰（红）","short_name":"095型永康型彩色胶兰（红）","goods_thumb":"http://120.25.201.114/images/201509/thumb_img/2919_thumb_G_1442952386752.jpg","goods_img":"http://120.25.201.114/images/201509/goods_img/2919_G_1442952386128.jpg","shop_price":"6.10","url":"http://120.25.201.114/goods.php?id=2919","promote_price":0}]
     * bought_goods : [{"img_id":"4077","img_url":"http://120.25.201.114/images/201509/goods_img/2921_P_1442951213165.jpg","thumb_url":"http://120.25.201.114/images/201509/thumb_img/2921_thumb_P_1442951213406.jpg","img_desc":""}]
     * discounta : 0
     * discount : 0
     * discount_type : 0
     * discount_time : 0
     * discount_name : 0
     */

    public List<ListallcatEntity> listallcat;

    public static class ListallcatEntity {
        public String goods_id;
        public String cat_id;
        public String goods_name;
        public String brand_name;
        public String goods_brief;
        public String guige;
        public String danwei;
        public String goods_img;
        public String goods_sn;
        public String goods_number;
        public String market_price;
        public String shop_price;
        public String gys_money;
        public String promote_price;
        public String goods_desc;
        public String sort_order;
        public String is_best;
        public String is_new;
        public String is_hot;
        public String display;
        public String give_integral;
        public String integral;
        public String extension_code;
        public String is_promote;
        public String discounta;
        public String discount;
        public String discount_type;
        public String discount_time;
        public String discount_name;
        public List<?> properties;
        public List<?> specification;

        //有库存的话inventory为1,没有的话inventory为0
        public String inventory;
        /**
         * goods_id : 2917
         * goods_name : 095型永康型彩色胶兰（梅红）
         * short_name : 095型永康型彩色胶兰（梅红）
         * goods_thumb : http://120.25.201.114/images/201509/thumb_img/2917_thumb_G_1442270368815.jpg
         * goods_img : http://120.25.201.114/images/201509/goods_img/2917_G_1442270368385.jpg
         * shop_price : 6.10
         * url : http://120.25.201.114/goods.php?id=2917
         * promote_price : 0
         */

        public List<PicturesEntity> pictures;
        /**
         * img_id : 4077
         * img_url : http://120.25.201.114/images/201509/goods_img/2921_P_1442951213165.jpg
         * thumb_url : http://120.25.201.114/images/201509/thumb_img/2921_thumb_P_1442951213406.jpg
         * img_desc :
         */

        public List<BoughtGoodsEntity> bought_goods;

        public static class PicturesEntity {
            public String goods_id;
            public String goods_name;
            public String short_name;
            public String goods_thumb;
            public String goods_img;
            public String shop_price;
            public String url;
            public int promote_price;
        }

        public static class BoughtGoodsEntity {
            public String img_id;
            public String img_url;
            public String thumb_url;
            public String img_desc;
        }
    }

    /**
     * listallcat : [{"give_integral":"100.00","discounta":"0","discount":"0","is_best":"0","pictures":[{"goods_name":"测试","shop_price":"200.00","goods_thumb":"http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg","goods_id":"364","promote_price":0,"short_name":"测试","goods_img":"http://120.25.224.250/images/201503/goods_img/364_G_1425995810795.jpg","url":"http://120.25.224.250/goods.php?id=364"},{"goods_name":"20150523测试","shop_price":"120.00","goods_thumb":"http://120.25.224.250/images/no_picture.gif","goods_id":"837","promote_price":0,"short_name":"20150523测试","goods_img":"http://120.25.224.250/images/no_picture.gif","url":"http://120.25.224.250/goods.php?id=837"},{"goods_name":"好心情硬管雨伞架","shop_price":"6.50","goods_thumb":"http://120.25.224.250/images/no_picture.gif","goods_id":"276","promote_price":0,"short_name":"好心情硬管雨伞架","goods_img":"http://120.25.224.250/images/no_picture.gif","url":"http://120.25.224.250/goods.php?id=276"}],"bought_goods":[{"thumb_url":"","img_desc":"","img_url":"http://120.25.224.250/images/Image.aspx2.jpg","img_id":""}],"extension_code":"","is_promote":"0","is_hot":"0","integral":"0","cat_id":"130","discount_name":"0","sort_order":"100","discount_time":"0","goods_name":"测试2","shop_price":"100.00","is_new":"0","display":"0","goods_id":"463","goods_sn":"ECS000463","promote_price":"0.00","specification":[{"values":[{"price":"","format_price":"0.00","label":"10cm","id":"289"},{"price":"","format_price":"0.00","label":"12cm","id":"290"}],"name":"测试一号","attr_type":"1"}],"brand_name":"测试","goods_desc":"","goods_brief":"","goods_number":"1003","gys_money":"10.00","market_price":"120.00","guige":"1*50","goods_img":"http://120.25.224.250/images/Image.aspx2.jpg","properties":[]}]
     *//*
    public List<ListallcatEntity> listallcat;

    public class ListallcatEntity {
        *//**
         * give_integral : 100.00
         * discounta : 0
         * discount : 0
         * is_best : 0
         * pictures : [{"goods_name":"测试","shop_price":"200.00","goods_thumb":"http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg","goods_id":"364","promote_price":0,"short_name":"测试","goods_img":"http://120.25.224.250/images/201503/goods_img/364_G_1425995810795.jpg","url":"http://120.25.224.250/goods.php?id=364"},{"goods_name":"20150523测试","shop_price":"120.00","goods_thumb":"http://120.25.224.250/images/no_picture.gif","goods_id":"837","promote_price":0,"short_name":"20150523测试","goods_img":"http://120.25.224.250/images/no_picture.gif","url":"http://120.25.224.250/goods.php?id=837"},{"goods_name":"好心情硬管雨伞架","shop_price":"6.50","goods_thumb":"http://120.25.224.250/images/no_picture.gif","goods_id":"276","promote_price":0,"short_name":"好心情硬管雨伞架","goods_img":"http://120.25.224.250/images/no_picture.gif","url":"http://120.25.224.250/goods.php?id=276"}]
         * bought_goods : [{"thumb_url":"","img_desc":"","img_url":"http://120.25.224.250/images/Image.aspx2.jpg","img_id":""}]
         * extension_code :
         * is_promote : 0
         * is_hot : 0
         * integral : 0
         * cat_id : 130
         * discount_name : 0
         * sort_order : 100
         * discount_time : 0
         * goods_name : 测试2
         * shop_price : 100.00
         * is_new : 0
         * display : 0
         * goods_id : 463
         * goods_sn : ECS000463
         * promote_price : 0.00
         * specification : [{"values":[{"price":"","format_price":"0.00","label":"10cm","id":"289"},{"price":"","format_price":"0.00","label":"12cm","id":"290"}],"name":"测试一号","attr_type":"1"}]
         * brand_name : 测试
         * goods_desc :
         * goods_brief :
         * goods_number : 1003
         * gys_money : 10.00
         * market_price : 120.00
         * guige : 1*50
         * goods_img : http://120.25.224.250/images/Image.aspx2.jpg
         * properties : []
         *//*
        public String give_integral;
        public String discounta;
        public String discount;
        public String is_best;
        public List<PicturesEntity> pictures;
        public List<Bought_goodsEntity> bought_goods;
        public String extension_code;
        public String is_promote;
        public String is_hot;
        public String integral;
        public String cat_id;
        public String discount_name;
        public String sort_order;
        public String discount_time;
        public String goods_name;
        public String shop_price;
        public String is_new;
        public String display;
        public String goods_id;
        public String goods_sn;
        public String promote_price;
        public List<SpecificationEntity> specification;
        public String brand_name;
        public String goods_desc;
        public String goods_brief;
        public String goods_number;
        public String gys_money;
        public String market_price;
        public String guige;
        public String goods_img;
        public List<?> properties;

        public class PicturesEntity {
            *//**
             * goods_name : 测试
             * shop_price : 200.00
             * goods_thumb : http://120.25.224.250/images/201503/thumb_img/364_thumb_G_1425995810033.jpg
             * goods_id : 364
             * promote_price : 0
             * short_name : 测试
             * goods_img : http://120.25.224.250/images/201503/goods_img/364_G_1425995810795.jpg
             * url : http://120.25.224.250/goods.php?id=364
             *//*
            public String goods_name;
            public String shop_price;
            public String goods_thumb;
            public String goods_id;
            public int promote_price;
            public String short_name;
            public String goods_img;
            public String url;
        }
        //view pager 展示图片
        public class Bought_goodsEntity {
            *//**
             * thumb_url :
             * img_desc :
             * img_url : http://120.25.224.250/images/Image.aspx2.jpg
             * img_id :
             *//*
            public String thumb_url;
            public String img_desc;
            public String img_url;
            public String img_id;
        }

        public class SpecificationEntity {
            *//**
             * values : [{"price":"","format_price":"0.00","label":"10cm","id":"289"},{"price":"","format_price":"0.00","label":"12cm","id":"290"}]
             * name : 测试一号
             * attr_type : 1
             *//*
            public List<ValuesEntity> values;
            public String name;
            public String attr_type;

            public class ValuesEntity {
                *//**
                 * price :
                 * format_price : 0.00
                 * label : 10cm
                 * id : 289
                 *//*
                public String price;
                public String format_price;
                public String label;
                public String id;
            }
        }
    }*/


}