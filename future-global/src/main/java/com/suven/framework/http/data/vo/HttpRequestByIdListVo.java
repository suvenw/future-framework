package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.HttpRequestType;

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


    /**
     * 创建实例的静态方法，支持Builder模式
     * 子类可以重写此方法返回子类类型
     * @return 当前类型的实例
     */
    public static HttpRequestByIdListVo build() {
        return new HttpRequestByIdListVo();
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
}
