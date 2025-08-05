package com.suven.framework.core.spring;

import com.suven.framework.common.enums.NumberEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Title: SpringBootAutoConfigSetting.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) spring boot 动态获取指定属性的配置值的实现类;
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


@Configuration("configSetting")
@ConfigurationProperties
public class SpringBootAutoConfigSetting  implements  EnvironmentAware{

    private Binder binder;

    @Override
    public void setEnvironment(Environment environment) {
        binder = Binder.get(environment);
    }


    /**
     * 动态获取配置文件的字符串;
     * @param configName
     * @return
     */
    public String property(String configName){
        return property(configName,"");
    }
    /**
     * 动态获取配置文件的字符串;
     * @param configName
     * @param defaultValue
     * @return
     */
    public String property(String configName, String defaultValue){
        try {
            if(configName == null){
                return defaultValue;
            }
            BindResult<String> result = binder.bind(configName,String.class);
            if(null != result && result.isBound() ){
                return result.get().trim();
            }
        }catch (Exception e){}
        return defaultValue;
    }

    /**
     * 动态获取配置文件的字符串对应的值;返回Int
     * @param configName
     * @return
     */
    public int propertyInt(String configName) {
       return property(configName,0);
    }
    /**
     * 动态获取配置文件的字符串对应的值;返回Int
     * @param configName
     * @return
     */
        public int property(String configName, int defaultValue){
        if(configName == null){
            return defaultValue;
        }
        try {
            BindResult<Integer> result = binder.bind(configName,Integer.class);
            if(null != result && result.isBound() ){
                return result.get();
            }
        }catch (Exception e){}
        return defaultValue;
    }

    /**
     * 动态获取配置文件的字符串对应的值;返回boolean, true/false(1:true)
     * @param configName
     * @return
     */
    public boolean propertyBoolean(String configName) {
        return property(configName,false);
    }
    public boolean property(String configName, boolean defaultValue){
        if(configName == null){
            return defaultValue;
        }
        try {
            BindResult<String> result = binder.bind(configName,String.class);
            if(null != result && result.isBound()){
                String value =  result.get().trim();
                if(value.equals(NumberEnum.ONE.id())){
                    return true;
                }
                return  Boolean.valueOf(value );
            }

        }catch (Exception e){}
        return defaultValue;
    }

    /**
     * 动态获取配置文件的字符串对应的值;返回object
     * @param configName
     * @return
     */
    public <T> T property(String configName, Class<T> parameterClasses){
        return property(configName,parameterClasses,null);
    }
    /**
     * 动态获取配置文件的字符串对应的值;返回object
     * @param configName
     * @return
     */
    public <T> T property(String configName, Class<T> parameterClasses, T defaultValue){
        try {
            BindResult<T> result = binder.bind(configName,parameterClasses);
            if(null != result && result.isBound()){
                return result.get();
            }
        }catch (Exception e){}
        return defaultValue;
    }

}
