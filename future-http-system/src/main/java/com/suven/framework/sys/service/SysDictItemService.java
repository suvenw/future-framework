package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysDictItemRequestDto;
import com.suven.framework.sys.dto.response.SysDictItemResponseDto;
import com.suven.framework.sys.dto.enums.SysDictItemQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;




/**
 * ClassName: SysDictItemService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:15
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 数据字典明细表 RPC业务接口逻辑实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


public interface SysDictItemService {



    /**
     * 保存数据字典明细表更新数据库和缓存的实现方法
     * @param sysDictItemRequestDto  SysDictItemRequestDto
     * @return
     */
    SysDictItemResponseDto saveSysDictItem(SysDictItemRequestDto sysDictItemRequestDto);


     /**
     * 保存数据字典明细表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDictItemRequestDto  SysDictItemRequestDto
     * @return
     */
    SysDictItemResponseDto saveIdSysDictItem(SysDictItemRequestDto sysDictItemRequestDto);


     /**
     * 保存数据字典明细表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysDictItem(Collection<SysDictItemRequestDto> entityList);

    /**
     * 保存数据字典明细表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDictItem(Collection<SysDictItemRequestDto> entityList, int batchSize);

    /**
     * 保存数据字典明细表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDictItem(Collection<SysDictItemRequestDto> entityList, int batchSize);



    /**
     * 更新数据字典明细表同时更新数据库和缓存的实现方法
     * @param sysDictItemRequestDto  SysDictItemRequestDto
     * @return
     */
    boolean updateSysDictItem (SysDictItemRequestDto sysDictItemRequestDto);

    /**
     * 更新数据字典明细表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDictItemRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysDictItemIds
     * @return
     */
    int delSysDictItemByIds(List<Long>  sysDictItemIds);


    /**
     * 通过主键ID更新对象数据字典明细表实现缓存和数据库更新的方法
     * @param  sysDictItemId
     * @return
     */
        SysDictItemResponseDto getSysDictItemById(long sysDictItemId);

    /**
     * 通过参数limit0,1获取对象数据字典明细表的查询方法
     * @param  queryEnum
     * @return
     */
    SysDictItemResponseDto getSysDictItemByOne( SysDictItemQueryEnum queryEnum,SysDictItemRequestDto sysDictItemRequestDto);


    /**
    * 通过分页和枚举条件获取SysDictItem信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:10:15
    */
    List<SysDictItemResponseDto> getSysDictItemListByQuery(Object  paramObject, SysDictItemQueryEnum queryEnum);


    /**
     * 通过分页获取SysDictItem信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:15
     */
    List<SysDictItemResponseDto> getSysDictItemListByPage(Pager page, SysDictItemQueryEnum queryEnum);




    /**
     * 通过分页获取SysDictItem 数据字典明细表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:15
     */
    PageResult<SysDictItemResponseDto> getSysDictItemByNextPage(Pager page, SysDictItemQueryEnum queryEnum);

    /**
     * 通过分页获取SysDictItem 数据字典明细表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:15
     */
    PageResult<SysDictItemResponseDto> getSysDictItemByQueryPage(Pager page, SysDictItemQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}