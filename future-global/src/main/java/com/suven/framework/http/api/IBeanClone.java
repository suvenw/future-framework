package com.suven.framework.http.api;


import com.suven.framework.http.data.entity.BeanCopierUtil;

/**
 * 框架操作实现entity 参数克隆实现接口类
 */
public interface IBeanClone {

    default  <T> T clone(Object source) {
          if(null == source){
               return null;
          }
          BeanCopierUtil.copy(source, this);
          return (T)this;
     }

}
