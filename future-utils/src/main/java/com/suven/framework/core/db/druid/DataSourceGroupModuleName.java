package com.suven.framework.core.db.druid;


/**
 * @Title: DataSourceGroupModuleName.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) DruidDataSource 用于定义动态加载业务分组的模块数据库名称的参数定义接口;
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public interface DataSourceGroupModuleName {

    final String MODULE_NAME_SYSTEM = "sys";//后台管理模块数据库
    final String MODULE_NAME_USER = "user";
    final String MODULE_NAME_OAUTH  = "oauth" ;//"验证模块数据库" 
    final String MODULE_NAME_ASSETS  = "assets" ;//"用户资产模块数据库" 
    final String MODULE_NAME_PAY  = "pay" ;//"支付模块数据库" 
    final String MODULE_NAME_ORDER  = "order" ;//"订单模块数据库" 
    final String MODULE_NAME_GIFT  = "gift" ;//"礼物模块数据库" 
    final String MODULE_NAME_GOODS  = "goods" ;//"商品模块数据库" 
    final String MODULE_NAME_RESOURCE  = "resource" ;//"资源模块数据库" 
    final String MODULE_NAME_CONFIG  = "config" ;//"配置模块数据库" 
    final String MODULE_NAME_VIDEO  = "video" ;//"视频模块数据库" 
    final String MODULE_NAME_ACTIVITY  = "activity" ;//"活动模块数据库" 
    final String MODULE_NAME_MARKET  = "market" ;//"市场模块数据库" 
    final String MODULE_NAME_LOG  = "log" ;//"日志模块数据库" 
    final String MODULE_NAME_DEPLOY  = "deploy" ;//"开发模块数据库" 
    final String MODULE_NAME_PAD  = "pad" ;//"设备块数据库" 
    final String MODULE_NAME_SPIDER  = "spider" ;//"爬虫模块数据库" 
    final String MODULE_NAME_MEDIA  = "media" ;//"媒体模块数据库" 
    final String MODULE_NAME_STATISTIC  = "statistic" ;//"采摘模块数据库"
    final String MODULE_NAME_SHORTER  = "shorter" ;//"采摘模块数据库"
}
