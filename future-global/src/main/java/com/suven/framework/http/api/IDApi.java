package com.suven.framework.http.api;


import com.suven.framework.util.ids.GlobalId;

import java.io.Serializable;
import java.util.List;

/**
 * 框架操作实现entity 实现接口类
 */
public interface IDApi<ID extends Serializable> extends IBeanClone {

    public ID getId();

    public void setId(ID id);

    public default long groupId(){
        return 0L;
    };

    public default long initId(){
        return GlobalId.get().nextId();
    }
    public default List<Long> initIds(int count){
            return GlobalId.get().nextIds(count);
    }

}