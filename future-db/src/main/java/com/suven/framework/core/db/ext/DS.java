package com.suven.framework.core.db.ext;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {

    @AliasFor("value")
    String dataSource()  default  "";//数据源

    @AliasFor("dataSource")
    String value() default  "";//数据源
}