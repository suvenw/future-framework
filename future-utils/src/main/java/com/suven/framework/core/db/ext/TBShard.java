package com.suven.framework.core.db.ext;



import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TBShard {
    TableTypeEnum column() default  TableTypeEnum.TB_ID;//数据源



}