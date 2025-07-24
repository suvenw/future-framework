package com.suven.framework.core.redis;

import com.suven.framework.core.redis.factory.RedisConfigurationSetting;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *d ate 创建时间: 2023-12-29
 * <pre>
 * description (说明): 通过配置实现缓存使用开关
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 *
 **/
public interface RedisSwitchControl {

        /**
         * 获取配置信息
         * @return RedisConfigurationSetting redis配置信息
         */
        RedisConfigurationSetting getRedisConfig();
        /**
         * 是否开启写入缓存 write
         * @return true/false 对或错
         */
        boolean isWrite();

        /**
         * 是否开启读取缓存 read
         * @return true/false 对或错
         */
        boolean isRead();

        /**
         * 是否开启 数据库数据同步到缓存 Read/Write
         * @return true/false 对或错
         */
        boolean isDaoCache();
    }