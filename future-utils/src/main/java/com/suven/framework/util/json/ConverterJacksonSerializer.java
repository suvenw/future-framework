package com.suven.framework.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class ConverterJacksonSerializer {

   static Logger log = LoggerFactory.getLogger(ConverterJacksonSerializer.class);

    static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";
    static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(FORMATTER);
    /**
     * 序列化 LocalDateTime
     */
    public static class SerializerByLocalDateTime extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (Objects.nonNull(value)) {
                String times = value.format(DATETIME_FORMATTER);

                gen.writeString(times);
            }
        }
    }



    /**
     * 反序列化 LocalDateTime
     */
    public static class DeserializerByLocalDateTime extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            String times = jsonParser.getText();
            if (Objects.isNull(times)) {
                return null;
            }
            LocalDateTime dateTime = LocalDateTime.parse(times, DATETIME_FORMATTER);
            return dateTime;

        }
    }
    /**
     * 序列化 Date
     */
    @Slf4j
    public static class SerializerByDate extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if(Objects.isNull(value)){
                gen.writeNull();
            }
            try {
                DateFormat format = new SimpleDateFormat(FORMATTER);
                String times =  format.format(value);
                gen.writeString(times);
                return;
            } catch (Exception e) {
                log.info("DateSerializer Exception", e);
            }
            gen.writeNull();

        }
    }



    /**
     * 反序列化 Date
     */
    @Slf4j
    public static class DeserializerDate extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            String times = jsonParser.getText();
            if (Objects.isNull(times)) {
                return null;
            }
            try {
                DateFormat format = new SimpleDateFormat(FORMATTER);
                return format.parse(times);
            }catch (Exception e){
                log.info("DateDeserializer Exception", e);
            }
            return null;
        }
    }

    /**
     * 反序列化 Long
     */
    @Slf4j
    public static class DeserializerByLong extends JsonDeserializer<Long> {

        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = jsonParser.getText();
            try {
                return StringUtils.isBlank(value)  ? null : Long.parseLong(value);
            } catch (NumberFormatException e) {
                log.error("Error when string convert long: value = " + value, e);
                return null;
            }
        }
    }

    /**
     * 反序列化 Long
     */
    public static class SerializerByLong extends JsonSerializer<Long> {
        @Override
        public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            String text = (value == null ? "0" : String.valueOf(value));
            if (Objects.nonNull(text)) {
                jsonGenerator.writeString(text);
            }
        }
    }




}
