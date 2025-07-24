package com.suven.framework.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @Title: SpringBootAutoConfigSetting.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) spring boot 动态获取指定属性的配置值的bean的实现类;
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        System.out.println("======== ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext() 获取applicationContext对象,applicationContext = "
                + SpringUtil.applicationContext +"========");

    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        try {
            return getApplicationContext().getBean(clazz);
        }catch (Exception e){}
        return null;
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }


    //通过name,以及Clazz返回指定的Bean
//    Collection<?> beans = applicationContext.getBeansOfType(clazz).values();
    public static <T> Collection<T> getBeansOfType( Class<T> clazz){
        Map<String, T> map =  getApplicationContext().getBeansOfType(clazz);
        if(null == map){
            return null;
        }
        return map.values();
    }


}