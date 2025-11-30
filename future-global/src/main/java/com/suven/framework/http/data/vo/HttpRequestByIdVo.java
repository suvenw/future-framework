package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.HttpFromRequest;
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
}
