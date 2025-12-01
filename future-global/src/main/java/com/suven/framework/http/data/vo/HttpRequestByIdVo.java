package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.HttpFromRequest;
import com.suven.framework.http.api.HttpRequestType;
import com.suven.framework.http.api.IBaseApi;

public  class HttpRequestByIdVo extends HttpFromRequestVo implements HttpFromRequest,IBaseApi{

    @ApiDesc(value= "对应的数据列表的主键属性,用于操作删除,修改 " )
    private Long id;


    @Override
    public Long getId() {
        return id == null ? 0 : id;
    }

    @Override
    public void setId(Long id) {
            this.id = id;
    }

    /**
     * 设置id并返回当前对象，支持链式调用
     * 子类继承时，无需重写此方法，链式调用会自动返回子类类型
     * @param id id 主键
     * @return 当前对象（子类类型）
     */
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestType> T toId(Long id) {
        this.id = id;
        return this.self();
    }
}
