package com.suven.framework.http.validator;


import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * @ClassName: 自定义非空检查
 * Description:
 * @Author lixiangling
 * @Date 2018/5/16 10:12
 * Copyright: (c) 2018 gc by https://www.suven.top
 * @Version : 1.0.0
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysParamValid {
    String message() default "参数不能为空";
    int min();
    int max();
    String regexp();
    Class<?>[] groups() default { };
}
