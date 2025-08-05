package com.suven.framework.sys.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

public class AllStatusRequestVo extends RequestParserVo {

    @ApiDesc(value= "对应的数据列表的主键属性,用于操作删除,修改 " )
    private long id;

    @ApiDesc(value = "禁言状态：0 正常，1 禁言")
    private int banned;

    @ApiDesc(value = "封号状态：0 正常，1 封号")
    private int ban;

    @ApiDesc(value = "上下架状态：0 未知 1 上架 2 已下架")
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public int getBan() {
        return ban;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
