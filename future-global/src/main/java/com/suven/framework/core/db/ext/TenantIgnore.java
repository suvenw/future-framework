package com.suven.framework.core.db.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 租户忽略注解，标注在实体类上表示忽略多租户处理
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TenantIgnore {
    
    /**
     * 忽略原因
     */
    String reason() default "";
    
    /**
     * 是否忽略, 默认为 true, 表示忽略
     * @return true 忽略
     */
    boolean isIgnore() default true;
}