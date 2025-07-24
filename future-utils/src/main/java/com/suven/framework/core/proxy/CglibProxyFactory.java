package com.suven.framework.core.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 使用 CGLIB 创建代理的工厂类
 * @param <T> 代理的目标类类型
 */
public class CglibProxyFactory<T> implements MethodInterceptor, InitializingBean {

    private final Class<T> targetClass;
    private final ProxyMethodTarget proxyMethodTarget;
    private T proxyInstance;

    public CglibProxyFactory(Class<T> targetClass, ProxyMethodTarget proxyMethodTarget) {
        this.targetClass = targetClass;
        this.proxyMethodTarget = proxyMethodTarget;
    }

    /**
     * 创建代理实例
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public T createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    /**
     * 获取代理实例
     * @return 代理对象
     */
    public T getProxyInstance() {
        return proxyInstance;
    }

    /**
     * 在属性设置完成后自动创建代理
     */
    @Override
    public void afterPropertiesSet() {
        this.proxyInstance = createProxy();
    }

    /**
     * 拦截方法调用
     */
    @Override
    public Object intercept(Object obj, Method methodProxy, Object[] args, MethodProxy proxy) throws Throwable {
        Object result;
        Object target = proxyMethodTarget.getMethodTarget(methodProxy);
        if (Objects.nonNull(target)){
            result =  methodProxy.invoke(target, args);
            return result;
        }
        result = this.intercept(obj, methodProxy, args, proxy);
       return result ;
    }
}
