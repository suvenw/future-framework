package com.suven.framework.util.json;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class ConverterFastJsonSerializer {


    /**
     * 序列化
     */
    /**
     * 由于 LocalDateTime 类型在转换 JSON 的时候，并不能被转换为字符串，使用 @JsonFormat 只能转换为指定的 pattern 类型，因此我们需要自定义一个序列化执行器
     * LocalDateTime 序列化（将 LocalDateTime类型 转换为 时间戳 返回给前端 ）
     *
     * @author Chimm Huang
     * date 2020/3/7
     */
    public static class LocalDateTimeSerializer implements ObjectSerializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            if (object != null && object instanceof LocalDateTime) {
                LocalDateTime localDateTime = (LocalDateTime) object;
                //将localDateTime转换为中国区（+8）时间戳。
                serializer.write(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            } else {
                serializer.write(null);
            }
        }
    }

    /**
     * 由于 时间戳 并不能直接被 fastJSON 转换为 LocalDateTime 类型，因此我们需要自定义一个序列化执行器
     * LocalDateTime 反序列化（将前端传递的 时间戳 转换为 LocalDateTime 类型）
     *
     * @author Chimm Huang
     * date 2020/3/7
     */
    public static class LocalDateTimeDeserializer implements ObjectDeserializer {

        @Override
        @SuppressWarnings("unchecked")
        public LocalDateTime deserialze(DefaultJSONParser parser, Type type, Object fieldName) {

            String timestampStr = parser.getLexer().numberString();

            if (Objects.isNull(timestampStr)) {
                return null;
            }

            timestampStr = timestampStr.replaceAll("\"", "");

            long timestamp = Long.parseLong(timestampStr);
            if(timestamp == 0) {
                return null;
            }
            return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        }

        @Override
        public int getFastMatchToken() {
            return 0;
        }
    }





    public static class LongDeserializer implements ObjectDeserializer {

        @Override
        @SuppressWarnings("unchecked")
        public Long deserialze(DefaultJSONParser parser, Type type, Object fieldName) {

//            Type type = type.getType();
            if (!type.equals(Long.TYPE)|| type.equals(long.class)) {
                return null;
            }
            String data = parser.getLexer().numberString();

            if (Objects.isNull(data)) {
                return null;
            }
            data = data.replaceAll("\"", "");
            long id = Long.parseLong(data);
            if(id == 0) {
                return null;
            }
            return id;
        }

        @Override
        public int getFastMatchToken() {
            return 0;
        }
    }

    public static class LongSerializer implements ObjectSerializer {
        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
            if (object == null){
                serializer.write(null);
                return;
            }
            if ( object instanceof Long || fieldType.equals(Long.TYPE)) {
                serializer.write(String.valueOf(object));
                return;
            }
            serializer.write(object);

        }
    }


}
