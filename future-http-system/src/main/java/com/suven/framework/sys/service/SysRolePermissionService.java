package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.dto.request.SysRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysRolePermissionQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;




/**
 * ClassName: SysRolePermissionService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:49
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色权限表 RPC业务接口逻辑实现类
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


public interface SysRolePermissionService {



    /**
     * 保存角色权限表更新数据库和缓存的实现方法
     * @param sysRolePermissionRequestDto  SysRolePermissionRequestDto
     * @return
     */
    SysRolePermissionResponseDto saveSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto);


     /**
     * 保存角色权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysRolePermissionRequestDto  SysRolePermissionRequestDto
     * @return
     */
    SysRolePermissionResponseDto saveIdSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto);


     /**
     * 保存角色权限表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysRolePermission(Collection<SysRolePermissionRequestDto> entityList);

    /**
     * 保存角色权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysRolePermission(Collection<SysRolePermissionRequestDto> entityList, int batchSize);

    /**
     * 保存角色权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysRolePermission(Collection<SysRolePermissionRequestDto> entityList, int batchSize);



    /**
     * 更新角色权限表同时更新数据库和缓存的实现方法
     * @param sysRolePermissionRequestDto  SysRolePermissionRequestDto
     * @return
     */
    boolean updateSysRolePermission (SysRolePermissionRequestDto sysRolePermissionRequestDto);

    /**
     * 更新角色权限表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysRolePermissionRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysRolePermissionIds
     * @return
     */
    int delSysRolePermissionByIds(List<Long>  sysRolePermissionIds);


    /**
     * 通过主键ID更新对象角色权限表实现缓存和数据库更新的方法
     * @param  sysRolePermissionId
     * @return
     */
        SysRolePermissionResponseDto getSysRolePermissionById(long sysRolePermissionId);

    /**
     * 通过参数limit0,1获取对象角色权限表的查询方法
     * @param  queryEnum
     * @return
     */
    SysRolePermissionResponseDto getSysRolePermissionByOne( SysRolePermissionQueryEnum queryEnum,SysRolePermissionRequestDto sysRolePermissionRequestDto);


    /**
    * 通过分页和枚举条件获取SysRolePermission信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:10:49
    */
    List<SysRolePermissionResponseDto> getSysRolePermissionListByQuery(Object  paramObject, SysRolePermissionQueryEnum queryEnum);


    /**
     * 通过分页获取SysRolePermission信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:49
     */
    List<SysRolePermissionResponseDto> getSysRolePermissionListByPage(Pager page, SysRolePermissionQueryEnum queryEnum);




    /**
     * 通过分页获取SysRolePermission 角色权限表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:49
     */
    ResponseResultPageVo<SysRolePermissionResponseDto> getSysRolePermissionByNextPage(Pager page, SysRolePermissionQueryEnum queryEnum);

    /**
     * 通过分页获取SysRolePermission 角色权限表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:10:49
     */
    ResponseResultPageVo<SysRolePermissionResponseDto> getSysRolePermissionByQueryPage(Pager page, SysRolePermissionQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);






    IResultCodeEnum saveRolePermission(long roleId, List<Long> permissionIds, List<Long> lastpermissionIds);

    List<SysRolePermissionResponseDto> queryRolePermissionByRoleId(long roleId);

}