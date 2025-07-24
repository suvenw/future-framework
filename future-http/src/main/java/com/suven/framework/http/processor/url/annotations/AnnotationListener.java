package com.suven.framework.http.processor.url.annotations;

/**
 * 作用,实现此接口的类会收到自己所关心的注解实例注入
 * 方法必须带 @Observer注解表明可以在这个方法上注入
 * 方法只能有两个参数, 第一个是url, 第二个是自己想得到的注解
 * Created by Alex on 2014/9/2
 */
public interface AnnotationListener  {
}
