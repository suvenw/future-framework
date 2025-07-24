package com.suven.framework.core.kafka;





/**
 * @Title: KafkaMessage.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) kafka Topic聚合管理
 * Copyright: (c) 2018 gc by https://www.suven.top
 */
public class KafkaTopic {
    //kafka分组，主营
    public final static String KAFKA_GROUPID = "hsz";

    //登录日志kafka的topic
    public final static String SYS_LOGIN_LOG = "OAUTH_SYS_LOGIN_LOG";

    //checkNewVersion接口发送kafka的topic
    public final static String CHANNEL_VERSION_LOG = "MARKET_CHANNEL_VERSION_LOG";

    //绑定体验设备接口发送kafka的topic
    public final static String BIND_TASTEPAD_USERPADLOG = "PAD_BIND_TASTEPAD_USERPAD_LOG";

    //绑定普通设备接口（破解）发送kafka的topic
    public final static String BIND_FREE_CRACKED_USERPADLOG = "PAD_BIND_FREECRACKED_USERPAD_LOG";

    //绑定体验设备记录用户设备操作日志接口发送kafka的topic
    public final static String BIND_TASTEPAD_LOGPAD = "PAD_BIND_TASTEPAD_LOG_PAD";

    //绑定普通设备接口（破解）记录用户设备操作日志接口发送kafka的topic
    public final static String BIND_FREE_CRACKED_LOGPAD = "PAD_BIND_FREECRACKED_LOG_PAD";

    //保存用户关闭广告图片日志（设备列表页调用）操作日志接口发送kafka的topic
    public final static String SAVE_AD_LOG_PICTURE = "RESOURCE_AD_LOG_PICTURE";
    
    //优惠卷日志的topic
    public final static String COUPON_LOG = "ASSETS_COUPON_LOG";

    //激活码日志
    public final static String ACTIATION_CODE_LOG = "ACTIVITY_ACTIVATION_CODE_LOG";

    //红豆日志
    public final static String RBC_LOG = "ASSETS_RBC_LOG";

    //积分日志
    public final static String SCORE_LOG = "ASSETS_SCORE_LOG";


}
