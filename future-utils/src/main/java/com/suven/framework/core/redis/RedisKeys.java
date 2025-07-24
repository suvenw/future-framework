package com.suven.framework.core.redis;

import com.suven.framework.core.redis.ext.RedisEx;

/**
 * redis的key数据库，聚合管理
 * @author jahe
 *
 */
@RedisEx
public interface RedisKeys  extends RedisConstants{




	/** 记录用户的accessToken   key： access_token:id  value：accessToken */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String AUTH_CODE="auth_code:";

	/** 记录用户的accessToken   key： access_token:id  value：accessToken */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String ACCESS_TOKEN="access_token:";


	/** 记录用户的refreshToken   key： refresh_token:id  value：refreshToken */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String REFRESH_TOKEN="refresh_token:";

	/** 记录用户的refreshToken   key： refresh_token:id  value：refreshToken */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String CLIENT_SECRET="client_secret:";

	/** 账号权限 */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String OAUTH_PERMISSION = "oauth_permission:";


	/** 验证服务的缓存临时前缀key */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String OAUTH_CODE = "oauth_code:";

	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String OAUTH_USER_ID_TOKEN_MAP = "oauth_user_id_token:";

	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String OAUTH_USER_REFRESH_TOKEN_MAP = "oauth_user_refresh_token:";


	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String USER_RANK_TREE_MAX_ID = "user_rank_tree_max_id:";


	/** mq 模块   key： mq_consumer:id  value：id */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String MQ_CONSUMER="mq_consumer:";

	/** im渠道 */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String IM_CHANNEL="im_channel:";

	/** 项目启用维护模式 */
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String OAUTH_MAINTAIN = "oauth_maintain:";

    /**登录后用户的渠道*/
    @RedisEx(redisKey=ONE_REDIS_GROUP)
    String CHANNEL_CODE = "channel_code:";

	/**碳账户渠道*/
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String CARBON_CHANNEL = "carbon_channel:";

	/**登录后用户的渠道*/
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String VIDEO_REPORT_UPDATE_KEY = "video_report_update:";


	/**激活码兑换*/
	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String USER_ACTIVATE_CODE_KEY = "user_activate_code:";

    /**登录后用户的渠道*/
    @RedisEx(redisKey=ONE_REDIS_GROUP)
    String CDN_RESULT_CACHE_KEY = "cdn_result_cache_key:";



	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String USER_LOGIN_TOKEN = "user_login_token:";

	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String USER_LOGIN_CHECK = "user_login_check:";

	@RedisEx(redisKey=ONE_REDIS_GROUP)
	String USER_LOGIN_ERROR_CHECK ="user_login_token_error:";

}


