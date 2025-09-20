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
        ObjectMapper mapper;
        
        if (timeFormat == TimeFormat.TIMESTAMP) {
            // 使用 JacksonUtil 的默认配置（时间戳格式）
            mapper = JacksonUtil.getInstance();
        } else {
            // 使用字符串格式，创建新的 ObjectMapper 并配置字符串序列化器
            mapper = createStringObjectMapper();
        }
        
        return mapper;
    }

    /**
     * 创建字符串格式的 ObjectMapper
     * 复用 JacksonUtil 的配置逻辑，但使用字符串序列化器
     * 
     * @return 配置了字符串序列化器的 ObjectMapper
     */
    private static ObjectMapper createStringObjectMapper() {
        // 获取 JacksonUtil 的 ObjectMapper 作为基础配置
        ObjectMapper baseMapper = JacksonUtil.getInstance();
        
        // 创建新的 ObjectMapper 并复制基础配置
        ObjectMapper stringMapper = baseMapper.copy();
        
        // 重新配置时间序列化器为字符串格式
        stringMapper.getSerializerProvider().setDefaultKeySerializer(null);
        stringMapper.getSerializerProvider().setNullValueSerializer(null);

        // 创建新的模块并添加字符串序列化器
        SimpleModule stringModule = new SimpleModule();
        stringModule.addSerializer(LocalDateTime.class, new ConverterJacksonSerializer.SerializerByLocalDateTime());
        stringModule.addDeserializer(LocalDateTime.class, new ConverterJacksonSerializer.DeserializerByLocalDateTime());
        stringModule.addSerializer(Date.class, new ConverterJacksonSerializer.SerializerByDate());
        stringModule.addDeserializer(Date.class, new ConverterJacksonSerializer.DeserializerDate());

        // 应用通用配置
        configureObjectMapper(stringMapper);
        
        // 注册字符串模块
        stringMapper.registerModule(stringModule);
        
        return stringMapper;
    }


    /**
     * 配置 ObjectMapper 以处理各种序列化场景
     * 包括空对象、Object 类型等的序列化
     * 
     * @param mapper 要配置的 ObjectMapper
     */
    private static void configureObjectMapper(ObjectMapper mapper) {
        // 禁用 FAIL_ON_EMPTY_BEANS，允许序列化空对象
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        // 禁用 FAIL_ON_UNKNOWN_PROPERTIES，允许未知属性
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        // 允许序列化空值（使用新的配置方式）
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
        
        // 配置日期格式
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        // 配置时区
        mapper.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
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