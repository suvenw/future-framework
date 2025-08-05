package com.suven.framework.sys.service;

import java.util.Collection;
import java.util.List;
import java.io.InputStream;


import com.suven.framework.sys.dto.request.SysDepartRoleUserRequestDto;
import com.suven.framework.sys.dto.response.SysDepartRoleUserResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartRoleUserQueryEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;




/**
 * @ClassName: SysDepartRoleUserService.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:21
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 RPC业务接口逻辑实现类
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


public interface SysDepartRoleUserService {



    /**
     * 保存部门角色用户表更新数据库和缓存的实现方法
     * @param sysDepartRoleUserRequestDto  SysDepartRoleUserRequestDto
     * @return
     */
    SysDepartRoleUserResponseDto saveSysDepartRoleUser(SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto);


     /**
     * 保存部门角色用户表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @param sysDepartRoleUserRequestDto  SysDepartRoleUserRequestDto
     * @return
     */
    SysDepartRoleUserResponseDto saveIdSysDepartRoleUser(SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto);


     /**
     * 保存部门角色用户表同时更新数据库和缓存的实现方法,同时保存Id主键到对象中
     * @return
     */
    boolean saveBatchIdSysDepartRoleUser(Collection<SysDepartRoleUserRequestDto> entityList);

    /**
     * 保存部门角色用户表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDepartRoleUser(Collection<SysDepartRoleUserRequestDto> entityList, int batchSize);

    /**
     * 保存部门角色用户表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDepartRoleUser(Collection<SysDepartRoleUserRequestDto> entityList, int batchSize);



    /**
     * 更新部门角色用户表同时更新数据库和缓存的实现方法
     * @param sysDepartRoleUserRequestDto  SysDepartRoleUserRequestDto
     * @return
     */
    boolean updateSysDepartRoleUser (SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto);

    /**
     * 更新部门角色用户表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDepartRoleUserRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysDepartRoleUserIds
     * @return
     */
    int delSysDepartRoleUserByIds(List<Long>  sysDepartRoleUserIds);


    /**
     * 通过主键ID更新对象部门角色用户表实现缓存和数据库更新的方法
     * @param  sysDepartRoleUserId
     * @return
     */
        SysDepartRoleUserResponseDto getSysDepartRoleUserById(long sysDepartRoleUserId);

    /**
     * 通过参数limit0,1获取对象部门角色用户表的查询方法
     * @param  queryEnum
     * @return
     */
    SysDepartRoleUserResponseDto getSysDepartRoleUserByOne( SysDepartRoleUserQueryEnum queryEnum,SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto);


    /**
    * 通过分页和枚举条件获取SysDepartRoleUser信息实现查找缓存和数据库的方法
    * @param paramObject Object
    * @return
    * @author suven
    * @date 2022-02-28 16:14:21
    */
    List<SysDepartRoleUserResponseDto> getSysDepartRoleUserListByQuery(Object  paramObject, SysDepartRoleUserQueryEnum queryEnum);


    /**
     * 通过分页获取SysDepartRoleUser信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:14:21
     */
    List<SysDepartRoleUserResponseDto> getSysDepartRoleUserListByPage(Pager page, SysDepartRoleUserQueryEnum queryEnum);




    /**
     * 通过分页获取SysDepartRoleUser 部门角色用户表信息实现查找缓存和数据库的方法,包括查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:14:21
     */
    ResponseResultPageVo<SysDepartRoleUserResponseDto> getSysDepartRoleUserByNextPage(Pager page, SysDepartRoleUserQueryEnum queryEnum);

    /**
     * 通过分页获取SysDepartRoleUser 部门角色用户表信息实现查找缓存和数据库的方法,不查总页数
     * @param page BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:14:21
     */
    ResponseResultPageVo<SysDepartRoleUserResponseDto> getSysDepartRoleUserByQueryPage(Pager page, SysDepartRoleUserQueryEnum queryEnum);






    /**
    * 通过上传excel 保存数据到数据库
    * @param initialStream
    * @return
    */
    public boolean saveData(InputStream initialStream);

}