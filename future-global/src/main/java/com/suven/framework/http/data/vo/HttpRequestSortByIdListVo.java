package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;

import java.util.List;

public class HttpRequestSortByIdListVo extends HttpFromRequestVo {

    @ApiDesc(value= "对应的数据列表的主键列表聚合,使用char k = 7,分隔的字符口串; ")
    private List<Long> idList;
    @ApiDesc(value= "排序字段参数聚合,使用char k = 7,分隔的字符口串; ")
    private List<Integer> sortList;


    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public List<Integer> getSortList() {
        return sortList;
    }

    public void setSortList(List<Integer> sortList) {
        this.sortList = sortList;
    }
}
