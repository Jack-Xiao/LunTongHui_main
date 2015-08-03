package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Administrator on 2015/7/14.
 */
public class GoodsThinkSearchList {

    /**
     * listallcat : [{"goods_name":"测试复制"},{"goods_name":"20150523测试"},{"goods_name":"测试2"}]
     */
    public List<ListallcatEntity> listallcat;

    public class ListallcatEntity {
        /**
         * goods_name : 测试复制
         */
        public String goods_name;
    }
}
