package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 15/7/30.
 */
public class MineServiceOrderListResult {


    /**
     * mysalelist : [{"handler":1,"money":"0.00","user_id":"176","user_name":"roo","mobile_phone":"15521251749","order_amount":"6344.00","allow_to_modify":0,"pay_name":"2","order_id":"369","add_time":"2015-07-30 01:39:37","order_sn":"2015072999373","page_count":1},{"handler":1,"money":"0.00","user_id":"176","user_name":"roo","mobile_phone":"15521251749","order_amount":"588.50","allow_to_modify":0,"pay_name":"2","order_id":"368","add_time":"2015-07-30 01:39:05","order_sn":"2015072950031","page_count":1}]
     */
    public List<MysalelistEntity> mysalelist;

    public static class MysalelistEntity {
        /**
         * handler : 1
         * money : 0.00
         * user_id : 176
         * user_name : roo
         * mobile_phone : 15521251749
         * order_amount : 6344.00
         * allow_to_modify : 0
         * pay_name : 2
         * order_id : 369
         * add_time : 2015-07-30 01:39:37
         * order_sn : 2015072999373
         * page_count : 1
         */
        public String handler;
        public String money;
        public String user_id;
        public String user_name;
        public String mobile_phone;
        public String order_amount;
        public String allow_to_modify;
        public String pay_name;
        public String order_id;
        public String add_time;
        public String order_sn;
        public String total_count;
        public String service_fee;
    }
}
