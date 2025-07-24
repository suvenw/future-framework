package com.suven.framework.core.es;

import java.io.Serializable;

/**
 * es 客户端操作对象 基础类
 *
 * @author xxx.xxx
 * @Description //TODO
 * @CreateDate 2019-09-11  16:14
 **/
public class EsBaseEntity<T> implements Serializable {

    private String id;
    private T data;

    public EsBaseEntity() {
    }

    public EsBaseEntity(String id, T data) {
        this.data = data;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
