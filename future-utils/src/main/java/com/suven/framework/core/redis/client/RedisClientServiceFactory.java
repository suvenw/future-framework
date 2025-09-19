package com.suven.framework.core.redis.client;

import com.suven.framework.core.proxy.JdkProxyFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings("ALL")
@Configuration
@ConditionalOnMissingBean(name = "redisTemplate")
@ConditionalOnClass(RedisTemplate.class)
public class RedisClientServiceFactory extends JdkProxyFactory<SpringRedisClient> implements InitializingBean, DisposableBean {

   private RedisTemplate redisTemplate;
    public RedisClientServiceFactory(RedisTemplate redisTemplate) {
        super(SpringRedisClient.class, new RedisProxyMethodTarget(redisTemplate));
        this.redisTemplate = redisTemplate;
    }



}
