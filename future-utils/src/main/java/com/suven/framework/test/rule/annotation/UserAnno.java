package com.suven.framework.test.rule.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface UserAnno {
    String username() default "kg7love";
    String pwd() default "123456";
    long userId() default 0;
    String token() default "";
    String env() default "dev";
}