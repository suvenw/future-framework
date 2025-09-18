package com.suven.framework.http.api;



import java.io.Serializable;
import java.util.List;

/**
 * 框架操作实现entity 实现接口类
 */
public interface IDApi<ID extends Serializable> {

    public ID getId();

    public void setId(ID id);

    public default long groupId(){
        return 0L;
    };


}