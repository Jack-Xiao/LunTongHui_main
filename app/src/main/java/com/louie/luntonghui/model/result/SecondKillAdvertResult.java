package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Administrator on 2015/7/24.
 */
public class SecondKillAdvertResult {


    /**
     * listallcat : [{"ad_id":"250","ad_code":"http://120.25.224.250/data/afficheimg/1437737625569759819.jpg","ad_link":"","ad_name":"秒杀"}]
     */
    public List<ListallcatEntity> listallcat;

    public class ListallcatEntity {
        /**
         * ad_id : 250
         * ad_code : http://120.25.224.250/data/afficheimg/1437737625569759819.jpg
         * ad_link :
         * ad_name : 秒杀
         */
        public String ad_id;
        public String ad_code;
        public String ad_link;
        public String ad_name;
    }
}
