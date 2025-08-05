package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysDepartRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysDepartRolePermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartRolePermissionQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;




/**
 * @ClassName: SysDepartRolePermissionService.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:13:36
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色权限表 RPC业务接口逻辑实现类
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


public interface SysDepartRolePermissionService {



    /**
     * 保存部门角色权限表更新数据库和缓存的实现方法
     * @param sysDepartRolePermissionRequestDto  SysDepartRolePermissionRequestDto
     * @return
     */
    SysDepartRolePermissionResponseDto saveSysDepartRolePermission(SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto);


     /**
     * 保存部门角色权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDepartRolePermissionRequestDto  SysDepartRolePermissionRequestDto
     * @return
     */
    SysDepartRolePermissionResponseDto saveIdSysDepartRolePermission(SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto);


     /**
     * 保存部门角色权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysDepartRolePermission(Collection<SysDepartRolePermissionRequestDto> entityList);

    /**
     * 保存部门角色权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDepartRolePermission(Collection<SysDepartRolePermissionRequestDto> entityList, int batchSize);

    /**
     * 保存部门角色权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDepartRolePermission(Collection<SysDepartRolePermissionRequestDto> entityList, int batchSize);



    /**
     * 更新部门角色权限表同时更新数据库和缓存的实现方法
     * @param sysDepartRolePermissionRequestDto  SysDepartRolePermissionRequestDto
     * @return
     */
    boolean updateSysDepartRolePermission (SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto);

    /**
     * 更新部门角色权限表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDepartRolePermissionRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysDepartRolePermissionIds
     * @return
     */
    int delSysDepartRolePermissionByIds(List<Long>  sysDepartRolePermissionIds);


    /**
     * 通过主键ID更新对象部门角色权限表实现缓存和数据库更新的方法
     * @param  sysDepartRolePermissionId
     * @return
     */
        SysDepartRolePermissionResponseDto getSysDepartRolePermissionById(long sysDepartRolePermissionId);

    /**
     * 通过参数limit0,1获取对象部门角色权限表的查询方法
     * @param  queryEnum
     * @return
     */
    SysDepartRolePermissionResponseDto getSysDepartRolePermissionByOne( SysDepartRolePermissionQueryEnum queryEnum,SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto);


    /**
    * 通过分页和枚举条件获取SysDepartRolePermission信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * @date 2022-02-28 16:13:36
    */
    List<SysDepartRolePermissionResponseDto> getSysDepartRolePermissionListByQuery(Object  paramObject, SysDepartRolePermissionQueryEnum queryEnum);


    /**
     * 通过分页获取SysDepartRolePermission信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    List<SysDepartRolePermissionResponseDto> getSysDepartRolePermissionListByPage(Pager page, SysDepartRolePermissionQueryEnum queryEnum);




    /**
     * 通过分页获取SysDepartRolePermission 部门角色权限表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    ResponseResultPageVo<SysDepartRolePermissionResponseDto> getSysDepartRolePermissionByNextPage(Pager page, SysDepartRolePermissionQueryEnum queryEnum);

    /**
     * 通过分页获取SysDepartRolePermission 部门角色权限表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     */
    ResponseResultPageVo<SysDepartRolePermissionResponseDto> getSysDepartRolePermissionByQueryPage(Pager page, SysDepartRolePermissionQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}