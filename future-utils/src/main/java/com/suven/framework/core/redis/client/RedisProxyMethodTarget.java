package com.suven.framework.core.redis.client;

import com.suven.framework.core.proxy.ProxyMethodTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//@Component
public class RedisProxyMethodTarget implements ProxyMethodTarget {


    Logger log = LoggerFactory.getLogger(RedisProxyMethodTarget.class);
    private final Map<String, Object> methodCachMap = new HashMap<>();
    /** 缓存各类方法名称和对应的方法,用于代码反映逻辑执行 **/
    private Map<String,Method> templateMethodMap = new HashMap<>();
    private Map<String,Method> operationsMethodMap = new HashMap<>();
    private Map<String,Method> customizeMethodMap = new HashMap<>();

    private RedisTemplate redisTemplate;
    // 构造方法私有化，防止外部创建实例
    public RedisProxyMethodTarget(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    /**
     * @param proxyMethod 对象方法
     * @return
     */
    @Override
    public Object getMethodTarget(Method proxyMethod) {
        Object result = null;
        try  {
            Method extMethod = customizeMethodMap.get(proxyMethod.getName());
            if (Objects.nonNull(extMethod)){
                result =  new DefaultRedisPlusOperations(this.redisTemplate);
            }
            Method valueMethod = operationsMethodMap.get(proxyMethod.getName());
            if (Objects.nonNull(valueMethod)){
                result = redisTemplate.opsForValue();
            }
            Method templateMethod =  templateMethodMap.get(proxyMethod.getName());
            if (Objects.nonNull(templateMethod)){
                result = redisTemplate;
            }

        } catch (Exception e) {
            log.info("redis template  不支持该功能,Redis Client Invoke Method Exception:{}",e);
            throw e;
        }
        return result;
    }
}
