package com.suven.framework.core.proxy;

import com.suven.framework.core.proxy.test.APlayer1;
import com.suven.framework.core.proxy.test.APlayer2;
import com.suven.framework.core.proxy.test.APlayer3;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 * date  创建时间: 2024-02-19
 * <pre>
 * description (说明):
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 **/
public class ProxyTargetCache implements ProxyMethodTarget, Serializable {

    private static class SingletonProxyTarget {
        private static final ProxyTargetCache INSTANCE = new ProxyTargetCache();

    }
    private final Map<String, Object> methodCachMap = new HashMap<>();

    // 构造方法私有化，防止外部创建实例
    private ProxyTargetCache() {

    }

    // 提供一个全局访问点
    public static ProxyTargetCache getInstance() {
        return SingletonProxyTarget.INSTANCE;
    }

    public ProxyTargetCache init(){
        List<Object> list = Arrays.asList(new APlayer1(),new APlayer2(), new APlayer3());
        list.forEach(p -> forEachMethod(p.getClass().getMethods(), methodCachMap,p));
        return this;
    }
    private  void forEachMethod(Method[] methods, Map<String,Object>  map,Object p){
        for (Method method : methods ) {
            map.put(method.getName(),p);
        }

    }

   public Object getMethodTarget(Method method){
        return methodCachMap.get(method.getName());
    }
}
