package com.suven.framework.core.proxy;

import com.suven.framework.core.redis.client.SpringRedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author Joven.wang
 * @version V1.0
 *  <pre> org.jetbrains.idea.maven.indices.MavenClassSearchResult@8e413ae
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @description : (说明) 基于RedisTemplate api方法 和自定义方法的具体功能实现的代理管理工厂 ,提供业务方使用
 *
 */

public class JdkProxyFactory<T> implements FactoryBean<T>, InitializingBean, DisposableBean {

    private Logger log = LoggerFactory.getLogger(JdkProxyFactory.class);

    private final Class<T> proxyTargetClass;
    private final ProxyMethodTarget proxyMethodTarget;
    private T proxyTarget;

    public JdkProxyFactory(Class<T> proxyTargetClass, ProxyMethodTarget proxyMethodTarget){
        this.proxyTargetClass = proxyTargetClass;
        this.proxyMethodTarget = proxyMethodTarget;
    }

    /** 初始化BeanFactory 的实现bean的方法**/
    @Override
    public void afterPropertiesSet() {
        // 在属性设置后执行初始化逻辑
        if (proxyTargetClass == null) {
            throw new IllegalArgumentException("proxyTargetHandler must be set");
        }
        // 使用 ProxyFactory 创建 RedisClient 的代理对象
        proxyTarget = createProxy();
        // 将代理对象赋值到每个方法名称和方法的缓存中
    }

    @Override
    public T getObject() {
        return proxyTarget;
    }

    @Override
    public Class<?> getObjectType() {
        return SpringRedisClient.class;
    }


    @Override
    public void destroy() {
        // 在销毁 bean 之前执行清理逻辑
        // 例如，关闭 Redis 连接等
        if (proxyTarget != null) {
            // 清理操作...
        }
    }
    public T createProxy() {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        ProxyFactoryHandler proxyHandler = new ProxyFactoryHandler(proxyMethodTarget);
        T proxy = newProxy(proxyTargetClass,proxyHandler);
        proxyTarget = proxy;
        return proxy;
    }

    private T newProxy(Class<T> interfaceType,InvocationHandler proxyFactoryHandler) {
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, proxyFactoryHandler);
    }

    // ProxyFactory 内部类
    public class ProxyFactoryHandler implements InvocationHandler {
        ProxyMethodTarget proxyTargetHandler;
        public ProxyFactoryHandler(ProxyMethodTarget proxyTargetHandler){
                this.proxyTargetHandler = proxyTargetHandler;
        }
        public Object getMethodTarget(Method proxyMethod){
            return proxyMethodTarget.getMethodTarget(proxyMethod);
        }

        @Override
        public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
            Object result = null;
            try  {
                Object proxyMethodTarget = getMethodTarget(proxyMethod);
                if (Objects.nonNull(proxyMethodTarget)){
                    return proxyMethod.invoke(proxyMethodTarget, args);
                }

            } catch (Exception e) {
                log.info("ProxyFactoryHandler  不支持该功能,{} Client Invoke Method Exception:{}",e);
                throw e;
            }
            return result;

        }
    }
}