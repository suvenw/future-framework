package com.suven.framework.core.redis.client;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author Joven.wang
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @description : (说明) RedisClient 统一实现 api,提供业务方使用
 *
 */

public  interface SpringRedisClient extends RedisPlusOperations, ValueOperations<String,String>, RedisOperations<String,String> {


}
