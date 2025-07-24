package com.suven.framework.test.rule.annotation;

import java.lang.annotation.*;

/**
 * 当没有使用到Spring的功能的时候, 可以快速启动
 * @author Alex
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FastBoot {
}
