package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;

import java.util.List;

public class HttpRequestByIdListVo extends HttpFromRequestVo {

    @ApiDesc(value= "对应的数据列表的主键列表聚合,使用char k = 7,分隔的字符口串; ")
    private List<Long> idList;


    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
