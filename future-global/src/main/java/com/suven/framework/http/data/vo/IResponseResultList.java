package com.suven.framework.http.data.vo;


import java.util.List;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-06-30
 * @WeeK 星期: 星期三
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  返回列表接口类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.66os.com
 **/

public interface IResponseResultList<T> {

    /** 返回列表集合 **/
    List<T> getList();

    /** 返回下一页的开始索引值 **/
    long getPageIndex();

    /** 返回总条数据,后台管理系统才有值,api接口不生效,返回0 **/
    int getTotal();

    /** 是否有下一页的标识,1.有下一页, 0.没有下一页 **/
    int getIsNextPage();


}
