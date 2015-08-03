package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Jack on 15/8/1.
 */
@Table(name = "mine_service_people_list")
public class MineServicePeopleListTable extends Model {
    @Column(name = "user_id")
    public String userId;
    @Column(name = "user_name")
    public String userName;
    @Column(name = "mobile_phone")
    public String mobilePhone;
    @Column(name ="register_time")
    public String registerTime;
    @Column(name ="remark")
    public String remark;

}
