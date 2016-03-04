package com.louie.luntonghui.util;

/**
 * Created by Administrator on 2015/6/2.
 */
public class ConstantURL {

    public static final String SUCCESSCODE = "000";

    public static final String HOST = "http://120.25.224.250/api/"; //测试 Ip

    //public static final String HOST = "http://120.24.209.210/api_new/"; //正式机Ip
    //public static final String HOST = "http://120.25.201.114/api_new/"; //另一台正式机

    //public static final String HOST = "http://img.zgltong.com/api_new/"; //另一台正式机

    public static final String MODE = "mode=test";

    private static final String ARGSCONTENT1 = "%1$s";
    private static final String ARGSCONTENT2 = "%2$s";

    private static final String ARGSCONTENT3 = "%3$s";
    private static final String ARGSCONTENT4 = "%4$s";
    private static final String ARGSCONTENT5 = "%5$s";
    private static final String ARGSCONTENT6 = "%6$s";
    private static final String ARGSCONTENT7 = "%7$s";
    private static final String ARGSCONTENT8 = "%8$s";
    private static final String ARGSCONTENT9 = "%9$s";
    private static final String ARGSCONTENT10 = "%10$s";
    private static final String ARGSCONTENT11 = "%11$s";
    private static final String ARGSCONTENT12 = "%12$s";

    public static final String INITPASSWORD = "000000";


    public String getCheckCode="http://120.24.209.210/api/mobile.php?act=bindingcode&user_tel=13417185094&mode=test";

    public static final String BASEURL="http://120.24.209.210/api/mobile.php";

    public static final String TESTMODE = "test";


    public static final String TESTURL = HOST +"mobile.php?act=users&username=13417185094&password=123456&tel=13417185094";

    //获取验证码
    public static final String CHECKCODEURL = HOST + "mobile.php?act=bindingcode3&user_tel=" + ARGSCONTENT1+"&mode=test";

    //注册
    public static final String REGISTER = HOST + "mobile.php?act=users&username=" +
            ARGSCONTENT1+"&password="+INITPASSWORD+"&tel=" +ARGSCONTENT2+"&mode=test";

   /* // new 注册
    public static final String NEWREGISTER = HOST +
            "mobile.php?act=new_users&" +
            "username=" + ARGSCONTENT1+"&" +
            "password="+INITPASSWORD+"&"+
            "password="+INITPASSWORD+"&"+
            "tel=" +ARGSCONTENT2+"&"+
            "mode=test";

    public static final String NEWREGISTERCOMMEND = HOST +
    "mobile.php?act=new_user&username=" +
    ARGSCONTENT1+"&password="+INITPASSWORD
    +"&tel=" +ARGSCONTENT2
    +"&mode=test"
    + "yqm=" + ARGSCONTENT3;*/

    public static final String NEWREGISTER = HOST +
            "mobile.php?act=new_users" + "&"
            +"username=" + ARGSCONTENT1 + "&"
            +"password=" + ARGSCONTENT2 + "&"
            +"tel=" + ARGSCONTENT3 + "&"
            +"mac=" + ARGSCONTENT4 + "&"
            +"s_ctype=" + ARGSCONTENT5 + "&"
            +"mode=test";

    public static final String NEWREGISTERCOMMEND = HOST +
            "mobile.php?act=new_users" +"&"
            +"username=" + ARGSCONTENT1 +"&"
            +"password=" + ARGSCONTENT2 + "&"
            +"tel=" + ARGSCONTENT3 + "&"
            +"yqm="+ARGSCONTENT4 +"&"
            +"mac="+ARGSCONTENT5 + "&"
            +"s_ctype=" +  ARGSCONTENT6 +"&"
            +"mode=test";


    //有邀请码的注册
    public static final String REGISTERRECOMMEND = HOST + "mobile.php?act=users&username=" +
            ARGSCONTENT1+"&password="+INITPASSWORD+"&tel=" +ARGSCONTENT2+"&mode=test" + "yqm=" + ARGSCONTENT3;

    //登录 example:  http://120.24.209.210/api/mobile.php?act=loginusr&mode=test&user=shishi&password=123456
    /*public static final String LOGIN = HOST + "mobile.php?act=loginusr&mode=test&user="+ARGSCONTENT1 +
                                "&password=" +ARGSCONTENT2;*/
    public static final String LOGIN = HOST
            + "mobile.php?act=loginusr&mode=test&user="+ARGSCONTENT1 +
            "&password=" +ARGSCONTENT2 + "&"
            +"mac=" + ARGSCONTENT3;

    //##############################################################################################
    /*
        收货地址管理: 我的地址管理, 地址新增/修改, 地址删除
     */
    public static final String ADDRESSLIST = HOST +
            "mobile.php?act=myaddressmgn&user_id=" + ARGSCONTENT1 + "&mode=test";

    public static final String ADDADDRESS = HOST
            + "mobile.php?act=newaddress&" + MODE + "&"
            + "user_id="  + ARGSCONTENT1 + "&"
            + "&address=" +ARGSCONTENT2 +"&"
            + "consignee=" + ARGSCONTENT3+ "&"
            + "mobile=" + ARGSCONTENT4 +"&"
            + "province=" + ARGSCONTENT5 + "&"
            + "city=" + ARGSCONTENT6 + "&"
            + "district=" + ARGSCONTENT7 + "&"
            + "default=" +ARGSCONTENT8;

    public static final String MODIFYADDRESS = HOST
            + "mobile.php?act=newaddress&" + MODE + "&"
            + "user_id=" +ARGSCONTENT1 +"&"
            + "address_id=" + ARGSCONTENT2 + "&"
            + "address=" + ARGSCONTENT3 +"&"
            + "consignee=" + ARGSCONTENT4 +"&"
            + "mobile=" + ARGSCONTENT5 + "&"
            + "province=" + ARGSCONTENT6 + "&"
            + "city=" + ARGSCONTENT7 + "&"
            + "district=" + ARGSCONTENT8 + "&"
            + "default=" + ARGSCONTENT9;

    public static final String DELADDRESS = HOST
            + "mobile.php?act=deladdress&" + MODE + "&"
            + "id=" +ARGSCONTENT1 + "&"
            +"user_id=" + ARGSCONTENT2;
    //##############################################################################################



    //地址Id 获取转为从本地xml文件 ecs_region1.xml
    //##############################################################################################
    /* 地址id  */

    public static final String REGION = HOST
            + "mobile.php?act=regions";

    public static final String REGIONLIST = HOST
            + "mobile.php?act=regions" + "&"
            + "region_id=" + ARGSCONTENT1;
    //##############################################################################################
/*
    //商品分类  goods category #######################
    public static final String GOODS_LIST = HOST
            + "mobile.php?act=listallcats" +"&"
            + "city=" + ARGSCONTENT1 + "&"
            + "ctype=" + ARGSCONTENT2;*/
    //商品分类
    public static final String GOODS_LIST = HOST
        + "mobile.php?act=listallcats" +"&"
        + "city=" + ARGSCONTENT1 + "&"
        + "ctype=" + ARGSCONTENT2 + "&"
        + "version=" + ARGSCONTENT3+ "&"
        + "user_id="+ARGSCONTENT4;


    //http://120.25.224.250/api/mobile.php?act=goodsdesc&goods_id=463&mode=test&city=2
    public static final String GOODS_DETAIL_ITEM  = HOST
            +"mobile.php?act=goodsdesc" + "&" +
            "goods_id=" + ARGSCONTENT1 + "&" +
            "mode=test" +"&" +
            "city=" + ARGSCONTENT2 + "&" +
            "ctype=" + ARGSCONTENT3 + "&" +
            "user_id="+ARGSCONTENT4;

    //## 删除购物车商品 ########http://120.25.224.250/api/mobile.php?act=delbuycart&rec_id=91&mode=test
    public static final String CAR_GOODS_DEL = HOST
            +"mobile.php?act=delbuycart" + "&"
            +"rec_id=" + ARGSCONTENT1 + "&"
            +"mode=test";

    //添加商品
    public static final String ADD_GOODS = HOST
            +"mobile.php?act=addbuycart&mode=test" + "&"
            +"userid="+ARGSCONTENT1 + "&"
            +"goods_id=" +ARGSCONTENT2 +"&"
            + "number=" +ARGSCONTENT3;

    //更新购物车内商品
    public static final String EDIT_GOODS = HOST
            +"mobile.php?act=edltbuycart&mode=test" + "&"
            +"rec_id=" +ARGSCONTENT1 + "&"
            +"user_id=" + ARGSCONTENT2 + "&"
            +"new_number=" + ARGSCONTENT3;

    //清空购物车
    public static final String GOODS_SHOPPING_CAR_EMPTY = HOST
            +"mobile.php?act=emptycart" + "&"
            +"user_id=" + ARGSCONTENT1;

    //购物车列表
    //http://120.25.224.250/api/mobile.php?act=buylistcart&user_id=10&mode=test
    public static final String GET_CAR_LIST = HOST
            +"mobile.php?act=buylistcart" + "&"
            +"user_id=" + ARGSCONTENT1 +"&"
            +"mode=test";

    //生成订单
    public static final String PRODUCE_ORDER = HOST
            + "mobile.php?act=generateorders" + "&"
            + "user_id=" +ARGSCONTENT1 + "&"
            + "new_number=" + ARGSCONTENT2 + "&"
            + "mode=test";
    //确认订单
    public static final String CONFIRM_ORDER = HOST +
            "mobile.php?act=orderconfirmation" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"address_id=" + ARGSCONTENT2 +"&"
            +"pay_id=" + ARGSCONTENT3 + "&"
            +"postscript="+ARGSCONTENT4 + "&"
            +"integral=" + ARGSCONTENT5 + "&"
            +"display=" + ARGSCONTENT6;

    //商品热搜
    public static final String HOT_SEARCH = HOST +
            "mobile.php?act=hotsearch";

    //商品联想搜索
    public static final String GOODS_THINK_LIST = HOST +
            "mobile.php?act=findgoodsprompt" + "&"
            +"mode=test" + "&"
            +"goodsname=" + ARGSCONTENT1 + "&"
            +"ctype=" + ARGSCONTENT2 + "&"
            +"display=" + ARGSCONTENT3 + "&"
            + "user_id="+ARGSCONTENT4;

    //商品搜索
    //http://120.25.224.250/api/mobile.php?act=findgoods&mode=test&user_id=143&
    // goodsname=%E8%BD%AE%E8%83%8E&ctype=2
    public static final String GOODS_SEARCH_LIST = HOST +
            "mobile.php?act=findgoods&mode=test" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"goodsname=" +ARGSCONTENT2 + "&"
            +"ctype=" + ARGSCONTENT3 + "&"
            +"display=" + ARGSCONTENT4;


    //首页商品轮播广告
    public static final String GOODS_HOME_ADVER = HOST +
            "mobile.php?act=carouselnew&mode=test" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"city=" + ARGSCONTENT2 +"&"
            +"display=" + ARGSCONTENT3;

    //版本更新
    public static final String CHECKVERSION = HOST +
            "mobile.php?act=appversion&mode=test" + "&"
            +"andios=1" + "&"
            +"version=" + ARGSCONTENT1;

    //每日签到  http://120.25.224.250/api/mobile.php?act=daily_sign_in&mode=test&user_id=15&platform=1
    public static final String DAILY_SIGN_IN = HOST +
            "mobile.php?act=daily_sign_in&mode=test" + "&"
            +"user_id=" + ARGSCONTENT1 + "&" +
            "qp=" + ARGSCONTENT2 +"&" +
             "platform=1";
    //获取全部订单
    //http://120.25.224.250/api/mobile.php?act=mysalelist&user_id=10&mode=test
    public static final String GET_WHOLE_ORDER = HOST +
            "mobile.php?act=mysalelist" + "&" +
            "user_id="+ ARGSCONTENT1 + "&" +
             "mode=test";
    //获取订单详情
    //http://120.25.224.250/api/mobile.php?act=mysaledesc&mode=test&user_id=10&order_id=36
    public static final String GET_ORDER_DETAIL = HOST +
            "mobile.php?act=mysaledesc&mode=test" + "&" +
            "user_id=" +ARGSCONTENT1 + "&" +
            "order_id=" + ARGSCONTENT2;
    //取消订单
    //http://120.25.224.250/api/mobile.php?act=cancelorder&mode=test&user_id=139&order_id=28
    public static final String CANCEL_ORDER = HOST +
            "mobile.php?act=cancelorder&mode=test" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"order_id=" + ARGSCONTENT2;

    //产品秒杀
    //http://120.25.224.250/api/mobile.php?act=classify_goods_new&mode=test&ctype=2
    public static final String SECOND_KILL_GOODS = HOST +
            "mobile.php?act=classify_goods_new&mode=test" +"&"
            +"mode=test" +"&"
            +"ctype=" + ARGSCONTENT1 + "&"
            + "user_id="+ARGSCONTENT2;


    //产品秒杀广告
    //http://120.25.224.250/api/mobile.php?act=productspike&mode=test&ctype=0&display=0&city=1
    public static final String SECOND_KILL_ADVERT = HOST +
            "mobile.php?act=productspike&mode=test" + "&"
            +"ctype=" +ARGSCONTENT1 + "&"
            +"display=" + ARGSCONTENT2 + "&"
            +"city=" +ARGSCONTENT3+"&"
            + "user_id="+ARGSCONTENT4;

    //我的客户服务列表  今天 1, 昨天  2.
    //http://120.25.224.250/api/mobile.php?act=subordinateorder&user_id=2&mode=test&date_field=1
    public static final String MINE_SERVICE_ORDER_LIST = HOST +
            "mobile.php?act=subordinateorder" + "&"
            +"mode=test" +"&"
            +"user_id=" +ARGSCONTENT1 +"&"
            +"date_field=" + ARGSCONTENT2;

    public static final String MINE_SERVICE_TOTAL_SERVICE_ORDER_LIST = HOST +
            "mobile.php?act=subordinateorder" + "&"
            +"mode=test" +"&"
            +"user_id=" +ARGSCONTENT1 + "&"
            +"date_field=" +ARGSCONTENT2 + "&"
            +"page=" +ARGSCONTENT3 + "&"
            +"page_size=" +ARGSCONTENT4;

    //服务商下级会员列表
    public static final String MINE_SERVICE_PEOPLE_LIST = HOST +
            "mobile.php?act=suppliersubordinate&mode=test" +"&"
            +"user_id=" + ARGSCONTENT1;

    //服务商下级会员利润
    public static final String MINE_SERVICE_COST = HOST +
            "mobile.php?act=orderprofit" + "&"
            +"user_id=" + ARGSCONTENT1 +"&"
            +"date_field=" + ARGSCONTENT2 + "&"
            +"mode=test";
    public static final String NEWGOODS = HOST
            +"mobile.php?act=listnewgoods" + "&"
            +"mode=test" + "&"
            +"display=" + ARGSCONTENT1 + "&"
            +"ctype=" + ARGSCONTENT2 + "&"
            +"page=" + ARGSCONTENT3 + "&"
            +"page_size=" + ARGSCONTENT4+"&"
            + "user_id="+ARGSCONTENT5;

    //http://120.25.224.250/api/mobile.php?act=get_users&user_id=146&mode=test
    public static final String GETUSER = HOST
            +"mobile.php?act=get_users" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"mode=test";

    //代注册
    /*public static final String PROXYREGISTER = HOST +
                    "mobile.php?act=new_users" + "&"
                    +"username=" + ARGSCONTENT1 + "&"
                    +"password=" + ARGSCONTENT2 + "&"
                    +"tel=" + ARGSCONTENT3 + "&"
                    +"mac=" + ARGSCONTENT4 + "&"
                    +"s_ctype=" + ARGSCONTENT5 + "&"
                    +"salesman=" + ARGSCONTENT6 + "&"
                    +"mode=test";*/
    //代注册
    public static final String PROXYREGISTER = HOST +
                    "mobile.php?act=new_users" + "&"
                    +"username=" + ARGSCONTENT1 + "&"
                    +"password=" + ARGSCONTENT2 + "&"
                    +"tel=" + ARGSCONTENT3 + "&"
                    +"mac=" + ARGSCONTENT4 + "&"
                    +"s_ctype=" + ARGSCONTENT5 + "&"
                    +"salesman=" + ARGSCONTENT6 + "&"
                    +"mode=test" + "&"
                    +"type=" + ARGSCONTENT7 + "&"
                    +"province=" + ARGSCONTENT8 + "&"
                    +"city=" + ARGSCONTENT9 + "&"
                    +"district=" + ARGSCONTENT10 + "&"
                    +"address=" + ARGSCONTENT11;



    //删除关注
    public static final String DELETEATTENTION = HOST
            +"mobile.php?act=delete_collection&mode=test" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"rec_id=" + ARGSCONTENT2;

    //我的关注
    public static final String MINEATTENTION = HOST
            +"mobile.php?act=collection_list&mode=test"  + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"ctype=" + ARGSCONTENT2;


    //修改订单商品数量
    public static final String FIXORDERNUMBER = HOST
            +"mobile.php?act=changeordernew" +"&"
            +"mode=test" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"order_id=" + ARGSCONTENT2 + "&"
            +"new_number=" + ARGSCONTENT3;

    //删除订单商品
    public static final String DELORDERGOODS = HOST
            +"mobile.php?act=deleteordergoods" + "&"
            +"mode=test" + "&"
            +"order_id=" + ARGSCONTENT1 + "&"
            +"goods_id=" +  ARGSCONTENT2;

    public static final String CATEGORYGOODS = HOST
            +"mobile.php?act=listcatgoods&cat_id=";

    public static final String HOME_ADV_TEST = HOST
            +"mobile.php?act=new_index";

    public static final String HOME_ADV_ARRAY = HOST
            +"mobile.php?act=new_index&mode=test"+"&"
            +"display=" + ARGSCONTENT1 + "&"
            +"user_id="+ ARGSCONTENT2;

    public static final String WXAPI_GET_CODE =
            "https://api.weixin.qq.com/sns/oauth2/access_token?" +
            "appid=" + ARGSCONTENT1 + "&" +
            "secret=" + ARGSCONTENT2 + "&" +
            "code=" + ARGSCONTENT3+ "&"+
            "grant_type=authorization_code";


    public static final String WX_OPENID_CHECK = HOST
            +"mobile.php?act=loginusr" + "&"
            +"openid="+ ARGSCONTENT1 + "&"
            +"mac="+ARGSCONTENT2;

    public static final String QQ_OPENID_CHECK = HOST
            +"mobile.php?act=loginusr" + "&"
            +"openid="+ ARGSCONTENT1 + "&"
            +"mac="+ARGSCONTENT2 + "&"
            +"type="+ARGSCONTENT3;

    //微信绑定openId本地账号
    public static final String WX_OPENID_BIND_ACCOUNT = HOST
            +"mobile.php?act=bind_by_openid";

    public static final String WX_OPENID_REGISTER = HOST
            +"mobile.php?act=reg_by_openid" + "&"
            +"openid=" + ARGSCONTENT1 + "&"
            +"mac="+ARGSCONTENT2 + "&"
            +"phone=" + ARGSCONTENT3 + "&"
            +"username=" + ARGSCONTENT4;

    //根据微信openId获取用户个人信息
    public static final String WX_USER_INFO =
            "https://api.weixin.qq.com/sns/userinfo?" +
                    "access_token=" + ARGSCONTENT1 + "&" +
                    "openid=" + ARGSCONTENT2;

    //生成预支付订单
    public static final String WX_BEFOREHAND_PAY =
            "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String TOGETHER_GROUP_LINK = HOST +
            "mobile.php?act=new_teambuying&mode=test" + "&" +
                    "user_id=" + ARGSCONTENT1;

    //首页特价
    public static final String SPECIAL_PRICE = HOST
            +"mobile.php?act=special_zone" + "&"
            +"user_id=" + ARGSCONTENT1;

    //首页品牌街
    public static final String BRAND_STREET = HOST
            +"mobile.php?act=brand_street";

    //我的团
    public static final String MINE_TUAN = HOST
            +"mobile.php?act=my_teambuying&mode=test" + "&"
            +"user_id=" + ARGSCONTENT1;
    //商品详情
    public static final String GOOD_DETAIL = HOST
             +"mobile.php?act=listcatgoods" + "&"
             +"mode=test" + "&"
             +"cat_id=" + ARGSCONTENT1 + "&"
             +"city=" + ARGSCONTENT2 + "&"
             +"ctype=" + ARGSCONTENT3 + "&"
             +"user_id=" + ARGSCONTENT4;

    //我的客户订单--工作人员
    public static final String MINE_WORK_CUSTOMER_ORDER = HOST
            +"mobile.php?act=order_of_salesmen_users" + "&"
            +"mode=test" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"date_field=" + ARGSCONTENT2 + "&"
            +"page=" + ARGSCONTENT3 + "&"
            +"page_size=" + ARGSCONTENT4;


    public static final String MINE_WORK_CUSTOMER_ORDER_DAY = HOST
            +"mobile.php?act=order_of_salesmen_users" + "&"
            +"mode=test" + "&"
            +"user_id=" + ARGSCONTENT1 + "&"
            +"date_field=" + ARGSCONTENT2;

    public static final String ABOUT_US = HOST
            +"mobile.php?act=about";

    //public static final String
    public static final String SCAN_QR_CODE = HOST
            +"mobile.php?act=scan_group_qrcode&"
            +"group_id=" + ARGSCONTENT1 + "&"
            +"user_id="+ARGSCONTENT2;

}
