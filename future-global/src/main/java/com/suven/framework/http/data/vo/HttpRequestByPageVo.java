package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;

/**
 * 分页请求VO基类，支持Builder模式链式调用
 * 子类继承时无需添加泛型参数，也无需重写父类方法，可以直接使用链式调用
 * 链式调用会自动返回子类类型，支持继续调用子类的方法
 */
public class HttpRequestByPageVo extends HttpFromRequestVo {


    @ApiDesc(value= "分页请求使用,对应为页码: 1,2,3 ...; 0/1 都是1")
    private int pageNo;
    @ApiDesc(value= "分页请求使用,对应为大小:默认是100,超过100条的接口需要重写page的方法")
    private int pageSize;
    /** status */
    @ApiDesc(value= "对应的数据列表的状态属性: 1.上架(显示), 0.下架(不显示/删除) ")
    private int status;
    /** sort */
    @ApiDesc(value= "对应的数据列表的排序属性 ")
    private int sort;

    /**
     * 创建实例的静态方法，支持Builder模式
     * 子类可以重写此方法返回子类类型
     * @return 当前类型的实例
     */
    public static HttpRequestByPageVo build() {
        return new HttpRequestByPageVo();
    }


    /**
     * 设置页码并返回当前对象，支持链式调用
     * 子类继承时，无需重写此方法，链式调用会自动返回子类类型
     * @param pageNo 页码
     * @return 当前对象（子类类型）
     */
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestByPageVo> T toPageNo(int pageNo) {
        this.pageNo = pageNo;
        return self();
    }

    /**
     * 设置每页大小并返回当前对象，支持链式调用
     * 子类继承时，无需重写此方法，链式调用会自动返回子类类型
     * @param pageSize 每页大小
     * @return 当前对象（子类类型）
     */
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestByPageVo> T toPageSize(int pageSize) {
        this.pageSize = pageSize;
        return self();
    }

    /**
     * 设置状态并返回当前对象，支持链式调用
     * 子类继承时，无需重写此方法，链式调用会自动返回子类类型
     * @param status 状态
     * @return 当前对象（子类类型）
     */
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestByPageVo> T toStatus(int status) {
        this.status = status;
        return self();
    }

    /**
     * 设置排序并返回当前对象，支持链式调用
     * 子类继承时，无需重写此方法，链式调用会自动返回子类类型
     * @param sort 排序
     * @return 当前对象（子类类型）
     */
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestByPageVo> T toSort(int sort) {
        this.sort = sort;
        return self();
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }
    public void setSort(int sort) {
        this.sort = sort;
    }


}
