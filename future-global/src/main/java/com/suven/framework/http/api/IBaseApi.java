package com.suven.framework.http.api;


/**
 * 框架操作实现entity 实现接口类
 */
public  interface  IBaseApi extends  IBeanClone ,IDApi<Long>{

   default public String getEsId(){
       return String.valueOf(getId());
   }
}