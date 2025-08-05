package com.suven.framework.core.redis;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.redis.client.SpringRedisClient;
import com.suven.framework.core.redis.ext.ExpansionDeal;
import com.suven.framework.core.redis.factory.RedisConfigurationSetting;
import com.suven.framework.core.redis.factory.RedisConnectionStrategy;
import com.suven.framework.http.exception.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Objects;


/**
 * Title: RedisClusterTest.java
 * @author Joven.wang
 * date 2016年7月26日
 * @version V1.0
 * Description: TODO(说明)
 */

public abstract class AbstractRedisFactoryRouter implements RedisFactoryRouter,RedisSwitchControl {

	protected Logger log = LoggerFactory.getLogger(getClass());
	protected final  String REDIS_RESULT_DEFAULT_OK  = "OK";
	protected final  int REDIS_DEFAULT_TIME  = 3000;
	protected RedisConnectionStrategy redisConnection;
	protected RedisConfigurationSetting redisConfig;
	private RedisSwitchControl switcher;
	private RedisModelType modelType;


	public AbstractRedisFactoryRouter(RedisConnectionStrategy redisConnection, RedisConfigurationSetting redisConfig) {
		this.redisConnection = redisConnection;
		this.redisConfig = redisConfig;
		this.switcher  =  RedisClientSwitchControl.build(redisConfig);
		this.modelType  =  RedisClientModelType.build(redisConfig);
	}


	/**
	 * 执行种模式代理
	 *
	 * @param redisStrategyEnum
	 **/
	@Override
	public boolean execStrategy(RedisStrategyEnum redisStrategyEnum) {
		return true;
	}



	/**
	 * 获取 redis Client 端的相关配置文件的方法
	 *
	 * @return RedisConfigurationSetting 配置文件
	 */
	@Override
	public RedisConfigurationSetting getRedisConfig(){
		return redisConfig;
	}

	/**
	 * 是否开启写入缓存 write
	 *
	 * @return true/false 对或错
	 */
	@Override
	public boolean isWrite() {
		return switcher.isWrite();
	}

	/**
	 * 是否开启读取缓存 read
	 *
	 * @return true/false 对或错
	 */
	@Override
	public boolean isRead() {
		return switcher.isRead();
	}

	/**
	 * 是否开启 数据库数据同步到缓存 Read/Write
	 *
	 * @return true/false 对或错
	 */
	@Override
	public boolean isDaoCache() {
		return switcher.isDaoCache();
	}

	/**
	 * @return
	 */
	@Override
	public RedisModelType modelType() {
		return modelType;
	}
	@Override
	public RedisSwitchControl switcher() {
		return switcher;
	}

	/**
	 *
	 * 1.如果是单个聚群模式,按RedisSentinelInterface返回
	 * 2.如果是多组聚群模式,即返回通过 redisCacheKey 做路由查找对应的分组;
	 * 3.如果是单结点或哨兵模式,按RedisSentinelInterface返回
	 * @param redisCacheKey
	 * @return
	 */

	public SpringRedisClient getRedisClientByKey(Object redisCacheKey) {
		return null;
	}

	/**
	 * 1.如果是单个聚群模式,按RedisSentinelInterface返回
	 * 2.如果是多组聚群模式,即返回通过 redisCacheKey 做路由查找对应的分组;
	 * 3.如果是单结点或哨兵模式,按RedisSentinelInterface返回
	 *
	 * @param redisCacheKey 通过key获取对象的redis组
	 * @return
	 */
	@Override
	public RedisConnectionFactory getRedisFactoryByKey(Object redisCacheKey) {
		int redisNode = -1;
		if(this.modelType().isClusterGroup()){
			String redisKey = null;
			if(redisCacheKey instanceof String){
				redisKey = ((String) redisCacheKey);
			}else if(redisCacheKey instanceof byte[]) {
				redisKey = new String((byte[]) redisCacheKey);
			}
			if (Objects.isNull(redisKey)){
				throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_REDIS_KEY_ERROR);
			}
			ExpansionDeal deal = this.getExpansionDeal();
			if (Objects.isNull(deal)){
				throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_REDIS_KEY_CLUSTER_GROUP);
			}
			redisNode =  deal.getRedisInstance(redisKey);
			//todo 返回 redisNode 对应的组的 redisConnection
			return redisConnection.createConnectionFactory();
		}
		return redisConnection.createConnectionFactory();
	}

	public abstract ExpansionDeal getExpansionDeal();


	protected boolean isOK(String result){
		return REDIS_RESULT_DEFAULT_OK.equals(result);
	}

	protected boolean isOK(Long result){
		return (null != result && result == 1) ? true : false;
	}

	protected boolean isOK(Boolean result){
		return Objects.equals(result,Boolean.TRUE);
	}
	public String isOK(){
		return REDIS_RESULT_DEFAULT_OK;
	}
	public int defaultTime(){
		return this.REDIS_DEFAULT_TIME;
	}

}
