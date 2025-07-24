package com.suven.framework.core.es;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Documented//说明该注解将被包含在javadoc中
/**
 *  //string类型
 *                             .field("type", "string")
 *                             //在文档中存储
 *                             //建立索引
 *                             .field("index", "analyzed")
 *                              .field("store", "yes")
 *                             //使用ik_smart进行分词
 *                             .field("analyzer", "ik_smart")
 *

 */
public @interface EsIk {

    String type() default "text";                   //"文本.text ,时间.date , string",;
    String index() default "";                       //在是否创建索引,not_analyzed.不分词，但是建索引 , analyzed.建立索引,
    String analyzer()  default "ik_max_word";         //使用分词类型；ik_max_word 或 ik_smart
    String search_analyzer()  default "";             //属性描述；ik_max_word, ik_max_word
    boolean store() default false;                    // 是否存储;默认为false;存储为"true"
    String format() default "yyyy-MM-dd HH:mm:ss";                       // "format", "yyyy-MM-dd HH:mm:ss"
	
}

