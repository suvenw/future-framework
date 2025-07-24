package com.suven.framework.test.rule.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author alex
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@FastBoot
public @interface Remote {
    String value() default "http://127.0.0.1:9010";//"http://139.224.61.69:9010"; // http://42.62.49.80/show7
}
