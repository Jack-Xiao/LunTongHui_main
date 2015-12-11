package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 15/10/22.
 */
public class HomeAdversion {
    /**
     * title : 好货推荐
     * recommend_adv : {"img":"http://120.25.224.250/data/afficheimg/1446012520472479700.jpg","cat_id":"2","id":"","url":"","name":""}
     * recommend_adv_left : {"img":"http://120.25.224.250/data/afficheimg/1446012543208321909.jpg","cat_id":"3","id":"","url":"","name":""}
     * recommend_adv_right1 : {"img":"http://120.25.224.250/data/afficheimg/1446012560477093958.jpg","cat_id":"4","id":"","url":"","name":""}
     * recommend_adv_right2 : {"img":"http://120.25.224.250/data/afficheimg/1446012570471884713.jpg","cat_id":"8","id":"","url":"","name":""}
     */

    public GoodsRecommendEntity goods_recommend;
    /**
     * title : 单车配件市场
     * goods_part_adv : {"img":"http://120.25.224.250/data/afficheimg/1446012644571618175.jpg","url":"","cat_id":"http://120.25.224.250/data/afficheimg/12"}
     * goods_part_adv_array : [{"id":"","cat_id":"47","name":"","title":"未定义标题","img":"http://120.25.224.250/data/afficheimg/1446012655395905594.jpg","url":""},{"id":"","cat_id":"56","name":"","title":"未定义标题","img":"http://120.25.224.250/data/afficheimg/1446012667516606602.jpg","url":""},{"id":"","cat_id":"58","name":"","title":"未定义标题","img":"http://120.25.224.250/data/afficheimg/1446012680169461461.jpg","url":""},{"id":"","cat_id":"249","name":"","title":"未定义标题","img":"http://120.25.224.250/data/afficheimg/1446012691038198644.jpg","url":""},{"id":"","cat_id":"61","name":"","title":"未定义标题","img":"http://120.25.224.250/data/afficheimg/1446012705097615789.jpg","url":""},{"id":"","cat_id":"62","name":"","title":"未定义标题","img":"http://120.25.224.250/data/afficheimg/1446012715602904531.jpg","url":""}]
     */

    public List<GoodsAdvPartEntity> goods_adv_part;

    public static class GoodsRecommendEntity {
        public String title;
        /**
         * img : http://120.25.224.250/data/afficheimg/1446012520472479700.jpg
         * cat_id : 2
         * id :
         * url :
         * name :
         */

        public RecommendAdvEntity recommend_adv;
        /**
         * img : http://120.25.224.250/data/afficheimg/1446012543208321909.jpg
         * cat_id : 3
         * id :
         * url :
         * name :
         */

        public RecommendAdvLeftEntity recommend_adv_left;
        /**
         * img : http://120.25.224.250/data/afficheimg/1446012560477093958.jpg
         * cat_id : 4
         * id :
         * url :
         * name :
         */

        public RecommendAdvRight1Entity recommend_adv_right1;
        /**
         * img : http://120.25.224.250/data/afficheimg/1446012570471884713.jpg
         * cat_id : 8
         * id :
         * url :
         * name :
         */

        public RecommendAdvRight2Entity recommend_adv_right2;

        public static class RecommendAdvEntity {
            public String img;
            public String cat_id;
            public String id;
            public String url;
            public String name;
        }

        public static class RecommendAdvLeftEntity {
            public String img;
            public String cat_id;
            public String id;
            public String url;
            public String name;
        }

        public static class RecommendAdvRight1Entity {
            public String img;
            public String cat_id;
            public String id;
            public String url;
            public String name;
        }

        public static class RecommendAdvRight2Entity {
            public String img;
            public String cat_id;
            public String id;
            public String url;
            public String name;
        }
    }

    public static class GoodsAdvPartEntity {
        public String title;
        /**
         * img : http://120.25.224.250/data/afficheimg/1446012644571618175.jpg
         * url :
         * cat_id : http://120.25.224.250/data/afficheimg/12
         */

        public GoodsPartAdvEntity goods_part_adv;
        /**
         * id :
         * cat_id : 47
         * name :
         * title : 未定义标题
         * img : http://120.25.224.250/data/afficheimg/1446012655395905594.jpg
         * url :
         */

        public List<GoodsPartAdvArrayEntity> goods_part_adv_array;

        public static class GoodsPartAdvEntity {
            public String img;
            public String url;
            public String cat_id;
        }

        public static class GoodsPartAdvArrayEntity {
            public String id;
            public String cat_id;
            public String name;
            public String title;
            public String img;
            public String url;
        }
    }


/*
    *//**
     * title : 好货推荐
     * recommend_adv : {"id":"261","url":"","name":"消费者","img":"http://120.25.224.250/data/afficheimg/1445424099325793456.jpg"}
     * recommend_adv_left : {"id":"260","url":"http://120.25.224.250/api/mobile.php?act=privilege&mode=test&privilege_type=4&huodong_type=20&user_type=&display=0&ad_id=260","name":"减免","img":"http://120.25.224.250/data/afficheimg/1444815218624560702.jpg"}
     * recommend_adv_right1 : {"id":"259","url":"http://120.25.224.250/api/mobile.php?act=privilege&mode=test&privilege_type=3&huodong_type=19&user_type=&display=0&ad_id=259","name":"赠品","img":"http://120.25.224.250/data/afficheimg/1444815227926495020.jpg"}
     * recommend_adv_right2 : {"id":"258","url":"http://120.25.224.250/api/mobile.php?act=privilege&mode=test&privilege_type=2&huodong_type=21&user_type=&display=0&ad_id=258","name":"打折","img":"http://120.25.224.250/data/afficheimg/1444815235781758248.jpg"}
     *//*

    public GoodsRecommendEntity goods_recommend;
    *//**
     * title : 各种车配件市场
     * goods_part_adv : {"img":"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=297036724,2521787144&fm=58","url":"http://finance.ifeng.com/a/20151021/14031256_0.shtml"}
     * goods_part_adv_array : [{"id":295,"name":"18x2.125吉路尔牌内胎（弯咀 ）","title":"没有标题0","img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=492791525,146044800&fm=80","url":"http://news.163.com/15/1021/12/B6F08JDG0001121M.html"},{"id":38,"name":"14x175赛阳牌神龟王内胎（美咀）","title":"没有标题1","img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=4183385477,2238126687&fm=80","url":"http://news.21cn.com/social/shixiang/a/2015/1021/01/30174014.shtml"},{"id":40,"name":"金万程丁基胶内胎","title":"没有标题2","img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2765800502,2267812933&fm=80","url":"http://news.hsw.cn/system/2015/1021/316877.shtml"},{"id":43,"name":"14x1.75杭泰自行车内胎","title":"没有标题3","img":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=85050,1976050761&fm=80","url":"http://sports.qq.com/a/20151021/037591.htm"},{"id":453,"name":"24x175吉路尔牌外胎","title":"没有标题4","img":"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=4127287155,1074968969&fm=80","url":"http://sports.qq.com/a/20151021/037591.htm"},{"id":274,"name":"26x175吉路尔牌好奇外胎","title":"没有标题5","img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=220362365,1636848821&fm=80","url":"http://sports.qq.com/a/20151021/037591.htm"}]
     *//*

    public List<GoodsAdvPartEntity> goods_adv_part;

    public static class GoodsRecommendEntity {
        public String title;
        *//**
         * id : 261
         * url :
         * name : 消费者
         * img : http://120.25.224.250/data/afficheimg/1445424099325793456.jpg
         *//*

        public RecommendAdvEntity recommend_adv;
        *//**
         * id : 260
         * url : http://120.25.224.250/api/mobile.php?act=privilege&mode=test&privilege_type=4&huodong_type=20&user_type=&display=0&ad_id=260
         * name : 减免
         * img : http://120.25.224.250/data/afficheimg/1444815218624560702.jpg
         *//*

        public RecommendAdvLeftEntity recommend_adv_left;
        *//**
         * id : 259
         * url : http://120.25.224.250/api/mobile.php?act=privilege&mode=test&privilege_type=3&huodong_type=19&user_type=&display=0&ad_id=259
         * name : 赠品
         * img : http://120.25.224.250/data/afficheimg/1444815227926495020.jpg
         *//*

        public RecommendAdvRight1Entity recommend_adv_right1;
        *//**
         * id : 258
         * url : http://120.25.224.250/api/mobile.php?act=privilege&mode=test&privilege_type=2&huodong_type=21&user_type=&display=0&ad_id=258
         * name : 打折
         * img : http://120.25.224.250/data/afficheimg/1444815235781758248.jpg
         *//*

        public RecommendAdvRight2Entity recommend_adv_right2;

        public static class RecommendAdvEntity {
            public String id;
            public String url;
            public String name;
            public String img;
        }

        public static class RecommendAdvLeftEntity {
            public String id;
            public String url;
            public String name;
            public String img;
        }

        public static class RecommendAdvRight1Entity {
            public String id;
            public String url;
            public String name;
            public String img;
        }

        public static class RecommendAdvRight2Entity {
            public String id;
            public String url;
            public String name;
            public String img;
        }
    }

    public static class GoodsAdvPartEntity {
        public String title;
        *//**
         * img : https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=297036724,2521787144&fm=58
         * url : http://finance.ifeng.com/a/20151021/14031256_0.shtml
         *//*

        public GoodsPartAdvEntity goods_part_adv;
        *//**
         * id : 295
         * name : 18x2.125吉路尔牌内胎（弯咀 ）
         * title : 没有标题0
         * img : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=492791525,146044800&fm=80
         * url : http://news.163.com/15/1021/12/B6F08JDG0001121M.html
         *//*

        public List<GoodsPartAdvArrayEntity> goods_part_adv_array;

        public static class GoodsPartAdvEntity {
            public String img;
            public String url;
        }

        public static class GoodsPartAdvArrayEntity {
            public int id;
            public String name;
            public String title;
            public String img;
            public String url;
        }
    }*/
}
