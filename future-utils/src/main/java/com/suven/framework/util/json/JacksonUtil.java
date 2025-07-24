package com.suven.framework.util.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author wangjiuji
 */
public class JacksonUtil {


    private static Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    private static class SingletonJackson {
        static ObjectMapper INSTANCE = new ObjectMapper();
        // 在实例化过程中设置业务参数
        static {
            init();
        }
        static void init() {
            SimpleModule builder = new SimpleModule();
            builder.addSerializer(LocalDateTime.class, new ConverterJacksonSerializer.SerializerByLocalDateTime());
            builder.addDeserializer(LocalDateTime.class, new ConverterJacksonSerializer.DeserializerByLocalDateTime());
            builder.addSerializer(Date.class, new ConverterJacksonSerializer.SerializerByDate());
            builder.addDeserializer(Date.class, new ConverterJacksonSerializer.DeserializerDate());
            builder.addSerializer(Long.class, new ConverterJacksonSerializer.SerializerByLong());
            builder.addSerializer(long.class, new ConverterJacksonSerializer.SerializerByLong());
            builder.addDeserializer(Long.class, new ConverterJacksonSerializer.DeserializerByLong());
            builder.addDeserializer(long.class, new ConverterJacksonSerializer.DeserializerByLong());
            INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            INSTANCE.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//            INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            INSTANCE.registerModule(builder);
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
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            if (Objects.isNull(object)){
                return null;
            }
            return getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonString is error : \n", e);
            throw new RuntimeException();
        }
    }

    /**
     * Java对象转JSON字符串 - 美化输出
     *
     * @param object 对象
     * @return 字符
     */
    public static String toJsonStringWithPretty(Object object) {
        try {
            return getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonString is error : \n", e);
            throw new RuntimeException();
        }
    }


    /**
     * Java对象转byte数组
     *
     * @param object 对象
     * @return 字节数组
     */
    public static byte[] toJsonBytes(Object object) {
        try {
            return getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("The JacksonUtil toJsonBytes is error : \n", e);
            throw new RuntimeException();
        }
    }


    /**
     * JSON字符串转对象
     *
     * @param json 字符串
     * @param clazz 转换类对象
     * @return 返回对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return getInstance().readValue(json, clazz);
        } catch (Exception e) {
            log.error("The JacksonUtil parseObject is error, json str is {}, class name is {} \n", json, clazz.getName(), e);
            throw new RuntimeException();
        }
    }

    /**
     * JSON字符串转List集合
     *
     * @param json 字符串
     * @param elementClasses 转换类对象
     * @return 返回对象
     */
    @SafeVarargs
    public static <T> List<T> parseList(String json, Class<T>... elementClasses) {
        try {
            return getInstance().readValue(json, getCollectionType(getInstance(), List.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseList is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException();
        }
    }


    /**
     * JSON字符串转Set集合
     *
     * @param json 字符串
     * @param elementClasses 转换类对象
     * @return 返回对象
     */
    @SafeVarargs
    public static <T> Set<T> parseSet(String json, Class<T>... elementClasses) {
        try {
            return getInstance().readValue(json, getCollectionType(getInstance(), Set.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseSet is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException();
        }
    }

    /**
     * JSON字符串转Collection集合
     *
     * @param json 字符串
     * @param elementClasses 转换类对象
     * @return 返回对象
     */
    @SafeVarargs
    public static <T> Collection<T> parseCollection(String json, Class<T>... elementClasses) {
        try {
            return getInstance().readValue(json, getCollectionType(getInstance(), Collection.class, elementClasses));
        } catch (Exception e) {
            log.error("The JacksonUtil parseCollection is error, json str is {}, element class name is {} \n",
                    json, elementClasses.getClass().getName(), e);
            throw new RuntimeException();
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
