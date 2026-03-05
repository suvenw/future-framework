package com.suven.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * 配置文件解析工具类
 * <p>
 * 继承PropertyPlaceholderConfigurer，用于解析Spring配置文件。
 * 提供便捷的方法获取配置值，支持String、Integer、Long、Boolean等类型。
 * </p>
 * <p>
 * <b>注意：</b>PropertyPlaceholderConfigurer已过时，建议使用PropertySourcesPlaceholderConfigurer。
 * </p>
 *
 * @author ShenHuaJie
 * @version 2016年7月30日 下午11:41:53
 * @deprecated PropertyPlaceholderConfigurer已过时，建议使用PropertySourcesPlaceholderConfigurer
 */
@SuppressWarnings("deprecation")
public final class PropertiesUtil extends PropertyPlaceholderConfigurer {

    private static Map<String, String> ctxPropertiesMap = new HashMap<>();


    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            if(null != value){
                value = value.trim();
            }
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    /**
     * 根据key获取String值
     * <p>
     * 如果key不存在则返回null。
     * </p>
     *
     * @param key 配置key
     * @return 配置值，key不存在返回null
     */
    @Nullable
    public static String getString(String key) {
        try {
            return ctxPropertiesMap.get(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key获取String值
     * <p>
     * 如果key不存在则返回默认值。
     * </p>
     *
     * @param key 配置key
     * @param defaultValue 默认值
     * @return 配置值，key不存在返回默认值
     */
    public static String getString(String key, String defaultValue) {
        try {
            String str = ctxPropertiesMap.get(key);
            if(str == null){
                return defaultValue;
            }
            return str.trim();
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key获取int值
     * <p>
     * 如果key不存在或值为空则返回0。
     * </p>
     *
     * @param key 配置key
     * @return 配置值，key不存在返回0
     */
    public static int getInt(String key) {
        try {
            String value = ctxPropertiesMap.get(key);
            if(null != value && !"".equals(value)){
                return Integer.parseInt(value);
            }
        } catch (MissingResourceException e) {
            return -1;
        }
        return 0;
    }

    /**
     * 根据key获取int值
     * <p>
     * 如果key不存在或值<=0则返回默认值。
     * </p>
     *
     * @param key 配置key
     * @param defaultValue 默认值
     * @return 配置值，key不存在或值<=0返回默认值
     */
    public static int getInt(String key, int defaultValue) {
        int count = 0;
        try {
            String value = ctxPropertiesMap.get(key);
            if(null != value && !"".equals(value)){
                count = Integer.parseInt(value);
            }
        } catch (Exception e) {  }
        if(count > 0){
            return count;
        }
        return defaultValue;
    }

    /**
     * 根据key获取long值
     * <p>
     * 如果key不存在或值<=0则返回默认值。
     * </p>
     *
     * @param key 配置key
     * @param defaultValue 默认值
     * @return 配置值，key不存在或值<=0返回默认值
     */
    public static long getLong(String key, long defaultValue) {
        long count = 0;
        try {
            String value = ctxPropertiesMap.get(key);
            if(null != value && !"".equals(value)){
                count = Long.parseLong(value);
            }
        } catch (Exception e) {  }
        if(count > 0){
            return count;
        }
        return defaultValue;
    }

    /**
     * 根据key获取boolean值
     * <p>
     * 值为"1"或"true"（不区分大小写）时返回true，否则返回false。
     * </p>
     *
     * @param key 配置key
     * @return 配置值，key不存在返回false
     */
    public static boolean getBoolean(String key) {
        try {
            String value = ctxPropertiesMap.get(key);
            boolean is = false;
            if(null != value && !"".equals(value) ){
                if( "1".equals(value) || "true".equals(value)){
                    is = true;
                }
                return is;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 根据key获取boolean值
     * <p>
     * 值为"1"或"true"（不区分大小写）时返回true，否则返回false。
     * </p>
     *
     * @param key 配置key
     * @param defaultValue 默认值
     * @return 配置值，key不存在返回默认值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            String value = ctxPropertiesMap.get(key);
            if(null == value){
                return defaultValue;
            }
            boolean is = false;
            if(null != value && !"".equals(value) ){
                if( "1".equals(value) || "true".equals(value)){
                    is = true;
                }
                return is;
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return false;
    }
}
