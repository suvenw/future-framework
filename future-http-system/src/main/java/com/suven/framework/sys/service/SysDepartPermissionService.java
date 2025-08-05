package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysDepartPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysDepartPermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartPermissionQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;




/**
 * ClassName: SysDepartPermissionService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:27
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门权限表 RPC业务接口逻辑实现类
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


public interface SysDepartPermissionService {



    /**
     * 保存部门权限表更新数据库和缓存的实现方法
     * @param sysDepartPermissionRequestDto  SysDepartPermissionRequestDto
     * @return
     */
    SysDepartPermissionResponseDto saveSysDepartPermission(SysDepartPermissionRequestDto sysDepartPermissionRequestDto);


     /**
     * 保存部门权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDepartPermissionRequestDto  SysDepartPermissionRequestDto
     * @return
     */
    SysDepartPermissionResponseDto saveIdSysDepartPermission(SysDepartPermissionRequestDto sysDepartPermissionRequestDto);


     /**
     * 保存部门权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysDepartPermission(Collection<SysDepartPermissionRequestDto> entityList);

    /**
     * 保存部门权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDepartPermission(Collection<SysDepartPermissionRequestDto> entityList, int batchSize);

    /**
     * 保存部门权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDepartPermission(Collection<SysDepartPermissionRequestDto> entityList, int batchSize);



    /**
     * 更新部门权限表同时更新数据库和缓存的实现方法
     * @param sysDepartPermissionRequestDto  SysDepartPermissionRequestDto
     * @return
     */
    boolean updateSysDepartPermission (SysDepartPermissionRequestDto sysDepartPermissionRequestDto);

    /**
     * 更新部门权限表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDepartPermissionRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysDepartPermissionIds
     * @return
     */
    int delSysDepartPermissionByIds(List<Long>  sysDepartPermissionIds);


    /**
     * 通过主键ID更新对象部门权限表实现缓存和数据库更新的方法
     * @param  sysDepartPermissionId
     * @return
     */
        SysDepartPermissionResponseDto getSysDepartPermissionById(long sysDepartPermissionId);

    /**
     * 通过参数limit0,1获取对象部门权限表的查询方法
     * @param  queryEnum
     * @return
     */
    SysDepartPermissionResponseDto getSysDepartPermissionByOne( SysDepartPermissionQueryEnum queryEnum,SysDepartPermissionRequestDto sysDepartPermissionRequestDto);


    /**
    * 通过分页和枚举条件获取SysDepartPermission信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:14:27
    */
    List<SysDepartPermissionResponseDto> getSysDepartPermissionListByQuery(Object  paramObject, SysDepartPermissionQueryEnum queryEnum);


    /**
     * 通过分页获取SysDepartPermission信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:27
     */
    List<SysDepartPermissionResponseDto> getSysDepartPermissionListByPage(Pager page, SysDepartPermissionQueryEnum queryEnum);




    /**
     * 通过分页获取SysDepartPermission 部门权限表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:27
     */
    ResponseResultPageVo<SysDepartPermissionResponseDto> getSysDepartPermissionByNextPage(Pager page, SysDepartPermissionQueryEnum queryEnum);

    /**
     * 通过分页获取SysDepartPermission 部门权限表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:14:27
     */
    ResponseResultPageVo<SysDepartPermissionResponseDto> getSysDepartPermissionByQueryPage(Pager page, SysDepartPermissionQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}