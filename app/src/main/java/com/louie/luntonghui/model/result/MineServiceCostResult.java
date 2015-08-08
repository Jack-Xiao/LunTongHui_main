package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 15/8/6.
 */
public class MineServiceCostResult {


    /**
     * listallcat : [{"date":"2015/05/25","profit":"40"},{"date":"2015/07/31","profit":"40"},{"date":"2015/08/01","profit":"40"},{"date":"2015/08/05","profit":"280"},{"date":"2015/08/06","profit":"65.98"}]
     */
    public List<ListallcatEntity> listallcat;

    public static class ListallcatEntity {
        /**
         * date : 2015/05/25
         * profit : 40
         */
        public String date;
        public String profit;
    }
}
