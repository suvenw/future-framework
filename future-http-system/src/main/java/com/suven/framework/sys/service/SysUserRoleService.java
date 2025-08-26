package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;
import java.util.Set;


import com.suven.framework.sys.dto.request.SysUserRoleRequestDto;
import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;
import com.suven.framework.sys.dto.enums.SysUserRoleQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;




/**
 * ClassName: SysUserRoleService.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:11:27
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 RPC业务接口逻辑实现类
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


public interface SysUserRoleService {



    /**
     * 保存用户角色关系表更新数据库和缓存的实现方法
     * @param sysUserRoleRequestDto  SysUserRoleRequestDto
     * @return
     */
    SysUserRoleResponseDto saveSysUserRole(SysUserRoleRequestDto sysUserRoleRequestDto);


     /**
     * 保存用户角色关系表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysUserRoleRequestDto  SysUserRoleRequestDto
     * @return
     */
    SysUserRoleResponseDto saveIdSysUserRole(SysUserRoleRequestDto sysUserRoleRequestDto);


     /**
     * 保存用户角色关系表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysUserRole(Collection<SysUserRoleRequestDto> entityList);

    /**
     * 保存用户角色关系表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysUserRole(Collection<SysUserRoleRequestDto> entityList, int batchSize);

    /**
     * 保存用户角色关系表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysUserRole(Collection<SysUserRoleRequestDto> entityList, int batchSize);



    /**
     * 更新用户角色关系表同时更新数据库和缓存的实现方法
     * @param sysUserRoleRequestDto  SysUserRoleRequestDto
     * @return
     */
    boolean updateSysUserRole (SysUserRoleRequestDto sysUserRoleRequestDto);

    /**
     * 更新用户角色关系表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysUserRoleRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysUserRoleIds
     * @return
     */
    int delSysUserRoleByIds(List<Long>  sysUserRoleIds);


    /**
     * 通过主键ID更新对象用户角色关系表实现缓存和数据库更新的方法
     * @param  sysUserRoleId
     * @return
     */
        SysUserRoleResponseDto getSysUserRoleById(long sysUserRoleId);

    /**
     * 通过参数limit0,1获取对象用户角色关系表的查询方法
     * @param  queryEnum
     * @return
     */
    SysUserRoleResponseDto getSysUserRoleByOne( SysUserRoleQueryEnum queryEnum,SysUserRoleRequestDto sysUserRoleRequestDto);


    /**
    * 通过分页和枚举条件获取SysUserRole信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * date 2022-02-28 16:11:27
    */
    List<SysUserRoleResponseDto> getSysUserRoleListByQuery(Object  paramObject, SysUserRoleQueryEnum queryEnum);


    /**
     * 通过分页获取SysUserRole信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:11:27
     */
    List<SysUserRoleResponseDto> getSysUserRoleListByPage(Pager page, SysUserRoleQueryEnum queryEnum);




    /**
     * 通过分页获取SysUserRole 用户角色关系表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:11:27
     */
    PageResult<SysUserRoleResponseDto> getSysUserRoleByNextPage(Pager page, SysUserRoleQueryEnum queryEnum);

    /**
     * 通过分页获取SysUserRole 用户角色关系表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * date 2022-02-28 16:11:27
     */
    PageResult<SysUserRoleResponseDto> getSysUserRoleByQueryPage(Pager page, SysUserRoleQueryEnum queryEnum);



    /**
     * 为指定角色批量添加用户关系
     * @param roleId
     * @param userIdList
     * @return
     */
     boolean addSysUserRole(long roleId, List<Long> userIdList);


    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

    Set<String> getSysPermissionListByUserId(long id);

    void editRole(long id, List<Long> roleIds);

    void deleteAll(Long id);
}