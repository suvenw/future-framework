package com.suven.framework.core.redis.client;

import com.suven.framework.core.proxy.CglibProxyFactory;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings("ALL")
@Configuration
@ConditionalOnMissingBean(name = "redisTemplate")
@ConditionalOnClass(RedisTemplate.class)
public class RedisClientServiceFactory extends CglibProxyFactory<SpringRedisClient> implements InitializingBean, DisposableBean {

   private RedisTemplate redisTemplate;
    public RedisClientServiceFactory(RedisTemplate redisTemplate) {
        super(SpringRedisClient.class, new RedisProxyMethodTarget(redisTemplate));
        this.redisTemplate = redisTemplate;
    }



    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     *                   but not rethrown to allow other beans to release their resources as well.
     */
    @Override
    public void destroy() throws Exception {

    }
}
