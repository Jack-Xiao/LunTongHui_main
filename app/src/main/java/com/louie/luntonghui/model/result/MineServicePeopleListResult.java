package com.louie.luntonghui.model.result;

import java.util.List;

/**
 * Created by Jack on 15/8/1.
 */
public class MineServicePeopleListResult {


    /**
     * listallcat : [{"user_id":"181","user_name":"ma","mobile_phone":"13924218815","reg_time":"2015-07-31 08:00:00"},{"user_id":"158","user_name":"goneprotoss","mobile_phone":"13690518980","reg_time":"2015-07-31 08:00:00"},{"user_id":"182","user_name":"jian","mobile_phone":"15915772127","reg_time":"2015-07-31 08:00:00"},{"user_id":"186","user_name":"13725275140","mobile_phone":"13725275140","reg_time":"2015-07-31 08:00:00"},{"user_id":"178","user_name":"平平","mobile_phone":"13533131061","reg_time":"2015-07-31 08:00:00"},{"user_id":"176","user_name":"roo","mobile_phone":"15521251749","reg_time":"2015-07-31 08:00:00"},{"user_id":"139","user_name":"亮测试2","mobile_phone":"13478212177","reg_time":"2015-07-31 08:00:00"},{"user_id":"137","user_name":"积分测试","mobile_phone":"15889954287","reg_time":"2015-07-31 08:00:00"}]
     */
    public List<ListallcatEntity> listallcat;

    public static class ListallcatEntity {
        /**
         * user_id : 181
         * user_name : ma
         * mobile_phone : 13924218815
         * reg_time : 2015-07-31 08:00:00
         */
        public String user_id;
        public String user_name;
        public String mobile_phone;
        public String reg_time;
        public String remark;
    }
}
