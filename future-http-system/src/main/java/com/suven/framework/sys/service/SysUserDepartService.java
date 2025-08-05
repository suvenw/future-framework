package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.dto.enums.SysUserDepartQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultList;




/**
 * @ClassName: SysUserDepartService.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:14
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户部门关系表 RPC业务接口逻辑实现类
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


public interface SysUserDepartService {



    /**
     * 保存用户部门关系表更新数据库和缓存的实现方法
     * @param sysUserDepartRequestDto  SysUserDepartRequestDto
     * @return
     */
    SysUserDepartResponseDto saveSysUserDepart(SysUserDepartRequestDto sysUserDepartRequestDto);


     /**
     * 保存用户部门关系表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysUserDepartRequestDto  SysUserDepartRequestDto
     * @return
     */
    SysUserDepartResponseDto saveIdSysUserDepart(SysUserDepartRequestDto sysUserDepartRequestDto);


     /**
     * 保存用户部门关系表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysUserDepart(Collection<SysUserDepartRequestDto> entityList);

    /**
     * 保存用户部门关系表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysUserDepart(Collection<SysUserDepartRequestDto> entityList, int batchSize);

    /**
     * 保存用户部门关系表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysUserDepart(Collection<SysUserDepartRequestDto> entityList, int batchSize);



    /**
     * 更新用户部门关系表同时更新数据库和缓存的实现方法
     * @param sysUserDepartRequestDto  SysUserDepartRequestDto
     * @return
     */
    boolean updateSysUserDepart (SysUserDepartRequestDto sysUserDepartRequestDto);

    /**
     * 更新用户部门关系表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysUserDepartRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysUserDepartIds
     * @return
     */
    int delSysUserDepartByIds(List<Long>  sysUserDepartIds);


    /**
     * 通过主键ID更新对象用户部门关系表实现缓存和数据库更新的方法
     * @param  sysUserDepartId
     * @return
     */
        SysUserDepartResponseDto getSysUserDepartById(long sysUserDepartId);

    /**
     * 通过参数limit0,1获取对象用户部门关系表的查询方法
     * @param  queryEnum
     * @return
     */
    SysUserDepartResponseDto getSysUserDepartByOne( SysUserDepartQueryEnum queryEnum,SysUserDepartRequestDto sysUserDepartRequestDto);


    /**
    * 通过分页和枚举条件获取SysUserDepart信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * @date 2022-02-28 16:14:14
    */
    List<SysUserDepartResponseDto> getSysUserDepartListByQuery(Object  paramObject, SysUserDepartQueryEnum queryEnum);


    /**
     * 通过分页获取SysUserDepart信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:14:14
     */
    List<SysUserDepartResponseDto> getSysUserDepartListByPage(Pager page, SysUserDepartQueryEnum queryEnum);




    /**
     * 通过分页获取SysUserDepart 用户部门关系表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:14:14
     */
    ResponseResultList<SysUserDepartResponseDto> getSysUserDepartByNextPage(Pager page, SysUserDepartQueryEnum queryEnum);

    /**
     * 通过分页获取SysUserDepart 用户部门关系表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:14:14
     */
    ResponseResultList<SysUserDepartResponseDto> getSysUserDepartByQueryPage(Pager page, SysUserDepartQueryEnum queryEnum);



    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

    Boolean deleteUserInDepart(long depId,List<Long> idList);

    Boolean editSysDepartWithUser(SysUserDepartRequestDto dto);
    /**
     * 查询用户角色信息by用户id
     * @param userId
     * @return
     */
    List<SysUserDepartResponseDto> getListByUserId(long userId);


}