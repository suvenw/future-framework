package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysPermissionQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultList;




/**
 * @ClassName: SysPermissionService.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:30
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限表 RPC业务接口逻辑实现类
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


public interface SysPermissionService {



    /**
     * 保存菜单权限表更新数据库和缓存的实现方法
     * @param sysPermissionRequestDto  SysPermissionRequestDto
     * @return
     */
    SysPermissionResponseDto saveSysPermission(SysPermissionRequestDto sysPermissionRequestDto);


     /**
     * 保存菜单权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysPermissionRequestDto  SysPermissionRequestDto
     * @return
     */
    SysPermissionResponseDto saveIdSysPermission(SysPermissionRequestDto sysPermissionRequestDto);


     /**
     * 保存菜单权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysPermission(Collection<SysPermissionRequestDto> entityList);

    /**
     * 保存菜单权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysPermission(Collection<SysPermissionRequestDto> entityList, int batchSize);

    /**
     * 保存菜单权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysPermission(Collection<SysPermissionRequestDto> entityList, int batchSize);



    /**
     * 更新菜单权限表同时更新数据库和缓存的实现方法
     * @param sysPermissionRequestDto  SysPermissionRequestDto
     * @return
     */
    boolean updateSysPermission (SysPermissionRequestDto sysPermissionRequestDto);

    /**
     * 更新菜单权限表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysPermissionRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysPermissionIds
     * @return
     */
    int delSysPermissionByIds(List<Long>  sysPermissionIds);


    /**
     * 通过主键ID更新对象菜单权限表实现缓存和数据库更新的方法
     * @param  sysPermissionId
     * @return
     */
        SysPermissionResponseDto getSysPermissionById(long sysPermissionId);

    /**
     * 通过参数limit0,1获取对象菜单权限表的查询方法
     * @param  queryEnum
     * @return
     */
    SysPermissionResponseDto getSysPermissionByOne( SysPermissionQueryEnum queryEnum,SysPermissionRequestDto sysPermissionRequestDto);


    /**
    * 通过分页和枚举条件获取SysPermission信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * @date 2022-02-28 16:10:30
    */
    List<SysPermissionResponseDto> getSysPermissionListByQuery(Object  paramObject, SysPermissionQueryEnum queryEnum);


    /**
     * 通过分页获取SysPermission信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    List<SysPermissionResponseDto> getSysPermissionListByPage(Pager page, SysPermissionQueryEnum queryEnum);




    /**
     * 通过分页获取SysPermission 菜单权限表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    ResponseResultList<SysPermissionResponseDto> getSysPermissionByNextPage(Pager page, SysPermissionQueryEnum queryEnum);

    /**
     * 通过分页获取SysPermission 菜单权限表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     */
    ResponseResultList<SysPermissionResponseDto> getSysPermissionByQueryPage(Pager page, SysPermissionQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);


    List<SysPermissionResponseDto> getSysPermissionList(SysPermissionRequestDto sysPermissionRequestDto);

    List<SysPermissionResponseDto> queryByUser(long userId);


    List<SysPermissionResponseDto> getAuthListByType(Integer menuType);
}