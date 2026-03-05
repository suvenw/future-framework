package com.suven.framework.util.json;



import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * fastjson2 自定义序列化器
 * 使用 ObjectWriter 和 ObjectReader 接口替代 fastjson1.x 的 ObjectSerializer/ObjectDeserializer
 */
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
    public static class LocalDateTimeSerializer implements com.alibaba.fastjson2.writer.ObjectWriter<LocalDateTime> {
        @Override
        public void write(com.alibaba.fastjson2.JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
            if (object != null && object instanceof LocalDateTime) {
                LocalDateTime localDateTime = (LocalDateTime) object;
                //将localDateTime转换为中国区（+8）时间戳。
                jsonWriter.writeInt64(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            } else {
                jsonWriter.writeNull();
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
    public static class LocalDateTimeDeserializer implements com.alibaba.fastjson2.reader.ObjectReader<LocalDateTime> {

        @Override
        @SuppressWarnings("unchecked")
        public LocalDateTime readObject(com.alibaba.fastjson2.JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
            Long timestamp = jsonReader.readInt64();

            if (timestamp == null || timestamp == 0) {
                return null;
            }

            return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        }
    }




    public static class LongDeserializer implements com.alibaba.fastjson2.reader.ObjectReader<Long> {

        @Override
        @SuppressWarnings("unchecked")
        public Long readObject(com.alibaba.fastjson2.JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
            String data = jsonReader.readString();

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
    }

    public static class LongSerializer implements com.alibaba.fastjson2.writer.ObjectWriter<Long> {
        @Override
        public void write(com.alibaba.fastjson2.JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
            if (object == null){
                jsonWriter.writeNull();
                return;
            }
            if ( object instanceof Long) {
                jsonWriter.writeString(String.valueOf(object));
                return;
            }
            jsonWriter.writeAny(object);

        }
    }


}
