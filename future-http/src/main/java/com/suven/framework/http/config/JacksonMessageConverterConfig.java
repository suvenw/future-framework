package com.suven.framework.http.config;

import com.suven.framework.http.handler.JacksonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Jackson 消息转换器配置
 * 使用 JacksonUtil 的时间转换实现，提供统一的业务 JSON 转换
 */
@Configuration
public class JacksonMessageConverterConfig {



    /**
     * 时间戳格式的 Jackson 消息转换器
     * 可以通过配置选择使用
     */
    @Bean
    @ConditionalOnProperty(name = "future.http.time-format.format", havingValue = "TIMESTAMP")
    public JacksonHttpMessageConverter timestampJacksonConverter() {
        return new JacksonHttpMessageConverter(JacksonHttpMessageConverter.TimeFormat.TIMESTAMP);
    }

    /**
     * 字符串格式的 Jackson 消息转换器
     * 可以通过配置选择使用
     */
    @Bean
    @ConditionalOnProperty(name = "future.http.time-format.format", havingValue = "STRING")
    public JacksonHttpMessageConverter stringJacksonConverter() {
        return new JacksonHttpMessageConverter(JacksonHttpMessageConverter.TimeFormat.STRING);
    }


}

