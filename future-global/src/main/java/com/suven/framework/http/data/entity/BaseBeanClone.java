package com.suven.framework.http.data.entity;

import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;



/**
 * @author Joven.wang
 * @version V1.0
 * Title: BaseAipEntity.java
 * date 2016年2月20日
 * Description: TODO(说明)
 * 提供 POJO Bean 对应的对象间,具有相同的方法的属性值,动态拷贝实现,但不支持builder类性的Bean对象属性拷贝;
 */
public abstract class BaseBeanClone  implements IBeanClone, Serializable {

}
