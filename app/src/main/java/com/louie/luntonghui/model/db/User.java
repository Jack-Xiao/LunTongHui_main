package com.louie.luntonghui.model.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/6/3.
 */
@Table( name = "User")
public class User extends Model {
    //是否员工: 0为否, 1为是
    public static final String IS_EMPLOYEE = "is_employee";
    public static final  String ISEMPLOYEE = "1";
    public static final  String NOTEMPLOYEE = "0";
    //用户Id
    @Column(name = "uid")
    public String uid;
    //用户名
    @Column(name = "username")
    public String username;

    //密码
    @Column(name ="password")
    public String password;
    //电子邮件
    @Column(name = "email")
    public String email;

    //是否供应商，0 不是 ，1 是
    @Column(name ="is_supplier")
    public String isSupplier;

    //上级供应商
    @Column(name = "superior_supplier")
    public String superiorSupplier;

    //供应商邀请码
    @Column(name = "superior_supplier_invite_code")
    public String superiorSupplierInviteCode;

    //积分
    @Column(name = "integral")
    public String integral;
    //用户手机
    @Column(name = "mobile_phone")
    public String mobilePhone;
    //用户级别
    @Column(name = "rank_name")
    public String rankName;
    //手机绑定 --[1 已绑定 0 未绑定]
    @Column(name = "verification")
    public String verification;

    //微信绑定
    @Column(name = "wechat_bd")
    public String wechatBd;

    //时间判断 1 表示通过, 0 表示不通过
    @Column(name = "reg_time")
    public String regTime;

    //地区
    @Column(name = "place")
    public String place;

    @Column(name = "type")
    public String type;

}
