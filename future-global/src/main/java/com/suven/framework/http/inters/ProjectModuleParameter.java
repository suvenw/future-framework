package com.suven.framework.http.inters;



/**
 * @ClassName PlatformEnum
 * @Author suven.wang
 * @Description //TODO ${END}$
 * @CreateDate 2018-11-15  12:45
 * @WeeK 星期四
 * @Version v2.0
 **/

public interface ProjectModuleParameter {

    final int  SERVER_HTTP_TYPE              =  1;
    final int  SERVER_RPC_TYPE               =  2;

    final String  MODULE_ALL                    =  "ALL";

    final String  CONFIG_HTTP                   =  ".http.";
    final  String  CONFIG_RPC                    =  ".rpc.";

    final  String  SERVER_HTTP                   =  "consumers-";
    final  String  SERVER_RPC                    =   "providers-";
    final  String  MODULE_RPC                    =  "rpc-";
    final  String  MODULE_HTTP                   =  "http-";


    final  int     PORT_HTTP_DEFAULT             = 8080;
    final  String  MODULE_DEFAULT                = "DEFAULT";

    /** 用户 **/
    String  MODULE_NAME_USER              = "user" ;
    int     PORT_HTTP_USER                = 9010;
    String  MODULE_HTTP_USER              =  MODULE_HTTP + MODULE_NAME_USER;

    int     PORT_RPC_USER                 = 19010;
    String  MODULE_RPC_USER               =  MODULE_RPC + MODULE_NAME_USER;

    /** 验证 **/
    String  MODULE_NAME_OAUTH             = "oauth" ;
    int     PORT_HTTP_OAUTH               = 9020;
    String  MODULE_HTTP_OAUTH           =  MODULE_HTTP + MODULE_NAME_OAUTH;

    int     PORT_RPC_OAUTH                = 19020;
    String  MODULE_RPC_OAUTH              = MODULE_RPC + MODULE_NAME_OAUTH;

    /** 上传下载 **/
    String  MODULE_NAME_FILE            = "file" ;
    int     PORT_HTTP_FILE              = 9030;
    String  MODULE_HTTP_FILE            =  MODULE_HTTP + MODULE_NAME_FILE;

    int     PORT_RPC_FILE               = 19030;
    String  MODULE_RPC_FILE             =  MODULE_RPC + MODULE_NAME_FILE;

    /** 资源配置 **/
    String  MODULE_NAME_RESOURCE          = "resource" ;
    int     PORT_HTTP_RESOURCE            = 9040;
    String  MODULE_HTTP_RESOURCE          =  MODULE_HTTP + MODULE_NAME_RESOURCE ;

    int     PORT_RPC_RESOURCE             = 19040;
    String  MODULE_RPC_RESOURCE           =  MODULE_RPC + MODULE_NAME_RESOURCE ;

    /** 环境配置 **/
    String  MODULE_NAME_CONFIG            = "config" ;
    int     PORT_HTTP_CONFIG              = 9050;
    String  MODULE_HTTP_CONFIG            = MODULE_HTTP + MODULE_NAME_CONFIG ;

    int     PORT_RPC_CONFIG               = 19050;
    String  MODULE_RPC_CONFIG             =  MODULE_RPC + MODULE_NAME_CONFIG;

    /** 视频 **/
    String  MODULE_NAME_VIDEO              = "video" ;
    int     PORT_HTTP_VIDEO                = 9060;
    String  MODULE_HTTP_VIDEO              =  MODULE_HTTP + MODULE_NAME_VIDEO ;

    int     PORT_RPC_VIDEO                 = 19060;
    String  MODULE_RPC_VIDEO               =  MODULE_RPC + MODULE_NAME_VIDEO;

    /** 订单 **/
    String  MODULE_NAME_ORDER              = "order" ;
    int     PORT_HTTP_ORDER                = 9070;
    String  MODULE_HTTP_ORDER              =  MODULE_HTTP + MODULE_NAME_ORDER ;

    int     PORT_RPC_ORDER                 = 19070;
    String  MODULE_RPC_ORDER               =  MODULE_RPC + MODULE_NAME_ORDER;

    /** 支付 **/
    String  MODULE_NAME_PAY                = "pay" ;
    int     PORT_HTTP_PAY                  = 9080;
    String  MODULE_HTTP_PAY                =  MODULE_HTTP + MODULE_NAME_PAY ;

    int     PORT_RPC_PAY                  = 19080;
    String  MODULE_RPC_PAY                 =   MODULE_RPC + MODULE_NAME_PAY;

    /** 资产,钱包 **/
    String  MODULE_NAME_ASSETS              = "assets" ;
    int     PORT_HTTP_ASSETS                =  9090;
    String  MODULE_HTTP_ASSETS              =  MODULE_HTTP + MODULE_NAME_ASSETS;

    int     PORT_RPC_ASSETS                 = 19090;
    String  MODULE_RPC_ASSETS               =   MODULE_RPC + MODULE_NAME_ASSETS;

    /** 活动 **/
    String  MODULE_NAME_ACTIVITY             = "activity" ;
    int     PORT_HTTP_ACTIVITY               = 9100;
    String  MODULE_HTTP_ACTIVITY             =  MODULE_HTTP + MODULE_NAME_ACTIVITY;

    int     PORT_RPC_ACTIVITY               = 19100;
    String  MODULE_RPC_ACTIVITY             =  MODULE_RPC +  MODULE_NAME_ACTIVITY;

    /** 礼物 **/
    String  MODULE_NAME_GIFT                = "gift" ;
    int     PORT_HTTP_GIFT                  = 9110;
    String  MODULE_HTTP_GIFT                =  MODULE_HTTP + MODULE_NAME_GIFT;

    int     PORT_RPC_GIFT                   = 19110;
    String  MODULE_RPC_GIFT                 =  MODULE_RPC +  MODULE_NAME_GIFT;


    /** 商品 **/
    String  MODULE_NAME_GOODS                = "gift" ;
    int     PORT_HTTP_GOODS                  = 9120;
    String  MODULE_HTTP_GOODS                =  MODULE_HTTP + MODULE_NAME_GIFT;

    int     PORT_RPC_GOODS                   = 19120;
    String  MODULE_RPC_GOODS                 =  MODULE_RPC +  MODULE_NAME_GIFT;




    /** 视频数据同步 **/
    String  MODULE_NAME_MEDIA              = "media" ;
    int    PORT_HTTP_MEDIA                   = 9130;
    String MODULE_HTTP_MEDIA                  = MODULE_HTTP  +  MODULE_NAME_MEDIA;

    int     PORT_RPC_MEDIA                   = 19130;
    String  MODULE_RPC_MEDIA                 =  MODULE_RPC + MODULE_NAME_MEDIA;



    /** 数据统计 **/
    String  MODULE_NAME_STATISTIC             = "statistic" ;
    int    PORT_HTTP_STATISTIC              = 9140;
    String MODULE_HTTP_STATISTIC             = MODULE_HTTP  +  MODULE_NAME_STATISTIC;

    /** 后台管理 **/
    String  MODULE_NAME_SYSTEM             = "admin" ;
    int    PORT_HTTP_SYSTEM              = 9900;
    String MODULE_HTTP_SYSTEM             = MODULE_HTTP  +  MODULE_NAME_SYSTEM;




}
