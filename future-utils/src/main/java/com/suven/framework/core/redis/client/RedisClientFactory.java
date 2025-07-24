//package com.suven.framework.core.redis.client;
//
//import com.suven.framework.core.redis.RedisClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @author Joven.wang
// * @version V1.0
// *  <pre> org.jetbrains.idea.maven.indices.MavenClassSearchResult@8e413ae
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * @description : (说明) 基于RedisTemplate api方法 和自定义方法的具体功能实现的代理管理工厂 ,提供业务方使用
// *
// */
//@Configuration
//@ConditionalOnMissingBean(name = "redisTemplate")
//@ConditionalOnClass(RedisTemplate.class)
//public class RedisClientFactory implements FactoryBean<RedisClient>, InitializingBean, DisposableBean {
//    private Logger log = LoggerFactory.getLogger(RedisClientFactory.class);
//    private RedisTemplate<String, String> redisTemplate;
//    public RedisClientFactory(RedisTemplate<String, String> redisTemplate){
//        this.redisTemplate = redisTemplate;
//    }
//
//    /** 缓存各类方法名称和对应的方法,用于代码反映逻辑执行 **/
//    private Map<String,Method> templateMethodMap = new HashMap<>();
//    private Map<String,Method> operationsMethodMap = new HashMap<>();
//    private Map<String,Method> customizeMethodMap = new HashMap<>();
//    private RedisClient redisClient;
//
//
//    /** 初始化BeanFactory 的实现bean的方法**/
//    @Override
//    public void afterPropertiesSet() {
//        // 在属性设置后执行初始化逻辑
//        if (redisTemplate == null) {
//            throw new IllegalArgumentException("redisTemplate must be set");
//        }
//        // 使用 ProxyFactory 创建 RedisClient 的代理对象
//        creatProxyFactory();
//        // 将代理对象赋值到每个方法名称和方法的缓存中
//        converterMethods();
//    }
//
//    public RedisClient getObject() {
//        return redisClient;
//    }
//
//    public Class<?> getObjectType() {
//        return RedisClient.class;
//    }
//
//    public boolean isSingleton() {
//        return true;
//    }
//    @Override
//    public void destroy() {
//        // 在销毁 bean 之前执行清理逻辑
//        // 例如，关闭 Redis 连接等
//        if (redisClient != null) {
//            // 清理操作...
//        }
//    }
//    public RedisClient creatProxyFactory() {
//        //这里主要是创建接口对应的实例，便于注入到spring容器中
//        ProxyFactory<RedisClient> proxyFactory = new ProxyFactory<>(redisTemplate);
//        RedisClient proxy = proxyFactory.createProxy(RedisClient.class);
//        redisClient = proxy;
//        return proxy;
//    }
//
//
//    private void converterMethods(){
//        Method[] customizeMethods =  RedisPlusOperations.class.getDeclaredMethods();
//        Method[] valueMethods = ValueOperations.class.getDeclaredMethods();
//        Method[] redisMethods = org.springframework.data.redis.core.RedisOperations.class.getDeclaredMethods();
//        forEachMethod(customizeMethods,customizeMethodMap);
//        forEachMethod(valueMethods,operationsMethodMap);
//        forEachMethod(redisMethods,templateMethodMap);
//    }
//
//    private void forEachMethod( Method[] methods, Map<String,Method>  map){
//        for (Method method : methods ) {
//            map.put(method.getName(),method);
//        }
//
//    }
//
//    // ProxyFactory 内部类
//    public class ProxyFactory<T> extends DefaultRedisPlusOperations implements InvocationHandler {
//        public ProxyFactory(RedisTemplate<String ,String> redisTemplate){
//            super(redisTemplate);
//        }
//        public T createProxy(Class<T> interfaceClass) {
//            return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
//            Object result = null;
//            try  {
//                Method extMethod = customizeMethodMap.get(proxyMethod.getName());
//                if (Objects.nonNull(extMethod)){
//                    return extMethod.invoke(this, args);
//                }
//                Method valueMethod = operationsMethodMap.get(proxyMethod.getName());
//                if (Objects.nonNull(valueMethod)){
//                    result = proxyMethod.invoke(redisTemplate.opsForValue(), args);
//                }
//                Method templateMethod =  templateMethodMap.get(proxyMethod.getName());
//                if (Objects.nonNull(templateMethod)){
//                    result = proxyMethod.invoke(redisTemplate, args);
//                }
//
//            } catch (Exception e) {
//                log.info("redis template  不支持该功能,Redis Client Invoke Method Exception:{}",e);
//                throw e;
//            }
//            return result;
//
//        }
//    }
//}