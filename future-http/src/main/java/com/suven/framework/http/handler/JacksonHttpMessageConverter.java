package com.suven.framework.http.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.suven.framework.util.json.ConverterJacksonSerializer;
import com.suven.framework.util.json.JacksonUtil;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 自定义 Jackson HTTP 消息转换器
 * 使用 JacksonUtil 的时间转换实现，实现统一的业务 JSON 转换
 */
public class JacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    /**
     * 时间格式类型枚举
     */
    public enum TimeFormat {
        STRING,     // 字符串格式：yyyy-MM-dd HH:mm:ss
        TIMESTAMP   // 时间戳格式：毫秒时间戳
    }

    private static final TimeFormat DEFAULT_TIME_FORMAT = TimeFormat.TIMESTAMP;

    /**
     * 默认构造函数，使用 JacksonUtil 的默认配置（字符串格式）
     */
    public JacksonHttpMessageConverter() {
        this(DEFAULT_TIME_FORMAT);
    }

    /**
     * 指定时间格式的构造函数
     * 
     * @param timeFormat 时间格式类型
     */
    public JacksonHttpMessageConverter(TimeFormat timeFormat) {
        super(createObjectMapper(timeFormat));
    }

    /**
     * 创建配置了自定义序列化器的 ObjectMapper
     * 复用 JacksonUtil 的配置逻辑，确保统一的业务 JSON 转换
     * 
     * @param timeFormat 时间格式类型
     * @return 配置好的 ObjectMapper
     */
    private static ObjectMapper createObjectMapper(TimeFormat timeFormat) {
        if (timeFormat == TimeFormat.TIMESTAMP) {
            // 使用 JacksonUtil 的默认配置（字符串格式）
            return JacksonUtil.getInstance();
        } else {
            // 使用时间戳格式，创建新的 ObjectMapper 并配置时间戳序列化器
            return createTimestampObjectMapper();
        }
    }

    /**
     * 创建时间戳格式的 ObjectMapper
     * 复用 JacksonUtil 的配置逻辑，但使用时间戳序列化器
     * 
     * @return 配置了时间戳序列化器的 ObjectMapper
     */
    private static ObjectMapper createTimestampObjectMapper() {
        // 获取 JacksonUtil 的 ObjectMapper 作为基础配置
        ObjectMapper baseMapper = JacksonUtil.getInstance();
        
        // 创建新的 ObjectMapper 并复制基础配置
        ObjectMapper timestampMapper = baseMapper.copy();
        
        // 重新配置时间序列化器为时间戳格式
        timestampMapper.getSerializerProvider().setDefaultKeySerializer(null);
        timestampMapper.getSerializerProvider().setNullValueSerializer(null);

        // 创建新的模块并添加时间戳序列化器
        SimpleModule timestampModule = new SimpleModule();
        timestampModule.addSerializer(LocalDateTime.class, new ConverterJacksonSerializer.TimestampSerializerByLocalDateTime());
        timestampModule.addDeserializer(LocalDateTime.class, new ConverterJacksonSerializer.TimestampDeserializerByLocalDateTime());
        timestampModule.addSerializer(Date.class, new ConverterJacksonSerializer.TimestampSerializerByDate());
        timestampModule.addDeserializer(Date.class, new ConverterJacksonSerializer.TimestampDeserializerByDate());
        
        // 注册时间戳模块
        timestampMapper.registerModule(timestampModule);
        
        return timestampMapper;
    }

    /**
     * 创建使用时间戳格式的转换器
     * 
     * @return 配置了时间戳格式的 JacksonHttpMessageConverter
     */
    public static JacksonHttpMessageConverter createTimestampConverter() {
        return new JacksonHttpMessageConverter(TimeFormat.TIMESTAMP);
    }

    /**
     * 创建使用字符串格式的转换器
     * 
     * @return 配置了字符串格式的 JacksonHttpMessageConverter
     */
    public static JacksonHttpMessageConverter createStringConverter() {
        return new JacksonHttpMessageConverter(TimeFormat.STRING);
    }


}