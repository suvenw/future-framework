package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.HttpRequestType;

import java.util.List;

/**
 * Title: HttpRequestSortByIdListVo 表单类型 HttpFromRequestVo http 表单请求,参数继承对象
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http请求头参数VO类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
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

    /**
     * 设置集体列表,返回当前对象，支持链式调用
     * 子类继承时，无需重写此方法，链式调用会自动返回子类类型
     * @param idList 每页大小
     * @return 当前对象（子类类型）
     */
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestType> T toIdList(List<Long> idList) {
        this.idList = idList;
        return self();
    }

    /**
     * 设置集体列表,返回当前对象，支持链式调用
     * 子类继承时，无需重写此方法，链式调用会自动返回子类类型
     * @param sortList 每页大小
     * @return 当前对象（子类类型）
     */
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestType> T toSortList(List<Integer> sortList) {
        this.sortList = sortList;
        return self();
    }
}
