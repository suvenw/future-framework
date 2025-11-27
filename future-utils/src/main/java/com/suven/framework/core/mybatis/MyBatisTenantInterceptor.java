//package com.suven.framework.core.mybatis;
//
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//
//import java.util.Properties;
//
//public class MyBatisTenantInterceptor implements Interceptor {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        // 在此处编写拦截逻辑
//        // 可以获取和修改SQL语句、参数等
//        // 可以添加租户条件
//
//        // 调用原始方法
//        return invocation.proceed();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        // 创建拦截代理对象
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        // 读取和设置拦截器的属性
//    }
//}