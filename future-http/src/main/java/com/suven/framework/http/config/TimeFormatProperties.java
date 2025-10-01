package com.suven.framework.http.config;

import com.suven.framework.http.handler.JacksonHttpMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 时间格式配置属性
 * 支持通过配置文件动态设置时间格式
 */
@ConfigurationProperties(prefix = "future.http.time-format")
public class TimeFormatProperties {
    
    /**
     * 时间格式类型
     * 可选值：STRING（字符串格式）、TIMESTAMP（时间戳格式）
     * 默认值：TIMESTAMP
     */
    private JacksonHttpMessageConverter.TimeFormat format = JacksonHttpMessageConverter.TimeFormat.TIMESTAMP;
    
    /**
     * 是否启用时间格式转换
     * 默认值：true
     */
    private boolean enabled = true;
    
    public JacksonHttpMessageConverter.TimeFormat getFormat() {
        return format;
    }
    
    public void setFormat(JacksonHttpMessageConverter.TimeFormat format) {
        this.format = format;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

