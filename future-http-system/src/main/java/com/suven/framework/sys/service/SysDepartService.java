package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.PageResult;




/**
 * ClassName: SysDepartService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:13:31
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 组织机构表 RPC业务接口逻辑实现类
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


public interface SysDepartService {



    /**
     * 保存组织机构表更新数据库和缓存的实现方法
     * @param sysDepartRequestDto  SysDepartRequestDto
     * @return
     */
    SysDepartResponseDto saveSysDepart(SysDepartRequestDto sysDepartRequestDto);


     /**
     * 保存组织机构表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDepartRequestDto  SysDepartRequestDto
     * @return
     */
    SysDepartResponseDto saveIdSysDepart(SysDepartRequestDto sysDepartRequestDto);


     /**
     * 保存组织机构表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysDepart(Collection<SysDepartRequestDto> entityList);

    /**
     * 保存组织机构表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDepart(Collection<SysDepartRequestDto> entityList, int batchSize);

    /**
     * 保存组织机构表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDepart(Collection<SysDepartRequestDto> entityList, int batchSize);



    /**
     * 更新组织机构表同时更新数据库和缓存的实现方法
     * @param sysDepartRequestDto  SysDepartRequestDto
     * @return
     */
    boolean updateSysDepart (SysDepartRequestDto sysDepartRequestDto);

    /**
     * 更新组织机构表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDepartRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysDepartIds
     * @return
     */
    int delSysDepartByIds(List<Long>  sysDepartIds);


    /**
     * 通过主键ID更新对象组织机构表实现缓存和数据库更新的方法
     * @param  sysDepartId
     * @return
     */
        SysDepartResponseDto getSysDepartById(long sysDepartId);

    /**
     * 通过参数limit0,1获取对象组织机构表的查询方法
     * @param  queryEnum
     * @return
     */
    SysDepartResponseDto getSysDepartByOne( SysDepartQueryEnum queryEnum,SysDepartRequestDto sysDepartRequestDto);


    /**
    * 通过分页和枚举条件获取SysDepart信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:13:31
    */
    List<SysDepartResponseDto> getSysDepartListByQuery(Object  paramObject, SysDepartQueryEnum queryEnum);


    /**
     * 通过分页获取SysDepart信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:13:31
     */
    List<SysDepartResponseDto> getSysDepartListByPage(Pager page, SysDepartQueryEnum queryEnum);




    /**
     * 通过分页获取SysDepart 组织机构表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:13:31
     */
    PageResult<SysDepartResponseDto> getSysDepartByNextPage(Pager page, SysDepartQueryEnum queryEnum);

    /**
     * 通过分页获取SysDepart 组织机构表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:13:31
     */
    PageResult<SysDepartResponseDto> getSysDepartByQueryPage(Pager page, SysDepartQueryEnum queryEnum);



    /**
     * 通过分页获取SysDepart信息列表,实现查找缓存和数据库的方法,并且查询总页数
     * @param idList Collection<Long>
     * @return
     * @author suven
     * date 2022-02-28 16:13:31
     */
    List<SysDepartResponseDto> getSysDepartByIdList(Collection<Long> idList);


    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

    List<SysDepartResponseDto> getList();
}