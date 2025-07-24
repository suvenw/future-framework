package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;

public class HttpRequestByIdPageVo extends HttpRequestByIdVo {


    @ApiDesc(value= "分页请求使用,对应为页码: 1,2,3 ...; 0/1 都是1")
    private int pageNo;
    @ApiDesc(value= "分页请求使用,对应为大小:默认是100,超过100条的接口需要重写page的方法")
    private int pageSize;

    public <T extends HttpRequestByIdPageVo> T toId(long id) {
        this.setId(id);
        return (T) this;
    }
    public <T extends HttpRequestByIdPageVo> T toPageNo(int pageNo) {
        this.pageNo = pageNo;
        return (T) this;
    }

    public <T extends HttpRequestByIdPageVo> T toPageSize(int pageSize) {
        this.pageSize = pageSize;
        return (T)this;
    }


    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
