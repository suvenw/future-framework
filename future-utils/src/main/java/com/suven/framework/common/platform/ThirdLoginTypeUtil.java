package com.suven.framework.common.platform;

/**
 * @ClassName:
 * Description:第三方登录的类型
 * @Author liulu
 * @Date 2018/5/23 17:49
 * Copyright: (c) 2018 gc by https://www.suven.top
 * @Version : 1.0.0
 */
public class ThirdLoginTypeUtil {

    /***获取ACCESS_TOKEN**/
    public static String WEIXIN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /***REFRESH_TOKEN**/
    public static String WEIXIN_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    public static String WEIXIN_GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    /**微博登录地址*/
    public static String WEIBO_GET_USERINFO_URL = "https://api.weibo.com/2/users/show.json?access_token=ACCESS_TOKEN&uid=UID";
    /**qq登录地址*/
    public static String QQ_GET_USERINFO_URL = "https://graph.qq.com/user/get_user_info?access_token=ACCESS_TOKEN&oauth_consumer_key=APPID&openid=OPENID";

}
