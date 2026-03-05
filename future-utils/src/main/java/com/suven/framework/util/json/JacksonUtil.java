package com.suven.framework.util.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Jackson JSON工具类
 * <p>
 * 提供JSON序列化和反序列化功能，支持对象、List、Set、Collection等类型的转换。
 * 支持时间戳格式和字符串格式的日期转换。
 * </p>
 *
 * @author wangjiuji
 */
public class JacksonUtil {


    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    private static class SingletonJackson {
        static ObjectMapper INSTANCE = new ObjectMapper();
        // 在实例化过程中设置业务参数
        static {
            init(true);
        }
        /**
         * 初始化 ObjectMapper
         * @param useTimestamp 是否使用时间戳格式，true=时间戳，false=字符串格式
         */
        static void init(boolean useTimestamp) {
            SimpleModule builder = new SimpleModule();
            
            if (useTimestamp) {
                // 使用时间戳格式
                builder.addSerializer(LocalDateTime.class, new ConverterJacksonSerializer.TimestampSerializerByLocalDateTime());
                builder.addDeserializer(LocalDateTime.class, new ConverterJacksonSerializer.TimestampDeserializerByLocalDateTime());
                builder.addSerializer(Date.class, new ConverterJacksonSerializer.TimestampSerializerByDate());
                builder.addDeserializer(Date.class, new ConverterJacksonSerializer.TimestampDeserializerByDate());
            } else {
                // 使用字符串格式（默认）
                builder.addSerializer(LocalDateTime.class, new ConverterJacksonSerializer.SerializerByLocalDateTime());
                builder.addDeserializer(LocalDateTime.class, new ConverterJacksonSerializer.DeserializerByLocalDateTime());
                builder.addSerializer(Date.class, new ConverterJacksonSerializer.SerializerByDate());
                builder.addDeserializer(Date.class, new ConverterJacksonSerializer.DeserializerDate());
            }
            
            builder.addSerializer(Long.class, new ConverterJacksonSerializer.SerializerByLong());
            builder.addSerializer(long.class, new ConverterJacksonSerializer.SerializerByLong());
            builder.addDeserializer(Long.class, new ConverterJacksonSerializer.DeserializerByLong());
            builder.addDeserializer(long.class, new ConverterJacksonSerializer.DeserializerByLong());
            
            configureObjectMapper(INSTANCE);
            INSTANCE.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//            INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            INSTANCE.registerModule(builder);
        }
        /**
         * 配置 ObjectMapper 以处理各种序列化场景
         * 包括空对象、Object 类型等的序列化
         *
         * @param mapper 要配置的 ObjectMapper
         */
        private static void configureObjectMapper(ObjectMapper mapper) {
            // 禁用 FAIL_ON_EMPTY_BEANS，允许序列化空对象
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            // 禁用 FAIL_ON_UNKNOWN_PROPERTIES，允许未知属性
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 允许序列化空值（使用新的配置方式）
            mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);

            // 配置日期格式
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            // 配置时区
            mapper.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        }

        /**
         * 获取使用时间戳格式的 ObjectMapper
         * @return 配置了时间戳序列化器的 ObjectMapper
         */
        public static ObjectMapper getTimestampObjectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule builder = new SimpleModule();

            // 使用时间戳格式
            builder.addSerializer(LocalDateTime.class, new ConverterJacksonSerializer.TimestampSerializerByLocalDateTime());
            builder.addDeserializer(LocalDateTime.class, new ConverterJacksonSerializer.TimestampDeserializerByLocalDateTime());
            builder.addSerializer(Date.class, new ConverterJacksonSerializer.TimestampSerializerByDate());
            builder.addDeserializer(Date.class, new ConverterJacksonSerializer.TimestampDeserializerByDate());

            builder.addSerializer(Long.class, new ConverterJacksonSerializer.SerializerByLong());
            builder.addSerializer(long.class, new ConverterJacksonSerializer.SerializerByLong());
            builder.addDeserializer(Long.class, new ConverterJacksonSerializer.DeserializerByLong());
            builder.addDeserializer(long.class, new ConverterJacksonSerializer.DeserializerByLong());

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.registerModule(builder);

            return mapper;
        }

//        static void init2() {
//
//            final String DATE_PATTERN = "yyyy-MM-dd";
//            final String TIME_PATTERN = "HH:mm:ss";
//            //处理LocalDateTime
//            SimpleModule javaTimeModule = new SimpleModule();
//            final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
//            javaTimeModule.addSerializer (LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
//            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
//
//            //处理LocalDate
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
//            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
//            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
//
//            //处理LocalTime
//            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
//            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
//            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
//        }
//        @Bean
//        @Primary
//        public ObjectMapper objectMapper() {
//            ObjectMapper mapper = new ObjectMapper();
//            SimpleModule module = new SimpleModule();
//            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//            module.addSerializer(Date.class, new DateTimeSerializer());
//            module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
//            module.addDeserializer(Date.class, new DateTimeDeserializer());
//            mapper.registerModule(module);
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//            return JsonUtils.getMapper();
//        }
    }




    private JacksonUtil(){
    }

    public static ObjectMapper getInstance() {
        return SingletonJackson.INSTANCE;
    }

    /**
     * Java对象转JSON字符串
     *
     * @param object 对象，可为null
     * @return JSON字符串，对象为null时返回null
     */
    @Nullable
    public static String toJson(@Nullable Object object) {
        try {
            if (Objects.isNull(object)){
                return null;
            }
            return getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonString is error : \n", e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Java对象转JSON字符串（时间戳格式）
     * <p>
     * 时间类型转换为时间戳格式。
     * </p>
     *
     * @param object 对象，可为null
     * @return JSON字符串，时间类型转换为时间戳，对象为null时返回null
     */
    @Nullable
    public static String toJsonWithTimestamp(@Nullable Object object) {
        try {
            if (Objects.isNull(object)){
                return null;
            }
            return getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonWithTimestamp is error : \n", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Java对象转JSON字符串 - 美化输出
     * <p>
     * 格式化输出JSON字符串，便于阅读。
     * </p>
     *
     * @param object 对象，可为null
     * @return 格式化的JSON字符串
     */
    public static String toJsonStringWithPretty(@Nullable Object object) {
        try {
            return getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonStringWithPretty is error : \n", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * Java对象转字节数组
     *
     * @param object 对象，可为null
     * @return 字节数组
     */
    public static byte[] toJsonBytes(@Nullable Object object) {
        try {
            return getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonBytes is error : \n", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * JSON字符串转对象
     *
     * @param json JSON字符串，可为null
     * @param clazz 转换类对象
     * @param <T> 泛型类型
     * @return 转换后的对象
     */
    public static <T> T parseObject(@Nullable String json, Class<T> clazz) {
        try {
            return getInstance().readValue(json, clazz);
        } catch (Exception e) {
            log.error("The JacksonUtil parseObject is error, json str is {}, class name is {} \n", json, clazz.getName(), e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * JSON字符串转对象（时间戳格式）
     * <p>
     * 时间戳格式转换为时间类型。
     * </p>
     *
     * @param json JSON字符串（时间戳格式），可为null
     * @param clazz 转换类对象
     * @param <T> 泛型类型
     * @return 转换后的对象，时间戳转换为时间类型
     */
    public static <T> T parseObjectWithTimestamp(@Nullable String json, Class<T> clazz) {
        try {
            return getInstance().readValue(json, clazz);
        } catch (Exception e) {
            log.error("The JacksonUtil parseObjectWithTimestamp is error, json str is {}, class name is {} \n", json, clazz.getName(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON字符串转List集合
     *
     * @param json JSON字符串，可为null
     * @param elementClasses 转换类对象
     * @param <T> 泛型类型
     * @return List集合
     */
    @SafeVarargs
    public static <T> List<T> parseList(@Nullable String json, Class<T>... elementClasses) {
        try {
            return getInstance().readValue(json, getCollectionType(getInstance(), List.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseList is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }


    /**
     * JSON字符串转Set集合
     *
     * @param json JSON字符串，可为null
     * @param elementClasses 转换类对象
     * @param <T> 泛型类型
     * @return Set集合
     */
    @SafeVarargs
    public static <T> Set<T> parseSet(@Nullable String json, Class<T>... elementClasses) {
        try {
            return getInstance().readValue(json, getCollectionType(getInstance(), Set.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseSet is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON字符串转Collection集合
     *
     * @param json JSON字符串，可为null
     * @param elementClasses 转换类对象
     * @param <T> 泛型类型
     * @return Collection集合
     */
    @SafeVarargs
    public static <T> Collection<T> parseCollection(@Nullable String json, Class<T>... elementClasses) {
        try {
            return getInstance().readValue(json, getCollectionType(getInstance(), Collection.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseCollection is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }



}
