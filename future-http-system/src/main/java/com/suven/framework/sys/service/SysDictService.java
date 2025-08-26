package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysDictRequestDto;
import com.suven.framework.sys.dto.response.SysDictResponseDto;
import com.suven.framework.sys.dto.enums.SysDictQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;




/**
 * ClassName: SysDictService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:09
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 后台字典类型表 RPC业务接口逻辑实现类
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


public interface SysDictService {



    /**
     * 保存后台字典类型表更新数据库和缓存的实现方法
     * @param sysDictRequestDto  SysDictRequestDto
     * @return
     */
    SysDictResponseDto saveSysDict(SysDictRequestDto sysDictRequestDto);


     /**
     * 保存后台字典类型表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDictRequestDto  SysDictRequestDto
     * @return
     */
    SysDictResponseDto saveIdSysDict(SysDictRequestDto sysDictRequestDto);


     /**
     * 保存后台字典类型表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysDict(Collection<SysDictRequestDto> entityList);

    /**
     * 保存后台字典类型表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDict(Collection<SysDictRequestDto> entityList, int batchSize);

    /**
     * 保存后台字典类型表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDict(Collection<SysDictRequestDto> entityList, int batchSize);



    /**
     * 更新后台字典类型表同时更新数据库和缓存的实现方法
     * @param sysDictRequestDto  SysDictRequestDto
     * @return
     */
    boolean updateSysDict (SysDictRequestDto sysDictRequestDto);

    /**
     * 更新后台字典类型表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDictRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysDictIds
     * @return
     */
    int delSysDictByIds(List<Long>  sysDictIds);


    /**
     * 通过主键ID更新对象后台字典类型表实现缓存和数据库更新的方法
     * @param  sysDictId
     * @return
     */
        SysDictResponseDto getSysDictById(long sysDictId);

    /**
     * 通过参数limit0,1获取对象后台字典类型表的查询方法
     * @param  queryEnum
     * @return
     */
    SysDictResponseDto getSysDictByOne( SysDictQueryEnum queryEnum,SysDictRequestDto sysDictRequestDto);


    /**
    * 通过分页和枚举条件获取SysDict信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:10:09
    */
    List<SysDictResponseDto> getSysDictListByQuery(Object  paramObject, SysDictQueryEnum queryEnum);


    /**
     * 通过分页获取SysDict信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    List<SysDictResponseDto> getSysDictListByPage(Pager page, SysDictQueryEnum queryEnum);




    /**
     * 通过分页获取SysDict 后台字典类型表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    PageResult<SysDictResponseDto> getSysDictByNextPage(Pager page, SysDictQueryEnum queryEnum);

    /**
     * 通过分页获取SysDict 后台字典类型表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     */
    PageResult<SysDictResponseDto> getSysDictByQueryPage(Pager page, SysDictQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}